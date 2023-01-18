package top.zoyn.grus.command.subcommand;

import com.google.common.collect.Lists;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.GrusAPI;
import top.zoyn.grus.command.SubCommand;
import top.zoyn.grus.manager.BoundaryManager;
import top.zoyn.grus.utils.MessageUtils;
import top.zoyn.grus.utils.PermissionUtils;

import java.util.List;
import java.util.UUID;

public class BoundaryCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (PermissionUtils.nonAdminAuth(sender)) {
            return;
        }
        if (args.length == 1) {
            I18N.HELP_BOUNDARY.getAsStringList().forEach(sender::sendMessage);
            return;
        }
        if ("look".equalsIgnoreCase(args[1])) {
            if (args.length <= 2) {
                MessageUtils.sendWrongUsageMessage(sender);
                return;
            }

            String playerName = args[2];
            OfflinePlayer player;
            UUID uid;
            try {
                uid = UUID.fromString(playerName);
                player = Bukkit.getOfflinePlayer(uid);
            } catch (IllegalArgumentException exception) {
                player = Bukkit.getOfflinePlayer(playerName);
            }
            if (player == null) {
                MessageUtils.sendPlayerNotFoundMessage(sender, playerName);
                return;
            }
            for (String s : I18N.BOUNDARY_LOOK.getAsStringList()) {
                // 这里直接套用 PlaceholderAPI 里面的变量
                sender.sendMessage(PlaceholderAPI.setPlaceholders(player, s));
            }
        }
        if ("add".equalsIgnoreCase(args[1])) {
            if (args.length <= 3) {
                MessageUtils.sendWrongUsageMessage(sender);
                return;
            }
            String playerName = args[2];
            double exp;
            try{
                exp = Double.parseDouble(args[3]);
            } catch (NumberFormatException exception) {
                MessageUtils.sendNotNumberMessage(sender);
                return;
            }

            OfflinePlayer player;
            UUID uid;
            try {
                uid = UUID.fromString(playerName);
                player = Bukkit.getOfflinePlayer(uid);
            } catch (IllegalArgumentException exception) {
                player = Bukkit.getOfflinePlayer(playerName);
            }
            if (player == null) {
                MessageUtils.sendPlayerNotFoundMessage(sender, playerName);
                return;
            }
            BoundaryManager manager = GrusAPI.getBoundaryManager();
            manager.addBoundaryExp(player, exp);
            sender.sendMessage(I18N.BOUNDARY_ADD.getMessage()
                    .replace("%player_name%", playerName)
                    .replace("%exp%", "" + exp));
        }
        if ("remove".equalsIgnoreCase(args[1])) {
            if (args.length <= 3) {
                MessageUtils.sendWrongUsageMessage(sender);
                return;
            }
            String playerName = args[2];
            double exp;
            try{
                exp = Double.parseDouble(args[3]);
            } catch (NumberFormatException exception) {
                MessageUtils.sendNotNumberMessage(sender);
                return;
            }
            OfflinePlayer player;
            UUID uid;
            try {
                uid = UUID.fromString(playerName);
                player = Bukkit.getOfflinePlayer(uid);
            } catch (IllegalArgumentException exception) {
                player = Bukkit.getOfflinePlayer(playerName);
            }
            if (player == null) {
                MessageUtils.sendPlayerNotFoundMessage(sender, playerName);
                return;
            }
            BoundaryManager manager = GrusAPI.getBoundaryManager();
            manager.removeBoundaryExp(player, exp);
            sender.sendMessage(I18N.BOUNDARY_REMOVE.getMessage()
                    .replace("%player_name%", playerName)
                    .replace("%exp%", "" + exp));
        }
    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> res = Lists.newArrayList("look", "add", "remove");
        res.removeIf(s -> !s.startsWith(args[1]));
        return Lists.newArrayList(res);
    }
}
