package dev.veax.nucleus.commands;

import dev.veax.nucleus.Nucleus;
import dev.veax.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

public class ListCommand extends Command {

    private Nucleus plugin = Nucleus.getInstance();

    public ListCommand() {
        super("list");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate("&cThis command doesn't permissions for execute in the Console."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        Server server = player.getServer();
        commandSender.sendMessage(CC.translate("&bThere " + (server.getInfo().getPlayers().size() == 1 ? "is": "are") + " currently &3" + server.getInfo().getPlayers().size() + " player" + (server.getInfo().getPlayers().size() == 1 ? "": "s") + " &bin &3" + server.getInfo().getName()));
    }
}
