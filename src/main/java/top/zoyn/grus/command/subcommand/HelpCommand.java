package top.zoyn.grus.command.subcommand;

import org.bukkit.command.CommandSender;
import top.zoyn.grus.I18N;
import top.zoyn.grus.command.SubCommand;

public class HelpCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        I18N.HELP.getAsStringList().forEach(sender::sendMessage);
        if (sender.isOp()) {
            I18N.HELP_OP.getAsStringList().forEach(sender::sendMessage);
        }
    }

}
