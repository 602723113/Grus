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

import java.util.List;
import java.util.UUID;

public class BoundaryCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(I18N.NO_PERMISSION.getMessage());
            return;
        }
        if (args.length == 1) {
            I18N.HELP_BOUNDARY.getAsStringList().forEach(sender::sendMessage);
            return;
        }
        if (args[1].equalsIgnoreCase("look")) {
            if (args.length <= 2) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.WRONG_COMMAND_USAGE.getMessage());
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
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.PLAYER_DO_NOT_EXIST.getMessage().replace("%player_name%", playerName));
                return;
            }
            for (String s : I18N.BOUNDARY_LOOK.getAsStringList()) {
                // 这里直接套用 PlaceholderAPI 里面的变量
                sender.sendMessage(PlaceholderAPI.setPlaceholders(player, s));
            }
        }
        if (args[1].equalsIgnoreCase("add")) {
            if (args.length <= 3) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.WRONG_COMMAND_USAGE.getMessage());
                return;
            }
            String playerName = args[2];
            double exp = Double.parseDouble(args[3]);
            OfflinePlayer player;
            UUID uid;
            try {
                uid = UUID.fromString(playerName);
                player = Bukkit.getOfflinePlayer(uid);
            } catch (IllegalArgumentException exception) {
                player = Bukkit.getOfflinePlayer(playerName);
            }
            if (player == null) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.PLAYER_DO_NOT_EXIST.getMessage().replace("%player_name%", playerName));
                return;
            }
            BoundaryManager manager = GrusAPI.getBoundaryManager();
            manager.addBoundaryExp(player, exp);
            sender.sendMessage(I18N.BOUNDARY_ADD.getMessage()
                    .replace("%player_name%", playerName)
                    .replace("%exp%", "" + exp));
        }
        if (args[1].equalsIgnoreCase("remove")) {
            if (args.length <= 3) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.WRONG_COMMAND_USAGE.getMessage());
                return;
            }
            String playerName = args[2];
            double exp = Double.parseDouble(args[3]);
            OfflinePlayer player;
            UUID uid;
            try {
                uid = UUID.fromString(playerName);
                player = Bukkit.getOfflinePlayer(uid);
            } catch (IllegalArgumentException exception) {
                player = Bukkit.getOfflinePlayer(playerName);
            }
            if (player == null) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.PLAYER_DO_NOT_EXIST.getMessage().replace("%player_name%", playerName));
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
        return Lists.newArrayList("look", "add", "remove");
    }
}
