package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;

public class debugInfoAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd info %name, %name = name of clan");
        }
        else {
            Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
            if (foundClan == null) {
                ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                return false;
            }
            ChatSender.MessageTo(sender,"UselessClan", String.format("&b### &cDEBUG &bCLAN %s INFO ###", foundClan.getPrefixClan()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# Name: %s", foundClan.getNameClan()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# Region: %s", foundClan.getClanRegionId()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# Prefix: %s", foundClan.getPrefixClan()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# Level: %s", foundClan.getClanLevel()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# LeaderName: %s", foundClan.getLeaderName()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# Count of Members: %s", foundClan.getMembers().size()));
            ChatSender.MessageTo(sender,"UselessClan", String.format("# Money: %s", foundClan.getMoneyClan()));
        }
        return true;
    }
}
