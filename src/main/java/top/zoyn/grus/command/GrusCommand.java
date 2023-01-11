package top.zoyn.grus.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import top.zoyn.grus.I18N;
import top.zoyn.grus.command.subcommand.*;

import java.util.List;
import java.util.Map;

public class GrusCommand implements TabExecutor {

    private static final Map<String, SubCommand> commandMap = Maps.newHashMap();

    /**
     * 初始化指令
     */
    public GrusCommand() {
        registerCommand("help", new HelpCommand());
        registerCommand("me", new MeCommand());
        registerCommand("lingem", new LingemCommand());
        registerCommand("boundary", new BoundaryCommand());
        registerCommand("reload", new ReloadCommand());
    }

    private void registerCommand(String commandName, SubCommand subCommand) {
        if (commandMap.containsKey(commandName)) {
            Bukkit.getLogger().warning("!");
            return;
        }
        commandMap.put(commandName, subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            commandMap.get("help").execute(sender, args);
            return true;
        }
        if (!commandMap.containsKey(args[0])) {
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.UNKNOWN_COMMAND.getMessage());
            return true;
        }

        // 第一个参数是 args[0]
        SubCommand subCommand = commandMap.get(args[0]);
        subCommand.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList(commandMap.keySet());
        }
        if (args.length == 2) {
        }
        return Lists.newArrayList();
    }
}
