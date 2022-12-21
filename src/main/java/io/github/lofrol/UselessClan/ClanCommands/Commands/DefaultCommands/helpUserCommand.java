package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager.BaseClanCommands;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class helpUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan help&b - to call this menu";
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
        List<PlayerCommandBase> tempCommandArray = BaseClanCommands.getExecutableCommands(tempPlayer, senderClan, senderRole).stream().toList();

        if (args.length == 1) {
            //  /Clan help
            int tempRow = 0;
            ChatSender.MessageTo(tempPlayer, "UselessClan", "############# CLAN HELP 1 #############");
            for (int i = tempRow; i < tempCommandArray.size(); ++i) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", tempCommandArray.get(i).commandDescription());
                ++tempRow;
                if (tempRow == numCommandsInOnePage) break;
            }
            if (tempCommandArray.size() - numCommandsInOnePage > 0) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "type &a/Clan help 2&b - to show commands in page 2");
            }
            return true;
        }
        else if (args.length > 1) {
            //  /Clan help 2
            int pageNum = Integer.parseInt(args[1]);
            if (pageNum > 0 && pageNum < tempCommandArray.size() && (pageNum - 1) * numCommandsInOnePage < tempCommandArray.size()) {
                int tempRow = 0;
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("############# CLAN HELP %d #############", pageNum));
                for (int i = (pageNum - 1) * numCommandsInOnePage; i < tempCommandArray.size(); ++i) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", tempCommandArray.get(i).commandDescription());
                    ++tempRow;
                    if (tempRow == numCommandsInOnePage) break;
                }
                if (tempCommandArray.size() - (pageNum * numCommandsInOnePage) > 0) {
                    final int pageNumPlus = pageNum + 1;
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            String.format("type &a/Clan help %d&b - to show commands in page %d", pageNumPlus, pageNumPlus));
                }
                return true;
            }
        }
        //  /Clan help asjd
        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis page of help isn't found");
        return false;
    }
}