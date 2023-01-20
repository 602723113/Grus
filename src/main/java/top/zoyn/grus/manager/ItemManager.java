package top.zoyn.grus.manager;

import com.google.common.collect.Maps;
import top.zoyn.grus.model.MagicTreasure;

import java.util.Map;

public class ItemManager {

    private final Map<String, MagicTreasure> treasures = Maps.newHashMap();

    public ItemManager() {
        reload();
    }

    public void reload() {

    }

    public void addItem(String key, MagicTreasure treasure) {

    }

    public void RemoveItem(String key) {

    }
}
