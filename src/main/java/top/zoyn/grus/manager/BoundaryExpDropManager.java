package top.zoyn.grus.manager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.GrusAPI;
import top.zoyn.grus.model.ChiOrb;
import top.zoyn.grus.utils.Logger;

import java.util.Map;

public class BoundaryExpDropManager {

    private Map<String, Double> defaultExpDrop = Maps.newHashMap();

    private ExpDropMode MODE;
    private PacketListener chiOrbListener;

    public BoundaryExpDropManager() {
        // 真气球监听
        chiOrbListener = new PacketAdapter(Grus.getInstance(), PacketType.Play.Server.COLLECT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                int collectedEntity = packet.getIntegers().read(0);

                // 经验球识别
                Entity entity = Grus.getInstance().getProtocolManager().getEntityFromID(event.getPlayer().getWorld(), collectedEntity);
                if (entity instanceof ExperienceOrb) {
                    ExperienceOrb expOrb = (ExperienceOrb) entity;
                    // 真气球识别
                    if (ChiOrb.getChiOrbName().equalsIgnoreCase(expOrb.getCustomName())) {
                        GrusAPI.getBoundaryManager().addBoundaryExp(player, expOrb.getExperience());
                        player.sendMessage(I18N.YOU_HAVE_GAINED_EXP.getMessage().replace("%exp%", "" + expOrb.getExperience()));
                    }
                }
            }
        };
        reload();
    }

    public void reload() {
        defaultExpDrop.clear();
        Grus.getInstance().getProtocolManager().removePacketListener(chiOrbListener);


        ConfigurationSection config = Grus.getInstance().getConfig().getConfigurationSection("boundary-exp-drop-settings");
        MODE = ExpDropMode.valueOf(config.getString("mode").toUpperCase());
        // config 预设数据
        config.getConfigurationSection("monster")
                .getKeys(false)
                .forEach(section -> defaultExpDrop.put(section.toUpperCase().replace("-", "_"), config.getDouble("monster." + section)));

        if (MODE.equals(ExpDropMode.ORB)) {
            // spigot.yml 经验球掉落合并 设置判断
            if (Grus.getInstance().getServer().spigot().getConfig().getInt("world-settings.default.merge-radius.exp") != -1) {
                Logger.error(I18N.CONSOLE_WRONG_CHI_ORB_SETTING.getMessage());
                MODE = ExpDropMode.VANILLA;
            } else {
                // 真气球获取监听
                Grus.getInstance().getProtocolManager().addPacketListener(chiOrbListener);
            }
        }

        Logger.info(I18N.CONSOLE_LOAD_BOUNDARY_EXP_DROP.getMessage().replace("%mode%", MODE.toString()));
    }

    public double getExpDropByEntityType(EntityType type) {
        if (type == null) {
            return 0;
        }
        return defaultExpDrop.getOrDefault(type.toString(), 0D);
    }

    public ExpDropMode getExpDropMode() {
        return MODE;
    }

    /**
     * 经验(修为)掉落模式
     */
    public enum ExpDropMode {
        VANILLA,
        ORB,
        NONE;
    }
}
