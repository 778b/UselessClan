package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;

public class levelAdminCommand extends CommandBase {
    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd level %name, %name = name of clan");
        }
        else if (args.length == 2) {
            ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %level, use /ClAd level %name %level, %level = level to give");
        }
        else {
            if (args[0].equalsIgnoreCase("level")) {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                int tempLevel = Integer.parseInt(args[2]);

                if (tempLevel < 0 || tempLevel > ClanManager.ClanLevelColors.length) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cWrong level number!");
                    return false;
                }

                findedClan.setClanLevel(tempLevel);
                ChatSender.MessageTo(sender, "&4UselessClan",
                        String.format("&aLevel of clan %s was changed to %d", findedClan.getPrefixClan(), findedClan.getClanLevel()));
            }
        }
        return true;
    }
}
