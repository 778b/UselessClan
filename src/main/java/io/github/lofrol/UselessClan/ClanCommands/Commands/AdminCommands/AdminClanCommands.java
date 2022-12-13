package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.ClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class AdminClanCommands {
    public static void setupCommands(Map<String, CommandBase> commands) {
        commands.put("help",        new helpAdminCommand());
        commands.put("list",        new listAdminCommand());
        commands.put("info",        new infoAdminCommand());
        commands.put("mates",       new matesAdminCommand());
        commands.put("delete",      new deleteAdminCommand());
        commands.put("home",        new homeAdminCommand());
        commands.put("calclvl",     new calclvlAdminCommand());
        commands.put("level",       new levelAdminCommand());
    }

    private static class helpAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer,Clan senderClan, String[] args) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","#################### CLAN HELP ####################");
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd - to call this menu");
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd list - list of all clans");
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd info %name - to info any clan");
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd mates %name - to mates any clan");
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd delete %name - to info any clan");
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd level %name %level - to force level any clan");
            return true;
        }
    }

    private static class listAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer,Clan senderClan, String[] args) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","########## CLAN LIST ##########");
            for (Clan tempClan : UselessClan.getMainManager().getServerClans().values()) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan",
                        String.format("# Name: &a%s&b, level: &a%d&b", tempClan.getNameClan(), tempClan.getClanLevel()));
            }
            return true;
        }
    }

    private static class infoAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd info %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("### CLAN %s INFO ###", findedClan.getPrefixClan()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Name: %s", findedClan.getNameClan()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Prefix: %s", findedClan.getPrefixClan()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Level: %s", findedClan.getClanLevel()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# LeaderName: %s", findedClan.getLeaderName()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Count of Members: %s", findedClan.getMembers().size()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Money: %s", findedClan.getMoneyClan()));
            }
            return true;
        }
    }

    private static class matesAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd mates %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "########## CLANMATES ##########");
                for (ClanMember tempMember: findedClan.getMembers()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", String.format(
                            "# %s &a%s", tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
                }
            }
            return true;
        }
    }

    private static class deleteAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd delete %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                UselessClan.getMainManager().DeleteClan(findedClan.getNameClan());
                ChatSender.MessageTo(tempPlayer, "&4UselessClan",String.format("You deleted a clan %s", findedClan.getNameClan()));
            }
            return true;
        }
    }

    private static class homeAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd home %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                tempPlayer.teleport(findedClan.getHomeClan());
            }
            return true;
        }
    }

    private static class calclvlAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd calclvl %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                UselessClan.getMainManager().CalculateClanLevel(findedClan);
                ChatSender.MessageTo(tempPlayer, "&4UselessClan",String.format("Calculated level of clan %s", findedClan.getPrefixClan()));
            }
            return true;
        }
    }

    private static class levelAdminCommand extends PlayerCommandBase {
        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd level %name, %name = name of clan");
            }
            else if (args.length == 2) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %level, use /ClAd level %name %level, %level = level to give");
            }
            else {
                if (args[0].equalsIgnoreCase("level")) {
                    Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                    if (findedClan == null) {
                        ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                        return false;
                    }
                    int tempLevel = Integer.parseInt(args[2]);

                    if (tempLevel < 0 || tempLevel > ClanManager.ClanLevelColors.length) {
                        ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cWrong level number!");
                        return false;
                    }

                    findedClan.setClanLevel(tempLevel);
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan",
                            String.format("&aLevel of clan %s was changed to %d", findedClan.getPrefixClan(), findedClan.getClanLevel()));
                }
            }
            return true;
        }
    }

}
