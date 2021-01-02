package dev.veax.nucleus.commands;

import dev.veax.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BuildsCommand extends Command {

    public BuildsCommand() {
        super("builds", "bungee.staff");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate("&cNo Console."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (player.getServer().getInfo().getName().equals("Builds")) {
            player.sendMessage(CC.translate("&cAlready connected!"));
            return;
        }
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("Builds");
        player.connect(serverInfo);
        player.sendMessage(CC.translate("&aSuccessfully sent!"));
    }
}
