package top.zoyn.grus.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.GrusAPI;
import top.zoyn.grus.manager.BoundaryManager;
import top.zoyn.grus.manager.LingemManager;

public class GrusExpansion extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "Zoyn";
    }

    @Override
    public String getIdentifier() {
        return "grus";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        BoundaryManager boundaryManager = GrusAPI.getBoundaryManager();
        LingemManager lingemManager = GrusAPI.getLingemManager();

        if (params.equalsIgnoreCase("boundary_level")) {
            return boundaryManager.getDisplayBoundary(boundaryManager.getPlayerBoundary(player));
        }
        if (params.equalsIgnoreCase("boundary_exp")) {
            return "" + GrusAPI.getBoundaryManager().getPlayerBoundaryExp(player);
        }
        if (params.equalsIgnoreCase("lingem")) {
            if (lingemManager.hasLingem(player)) {
                return lingemManager.getPlayerLingem(player).toString();
            }
            return I18N.NO_LINGEM.getMessage();
        }
        return null;
    }
}
