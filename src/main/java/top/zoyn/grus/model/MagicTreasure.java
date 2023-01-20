package top.zoyn.grus.model;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * 代表一个法器
 *
 * @author Zoyn
 */
public abstract class MagicTreasure {

    private ItemStack itemStack;

    public MagicTreasure() {
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public abstract void onInteract(Player player);

    public abstract void onAttack(LivingEntity attack, LivingEntity entity);

}
