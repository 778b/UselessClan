package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class levelAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Level";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            ChatSender.MessageTo(sender, "<red>UselessClan</red>", "Economy.Admin.MissingArgToLevel");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "<red>UselessClan</red>", "Base.HavntClan");
            return false;
        }
        int tempLevel = Integer.parseInt(args[2]);

        if (tempLevel < 0 || tempLevel > ClanManager.ClanLevelColors.size()) {
            ChatSender.MessageTo(sender, "<red>UselessClan</red>", "Economy.Admin.WrongLvl");
            return false;
        }

        foundClan.setClanLevel(tempLevel);
        foundClan.SendMessageForOnlinePlayers(String.format(
                UselessClan.getLocalManager().getLocalizationMessage(
                        "Economy.Admin.SuccessLvlChange"), foundClan.getPrefixClan(), foundClan.getClanLevel()));
        return true;
    }
}
