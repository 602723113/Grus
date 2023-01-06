package top.zoyn.grus.manager;

import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.utils.Logger;

import java.util.Map;

public class BoundaryExpDropManager {

    private Map<String, Double> defaultExpDrop = Maps.newHashMap();

    private ExpDropMode MODE;

    public BoundaryExpDropManager() {
        ConfigurationSection config = Grus.getInstance().getConfig().getConfigurationSection("boundary-exp-drop-settings");

        MODE = ExpDropMode.valueOf(config.getString("mode").toUpperCase());
        // config 预设数据
        config.getConfigurationSection("monster")
                .getKeys(false)
                .forEach(section -> defaultExpDrop.put(section.toUpperCase().replace("-", "_"), config.getDouble("monster." + section)));

        Logger.info(I18N.CONSOLE_LOAD_BOUNDARY_EXP_DROP.getMessage());
    }

    public void reload() {
        defaultExpDrop.clear();

        ConfigurationSection config = Grus.getInstance().getConfig().getConfigurationSection("boundary-exp-drop-settings");

        MODE = ExpDropMode.valueOf(config.getString("mode").toUpperCase());
        // config 预设数据
        config.getConfigurationSection("monster")
                .getKeys(false)
                .forEach(section -> defaultExpDrop.put(section.toUpperCase().replace("-", "_"), config.getDouble("monster." + section)));

        Logger.info(I18N.CONSOLE_LOAD_BOUNDARY_EXP_DROP.getMessage());
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
     * 经验(灵力值)掉落模式
     */
    public enum ExpDropMode {
        VANILLA,
        NONE;
    }
}
