package top.zoyn.grus.command.subcommand;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.zoyn.grus.I18N;
import top.zoyn.grus.command.SubCommand;

public class MeCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.CONSOLE_MUST_BE_PLAYER.getMessage());
            return;
        }
        Player player = (Player) sender;
        I18N.ME.getAsStringList().forEach(s -> {
            // 这里直接套用 PlaceholderAPI 里面的变量
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, s));
        });
    }
}
