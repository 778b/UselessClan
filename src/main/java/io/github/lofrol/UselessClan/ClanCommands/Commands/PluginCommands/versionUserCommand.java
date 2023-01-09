package io.github.lofrol.UselessClan.ClanCommands.Commands.PluginCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class versionUserCommand extends CommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.General.version";
    }

    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        ChatSender.NonTranslateMessageTo(sender, "UselessClan", UselessClan.getConfigManager().getServerVersion());
        return true;
    }
}
