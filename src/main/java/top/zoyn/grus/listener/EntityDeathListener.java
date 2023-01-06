package top.zoyn.grus.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.GrusAPI;
import top.zoyn.grus.manager.BoundaryExpDropManager;

public class EntityDeathListener implements Listener {

    public EntityDeathListener(Grus instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getKiller() != null && entity.getKiller() instanceof Player) {
            // 原版掉落处理
            if (GrusAPI.getBoundaryExpDropManager().getExpDropMode().equals(BoundaryExpDropManager.ExpDropMode.VANILLA)) {
                double exp = GrusAPI.getBoundaryExpDropManager().getExpDropByEntityType(entity.getType());
                Player player = entity.getKiller();
                GrusAPI.getBoundaryManager().addBoundaryExp(player, exp);
                player.sendMessage(I18N.YOU_HAVE_GAINED_EXP.getMessage());
            }
        }
    }

}
