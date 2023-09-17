package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.ClanCommand;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class helpUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.Help";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        EClanRole senderRole = null;
        if (senderClan != null) {
            senderRole = senderClan.getMemberRole(tempPlayer.getName());
        }

        final int numCommandsInOnePage = 8;
        Command tempCommand = UselessClan.GetCommandByClass(ClanCommand.class);
        List<PlayerCommandBase> tempCommandArray = null;

        if (tempCommand instanceof ClanCommand tempClanCommand) {
            tempCommandArray = tempClanCommand.getExecutableCommands(tempPlayer, senderClan, senderRole).stream().toList();
        }

        if (tempCommandArray == null) {
            ChatSender.MessageTo(tempPlayer, "<red>UselessClan</Red>", "Help.NoCommands");
            return false;
        }

        if (args.length == 1) {
            int tempRow = 0;
            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Help.Label"), 1));
            for (int i = tempRow; i < tempCommandArray.size(); ++i) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", tempCommandArray.get(i).commandDescription());
                ++tempRow;
                if (tempRow == numCommandsInOnePage) break;
            }
            if (tempCommandArray.size() - numCommandsInOnePage > 0) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Help.ClanPageCommand"), 2, 2));
            }
            return true;
        }
        else if (args.length > 1) {
            //  /Clan help 2
            int pageNum = Integer.parseInt(args[1]);
            if (pageNum > 0 && pageNum < tempCommandArray.size() && (pageNum - 1) * numCommandsInOnePage < tempCommandArray.size()) {
                int tempRow = 0;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Help.Label"), pageNum));
                for (int i = (pageNum - 1) * numCommandsInOnePage; i < tempCommandArray.size(); ++i) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", tempCommandArray.get(i).commandDescription());
                    ++tempRow;
                    if (tempRow == numCommandsInOnePage) break;
                }
                if (tempCommandArray.size() - (pageNum * numCommandsInOnePage) > 0) {
                    final int pageNumPlus = pageNum + 1;
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Help.ClanPageCommand"), pageNumPlus, pageNumPlus));
                }
                return true;
            }
        }
        //  /Clan help asjd
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Help.WrongPage");
        return false;
    }
}