package top.zoyn.grus.command.subcommand;

import org.bukkit.command.CommandSender;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.api.GrusAPI;
import top.zoyn.grus.command.SubCommand;

public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Grus.getInstance().reloadConfig();
        GrusAPI.getLanguageManager().reload();
        GrusAPI.getLingemManager().reload();
        GrusAPI.getBoundaryExpDropManager().reload();
        GrusAPI.getBoundaryManager().reload();

        sender.sendMessage(I18N.RELOAD_DONE.getMessage());
    }

}
