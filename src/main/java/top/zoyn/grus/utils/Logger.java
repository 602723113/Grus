package top.zoyn.grus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import top.zoyn.grus.I18N;

import java.util.Arrays;

/**
 * @author Zoyn
 * @since 2023-1-14
 */
public final class Logger {

    private static final ConsoleCommandSender logger = Bukkit.getConsoleSender();

    public static void info(String msg) {
        logger.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + msg);
    }

    public static void info(String... msg) {
        Arrays.stream(msg).forEach(Logger::info);
    }

    public static void warn(String msg) {
        logger.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + "§f| §c§lWARN§7] §r" + msg);
    }

    public static void warn(String... msg) {
        Arrays.stream(msg).forEach(Logger::warn);
    }

    public static void error(String msg) {
        logger.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + "§f| §4§lERROR§7] §r" + msg);
    }

    public static void error(String... msg) {
        Arrays.stream(msg).forEach(Logger::error);
    }


}
