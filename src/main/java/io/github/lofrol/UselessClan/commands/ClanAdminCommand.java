package io.github.lofrol.UselessClan.commands;

import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public final class ClanAdminCommand extends Command {

    private ClanManager ManagerPtr;

    public static ClanAdminCommand CreateDefaultInts(ClanManager manager) {
        ClanAdminCommand tempInst = new ClanAdminCommand("ClanAdmin", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clanadmin", "ClanAdmin", "clad", "ClAd").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    protected ClanAdminCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        if (!tempPlayer.hasPermission("UselessClan.Admin")) return false;

        int size = args.length;
        if (size == 0) {
            // just only /ClanAdmin
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","Use command /ClanAdmin help, for access to clan system");
            return true;
        }
        else if (size == 1) {
            // /Clan help/list/info/delete/force
            if (args[0].equalsIgnoreCase( "help")) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","#################### CLAN HELP ####################");
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd - to call this menu");
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd list - list of all clans");
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd info %name - to info any clan");
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd mates %name - to mates any clan");
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd delete %name - to info any clan");
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","/ClAd level %name %level - to force level any clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("list")) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","########## CLAN LIST ##########");
                for (Clan tempClan : ManagerPtr.getServerClans().values()) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan",
                            String.format("# Name: &a%s&b, level: &a%d&b", tempClan.getNameClan(), tempClan.getClanLevel()));
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("info")) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd info %name, %name = name of clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("mates")) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd mates %name, %name = name of clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %name, use /ClAd delete %name, %name = name of clan");
                return true;
            }
        }
        else if (size == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                Clan findedClan = ManagerPtr.getServerClans().get(args[1]);
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
                return true;
            }
            else if (args[0].equalsIgnoreCase("mates")) {
                Clan findedClan = ManagerPtr.getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "########## CLANMATES ##########");
                for (ClanMember tempMember: findedClan.getMembers()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", String.format(
                            "# %s &a%s", tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                Clan findedClan = ManagerPtr.getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ManagerPtr.DeleteClan(findedClan.getNameClan());
                ChatSender.MessageTo(tempPlayer, "&4UselessClan",String.format("You deleted a clan %s", findedClan.getNameClan()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("level")) {
                ChatSender.MessageTo(tempPlayer, "&4UselessClan","You forgot about clan %level, use /ClAd level %name %level, %level = level to give");
                return true;
            }
            else if (args[0].equalsIgnoreCase("calclvl")) {
                Clan findedClan = ManagerPtr.getServerClans().get(args[1]);
                if (findedClan == null) {
                    ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cThis clan didnt exist!");
                    return false;
                }
                ManagerPtr.CalculateClanLevel(findedClan);
                ChatSender.MessageTo(tempPlayer, "&4UselessClan",String.format("Calculated level of clan %s", findedClan.getPrefixClan()));
                return true;
            }
        }
        else if (size == 3) {
            if (args[0].equalsIgnoreCase("level")) {
                Clan findedClan = ManagerPtr.getServerClans().get(args[1]);
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
                return true;
            }
        }
        ChatSender.MessageTo(tempPlayer, "&4UselessClan","&cInvalid command. Use command /Clan help, for access to clan system");
        return false;
    }
}
