package top.zoyn.grus.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.utils.ConfigurationUtils;
import top.zoyn.grus.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 玩家灵根管理器
 *
 * @author Zoyn
 * @since 2023/1/05
 */
public class LingemManager {

    /**
     * 玩家uid 对应 灵根数组
     */
    private Map<UUID, List<String>> playerLingem = Maps.newHashMap();
    private Map<String, Double> lingemChances = Maps.newHashMap();
    private File lingemFile;
    private File lingemFolder;
    private FileConfiguration lingemDataConfig;

    public LingemManager() {
        reload();
    }

    public void reload() {
        lingemChances.clear();
        playerLingem.clear();

        lingemFolder = new File(Grus.getInstance().getDataFolder(), "data");
        lingemFile = new File(lingemFolder, "Lingem-data.yml");
        // 灵根数据文件创建
        if (!lingemFile.exists()) {
            lingemFolder.mkdirs();
            try {
                lingemFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lingemDataConfig = ConfigurationUtils.loadYML(lingemFile);
        // 玩家数据
        ConfigurationSection data = lingemDataConfig.getConfigurationSection("data");
        if (data != null) {
            data.getKeys(false).forEach(uid -> playerLingem.put(UUID.fromString(uid), lingemDataConfig.getStringList("data." + uid)));
        }

        ConfigurationSection lingemConfig = Grus.getInstance().getConfig().getConfigurationSection("lingem-settings");
        // config 预设数据
        lingemConfig.getConfigurationSection("chances").getKeys(false).forEach(section -> lingemChances.put(section, lingemConfig.getDouble("chances." + section)));

        Logger.info(I18N.CONSOLE_LOAD_LINGEM.getMessage()
                .replace("%num%", "" + lingemChances.keySet().size())
                .replace("%content%", lingemChances.keySet().toString()));
    }

    public Map<UUID, List<String>> getPlayerLingem() {
        return playerLingem;
    }

    public Map<String, Double> getLingemChances() {
        return lingemChances;
    }

    /**
     * 获取玩家的灵根
     *
     * @return 灵根列表
     */
    public List<String> getPlayerLingem(OfflinePlayer player) {
        if (hasLingem(player)) {
            return playerLingem.get(player.getUniqueId());
        }
        return null;
    }

    /**
     * 判断一名玩家是否有灵根
     *
     * @param player 要判断的玩家
     * @return 如果有灵根则返回true
     */
    public boolean hasLingem(OfflinePlayer player) {
        if (playerLingem.containsKey(player.getUniqueId())) {
            return !playerLingem.get(player.getUniqueId()).isEmpty();
        }
        return false;
    }

    /**
     * 移除一名玩家的某个灵根
     *
     * @param player 指定的玩家
     * @param lingem 指定的灵根
     */
    public void removeLingem(OfflinePlayer player, String lingem) {
        if (hasLingem(player)) {
            playerLingem.get(player.getUniqueId()).remove(lingem);
        }
    }

    /**
     * 重置某一个玩家的所有灵根数据
     *
     * @param player 指定的玩家
     */
    public void resetLingem(OfflinePlayer player) {
        // 移除数据
        playerLingem.remove(player.getUniqueId());

        List<String> lingems = Lists.newArrayList();
        lingemChances.keySet().forEach(lingem -> {
            // 多重灵根判断
            if (Math.random() < lingemChances.get(lingem)) {
                lingems.add(lingem);
            }
        });
        if (lingems.size() == 0) {
            lingems.add(lingemChances.keySet()
                    .stream()
                    .findAny()
                    .get()
            );
        }
        playerLingem.put(player.getUniqueId(), lingems);
    }

    /**
     * 给指定的玩家增加一个灵根
     *
     * @param player 指定的玩家
     * @param lingem 指定的灵根
     */
    public void addLingem(OfflinePlayer player, String lingem) {
        if (hasLingem(player)) {
            playerLingem.get(player.getUniqueId()).add(lingem);
        } else {
            playerLingem.put(player.getUniqueId(), Lists.newArrayList(lingem));
        }
    }

    /**
     * 查询给定的灵根是否属于 config 中的灵根
     *
     * @param lingem 给定的灵根
     * @return 如果存在于 config 中则返回 true
     */
    public boolean hasLingemInDefault(String lingem) {
        return lingemChances.containsKey(lingem);
    }
}
