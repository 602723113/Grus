package top.zoyn.grus.manager;

import org.bukkit.configuration.file.FileConfiguration;
import top.zoyn.grus.Grus;
import top.zoyn.grus.I18N;
import top.zoyn.grus.utils.ConfigurationUtils;
import top.zoyn.grus.utils.Logger;

import java.io.File;

/**
 * 语言管理器
 *
 * @author Zoyn
 */
public class LanguageManager {

    private String language;
    private File languageFile;
    private File languageFolder;
    private FileConfiguration languageConfig;

    public LanguageManager() {
        // 默认语言为 zh-CN
        language = Grus.getInstance().getConfig().getString("language", "zh-CN");
        languageFolder = new File(Grus.getInstance().getDataFolder(), "language");
        languageFile = new File(languageFolder, language + ".yml");
        languageConfig = ConfigurationUtils.loadYML(languageFile);

        // 重新加载多语言系统
        for (I18N value : I18N.values()) {
            value.init(languageConfig);
        }

        Logger.info(I18N.CONSOLE_SET_LANGUAGE.getMessage() + language);
    }

    public LanguageManager reload() {
        // 默认语言为 zh-CN
        language = Grus.getInstance().getConfig().getString("language", "zh-CN");
        Logger.info(I18N.CONSOLE_SET_LANGUAGE.getMessage() + language);
        languageFolder = new File(Grus.getInstance().getDataFolder(), "language");
        languageFile = new File(languageFolder, language + ".yml");
        languageConfig = ConfigurationUtils.loadYML(languageFile);

        // 重新加载多语言系统
        for (I18N value : I18N.values()) {
            value.init(languageConfig);
        }

        Logger.info(I18N.CONSOLE_SET_LANGUAGE.getMessage() + language);
        return this;
    }

    public FileConfiguration getLanguageConfig() {
        return languageConfig;
    }

}
