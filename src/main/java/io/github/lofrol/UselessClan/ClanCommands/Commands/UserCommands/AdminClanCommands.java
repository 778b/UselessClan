package io.github.lofrol.UselessClan.ClanCommands.Commands.UserCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

import static java.util.Map.entry;

public class AdminClanCommands {
    private static final Map<String, CommandBase> ClanCommands = Map.ofEntries(
            entry("help",        new helpAdminCommand()),
            entry("list",        new listAdminCommand()),
            entry("info",        new infoAdminCommand()),
            entry("mates",       new matesAdminCommand()),
            entry("delete",      new deleteAdminCommand()),
            entry("home",        new homeAdminCommand()),
            entry("calclvl",     new calclvlAdminCommand()),
            entry("level",       new levelAdminCommand())
    );

    public static CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }
    private static class helpAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            ChatSender.MessageTo(sender, "&4UselessClan","########## CLAN ADMIN HELP ##########");
            ChatSender.MessageTo(sender, "&4UselessClan","/ClAd - to call this menu");
            ChatSender.MessageTo(sender, "&4UselessClan","/ClAd list - list of all clans");
            ChatSender.MessageTo(sender, "&4UselessClan","/ClAd info %name - to info any clan");
            ChatSender.MessageTo(sender, "&4UselessClan","/ClAd mates %name - to mates any clan");
            ChatSender.MessageTo(sender, "&4UselessClan","/ClAd delete %name - to info any clan");
            ChatSender.MessageTo(sender, "&4UselessClan","/ClAd level %name %level - to force level any clan");
            return true;
        }
    }

    private static class listAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            ChatSender.MessageTo(sender, "&4UselessClan","########## CLAN LIST ##########");
            for (Clan tempClan : UselessClan.getMainManager().getServerClans().values()) {
                ChatSender.MessageTo(sender, "&4UselessClan",
                        String.format("# Name: &a%s&b, level: &a%d&b", tempClan.getNameClan(), tempClan.getClanLevel()));
            }
            return true;
        }
    }

    private static class infoAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd info %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ChatSender.MessageTo(sender,"UselessClan", String.format("### CLAN %s INFO ###", findedClan.getPrefixClan()));
                ChatSender.MessageTo(sender,"UselessClan", String.format("# Name: %s", findedClan.getNameClan()));
                ChatSender.MessageTo(sender,"UselessClan", String.format("# Prefix: %s", findedClan.getPrefixClan()));
                ChatSender.MessageTo(sender,"UselessClan", String.format("# Level: %s", findedClan.getClanLevel()));
                ChatSender.MessageTo(sender,"UselessClan", String.format("# LeaderName: %s", findedClan.getLeaderName()));
                ChatSender.MessageTo(sender,"UselessClan", String.format("# Count of Members: %s", findedClan.getMembers().size()));
                ChatSender.MessageTo(sender,"UselessClan", String.format("# Money: %s", findedClan.getMoneyClan()));
            }
            return true;
        }
    }

    private static class matesAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd mates %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ChatSender.MessageTo(sender,"UselessClan", "########## CLANMATES ##########");
                for (ClanMember tempMember: findedClan.getMembers()) {
                    ChatSender.MessageTo(sender,"UselessClan", String.format(
                            "# %s &a%s", tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
                }
            }
            return true;
        }
    }

    private static class deleteAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd delete %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                UselessClan.getMainManager().DeleteClan(findedClan);
                ChatSender.MessageTo(sender, "&4UselessClan",String.format("You deleted a clan %s", findedClan.getPrefixClan()));
            }
            return true;
        }
    }

    private static class homeAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd home %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                if (sender instanceof Player tempPlayer) tempPlayer.teleport(findedClan.getHomeClan());
            }
            return true;
        }
    }

    private static class calclvlAdminCommand extends CommandBase {
        @Override
        public boolean executeCommand(CommandSender sender, String[] args) {
            if (args.length == 1) {
                ChatSender.MessageTo(sender, "&4UselessClan","You forgot about clan %name, use /ClAd calclvl %name, %name = name of clan");
            }
            else {
                Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(sender, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                UselessClan.getMainManager().CalculateClanLevel(findedClan);
                ChatSender.MessageTo(sender, "&4UselessClan",String.format("Calculated level of clan %s", findedClan.getPrefixClan()));
            }
            return true;
        }
    }

    private static class levelAdminCommand extends CommandBase {
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

}
