package dev.veax.nucleus.commands;

import dev.veax.nucleus.Nucleus;
import dev.veax.nucleus.util.CC;
import io.netty.util.internal.ConcurrentSet;
import joptsimple.internal.Strings;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.*;

public class StaffChatCommand extends Command {

    private final Nucleus plugin = Nucleus.getInstance();
    public static final Set<UUID> toggle = new ConcurrentSet<>();

    public StaffChatCommand() {
        super("staffchat", "veax.nucleus.staffchat", "sc", "staffc");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate("&cThis command only executable by Players."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (strings.length == 0) {
            if (!toggle.contains(player.getUniqueId())) {
                toggle.add(player.getUniqueId());
                commandSender.sendMessage(CC.translate("&bStaff chat is now &aon."));
            } else {
                toggle.remove(player.getUniqueId());
                commandSender.sendMessage(CC.translate("&bStaff chat is now &coff."));
            }
            return;
        }
        StringBuilder message = new StringBuilder(Strings.EMPTY);
        for (String string : strings) {
            message.append(string).append(" ");
        }
        String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
        String playerString = prefix + player.getName();
        this.plugin.getProxy().getPlayers().forEach(players -> {
            if (players.hasPermission(getPermission())) {
                players.sendMessage(CC.translate("&9[" + player.getServer().getInfo().getName() + "] " + playerString + "&7: &f" + message.toString()));
            }
        });
    }
}
