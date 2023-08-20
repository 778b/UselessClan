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
        return "Description.Admin.list";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        ChatSender.MessageTo(sender, "&4UselessClan","########## CLAN LIST ##########");
        for (Clan tempClan : UselessClan.getMainManager().getServerClans().values()) {
            ChatSender.MessageTo(sender, "&4UselessClan",
                    String.format("# Name: &a%s&b, level: &a%d&b", tempClan.getNameClan(), tempClan.getClanLevel()));
        }
        return true;
    }
}