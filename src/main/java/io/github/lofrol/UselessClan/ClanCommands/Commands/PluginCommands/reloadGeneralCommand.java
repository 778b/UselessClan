package io.github.lofrol.UselessClan.ClanCommands.Commands.PluginCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class reloadGeneralCommand extends CommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.General.Reload";
    }

    @Override
    public boolean havePermission(CommandSender sender) {
        return sender.hasPermission("UselessClan.Admin.Reload");
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        UselessClan tempPlugin = UselessClan.getPlugin(UselessClan.class);
        if (tempPlugin.isEnabled()){
            tempPlugin.reloadPlugin();
        }
        return true;
    }
}