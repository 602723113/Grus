package top.zoyn.grus.command;

import org.bukkit.command.CommandSender;

/**
 * 表示一个子命令
 *
 * @author Zoyn
 * @since 2023-1-14
 */
@FunctionalInterface
public interface SubCommand {

    void execute(CommandSender sender, String[] args);

}