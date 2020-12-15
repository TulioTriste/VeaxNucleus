package dev.veax.nucleus.commands;

import dev.veax.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.util.Collections;

public class GlobalListCommand extends Command {

    public GlobalListCommand() {
        super("globallist", "veax.nucleus.globallist", "glist");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }
        commandSender.sendMessage(CC.translate("&8&m------------------------------------"));
        commandSender.sendMessage(CC.translate("&b&lServer Status"));
        ProxyServer.getInstance().getServers().keySet().forEach(server ->
            commandSender.sendMessage(CC.translate("&7 - &3" + server + " &7(" + ProxyServer.getInstance().getServerInfo(server).getPlayers().size() + ")")));
        commandSender.sendMessage(CC.translate("&bGlobal players&7: &f" + ProxyServer.getInstance().getOnlineCount()));
        commandSender.sendMessage(CC.translate("&8&m------------------------------------"));
    }
}
