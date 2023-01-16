package top.zoyn.grus.command;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * 表示一个子命令
 *
 * @author Zoyn
 * @since 2023-1-14
 */
public interface SubCommand {

    void execute(CommandSender sender, String[] args);

    default List<String> tabComplete(String[] args) {
        return null;
    }

}