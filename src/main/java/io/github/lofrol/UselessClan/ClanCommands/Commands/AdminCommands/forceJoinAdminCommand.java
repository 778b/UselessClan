package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class forceJoinAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.forceJoin";
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

            foundClan.PlayerJoinToClan(EClanRole.OFFICER, sender.getName());

            ChatSender.MessageTo(sender,"&4UselessClan", String.format("&a You successfully join to clan %s!", foundClan.getPrefixClan()));
        }
        return true;
    }
}
