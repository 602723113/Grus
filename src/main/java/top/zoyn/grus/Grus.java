package top.zoyn.grus;

import org.bukkit.plugin.java.JavaPlugin;
import top.zoyn.grus.command.GrusCommand;
import top.zoyn.grus.manager.LanguageManager;

import java.io.File;

public final class Grus extends JavaPlugin {

    private static Grus instance;

    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("language" + File.separator + "zh-CN.yml", false);
        saveResource("language" + File.separator + "en-US.yml", false);

        languageManager = new LanguageManager();
        getCommand("grus").setExecutor(new GrusCommand());
    }

    public static Grus getInstance() {
        return instance;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

}
