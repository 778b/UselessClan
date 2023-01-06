package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class settingsUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.Settings";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null) return false;
        return senderRole == EClanRole.LEADER;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.WithoutArgs");
        }
        else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("info")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.Label");
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format("DefaultJoinRole = %s", senderClan.getSettingsClan().DefaultJoinRole.toString()));
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanSetHome = %s", senderClan.getSettingsClan().RoleCanSethome.toString()));
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanKick = %s", senderClan.getSettingsClan().RoleCanKick.toString()));
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanWithdraw. = %s", senderClan.getSettingsClan().RoleCanWithdraw.toString()));
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format("RoleCanAccept = %s", senderClan.getSettingsClan().RoleCanSethome.toString()));
            }
            else if (args[1].equalsIgnoreCase("DefaultJoinRole")) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.SelectedWithoutArgs"), "DefaultJoinRole"));
            }
            else if (args[1].equalsIgnoreCase("RoleCanSetHome")) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.SelectedWithoutArgs"), "RoleCanSetHome"));
            }
            else if (args[1].equalsIgnoreCase("RoleCanKick")) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.SelectedWithoutArgs"), "RoleCanKick"));
            }
            else if (args[1].equalsIgnoreCase("RoleCanWithdraw")) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.SelectedWithoutArgs"), "RoleCanWithdraw"));
            }
            else if (args[1].equalsIgnoreCase("RoleCanAccept")) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.SelectedWithoutArgs"), "RoleCanAccept"));
            }
            else if (args[1].equalsIgnoreCase("help")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.Description.RoleCanSetHome");
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.Description.RoleCanKick");
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Settings.Description.DefaultJoinRole");
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Settings.Description.RoleCanWithdraw");
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Settings.Description.RoleCanAccept");
            }
            else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.WithoutArgs");
            }
        }
        else {
            if (args[1].equalsIgnoreCase("DefaultJoinRole")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Setting.UseExample"), "DefaultJoinRole 2"));
                    return false;
                }
                if (tempRole == EClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.LeaderError");
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.Changed"), "DefaultJoinRole", tempRole.toString()));
            }
            else if (args[1].equalsIgnoreCase("RoleCanSetHome")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Setting.UseExample"), "RoleCanSetHome 2"));
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.Changed"), "RoleCanSetHome", tempRole.toString()));
            }
            else if (args[1].equalsIgnoreCase("RoleCanKick")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Setting.UseExample"), "RoleCanKick 3"));
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.Changed"), "RoleCanKick", tempRole.toString()));
            }
            else if (args[1].equalsIgnoreCase("RoleCanWithdraw")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Setting.UseExample"), "RoleCanWithdraw 2"));
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.Changed"), "RoleCanWithdraw", tempRole.toString()));
            }
            else if (args[1].equalsIgnoreCase("RoleCanAccept")) {
                EClanRole tempRole = EClanRole.fromString(args[2]);
                if (tempRole == EClanRole.NONE) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Setting.UseExample"), "RoleCanAccept 3"));
                    return false;
                }

                senderClan.getSettingsClan().DefaultJoinRole = tempRole;
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Setting.Changed"), "RoleCanAccept", tempRole.toString()));
            }
            else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Setting.WrongArgs");
            }
        }
        return true;
    }
}
