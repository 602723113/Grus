package top.zoyn.grus;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import top.zoyn.grus.command.GrusCommand;
import top.zoyn.grus.listener.EntityDeathListener;
import top.zoyn.grus.manager.BoundaryExpDropManager;
import top.zoyn.grus.manager.BoundaryManager;
import top.zoyn.grus.manager.LanguageManager;
import top.zoyn.grus.manager.LingemManager;
import top.zoyn.grus.papi.GrusExpansion;

import java.io.File;

public final class Grus extends JavaPlugin {

    private static Grus instance;

    private LanguageManager languageManager;
    private BoundaryManager boundaryManager;
    private BoundaryExpDropManager boundaryExpDropManager;
    private LingemManager lingemManager;
    private ProtocolManager protocolManager;

    public static Grus getInstance() {
        return instance;
    }

    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        instance = this;

        // 本地数据
        saveDefaultConfig();
        saveResource("language" + File.separator + "zh-CN.yml", false);
        saveResource("language" + File.separator + "en-US.yml", false);

        // 管理器
        languageManager = new LanguageManager();
        boundaryManager = new BoundaryManager();
        boundaryExpDropManager = new BoundaryExpDropManager();
        lingemManager = new LingemManager();

        // 监听器
        new EntityDeathListener(instance);

        // 指令
        getCommand("grus").setExecutor(new GrusCommand());

        // PlaceholderAPI 注册
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new GrusExpansion().register();
        }
    }

    @Override
    public void onDisable() {
        boundaryManager.save();
        lingemManager.save();
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

    public BoundaryExpDropManager getBoundaryExpDropManager() {
        return boundaryExpDropManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
