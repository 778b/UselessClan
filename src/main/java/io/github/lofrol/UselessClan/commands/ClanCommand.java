package io.github.lofrol.UselessClan.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.ClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.*;

public final class ClanCommand extends Command {

    private ClanManager ManagerPtr;
    public static ClanCommand CreateDefaultInst(ClanManager manager) {
        ClanCommand tempInst = new ClanCommand("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerCommand() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    private ClanCommand(@NotNull String name, @NotNull String description,
                          @NotNull String usageMessage,
                          @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        Clan SenderClan = ManagerPtr.FindClanToPlayer(tempPlayer.getName());
        ClanRole SenderRole = null;
        if (SenderClan != null) {
            SenderRole = SenderClan.getMemberRole(tempPlayer.getName());
        }

        int size = args.length;
        if (size == 0) {
            // just only /Clan
            ChatSender.MessageTo(tempPlayer,"UselessClan", "Use command &a/Clan help&b,%s for access to clan system");
        }
        else if (size == 1) {
            if (args[0].equalsIgnoreCase("top")) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "########## CLAN TOP ##########");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "# ClanName              Bank #");
                for (Clan tempClan : ManagerPtr.getServerClans().values()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# %s          %s ", tempClan.getNameClan(), tempClan.getMoneyClan()));
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("help")) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "############# CLAN HELP #############");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan help&b - to call this menu");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan top&b - top of all clans");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan create %name&b - to create your own clan with name %name");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan leave&b - to leave from your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan join %name&b - to send request for join the clan %name");
                if (UselessClan.EconomyPtr != null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan deposit %value&b - to deposit money to your clan");
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan withdraw %value&b - to withdraw money from your clan");
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan info&b - to info about your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan mates&b - to execute list of clanmates");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan home&b - to teleport to home of your clan");
                if (SenderClan == null) return false;
                if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan claim&b - to claim territory what you selected to clan territory");
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan requests&b - to see list of all requests to join your clan");
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan accept %name&b - to accept %name for join to your clan");
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan kick %name&b - to kick player %name from your clan");
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan promote %name&b - to promote player %name of your clan");
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan demote %name&b - to demote player %name of your clan");
                }
                if (SenderRole.ordinal() >= SenderClan.getSettingsClan().HomeChangerMinRole.ordinal()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan sethome&b - to set home location of your clan");
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("version")) {
                PluginDescriptionFile tempDescr =  ManagerPtr.getOwnerPlugin().getDescription();
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("Plugin version is %s" ,tempDescr.getVersion()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("create")) {
                if (SenderClan != null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou cant create clan while you have been in clan!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou forgot about clan %name, use &a/Clan create %name&b, %name = name of your clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (SenderClan != null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HaveClanMessage);
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou forgot about clan %name, use &a/Clan join %name&b, %name = name of clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("deposit")) {
                if (UselessClan.EconomyPtr == null) return false;

                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou forgot about value of deposit, use &a/clan deposit %money");
                return true;
            }
            else if (args[0].equalsIgnoreCase("withdraw")) {
                if (UselessClan.EconomyPtr == null) return false;

                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                if (SenderRole.ordinal() < SenderClan.getSettingsClan().MinRoleForWithdraw.ordinal()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou forgot about value of withdraw, use &a/clan withdraw %money");
                return true;
            }
            else if (args[0].equalsIgnoreCase("leave")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }

                ManagerPtr.CalculateClanLevel(SenderClan);
                if (SenderRole == ClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou cant leave from clan, because you are Leader of this clan");
                    return false;
                }
                SenderClan.PlayerLeavedFromClan(tempPlayer);
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("You successfully leaved from &6%s", SenderClan.getNameClan()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("home")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                Location tempHome = SenderClan.getHomeClan();
                if (tempHome == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYour clan doesnt have home!");
                    return false;
                }
                if (!tempPlayer.getWorld().getName().equals("world")) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou cant teleport clan home from this world!");
                    return false;
                }
                tempPlayer.teleport(tempHome);
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&aYou teleported to clan home!");
                return true;
            }
            else if (args[0].equalsIgnoreCase("sethome")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                if (SenderRole.ordinal() >= SenderClan.getSettingsClan().HomeChangerMinRole.ordinal() || SenderRole == ClanRole.LEADER) {
                    if (!tempPlayer.getWorld().getName().equals("world")) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou cant set clan home in this world!");
                        return false;
                    }
                    Location tempLoc = tempPlayer.getLocation();
                    SenderClan.setHomeClan(tempLoc);
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&aClan home set successfully!");
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("mates")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "########## CLANMATES ##########");
                for (ClanMember tempMember: SenderClan.getMembers()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", String.format(
                            "# %s      %s", tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("info")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "########## CLAN INFO ##########");
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Name: %s", SenderClan.getNameClan()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Prefix: %s", SenderClan.getPrefixClan()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Level: %s", SenderClan.getClanLevel()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# LeaderName: %s", SenderClan.getLeaderName()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Count of Members: %s", SenderClan.getMembers().size()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Money: %s", SenderClan.getMoneyClan()));
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# Your rank: %s", SenderRole.toString()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("requests")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                    if (SenderClan.getRequests().size() > 0) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", "######## CLAN REQUESTS ########");
                        for (String tempMemberName: SenderClan.getRequests()) {
                            ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# %s", tempMemberName));
                        }
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", "0 requests for join to your clan");
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    return false;
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan",
                                "&cYou forgot about player %name, use &a/Clan kick %name&b, %name = name of player, which you want to kick");
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("kick")) {
                if (SenderClan != null) {
                    if (SenderRole == SenderClan.getSettingsClan().RoleCanKick) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan",
                                "&cYou forgot about player %name, use &a/Clan kick %name&b, %name = name of player, which you want to kick");
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("promote")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan",
                                "&cYou forgot about player %name and %rank, use &a/Clan promote %name %rank");
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("demote")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", HavntClanMessage);
                    return false;
                }
                if (SenderRole == ClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cYou forgot about player %name and %rank, use &a/Clan promote %name %rank");
                }
                else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", LowRankMessage);
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("claim")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", HavntClanMessage);
                    return false;
                }

                if (SenderRole.ordinal() < ClanRole.OFFICER.ordinal()) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", LowRankMessage);
                    return false;
                }

                if (SenderClan.getClanLevel() < 1) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&c0 level clan cant claim a territory!");
                    return false;
                }

                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionManager tempRegionManager = container.get(BukkitAdapter.adapt(tempPlayer.getWorld()));

                LocalPlayer tempLocalPlayer = WorldGuardPlugin.inst().wrapPlayer(tempPlayer);

                LocalSession tempLocalSession = WorldEdit.getInstance().getSessionManager().get(tempLocalPlayer);

                if (tempLocalSession == null || tempLocalSession.getSelectionWorld() == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",  "&cPlease select an area first.");
                    return false;
                }
                Region tempRegion;

                try {
                    tempRegion = tempLocalSession.getRegionSelector(tempLocalSession.getSelectionWorld()).getRegion();
                } catch (IncompleteRegionException e) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",  "&cInternal error, RIP #1");
                    throw new RuntimeException(e);
                }

                if (tempRegionManager == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",  "&cInternal error, RIP #2");
                    return false;
                }
                if (tempRegion == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",  "&cInternal error, RIP #3");
                    return false;
                }
                {
                    double tempDistance = Math.sqrt(Math.pow(tempRegion.getMaximumPoint().getBlockX() - tempRegion.getMinimumPoint().getBlockX(), 2)
                            + Math.pow(tempRegion.getMaximumPoint().getBlockZ() - tempRegion.getMinimumPoint().getBlockZ(), 2));
                    if (tempDistance > SenderClan.getClanLevel() * 50) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                String.format("&cYour clan cant have more than &a%d&a distance between points, but you selected &a%s&a", SenderClan.getClanLevel() * 75, tempDistance));
                        return false;
                    }
                }

                BlockVector3 FirstPoint = BlockVector3.at(tempRegion.getMinimumPoint().getBlockX(),-100,tempRegion.getMinimumPoint().getZ());
                BlockVector3 SecondPoint = BlockVector3.at(tempRegion.getMaximumPoint().getBlockX(),300,tempRegion.getMaximumPoint().getZ());
                ProtectedRegion tempProtectedRegion = new ProtectedCuboidRegion(SenderClan.getPrefixClan(), FirstPoint, SecondPoint);

                ApplicableRegionSet tempRegionApp = tempRegionManager.getApplicableRegions(tempProtectedRegion);

                if (tempRegionApp.size() > 0) {
                    if (tempRegionApp.size() == 1) {
                        ProtectedRegion tempProtected = tempRegionApp.getRegions().iterator().next();
                        if (!tempProtected.getId().equalsIgnoreCase(SenderClan.getPrefixClan())) {
                            ChatSender.MessageTo(tempPlayer, "UselessClan",
                                    "&cSelected territory overlap another region!");
                            return false;
                        }
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                "&cSelected territory overlap another region!");
                        return false;
                    }
                }
                if (SenderClan.getClanRegionId() != null) {
                    ProtectedRegion tempPrevious = tempRegionManager.getRegion(SenderClan.getClanRegionId());
                    if (tempPrevious != null) {
                        tempRegionManager.removeRegion(SenderClan.getPrefixClan(), RemovalStrategy.REMOVE_CHILDREN);
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                "&aYour previous region was deleted ...");
                    }
                }
                DefaultDomain tempDomain = new DefaultDomain();

                for (ClanMember tempMember : SenderClan.getMembers()) {
                    tempDomain.addPlayer(tempMember.getPlayerName());
                }

                tempProtectedRegion.setMembers(tempDomain);

                tempRegionManager.addRegion(tempProtectedRegion);
                SenderClan.setClanRegionId(tempProtectedRegion.getId());
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&aYou Successfully claim this territory :)");
                return true;
            }
            else {
                ChatSender.MessageTo(tempPlayer,"UselessClan",
                        "&cInvalid command. Use command &a/Clan help&b, for access to clan system");
                return false;
            }
        }
        else if (size == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (SenderClan != null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HaveClanMessage);
                    return false;
                }

                if (args[1].length() < 3) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYour name is too short, name must be >3 symbols");
                    return false;
                }
                else if (args[1].length() > 6) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYour name is too long, name must be <7 symbols");
                    return false;
                }
                // check for bad symbols
                for (char tempChar : args[1].toCharArray()) {
                    if (!(tempChar == 95 || (tempChar >= 65 && tempChar <= 90) ||
                            (tempChar >= 97 && tempChar <= 122) ||
                            (tempChar >= 48 && tempChar <= 57))) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", "&cInvalid clan name, use [A-Z; a-z; _; 0-9]");
                        return false;
                    }
                }
                // check another clan name collision
                if (ManagerPtr.getServerClans().get(args[1]) != null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cClan with this name already exist!");
                    return false;
                }
                // if had Economy extension
                if (UselessClan.EconomyPtr != null) {
                    double moneyToClan = 10000;
                    if (!UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToClan)) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("&cFor create your own clan you must have more than %s$", moneyToClan));
                        return false;
                    }
                    UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToClan);
                }
                // Creating new clan
                Clan NewClan = new Clan(args[1], tempPlayer.getName());
                ManagerPtr.getServerClans().put(args[1], NewClan);

                NewClan.PlayerJoinToClan(ClanRole.LEADER, tempPlayer.getName());
                ManagerPtr.RegisterOnlineClanPlayer(NewClan, tempPlayer);
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("Clan %s was created successfully!", args[1]));
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (SenderClan != null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou cant join clan while you have been in clan!");
                    return false;
                }
                Clan ClanToRequest = ManagerPtr.getClanByName(args[1]);
                if (ClanToRequest != null) {
                    if (!ClanToRequest.SendRequestForJoin(tempPlayer.getName())) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou already sent request for join to this clan!");
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cInvalid clan name!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer,"UselessClan", "You send request for join to this clan, wait until leader or officer accept this request");
                ClanToRequest.SendMessageForOnlineOfficers(String.format("Player %s was send request for join to you clan, type &a/clan requests", tempPlayer.getName()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("deposit")) {
                if (UselessClan.EconomyPtr == null) return false;

                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                double moneyToDeposit = Double.parseDouble(args[1]);
                if (moneyToDeposit <= 0 || !UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToDeposit)) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cWrong money count!");
                    return false;
                }
                SenderClan.DepositMoneyToClan(moneyToDeposit);
                ClanMember tempMember = SenderClan.getClanMember(tempPlayer.getName());
                tempMember.addGeneralPlayerDeposit(moneyToDeposit);

                UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToDeposit);
                SenderClan.SendMessageForOnlinePlayers(String.format("player &a%s&b deposit &a%s&b to your clan!", tempPlayer.getName(), moneyToDeposit));
                return true;
            }
            else if (args[0].equalsIgnoreCase("withdraw")) {
                if (UselessClan.EconomyPtr == null) return false;

                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                if (SenderRole.ordinal() < SenderClan.getSettingsClan().MinRoleForWithdraw.ordinal()) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    return false;
                }
                double moneyToWithdraw = Double.parseDouble(args[1]);
                if (moneyToWithdraw  <= 0) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "&cWrong money count! Use [0;+inf)");
                }
                
                double tempValue = SenderClan.WithdrawMoneyFromClan(moneyToWithdraw);
                if (tempValue == 0) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("&cYou cant withdraw &a%s&c from you clan", moneyToWithdraw));
                    return false;
                }

                ClanMember tempMember = SenderClan.getClanMember(tempPlayer.getName());
                tempMember.addGeneralPlayerDeposit(-moneyToWithdraw);
                UselessClan.EconomyPtr.depositPlayer(getOfflinePlayer(tempPlayer.getName()), tempValue);
                SenderClan.SendMessageForOnlinePlayers(String.format("player &a%s&b withdraw &a%s&b from clan balance", tempPlayer.getName(), tempValue));
                return true;
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                        String AcceptedPlayerName = args[1];
                        if (ManagerPtr.FindClanToPlayer(AcceptedPlayerName) != null) {
                            ChatSender.MessageTo(tempPlayer,"UselessClan", "&cThis player already in Clan");
                            return false;
                        }
                        if (!SenderClan.HaveRequestFromPlayer(AcceptedPlayerName)) {
                            ChatSender.MessageTo(tempPlayer,"UselessClan", "&cThis player didn't send request to you Clan");
                            return false;
                        }
                        SenderClan.PlayerJoinToClan(ClanRole.ROOKIE, AcceptedPlayerName);
                        ManagerPtr.CalculateClanLevel(SenderClan);
                        SenderClan.RemoveFromRequest(AcceptedPlayerName);
                        Player AcceptedPlayer = getPlayer(AcceptedPlayerName);
                        if (SenderClan.getClanRegionId() != null) {
                            RegionContainer tempRegionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                            World tempWorld = getServer().getWorld("world");
                            if (tempWorld == null) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cError cant add new player to region! #1");
                                return false;
                            }
                            RegionManager tempRegionManager = tempRegionContainer.get(BukkitAdapter.adapt(tempWorld));
                            if (tempRegionManager == null) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cError cant add new player to region! #2");
                                return false;
                            }
                            ProtectedRegion tempRegion = tempRegionManager.getRegion(SenderClan.getClanRegionId());
                            if (tempRegion == null) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cError cant add new player to region! #3");
                                return false;
                            }
                            tempRegion.getMembers().addPlayer(AcceptedPlayerName);
                        }
                        if (AcceptedPlayer != null) {
                            ManagerPtr.RegisterOnlineClanPlayer(SenderClan, AcceptedPlayer);
                            SenderClan.SendMessageForOnlinePlayers(String.format(
                                    "Player &a%s&b joined to your clan!", AcceptedPlayerName));
                        }
                        else {
                            SenderClan.SendMessageForOnlinePlayers(String.format(
                                    "Player &a%s&b accepted to your clan!", AcceptedPlayerName));
                        }
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                        return false;
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                    return false;
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("kick")) {
                if (SenderClan != null) {
                    if (SenderRole.ordinal() >= SenderClan.getSettingsClan().RoleCanKick.ordinal()) {
                        ClanMember tempMember = SenderClan.getClanMember(args[1]);
                        if (tempMember == null) {
                            ChatSender.MessageTo(tempPlayer,"UselessClan", "&cCant find this player in your clan!");
                            return false;
                        }
                        SenderClan.PlayerLeavedFromClan(tempMember.getPlayerName());
                        ManagerPtr.CalculateClanLevel(SenderClan);
                        SenderClan.SendMessageForOnlinePlayers(String.format(
                                "&cPlayer %s was kicked from your clan!", tempMember.getPlayerName()));
                        if (SenderClan.getClanRegionId() != null) {
                            RegionContainer tempRegionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                            World tempWorld = getServer().getWorld("world");
                            if (tempWorld == null) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cError cant remove player from region! #1");
                                return false;
                            }
                            RegionManager tempRegionManager = tempRegionContainer.get(BukkitAdapter.adapt(tempWorld));
                            if (tempRegionManager == null) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cError cant remove player from region! #2");
                                return false;
                            }
                            ProtectedRegion tempRegion = tempRegionManager.getRegion(SenderClan.getClanRegionId());
                            if (tempRegion == null) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cError cant remove player from region! #3");
                                return false;
                            }
                            tempRegion.getMembers().removePlayer(tempMember.getPlayerName());
                        }
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                }
                return true;
            }
            else if (args[0].equalsIgnoreCase("promote")) {
                if (SenderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", HavntClanMessage);
                    return false;
                }
                if (!(SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", LowRankMessage);
                    return false;
                }
                ClanMember tempClanMember = SenderClan.getClanMember(args[1]);
                if (tempClanMember == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", WrongClanMessage);
                    return false;
                }
                if (tempClanMember.getMemberRole() == ClanRole.ROOKIE) {
                    if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.MEMBER)) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", HaveRankMessage);
                        return false;
                    }
                    SenderClan.SendMessageForOnlinePlayers(String.format(
                            "&aPlayer %s was promoted to %s", tempClanMember.getPlayerName(), ClanRole.MEMBER));
                }
                else if (tempClanMember.getMemberRole() == ClanRole.MEMBER) {
                    if (SenderRole == ClanRole.LEADER) {
                        if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.OFFICER)) {
                            ChatSender.MessageTo(tempPlayer, "UselessClan", HaveRankMessage);
                            return false;
                        }
                        SenderClan.SendMessageForOnlinePlayers(String.format(
                                "&aPlayer %s was promoted to %s", tempClanMember.getPlayerName(), ClanRole.OFFICER));
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", LowRankMessage);
                    }
                }
                else if (tempClanMember.getMemberRole() == ClanRole.OFFICER) {
                    if (SenderRole == ClanRole.LEADER) {
                        SenderClan.ChangeLeader(tempClanMember.getPlayerName());
                        SenderClan.SendMessageForOnlinePlayers(String.format(
                                "&aLeader is changed! New leader of clan is %s", tempClanMember.getPlayerName()));
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", LowRankMessage);
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Nothing changed, :(");
                }

                return true;
            }
            else if (args[0].equalsIgnoreCase("demote")) {
                if (SenderClan != null) {
                    if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                        ClanMember tempClanMember = SenderClan.getClanMember(args[1]);
                        if (tempClanMember == null) {
                            ChatSender.MessageTo(tempPlayer,"UselessClan", WrongClanMessage);
                            return false;
                        }
                        if (tempClanMember.getMemberRole() == ClanRole.MEMBER) {
                            if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.ROOKIE)) {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", HaveRankMessage);
                                return false;
                            }
                            SenderClan.SendMessageForOnlinePlayers(String.format(
                                    "&cPlayer %s was demoted to %s", tempClanMember.getPlayerName(), ClanRole.ROOKIE));
                        }
                        else if (tempClanMember.getMemberRole() == ClanRole.OFFICER) {
                            if (SenderRole == ClanRole.LEADER) {
                                if (!SenderClan.ChangeMemberRole(tempClanMember.getPlayerName(), ClanRole.MEMBER)) {
                                    ChatSender.MessageTo(tempPlayer,"UselessClan", HaveRankMessage);
                                    return false;
                                }
                                SenderClan.SendMessageForOnlinePlayers(String.format(
                                        "&cPlayer %s was demoted to %s", tempClanMember.getPlayerName(), ClanRole.MEMBER));
                            }
                            else {
                                ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                            }
                        }
                        else {
                            ChatSender.MessageTo(tempPlayer,"UselessClan", "Nothing changed, :(");
                        }
                    }
                    else {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", LowRankMessage);
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", HavntClanMessage);
                }
                return true;
            }
            else {
                ChatSender.MessageTo(tempPlayer,"UselessClan",
                        "&cInvalid command. Use command &a/Clan help&c, for access to clan system");
                return false;
            }
        }
        return true;
    }

    // Static messages
    private static final String HavntClanMessage = "&cYou havent Clan!";
    private static final String HaveClanMessage = "&cYou already have Clan!";
    private static final String WrongClanMessage = "&cThis player not in your clan!";
    private static final String LowRankMessage = "&cYou rank is too low to do that!";
    private static final String HaveRankMessage = "&cThis Player already have this rank!";
}
