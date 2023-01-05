package top.zoyn.grus.manager;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

/**
 * 玩家灵根管理器
 *
 * @author Zoyn
 * @since 2023/1/05
 */
public class LingamManager {

    /**
     * 玩家uid 对应 灵根数组
     */
    private Map<UUID, String[]> playerAttribute = Maps.newHashMap();


}
