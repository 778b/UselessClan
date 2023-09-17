package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.ClanAdminCommand;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class helpAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Help";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        final int numCommandsInOnePage = 8;
        Command tempCommand = UselessClan.GetCommandByClass(ClanAdminCommand.class);
        List<CommandBase> tempCommandArray = null;

        if (tempCommand instanceof ClanAdminCommand tempClanCommand) {
            tempCommandArray = tempClanCommand.getExecutableCommands().stream().toList();
        }

        if (tempCommandArray == null) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Help.NoCommands");
            return false;
        }

        if (args.length == 1) {
            int tempRow = 0;
            ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Help.Label"), 1));
            for (int i = tempRow; i < tempCommandArray.size(); ++i) {
                ChatSender.MessageTo(sender, "<red>UselessClan</Red>", tempCommandArray.get(i).commandDescription());
                ++tempRow;
                if (tempRow == numCommandsInOnePage) break;
            }
            if (tempCommandArray.size() - numCommandsInOnePage > 0) {
                ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Help.ClanPageCommand"), 2, 2));
            }
            return true;
        }
        else if (args.length > 1) {
            //  /Clan help 2
            int pageNum = Integer.parseInt(args[1]);
            if (pageNum > 0 && pageNum < tempCommandArray.size() && (pageNum - 1) * numCommandsInOnePage < tempCommandArray.size()) {
                int tempRow = 0;
                ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Help.Label"), pageNum));
                for (int i = (pageNum - 1) * numCommandsInOnePage; i < tempCommandArray.size(); ++i) {
                    ChatSender.MessageTo(sender, "<red>UselessClan</Red>", tempCommandArray.get(i).commandDescription());
                    ++tempRow;
                    if (tempRow == numCommandsInOnePage) break;
                }
                if (tempCommandArray.size() - (pageNum * numCommandsInOnePage) > 0) {
                    final int pageNumPlus = pageNum + 1;
                    ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Help.ClanPageCommand"), pageNumPlus, pageNumPlus));
                }
                return true;
            }
        }
        //  /Clan help asjd
        ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Help.WrongPage");
        return false;
    }
}