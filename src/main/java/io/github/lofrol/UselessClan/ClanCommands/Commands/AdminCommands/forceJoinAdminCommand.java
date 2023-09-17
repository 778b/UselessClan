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
        return "Description.Admin.ForceJoin";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "<red>UselessClan</red>", "Enter.Admin.MissedArgToForceJoin");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "<red>UselessClan</red>", "Base.HavntClan");
            return false;
        }

        foundClan.PlayerJoinToClan(EClanRole.OFFICER, sender.getName());

        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Enter.Admin.SuccessJoin"), foundClan.getPrefixClan()));
        return true;
    }
}
