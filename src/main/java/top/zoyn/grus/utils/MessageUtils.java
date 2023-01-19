package top.zoyn.grus.utils;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import top.zoyn.grus.I18N;

/**
 * @author Crsuh2er0
 * @apiNote
 * @since 2023/1/18
 */
public class MessageUtils {
    private static final String PREFIX = I18N.MESSAGE_PREFIX.getMessage();
    private MessageUtils() {
    }
    public static void sendPrefixMessage(@NotNull CommandSender sender, String message){
        sender.sendMessage(PREFIX + message);
    }
    public static void sendWrongUsageMessage(CommandSender sender){
        sendPrefixMessage(sender,I18N.WRONG_COMMAND_USAGE.getMessage());
    }
    public static void sendPlayerNotFoundMessage(CommandSender sender,String playerName){
        sendPrefixMessage(sender,I18N.PLAYER_DO_NOT_EXIST.getMessage().replace("%player_name%",playerName));
    }
    public static void sendNotNumberMessage(CommandSender sender){
        sendPrefixMessage(sender,I18N.NOT_NUMBER.getMessage());
    }
}
