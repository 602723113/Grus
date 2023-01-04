package top.zoyn.grus.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zoyn
 */
public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder() {
        this(Material.STONE, 1, 0);
    }

    public ItemBuilder(Material material) {
        this(material, 1, 0);
    }

    public ItemBuilder(Material material, int amount, int damage) {
        this(material, amount, (short) damage);
    }

    public ItemBuilder(ItemStack itemStack) {
        this(itemStack.getType(), itemStack.getAmount(), itemStack.getDurability());
    }

    public ItemBuilder(Material material, int amount, short damage) {
        this.itemStack = new ItemStack(material, amount, damage);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder material(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder itemFlag(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder glow(boolean isGlow) {
        if (isGlow) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        } else {
            itemMeta.getEnchants().keySet().forEach(itemMeta::removeEnchant);
        }
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(lore.stream().map(s -> s.replace("&", "§")).collect(Collectors.toList()));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        itemMeta.setLore(Arrays.stream(lore).map(s -> s.replace("&", "§")).collect(Collectors.toList()));
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        itemMeta.setDisplayName(displayName.replace("&", "§"));
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        itemMeta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}
