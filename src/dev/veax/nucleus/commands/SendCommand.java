package dev.veax.nucleus.commands;

import dev.veax.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class SendCommand extends Command {

    public SendCommand() {
        super("send", "veax.nucleus.send");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /send <player\u007Cserver\u007Call> <server>"));
            return;
        }
        else if (strings.length == 1) {
            commandSender.sendMessage(CC.translate("&cUsage: /send " + strings[0] + " <server>"));
            return;
        }
        if (!ProxyServer.getInstance().getServers().containsKey(strings[1])) {
            commandSender.sendMessage(CC.translate("&cThis server doesn't exist"));
            return;
        }
        ServerInfo serverTarget = ProxyServer.getInstance().getServers().get(strings[1]);
        if (strings[0].equalsIgnoreCase("ALL") || strings[0].equalsIgnoreCase("all")) {
            ProxyServer.getInstance().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.connect(serverTarget));
            return;
        }
        ProxyServer.getInstance().getServers().keySet().forEach(server -> {
            if (server.equals(strings[1])) {
                ServerInfo serverInfo = ProxyServer.getInstance().getServers().get(server);
                serverInfo.getPlayers().forEach(proxiedPlayer -> proxiedPlayer.connect(serverTarget));
            }
        });
        ProxiedPlayer target = ProxyServer.getInstance().getPlayer(strings[0]);
        if (target == null || !target.isConnected()) {
            commandSender.sendMessage(CC.translate("&cThis player is not connected."));
            return;
        }
        target.connect(serverTarget);
        target.sendMessage(CC.translate("&6You've been sent by a staff"));
        commandSender.sendMessage(CC.translate("&aThe Player " + target.getName() + " has been sent correctly."));
    }
}
