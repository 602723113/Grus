package top.zoyn.grus.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.GrusAPI;

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
        if (params.equalsIgnoreCase("boundary_level")) {
            return GrusAPI.getBoundaryManager().getPlayerBoundary(player);
        }
        if (params.equalsIgnoreCase("boundary_exp")) {
            return "" + GrusAPI.getBoundaryManager().getPlayerBoundaryExp(player);
        }
        if (params.equalsIgnoreCase("lingem")) {
            if (GrusAPI.getLingemManager().hasLingem(player)) {
                return GrusAPI.getLingemManager().getPlayerLingem(player).toString();
            }
            return I18N.NO_LINGEM.getMessage();
        }
        return null;
    }
}
