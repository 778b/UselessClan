package io.github.lofrol.UselessClan.commands;

import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.ClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class ClanCommand extends Command {

    private ClanManager ManagerPtr;
    public static ClanCommand CreateDefaultInts(ClanManager manager) {
        ClanCommand tempInst = new ClanCommand("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    protected ClanCommand(@NotNull String name, @NotNull String description,
                          @NotNull String usageMessage,
                          @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player tempPlayer = ((Player)sender);
        Clan SenderClan = ManagerPtr.FindClanToPlayer(tempPlayer.getName());
        ClanRole SenderRole = null;
        if (SenderClan != null) {
            SenderRole = SenderClan.getMemberRole(tempPlayer.getName());
        }

        int size = args.length;
        if (size == 0) {
            // just only /Clan
            sender.sendMessage("Use command /Clan help, for access to clan system");
        }
        // /Clan help/create/info/mates/join/accept/leave/top/kick/promote/demote/requests/home/sethome
        else if (args[0].equalsIgnoreCase( "help")) {
                sender.sendMessage("################### CLAN HELP ###################");
                sender.sendMessage("/Clan help          - to call this menu");
                sender.sendMessage("/Clan top           - top of all clans");
                sender.sendMessage("/Clan create %name  - to create your own clan with name %name");
                sender.sendMessage("/Clan leave         - to leave from your clan");
                sender.sendMessage("/Clan join %name    - to send request for join the clan %name");
                sender.sendMessage("/Clan info          - to info about your clan");
                if (SenderClan == null) return false;
                if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                    sender.sendMessage("/Clan requests      - to see list of all requests to join your clan");
                    sender.sendMessage("/Clan accept %name  - to accept %name for join to your clan");
                    sender.sendMessage("/Clan kick %name    - to kick player %name from your clan");
                    sender.sendMessage("/Clan promote %name - to promote player %name of your clan");
                    sender.sendMessage("/Clan demote %name  - to demote player %name of your clan");
                }
                return true;
            }
        else if (size == 1) {
            if (args[0].equalsIgnoreCase("top")) {
                sender.sendMessage("########## CLAN TOP ##########");
                sender.sendMessage("# ClanName              Bank #");
                for (Clan tempClan : ManagerPtr.getServerClans().values()) {
                    sender.sendMessage(String.format("# %s          %s ", tempClan.getNameClan(), tempClan.getMoneyClan()));
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("create")) {
                if (SenderClan != null) {
                    sender.sendMessage("You cant create clan while you have been in clan!");
                    return false;
                }
                sender.sendMessage("You forgot about clan %name, use /Clan create %name, %name = name of your clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (SenderClan != null) {
                    sender.sendMessage("You cant join clan while you have been in clan!");
                    return false;
                }
                sender.sendMessage("You forgot about clan %name, use /Clan join %name, %name = name of clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("deposit")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                sender.sendMessage("You forgot about value of deposit, use /clan deposit %money");
                return true;
            }
            else if (args[0].equalsIgnoreCase("withdraw")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                if (SenderRole.ordinal() < SenderClan.getSettingsClan().MinRoleForWithdraw.ordinal()) {
                    sender.sendMessage("You rank is too low to do that!");
                    return false;
                }
                sender.sendMessage("You forgot about value of withdraw, use /clan withdraw %money");
                return true;
            }
            else if (args[0].equalsIgnoreCase("leave")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent a clan!");
                    return false;
                }
                SenderClan.PlayerLeavedFromClan(tempPlayer);
                sender.sendMessage(String.format("You successfully leaved from %s", SenderClan.getNameClan()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("home")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent a clan!");
                    return false;
                }
                Location tempHome = SenderClan.getHomeClan();
                if (tempHome == null) {
                    sender.sendMessage("Your clan doesnt have home!");
                    return false;
                }
                tempPlayer.teleport(tempHome);
                return true;
            }
            else if (args[0].equalsIgnoreCase("sethome")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent a clan!");
                    return false;
                }
                if (SenderRole.ordinal() >= SenderClan.getSettingsClan().HomeChangerMinRole.ordinal() || SenderRole == ClanRole.LEADER) {
                    if (tempPlayer.getWorld().getName().equals("world")) {
                        Location tempLoc = tempPlayer.getLocation();
                        SenderClan.setHomeClan(tempLoc);
                    }
                    else {
                        sender.sendMessage("You cant set home there!");
                    }
                }
                else {
                    sender.sendMessage("You rank is too low to do that!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("mates")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                sender.sendMessage("########## CLANMATES ##########");
                for (ClanMember tempMember: SenderClan.getMembers()) {
                    sender.sendMessage(String.format(
                            "# %s      %s", tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("info")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                sender.sendMessage("########## CLAN INFO ##########");
                sender.sendMessage(String.format("# Name: %s", SenderClan.getNameClan()));
                sender.sendMessage(String.format("# LeaderName: %s", SenderClan.getLeaderName()));
                sender.sendMessage(String.format("# Prefix: %s", SenderClan.getPrefixClan()));
                sender.sendMessage(String.format("# Count of Members: %s", SenderClan.getMembers().size()));
                sender.sendMessage(String.format("# Money: %s", SenderClan.getMoneyClan()));
                sender.sendMessage(String.format("# Your rank: %s", SenderRole.toString()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("requests")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                    sender.sendMessage("######## CLAN REQUESTS ########");
                    for (String tempMemberName: SenderClan.getRequests()) {
                        sender.sendMessage(String.format("# %s", tempMemberName));
                    }
                }
                else {
                    sender.sendMessage("You rank is too low to do that!");
                    return false;
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                        sender.sendMessage("You forgot about player %name, use /Clan kick %name, %name = name of player, which you want to kick");
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("kick")) {
                if (SenderClan != null) {
                    if (SenderRole == SenderClan.getSettingsClan().RoleCanKick) {
                        sender.sendMessage("You forgot about player %name, use /Clan kick %name, %name = name of player, which you want to kick");
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("promote")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                        sender.sendMessage("You forgot about player %name and %rank, use /Clan promote %name %rank");
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("demote")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.LEADER) {
                        sender.sendMessage("You forgot about player %name and %rank, use /Clan promote %name %rank");
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
            else {
                sender.sendMessage("Invalid command. Use command /Clan help, for access to clan system");
                return false;
            }
        }
        else if (size == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (SenderClan != null) {
                    sender.sendMessage("You already in clan!");
                    return false;
                }

                if (args[1].length() < 3) {
                    sender.sendMessage("Your name is too short, name must be >3 symbols");
                    return false;
                }
                else if (args[1].length() > 6) {
                    sender.sendMessage("Your name is too long, name must be <7 symbols");
                    return false;
                }
                // check for bad symbols
                for (char tempChar : args[1].toCharArray()) {
                    if (!(tempChar == 95 || (tempChar >= 65 && tempChar <= 90) ||
                            (tempChar >= 97 && tempChar <= 122) ||
                            (tempChar >= 48 && tempChar <= 57))) {
                        sender.sendMessage("Invalid clan name, use [A-Z; a-z; _; 0-9]");
                        return false;
                    }
                }
                // check another clan name collision
                if (ManagerPtr.getServerClans().get(args[1]) != null) {
                    sender.sendMessage("Clan with this name already exist!");
                    return false;
                }
                // Creating new clan
                double moneyToClan = 10000;
                if (!UselessClan.EconomyPtr.has(tempPlayer, moneyToClan)) {
                    sender.sendMessage(String.format("For create your own clan you must have more than %s$", moneyToClan));
                    return false;
                }
                UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, 10000.d);

                Clan NewClan = new Clan(args[1], tempPlayer.getName());
                NewClan.PlayerJoinToClan(ClanRole.LEADER, tempPlayer.getName());
                ManagerPtr.getServerClans().put(args[1], NewClan);
                sender.sendMessage(String.format("Clan %s was created successfully!", args[1]));
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (SenderClan != null) {
                    sender.sendMessage("You cant join clan while you have been in clan!");
                    return false;
                }
                Clan ClanToRequest = ManagerPtr.getClanByName(args[1]);
                if (ClanToRequest != null) {
                    if (!ClanToRequest.SendRequestForJoin(tempPlayer.getName())) {
                        sender.sendMessage("You already sent request for join to this clan!");
                    }
                }
                else {
                    sender.sendMessage("Invalid clan name!");
                    return false;
                }
                sender.sendMessage("You send request for join to this clan, wait until leader or officer accept this request");
                return true;
            }
            else if (args[0].equalsIgnoreCase("deposit")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                double moneyToDeposit = Double.parseDouble(args[1]);
                if (moneyToDeposit <= 0) {
                    sender.sendMessage("Wrong money count! Use [0;+inf)");
                }
                SenderClan.DepositMoneyToClan(moneyToDeposit);
                sender.sendMessage(String.format("You deposit %s to clan %s", moneyToDeposit, SenderClan.getNameClan()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("withdraw")) {
                if (SenderClan == null) {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                if (SenderRole.ordinal() < SenderClan.getSettingsClan().MinRoleForWithdraw.ordinal()) {
                    sender.sendMessage("You rank is too low to do that!");
                    return false;
                }
                double moneyToWithdraw = Double.parseDouble(args[1]);
                if (moneyToWithdraw  <= 0) {
                    sender.sendMessage("Wrong money count! Use [0;+inf)");
                }
                
                SenderClan.DepositMoneyToClan(moneyToWithdraw );
                sender.sendMessage(String.format("You withdraw %s from clan %s", moneyToWithdraw , SenderClan.getNameClan()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                        String AcceptedPlayerName = args[1];
                        if (ManagerPtr.FindClanToPlayer(AcceptedPlayerName) != null) {
                            sender.sendMessage("This player already in Clan");
                            return false;
                        }
                        SenderClan.PlayerJoinToClan(ClanRole.ROOKIE, AcceptedPlayerName);
                        SenderClan.RemoveFromRequest(AcceptedPlayerName);
                        SenderClan.SendMessageForOnlinePlayers(String.format(
                                "Player %s joined to your clan!", AcceptedPlayerName));
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                        return false;
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                    return false;
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("kick")) {
                if (SenderClan != null) {
                    if (SenderRole == SenderClan.getSettingsClan().RoleCanKick) {
                        ClanMember tempMember = SenderClan.getClanMember(args[1]);
                        if (tempMember == null) {
                            sender.sendMessage("Cant find this player in your clan!");
                            return false;
                        }
                        SenderClan.PlayerLeavedFromClan(tempMember.getPlayerName());
                        SenderClan.SendMessageForOnlinePlayers(String.format(
                                "Player %s was kicked from your clan!", tempMember.getPlayerName()));
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("promote")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                        ClanMember tempClanMember = SenderClan.getClanMember(args[1]);
                        if (tempClanMember == null) {
                            sender.sendMessage("This player not in your clan!");
                            return false;
                        }
                        if (tempClanMember.getMemberRole() == ClanRole.ROOKIE) {
                            if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.MEMBER)) {
                                sender.sendMessage("This Player already have this rank!");
                                return false;
                            }
                            SenderClan.SendMessageForOnlinePlayers(String.format(
                                    "Player %s was promoted to %s", tempClanMember.getPlayerName(), ClanRole.MEMBER.toString()));
                        }
                        else if (tempClanMember.getMemberRole() == ClanRole.MEMBER) {
                            if (SenderRole == ClanRole.LEADER) {
                                if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.OFFICER)) {
                                    sender.sendMessage("This Player already have this rank!");
                                    return false;
                                }
                                SenderClan.SendMessageForOnlinePlayers(String.format(
                                        "Player %s was promoted to %s", tempClanMember.getPlayerName(), ClanRole.OFFICER.toString()));
                            }
                            else {
                                sender.sendMessage("You rank is too low to do that!");
                            }
                        }
                        else if (tempClanMember.getMemberRole() == ClanRole.OFFICER) {
                            if (SenderRole == ClanRole.LEADER) {
                                SenderClan.ChangeLeader(tempClanMember.getPlayerName());
                                SenderClan.SendMessageForOnlinePlayers(String.format(
                                        "Leader is changed! New leader of clan is %s", tempClanMember.getPlayerName()));
                            }
                            else {
                                sender.sendMessage("You rank is too low to do that!");
                            }
                        }
                        else {
                            sender.sendMessage("Nothing changed, :(");
                        }
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("demote")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                        ClanMember tempClanMember = SenderClan.getClanMember(args[1]);
                        if (tempClanMember == null) {
                            sender.sendMessage("This player not in your clan!");
                            return false;
                        }
                        if (tempClanMember.getMemberRole() == ClanRole.MEMBER) {
                            if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.ROOKIE)) {
                                sender.sendMessage("This Player already have this rank!");
                                return false;
                            }
                            SenderClan.SendMessageForOnlinePlayers(String.format(
                                    "Player %s was demoted to %s", tempClanMember.getPlayerName(), ClanRole.ROOKIE.toString()));
                        }
                        else if (tempClanMember.getMemberRole() == ClanRole.OFFICER) {
                            if (SenderRole == ClanRole.LEADER) {
                                if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.MEMBER)) {
                                    sender.sendMessage("This Player already have this rank!");
                                    return false;
                                }
                                SenderClan.SendMessageForOnlinePlayers(String.format(
                                        "Player %s was demoted to %s", tempClanMember.getPlayerName(), ClanRole.MEMBER.toString()));
                            }
                            else {
                                sender.sendMessage("You rank is too low to do that!");
                            }
                        }
                        else {
                            sender.sendMessage("Nothing changed, :(");
                        }
                    }
                    else {
                        sender.sendMessage("You rank is too low to do that!");
                    }
                }
                else {
                    sender.sendMessage("You havent Clan!");
                }
                return true;
            }
        }
        return true;
    }
}
