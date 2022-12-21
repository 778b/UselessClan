package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class settingsUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan settings help&b - to show help about settings of your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return senderRole == EClanRole.LEADER;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        // /clan setting info
        // /clan setting HomeChangeMinRank 4
        EClanRole SenderRole = null;
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
            return false;
        }
        SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        if (SenderRole.ordinal() < EClanRole.LEADER.ordinal()) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan",
                    "&cYou forgot about args, use &a/Clan setting help");
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("info")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "### CLAN SETTINGS ###");
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("DefaultJoinRole = %s", senderClan.getSettingsClan().DefaultJoinRole.toString()));
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanSetHome = %s", senderClan.getSettingsClan().RoleCanSethome.toString()));
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanKick = %s", senderClan.getSettingsClan().RoleCanKick.toString()));
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanWithdraw. = %s", senderClan.getSettingsClan().RoleCanWithdraw.toString()));
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanAccept = %s", senderClan.getSettingsClan().RoleCanSethome.toString()));
            } else if (args[1].equalsIgnoreCase("DefaultJoinRole")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cNot enough arguments, try &a/clan setting DefaultJoinRole [1-4] 1 = Rookie, 4 = Leader");
            } else if (args[1].equalsIgnoreCase("RoleCanSetHome")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cNot enough arguments, try &a/clan setting RoleCanSetHome [1-4] 1 = Rookie, 4 = Leader");
            } else if (args[1].equalsIgnoreCase("RoleCanKick")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cNot enough arguments, try &a/clan setting DefaultJoinRole [1-4] 1 = Rookie, 4 = Leader");
            } else if (args[1].equalsIgnoreCase("RoleCanWithdraw")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cNot enough arguments, try &a/clan setting RoleCanWithdraw [1-4] 1 = Rookie, 4 = Leader");
            } else if (args[1].equalsIgnoreCase("RoleCanAccept")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cNot enough arguments, try &a/clan setting RoleCanAccept [1-4] 1 = Rookie, 4 = Leader");
            } else if (args[1].equalsIgnoreCase("help")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&a/clan setting info - show info about setting clan");
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&a/clan setting HomeChangeMinRank [Role] - set min role to change home location [1-4]");
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&a/clan setting RoleCanKick [Role] - set min role which can kick from clan [1-4]");
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&a/clan setting DefaultJoinRole [Role] - set default join role [1-4]");
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&a/clan setting WithdrawMinRole [Role] - set role which can withdraw money from clan [1-4]");
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cYou have wrong args! Use &a/Clan setting help");
            }
        } else {
            if (args[1].equalsIgnoreCase("DefaultJoinRole")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cWrong arguments, example &a/clan setting DefaultJoinRole 2");
                    return false;
                }
                if (tempRole == EClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cDefaultJoinRole cant be LEADER!");
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("&aDefaultJoinRole successfully changed to %s!", tempRole.toString()));
            } else if (args[1].equalsIgnoreCase("RoleCanSetHome")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cWrong arguments, example &a/clan setting RoleCanSetHome 2");
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("&aRoleCanSetHome successfully changed to %s!", tempRole.toString()));
            } else if (args[1].equalsIgnoreCase("RoleCanKick")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cWrong arguments, example &a/clan setting RoleCanKick 3");
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("&aRoleCanKick successfully changed to %s!", tempRole.toString()));
            } else if (args[1].equalsIgnoreCase("RoleCanWithdraw")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cWrong arguments, example &a/clan setting RoleCanWithdraw 2");
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("&aRoleCanWithdraw successfully changed to %s!", tempRole.toString()));
            } else if (args[1].equalsIgnoreCase("RoleCanAccept")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cWrong arguments, example &a/clan setting RoleCanAccept 3");
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("&aRoleCanAccept successfully changed to %s!", tempRole.toString()));
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cYou have wrong args! Use &a/Clan setting help");
            }
        }
        return true;
    }
}
