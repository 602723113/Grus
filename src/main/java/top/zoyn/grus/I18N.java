package top.zoyn.grus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public enum I18N {

    HELP("help"),
    HELP_OP("help-op"),
    HELP_BOUNDARY("help-boundary"),
    HELP_LINGEM("help-lingem"),
    RELOAD_DONE("reload-done"),
    WRONG_COMMAND_USAGE("wrong-command-usage"),

    MESSAGE_PREFIX("message-prefix"),
    UNKNOWN_COMMAND("unknown-command"),
    NO_PERMISSION("no-permission"),
    NO_LINGEM("no-lingem"),

    ME("me"),
    YOU_HAVE_GAINED_EXP("you-have-gained-exp"),
    PLAYER_DO_NOT_EXIST("player-do-not-exist"),
    BOUNDARY_LOOK("boundary-look"),
    BOUNDARY_ADD("boundary-add"),
    BOUNDARY_REMOVE("boundary-remove"),
    LINGEM_LOOK("lingem-look"),
    LINGEM_ADD("lingem-add"),
    LINGEM_REMOVE("lingem-remove"),
    LINGEM_RESET("lingem-reset"),
    LINGEM_IS_NOT_EXIST("lingem-is-not-exist"),

    CONSOLE_SET_LANGUAGE("console-set-language"),
    CONSOLE_LOAD_LINGEM("console-load-lingem"),
    CONSOLE_LOAD_BOUNDARY("console-load-boundary"),
    CONSOLE_LOAD_BOUNDARY_EXP_DROP("console-load-boundary-exp-drop"),
    CONSOLE_MUST_BE_PLAYER("console-must-be-player");

    private final String key;
    private Object message;

    I18N(String key) {
        this.key = key;
    }

    public void init(FileConfiguration config) {
        if (config.isString(key)) {
            this.message = translateColorCode(config.getString(key));
        } else if (config.isList(key)) {
            this.message = translateColorCode(config.getStringList(key));
        }
    }

    public String getMessage() {
        return (String) this.message;
    }

    public List<String> getAsStringList() {
        return (List<String>) this.message;
    }

    private static String translateColorCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static List<String> translateColorCode(List<String> messages) {
        return messages.stream().map(I18N::translateColorCode).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
