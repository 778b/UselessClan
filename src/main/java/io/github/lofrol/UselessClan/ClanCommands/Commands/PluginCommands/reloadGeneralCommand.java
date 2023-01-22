package io.github.lofrol.UselessClan.ClanCommands.Commands.PluginCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class reloadGeneralCommand extends CommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.General.reload";
    }

    @Override
    public boolean havePermission(CommandSender sender) {
        return sender.hasPermission("UselessClan.Admin");
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        UselessClan.reloadPlugin();
        return true;
    }
}