package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class calclvlAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Calclvl";
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "<Red>UselessClan</Red>", "Treasure.Admin.MissedArgToCalculate");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "<Red>UselessClan</Red>", "Base.HavntClan");
            return false;
        }
        UselessClan.getMainManager().CalculateClanLevel(foundClan);

        ChatSender.NonTranslateMessageTo(sender, "<Red>UselessClan</Red>",
                String.format(UselessClan.getLocalManager().getLocalizationMessage(
                        "Treasure.Admin.LevelCalculation"), foundClan.getPrefixClan()));
        return true;
    }
}
