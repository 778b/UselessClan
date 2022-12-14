package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;

public class requestsAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd mates %name, %name = name of clan");
        }
        else {
            Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
            if (findedClan == null) {
                ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                return false;
            }
            ChatSender.MessageTo(sender,"UselessClan", "########## CLANREQUESTS ##########");
            for (var tempMember : findedClan.getRequests()) {
                ChatSender.MessageTo(sender,"UselessClan", String.format(
                        "# &a%s", tempMember));
            }
        }
        return true;
    }
}
