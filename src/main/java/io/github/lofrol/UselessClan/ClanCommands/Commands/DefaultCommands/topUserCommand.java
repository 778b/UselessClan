package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import io.github.lofrol.UselessClan.Utils.TopListClan;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class topUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Top";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        List<TopListClan> tempTopListClans = UselessClan.getMainManager().getTopClans().getSortedClans();
        final int numClansInOnePage = 10;

        if (args.length == 1) {
            //  /Clan help
            int tempRow = 0;
            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",  String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Top.Label"), 1));
            for (int i = tempRow; i < tempTopListClans.size(); ++i) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format("&b#%d) %s : &e%d : &a%d", tempRow + 1,
                        tempTopListClans.get(i).ClanName, tempTopListClans.get(i).ClanLevel, tempTopListClans.get(i).ClanMoney));
                ++tempRow;
                if (tempRow == numClansInOnePage) break;
            }
            if (tempTopListClans.size() - numClansInOnePage > 0) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Top.PageCommand"), 2, 2));
            }
            return true;
        }
        else if (args.length > 1) {
            //  /Clan help (1,2,3...n)
            int pageNum = Integer.parseInt(args[1]);
            if (pageNum > 0 && pageNum < tempTopListClans.size() && (pageNum - 1) * numClansInOnePage < tempTopListClans.size()) {
                int tempRow = 0;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Top.Label"), pageNum));
                for (int i = (pageNum - 1) * numClansInOnePage; i < tempTopListClans.size(); ++i) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format("&b#%d) %s : &e%d : &a%d",(tempRow + 1) * pageNum,
                            tempTopListClans.get(i).ClanName, tempTopListClans.get(i).ClanLevel, tempTopListClans.get(i).ClanMoney));
                    ++tempRow;
                    if (tempRow == numClansInOnePage) break;
                }
                if (tempTopListClans.size() - (pageNum * numClansInOnePage) > 0) {
                    final int pageNumPlus = pageNum + 1;
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage("Top.PageCommand"), pageNumPlus, pageNumPlus));
                }
                return true;
            }
        }
        //  /Clan help (not a number or too big)
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Top.WrongPage");
        return false;
    }
}