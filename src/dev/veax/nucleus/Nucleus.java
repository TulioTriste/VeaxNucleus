package dev.veax.nucleus;

import dev.veax.nucleus.commands.*;
import dev.veax.nucleus.listeners.PlayerListener;
import dev.veax.nucleus.util.CooldownManager;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public class Nucleus extends Plugin {

    private static Nucleus instance;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        registerCommands();
        registerManagers();
        registerListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        cooldownManager.clearCooldowns();
    }

    private void registerCommands() {
        try {
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new AlertCommand());
        } catch (Exception ignored) {}
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalListCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SendCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ListCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new RequestCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReportCommand());
    }

    private void registerListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerListener());
    }

    private void registerManagers() {
        this.cooldownManager = new CooldownManager();
    }

    public static Nucleus getInstance() {
        return instance;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
