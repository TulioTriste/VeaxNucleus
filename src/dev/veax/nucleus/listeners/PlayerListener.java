package dev.veax.nucleus.listeners;

import dev.veax.nucleus.Nucleus;
import dev.veax.nucleus.commands.StaffChatCommand;
import dev.veax.nucleus.util.CC;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.scheduler.BungeeScheduler;
import net.md_5.bungee.scheduler.BungeeTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {

    private final Nucleus plugin = Nucleus.getInstance();
    private Map<ProxiedPlayer, String> leftServer = new HashMap<>();

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        if (event.getMessage().startsWith("/")) return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (StaffChatCommand.toggle.contains(player.getUniqueId())) {
            String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
            String playerString = prefix + player.getName();
            this.plugin.getProxy().getPlayers().forEach(players -> {
                if (players.hasPermission("veax.nucleus.staffchat"))
                    players.sendMessage(CC.translate("&9[" + player.getServer().getInfo().getName() + "] " + playerString + "&7: &f" + event.getMessage()));
            });
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        leftServer.remove(event.getPlayer());
    }

    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent event) {
        leftServer.put(event.getPlayer(), event.getTarget().getName());
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("veax.nucleus.serverswitch")) {
            String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
            String playerString = prefix + player.getName();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    plugin.getProxy().getPlayers().forEach(players -> {
                        if (players.hasPermission("veax.nucleus.serverswitch"))
                            players.sendMessage(CC.translate("&9[Staff] " + playerString + " &bhas joined &f" + player.getServer().getInfo().getName() + " &bfrom &f" + leftServer.getOrDefault(player, "Network")));
                    });
                }
            };
            this.plugin.getProxy().getScheduler().schedule(this.plugin, runnable, 1L, TimeUnit.MILLISECONDS);
        }
    }

    /*@EventHandler
    public void onJoin(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("veax.nucleus.serverconnect")) {
            String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
            String playerString = prefix + player.getName();
            this.plugin.getProxy().getPlayers().forEach(players -> {
                if (players.hasPermission("veax.nucleus.serverconnect"))
                    players.sendMessage(CC.translate("&9[Staff] " + playerString + " &bhas joined the Network!"));
            });
        }
    }*/

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("veax.nucleus.serverdisconnect")) {
            String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
            String playerString = prefix + player.getName();
            this.plugin.getProxy().getPlayers().forEach(players -> {
                if (players.hasPermission("veax.nucleus.serverdisconnect"))
                    players.sendMessage(CC.translate("&9[Staff] " + playerString + " &bhas disconnected from the Network!"));
            });
        }
    }
}
