package top.zoyn.grus.model;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import top.zoyn.grus.Grus;

/**
 * 表示一个真气的实体
 *
 * @author Zoyn
 */
public class ChiOrb {

    private ExperienceOrb expOrb;

    public ChiOrb(Location spawnLocation, int exp) {
        expOrb = (ExperienceOrb) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.EXPERIENCE_ORB);
        expOrb.setCustomName(ChatColor.translateAlternateColorCodes('&', Grus.getInstance().getConfig().getString("boundary-exp-drop-settings.chi-orb-name")));
        expOrb.setCustomNameVisible(true);
        expOrb.setExperience(exp);
    }

    public ExperienceOrb getEntity() {
        return expOrb;
    }

    public Location getLocation() {
        return expOrb.getLocation();
    }

    public int getBoundaryExp() {
        return expOrb.getExperience();
    }

    public void setBoundaryExp(int exp) {
        expOrb.setExperience(exp);
    }

    public static String getChiOrbName() {
        return ChatColor.translateAlternateColorCodes('&', Grus.getInstance().getConfig().getString("chi-orb-name"));
    }
}
