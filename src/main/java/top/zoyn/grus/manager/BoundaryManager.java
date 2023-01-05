package top.zoyn.grus.manager;

import com.google.common.collect.Maps;

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
    private Map<UUID, Double> defaultBoundary = Maps.newHashMap();

}
