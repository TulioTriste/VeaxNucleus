package dev.veax.nucleus.commands;

import dev.veax.nucleus.Nucleus;
import dev.veax.nucleus.util.CC;
import joptsimple.internal.Strings;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class RequestCommand extends Command {

    private final Nucleus plugin = Nucleus.getInstance();

    public RequestCommand() {
        super("request", "veax.nucleus.request", "helpop");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate("&cThis command only executable by Players."));
            return;
        }
        if (!commandSender.hasPermission(getPermission())) {
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /request <message>"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        StringBuilder reason = new StringBuilder(Strings.EMPTY);
        for (String string : strings) {
            reason.append(string).append(" ");
        }
        long timeLeft = System.currentTimeMillis() - this.plugin.getCooldownManager().getCooldown("request", player.getUniqueId());
        if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= 60L) {
            for (ProxiedPlayer online : this.plugin.getProxy().getPlayers()) {
                if (online.hasPermission(getPermission())) {
                    String prefix = LuckPermsProvider.get().getUserManager().getUser(online.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(online.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
                    String playerString = prefix + online.getName();
                    online.sendMessage(CC.translate("&9[Request] &7[" + player.getServer().getInfo().getName() + "] &b" + playerString + " &bhas requested assistance"));
                    online.sendMessage(CC.translate("      &9Reason: &b" + reason.toString()));
                }
            }
            player.sendMessage(CC.translate("&aThank you! If any staff are online, they will respond shortly."));
            this.plugin.getCooldownManager().setCooldown("request", player.getUniqueId(), System.currentTimeMillis());
        }
        else {
            String theWait = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - 60L);
            player.sendMessage(CC.translate("&cYou have to wait " + theWait.replace("-", "") + " second(s) to use /request again!"));
        }
    }
}
