package top.zoyn.grus;

import org.bukkit.plugin.java.JavaPlugin;
import top.zoyn.grus.command.GrusCommand;
import top.zoyn.grus.manager.BoundaryManager;
import top.zoyn.grus.manager.LanguageManager;
import top.zoyn.grus.manager.LingemManager;

import java.io.File;

public final class Grus extends JavaPlugin {

    private static Grus instance;

    private LanguageManager languageManager;
    private BoundaryManager boundaryManager;
    private LingemManager lingemManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("language" + File.separator + "zh-CN.yml", false);
        saveResource("language" + File.separator + "en-US.yml", false);

        languageManager = new LanguageManager();
        boundaryManager = new BoundaryManager();
        lingemManager = new LingemManager();

        getCommand("grus").setExecutor(new GrusCommand());
    }

    public static Grus getInstance() {
        return instance;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public BoundaryManager getBoundaryManager() {
        return boundaryManager;
    }

    public LingemManager getLingemManager() {
        return lingemManager;
    }
}
