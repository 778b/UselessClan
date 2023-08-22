package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class listAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.List";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        ChatSender.MessageTo(sender, "&4UselessClan","Info.Admin.ListLabel");
        for (Clan tempClan : UselessClan.getMainManager().getServerClans().values()) {
            ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Info.Admin.ListUnit"), tempClan.getNameClan(), tempClan.getClanLevel()));
        }
        return true;
    }
}