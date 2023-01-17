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
import top.zoyn.grus.manager.LingemManager;

import java.util.List;
import java.util.UUID;

public class LingemCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.NO_PERMISSION.getMessage());
            return;
        }
        if (args.length == 1) {
            I18N.HELP_LINGEM.getAsStringList().forEach(sender::sendMessage);
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
            for (String s : I18N.LINGEM_LOOK.getAsStringList()) {
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
            String lingem = args[3];
            if (!GrusAPI.getLingemManager().hasLingemInDefault(lingem)) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.LINGEM_IS_NOT_EXIST.getMessage().replace("%lingem%", lingem));
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
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.PLAYER_DO_NOT_EXIST.getMessage().replace("%player_name%", playerName));
                return;
            }
            LingemManager manager = GrusAPI.getLingemManager();
            manager.addLingem(player, lingem);
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.LINGEM_ADD.getMessage()
                    .replace("%player_name%", playerName)
                    .replace("%lingem%", "" + lingem));
        }
        if (args[1].equalsIgnoreCase("remove")) {
            if (args.length <= 3) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.WRONG_COMMAND_USAGE.getMessage());
                return;
            }
            String playerName = args[2];
            String lingem = args[3];
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
            LingemManager manager = GrusAPI.getLingemManager();
            if (!manager.hasLingem(player)) {
                sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.LINGEM_IS_NOT_EXIST.getMessage().replace("%lingem%", lingem));
                return;
            }
            manager.removeLingem(player, lingem);
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.LINGEM_REMOVE.getMessage()
                    .replace("%player_name%", playerName)
                    .replace("%lingem%", "" + lingem));
        }

        if (args[1].equalsIgnoreCase("reset")) {
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
            LingemManager manager = GrusAPI.getLingemManager();
            manager.resetLingem(player);
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.LINGEM_RESET.getMessage().replace("%player_name%", playerName));
        }
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return Lists.newArrayList("look", "add", "remove", "reset");
    }
}
