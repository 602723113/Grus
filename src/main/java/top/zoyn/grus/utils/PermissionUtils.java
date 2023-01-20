package top.zoyn.grus.utils;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import top.zoyn.grus.I18N;

/**
 * @author Crsuh2er0
 * @apiNote
 * @since 2023/1/18
 */
public class PermissionUtils {
    private PermissionUtils() {
    }

    /**
     * @return true if the sender is not op
     * false if the sender is op
     */
    public static boolean nonAdminAuth(@NotNull CommandSender sender) {
        if (sender.isOp()) {
            return false;
        } else {
            sender.sendMessage(I18N.MESSAGE_PREFIX.getMessage() + I18N.NO_PERMISSION.getMessage());
            return true;
        }
    }
}
