package top.zoyn.grus.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import top.zoyn.grus.Grus;
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
        return Grus.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        BoundaryManager boundaryManager = GrusAPI.getBoundaryManager();
        LingemManager lingemManager = GrusAPI.getLingemManager();
        switch (params.toLowerCase()){
            case "boundary_level":
                return boundaryManager.getDisplayBoundary(boundaryManager.getPlayerBoundary(player));
            case "boundary_exp":
                return "" + GrusAPI.getBoundaryManager().getPlayerBoundaryExp(player);
            case "boundary_excess_exp":
                return "" + GrusAPI.getBoundaryManager().getExcessExp(player);
            case "lingem":
                return lingemManager.hasLingem(player) ? lingemManager.getPlayerDisplayLingem(player).toString() : I18N.NO_LINGEM.getMessage();
            default:
        }
        return null;
    }
}
