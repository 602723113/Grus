package top.zoyn.grus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public enum I18N {

    HELP("help"),
    MESSAGE_PREFIX("message-prefix"),
    UNKNOWN_COMMAND("unknown-command"),
    NO_PERMISSION("no-permission"),
    CONSOLE_SET_LANGUAGE("console-set-language");

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
