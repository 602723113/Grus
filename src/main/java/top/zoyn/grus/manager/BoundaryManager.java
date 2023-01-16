package top.zoyn.grus.manager;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.event.PlayerBoundaryExpChangeEvent;
import top.zoyn.grus.utils.ConfigurationUtils;
import top.zoyn.grus.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家境界管理器
 *
 * @author Zoyn
 * @since 2023/1/05
 */
public class BoundaryManager {

    /**
     * 玩家uid 对应 境界数值(经验值)
     */
    private Map<UUID, Double> playerBoundary = Maps.newHashMap();
    /**
     * 预设境界 来源于 config 当中
     */
    private Map<String, Double> defaultBoundary = Maps.newLinkedHashMap();
    /**
     * 预设境界的显示设定
     */
    private Map<String, String> boundaryDisplay = Maps.newHashMap();

    /**
     * 无境界时的显示名
     */
    private static String NO_BOUNDARY_DISPLAY;

    private File boundaryFile;
    private File boundaryFolder;
    private FileConfiguration boundaryDataConfig;

    public BoundaryManager() {
        reload();
    }

    public void reload() {
        defaultBoundary.clear();
        playerBoundary.clear();

        boundaryFolder = new File(Grus.getInstance().getDataFolder(), "data");
        boundaryFile = new File(boundaryFolder, "boundary-data.yml");
        // 玩家境界灵气数据文件创建
        if (!boundaryFile.exists()) {
            boundaryFolder.mkdirs();
            try {
                boundaryFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boundaryDataConfig = ConfigurationUtils.loadYML(boundaryFile);
        // 玩家数据
        ConfigurationSection data = boundaryDataConfig.getConfigurationSection("data");
        if (data != null) {
            data.getKeys(false).forEach(uid -> playerBoundary.put(UUID.fromString(uid), boundaryDataConfig.getDouble("data." + uid)));
        }

        ConfigurationSection boundaryConfig = Grus.getInstance().getConfig().getConfigurationSection("boundary-settings");
        // config 预设数据
        boundaryConfig.getConfigurationSection("level")
                .getKeys(false)
                .forEach(section -> defaultBoundary.put(section, boundaryConfig.getDouble("level." + section)));
        boundaryConfig.getConfigurationSection("display")
                .getKeys(false)
                .forEach(section -> boundaryDisplay.put(section, ChatColor.translateAlternateColorCodes('&', boundaryConfig.getString("display." + section))));

        // 无境界时的显示名
        NO_BOUNDARY_DISPLAY = ChatColor.translateAlternateColorCodes('&', boundaryConfig.getString("no-boundary-display"));

        Logger.info(I18N.CONSOLE_LOAD_BOUNDARY.getMessage()
                .replace("%num%", "" + defaultBoundary.keySet().size())
                .replace("%content%", defaultBoundary.keySet().toString()));
    }

    public void save() {
        for (Map.Entry<UUID, Double> entry : playerBoundary.entrySet()) {
            boundaryDataConfig.set("data." + entry.getKey(), entry.getValue());
        }
        // 保存数据
        ConfigurationUtils.saveYML(boundaryDataConfig, boundaryFile);
    }

    /**
     * 取得境界的展示名
     *
     * @param boundary 指定的境界
     * @return 如果无法找到对应的境界展示名则会返回 无境界时的展示名
     */
    public String getDisplayBoundary(String boundary) {
        return boundaryDisplay.getOrDefault(boundary, NO_BOUNDARY_DISPLAY);
    }

    /**
     * 获取玩家对应的境界
     *
     * @param player 指定的玩家
     * @return 玩家当前的境界
     */
    public String getPlayerBoundary(OfflinePlayer player) {
        String boundary = NO_BOUNDARY_DISPLAY;
        if (hasBoundary(player)) {
            double playerBoundaryExp = playerBoundary.get(player.getUniqueId());
            // 查找玩家对应的境界
            for (Map.Entry<String, Double> entry : defaultBoundary.entrySet()) {
                if (entry.getValue() <= playerBoundaryExp) {
                    boundary = entry.getKey();
                }
            }
        }
        return boundary;
    }

    /**
     * 获取玩家的修为值, 即修炼得到的经验值
     *
     * @param player 指定的玩家
     * @return 玩家灵力值
     */
    public double getPlayerBoundaryExp(OfflinePlayer player) {
        if (hasBoundary(player)) {
            return playerBoundary.get(player.getUniqueId());
        }
        return 0;
    }

    /**
     * 给指定的玩家增加指定的修为
     *
     * @param player 指定的玩家
     * @param exp    指定的修为
     */
    public void addBoundaryExp(OfflinePlayer player, double exp) {
        if (hasBoundary(player)) {
            double result = playerBoundary.get(player.getUniqueId()) + exp;
            if (result < 0) {
                result = 0;
            }
            PlayerBoundaryExpChangeEvent event = new PlayerBoundaryExpChangeEvent(playerBoundary.get(player.getUniqueId()), result, player.getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            playerBoundary.put(player.getUniqueId(), result);
        } else {
            PlayerBoundaryExpChangeEvent event = new PlayerBoundaryExpChangeEvent(0, exp, player.getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            playerBoundary.put(player.getUniqueId(), exp);
        }
    }

    /**
     * 移除指定的玩家指定的修为值
     *
     * @param player 指定的玩家
     * @param exp    指定的修为
     */
    public void removeBoundaryExp(OfflinePlayer player, double exp) {
        if (hasBoundary(player)) {
            double result = playerBoundary.get(player.getUniqueId()) - exp;
            if (result < 0) {
                result = 0;
            }
            PlayerBoundaryExpChangeEvent event = new PlayerBoundaryExpChangeEvent(playerBoundary.get(player.getUniqueId()), result, player.getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            playerBoundary.put(player.getUniqueId(), result);
        }
    }

    /**
     * 判断一名玩家是否有修为值
     *
     * @param player 指定的玩家
     * @return 若玩家已有修为则会返回 true
     */
    public boolean hasBoundary(OfflinePlayer player) {
        return playerBoundary.containsKey(player.getUniqueId());
    }


}
