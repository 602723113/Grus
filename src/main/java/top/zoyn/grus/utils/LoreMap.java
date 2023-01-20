package top.zoyn.grus.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 只读Lore对象存储Map
 * <p>
 * 来源于: https://github.com/TabooLib/taboolib/blob/master/common-5/src/main/java/taboolib/common5/LoreMap.java
 *
 * @author YiMiner
 **/
public class LoreMap<T> {

    private final TrieNode root = new TrieNode();

    private final boolean ignorePrefix;
    private final boolean ignoreSpace;
    private final boolean ignoreColor;

    /**
     * 初始化一个LoreMap
     *
     * @param ignoreSpace  是否无视空格
     * @param ignoreColor  是否无视颜色
     * @param ignorePrefix 是否无视前缀
     **/
    public LoreMap(boolean ignoreSpace, boolean ignoreColor, boolean ignorePrefix) {
        this.ignoreSpace = ignoreSpace;
        this.ignoreColor = ignoreColor;
        this.ignorePrefix = ignorePrefix;
    }

    /**
     * 初始化一个LoreMap, 匹配lore前缀, 无视颜色和空格
     **/
    public LoreMap() {
        this(true, true, false);
    }

    /**
     * 向LoreMap中放入lore和对应的对象
     *
     * @param lore  物品lore
     * @param value 要放入的对象
     **/
    public void put(String lore, T value) {
        lore = lore.replaceAll("&", "§");
        if (ignoreSpace) {
            lore = lore.replaceAll("\\s", "");
        }
        if (ignoreColor) {
            lore = lore.replaceAll("§.", "");
        }
        int depth = 0;
        TrieNode current = root;
        while (depth < lore.length()) {
            LoreChar c = new LoreChar(lore.charAt(depth));
            if (current.child.containsKey(c)) {
                current = current.child.get(c);
            } else {
                TrieNode node = new TrieNode();
                node.depth++;
                node.pre = current;
                current.child.put(c, node);
                current = node;
            }
            if (depth == lore.length() - 1) {
                current.obj = value;
            }
            depth++;
        }
    }

    /**
     * 查询lore对应的对象
     *
     * @return 查到的对象. 无则返回null
     **/
    public T get(String lore) {
        int depth = 0;
        if (ignoreSpace) {
            lore = lore.replaceAll("\\s", "");
        }
        if (ignoreColor) {
            lore = lore.replaceAll("§.", "");
        }
        TrieNode current = root;
        if (ignorePrefix) {
            while (depth < lore.length()) {
                if (root.child.containsKey(new LoreChar(lore.charAt(depth)))) {
                    break;
                }
                depth++;
            }
        }
        while (depth < lore.length()) {
            LoreChar c = new LoreChar(lore.charAt(depth));
            TrieNode node = current.child.get(c);
            if (node == null) {
                return null;
            }
            if (node.obj != null) {
                return node.obj;
            }
            current = node;
            depth++;
        }
        return null;
    }

    public MatchResult<T> getMatchResult(String lore) {
        int depth = 0;
        if (ignoreSpace) {
            lore = lore.replaceAll("\\s", "");
        }
        if (ignoreColor) {
            lore = lore.replaceAll("§.", "");
        }
        if (ignorePrefix) {
            while (depth < lore.length()) {
                if (root.child.containsKey(new LoreChar(lore.charAt(depth)))) {
                    break;
                }
                depth++;
            }
        }
        TrieNode current = root;
        while (depth < lore.length()) {
            LoreChar c = new LoreChar(lore.charAt(depth));
            TrieNode node = current.child.get(c);
            if (node == null) {
                return null;
            }
            if (node.obj != null) {
                return new MatchResult<>(depth < lore.length() - 1 ? lore.substring(depth + 1) : null, node.obj);
            }
            current = node;
            depth++;
        }
        return null;
    }

    public void clear() {
        root.child.clear();
    }

    public class TrieNode {
        final ConcurrentHashMap<LoreChar, TrieNode> child = new ConcurrentHashMap<>();
        TrieNode pre = null;
        T obj = null;
        int depth = 0;
    }

    public static class MatchResult<T> {
        public final String remain;
        public final T obj;

        public MatchResult(String remain, T obj) {
            this.remain = remain;
            this.obj = obj;
        }
    }

    public static class LoreChar {

        private final char c;

        public LoreChar(char c) {
            this.c = c;
        }

        public char get() {
            return c;
        }

        @Override
        public int hashCode() {
            return c;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof LoreChar) && ((LoreChar) o).c == c;
        }
    }

}
