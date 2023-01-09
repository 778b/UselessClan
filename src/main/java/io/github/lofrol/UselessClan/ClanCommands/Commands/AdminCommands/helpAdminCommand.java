package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class helpAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.help";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        ChatSender.MessageTo(sender, "&4UselessClan","########## CLAN ADMIN HELP ##########");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd - to call this menu");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd list - list of all clans");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd info %name - to info any clan");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd debuginfo %name - to debug info any clan");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd mates %name - to mates any clan");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd delete %name - to info any clan");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd level %name %level - to force level any clan");
        ChatSender.MessageTo(sender, "&4UselessClan","/ClAd join %name - to force join any clan");
        return true;
    }
}