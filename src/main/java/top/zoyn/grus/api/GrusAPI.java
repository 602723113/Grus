package top.zoyn.grus.api;

import org.bukkit.Location;
import top.zoyn.grus.Grus;
import top.zoyn.grus.manager.BoundaryExpDropManager;
import top.zoyn.grus.manager.BoundaryManager;
import top.zoyn.grus.manager.LanguageManager;
import top.zoyn.grus.manager.LingemManager;
import top.zoyn.grus.model.ChiOrb;

public class GrusAPI {

    /**
     * 获取 语言管理器
     *
     * @return {@link LanguageManager}
     */
    public static LanguageManager getLanguageManager() {
        return Grus.getInstance().getLanguageManager();
    }

    /**
     * 获取 境界管理器
     *
     * @return {@link BoundaryManager}
     */
    public static BoundaryManager getBoundaryManager() {
        return Grus.getInstance().getBoundaryManager();
    }

    /**
     * 获取 境界经验值掉落管理器
     *
     * @return {@link BoundaryExpDropManager}
     */
    public static BoundaryExpDropManager getBoundaryExpDropManager() {
        return Grus.getInstance().getBoundaryExpDropManager();
    }


    /**
     * 获取 灵根管理器
     *
     * @return {@link LingemManager}
     */
    public static LingemManager getLingemManager() {
        return Grus.getInstance().getLingemManager();
    }

    /**
     * 在给定的坐标生成一个真气实体
     *
     * @param spawnLocation 给定的坐标
     * @param exp           真气所携带的修为
     * @return {@link ChiOrb}
     */
    public static ChiOrb spawnChiOrb(Location spawnLocation, int exp) {
        return new ChiOrb(spawnLocation, exp);
    }

}
