package io.github.lofrol.UselessClan.ClanCommands.Commands.UserCommands;

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
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import io.github.lofrol.UselessClan.Utils.TopListClan;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.bukkit.Bukkit.*;
import static org.bukkit.Bukkit.getServer;

public class BaseClanCommands {
    private static final Map<String, CommandBase> ClanCommands = Map.ofEntries(
            entry("help",       new helpUserCommand()),
            entry("requests",   new requestsUserCommand()),
            entry("top",        new topUserCommand()),
            entry("claim",      new claimUserCommand()),
            entry("create",     new createUserCommand()),
            entry("delete",     new deleteUserCommand()),
            entry("leave",      new leaveUserCommand()),
            entry("deposit",    new depositUserCommand()),
            entry("withdraw",   new withdrawUserCommand()),
            entry("home",       new homeUserCommand()),
            entry("sethome",    new sethomeUserCommand()),
            entry("info",       new infoUserCommand()),
            entry("mates",      new matesUserCommand()),
            entry("accept",     new acceptUserCommand()),
            entry("join",       new joinUserCommand()),
            entry("kick",       new kickUserCommand()),
            entry("promote",    new promoteUserCommand()),
            entry("demote",     new demoteUserCommand()),
            entry("setting",    new settingsUserCommand())
    );

    public static CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

    public static @NotNull Collection<PlayerCommandBase> getExecutableCommands(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        Collection<PlayerCommandBase> tempCommandArray = new ArrayList<>();
        for (CommandBase tempBase : ClanCommands.values()) {
            if (tempBase instanceof PlayerCommandBase tempPlayerCommand) {
                if (tempPlayerCommand.havePermission(tempPlayer, senderClan, senderRole)) {
                    tempCommandArray.add(tempPlayerCommand);
                }
            }
        }
        return tempCommandArray;
    }

    private static class helpUserCommand extends PlayerCommandBase {
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

    private static class requestsUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan requests&b - to see list of all requests to join your clan\"";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal());
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou havent Clan!");
                return false;
            }
            if (SenderRole == EClanRole.LEADER || SenderRole == EClanRole.OFFICER) {
                if (senderClan.getRequests().size() > 0) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "######## CLAN REQUESTS ########");
                    for (String tempMemberName : senderClan.getRequests()) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# %s", tempMemberName));
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "0 requests for join to your clan");
                }
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }
            return true;
        }
    }

    private static class topUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan top&b - top of all clans";
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
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&b########## CLAN TOP 1 ##########");
                for (int i = tempRow; i < tempTopListClans.size(); ++i) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("&b# %s : &e%d : &a%d",
                                    tempTopListClans.get(i).ClanName, tempTopListClans.get(i).ClanLevel, tempTopListClans.get(i).ClanMoney));
                    ++tempRow;
                    if (tempRow == numClansInOnePage) break;
                }
                if (tempTopListClans.size() - numClansInOnePage > 0) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "type &a/Clan top 2&b - to show commands in page 2");
                }
                return true;
            }
            else if (args.length > 1) {
                //  /Clan help (1,2,3...n)
                int pageNum = Integer.parseInt(args[1]);
                if (pageNum > 0 && pageNum < tempTopListClans.size() && (pageNum - 1) * numClansInOnePage < tempTopListClans.size()) {
                    int tempRow = 0;
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            String.format("&b########## CLAN TOP %d ##########", pageNum));
                    for (int i = (pageNum - 1) * numClansInOnePage; i < tempTopListClans.size(); ++i) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("&b# %s : &e%d : &a%d",
                                tempTopListClans.get(i).ClanName, tempTopListClans.get(i).ClanLevel, tempTopListClans.get(i).ClanMoney));
                        ++tempRow;
                        if (tempRow == numClansInOnePage) break;
                    }
                    if (tempTopListClans.size() - (pageNum * numClansInOnePage) > 0) {
                        final int pageNumPlus = pageNum + 1;
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                String.format("type &a/Clan top %d&b - to show commands in page %d", pageNumPlus, pageNumPlus));
                    }
                    return true;
                }
            }
            //  /Clan help (not a number or too big)
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis page of clans top isn't found");
            return false;
        }
    }

    private static class claimUserCommand extends PlayerCommandBase {
        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan claim&b - to claim territory what you selected to clan territory";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole == EClanRole.OFFICER || senderRole == EClanRole.LEADER);
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }

            if (SenderRole.ordinal() < EClanRole.OFFICER.ordinal()) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }

            if (senderClan.getClanLevel() < 1) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&c0 level clan cant claim a territory!");
                return false;
            }

            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager tempRegionManager = container.get(BukkitAdapter.adapt(tempPlayer.getWorld()));

            LocalPlayer tempLocalPlayer = WorldGuardPlugin.inst().wrapPlayer(tempPlayer);

            LocalSession tempLocalSession = WorldEdit.getInstance().getSessionManager().get(tempLocalPlayer);

            if (tempLocalSession == null || tempLocalSession.getSelectionWorld() == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cPlease select an area first.");
                return false;
            }
            Region tempRegion;

            try {
                tempRegion = tempLocalSession.getRegionSelector(tempLocalSession.getSelectionWorld()).getRegion();
            } catch (IncompleteRegionException e) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInternal error, RIP #1");
                throw new RuntimeException(e);
            }

            if (tempRegionManager == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInternal error, RIP #2");
                return false;
            }
            if (tempRegion == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInternal error, RIP #3");
                return false;
            }
            {
                double tempDistance = Math.sqrt(Math.pow(tempRegion.getMaximumPoint().getBlockX() - tempRegion.getMinimumPoint().getBlockX(), 2)
                        + Math.pow(tempRegion.getMaximumPoint().getBlockZ() - tempRegion.getMinimumPoint().getBlockZ(), 2));
                if (tempDistance > senderClan.getClanLevel() * 50) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            String.format("&cYour clan cant have more than &a%d&a distance between points, but you selected &a%s&a", senderClan.getClanLevel() * 75, tempDistance));
                    return false;
                }
            }

            BlockVector3 FirstPoint = BlockVector3.at(tempRegion.getMinimumPoint().getBlockX(), -100, tempRegion.getMinimumPoint().getZ());
            BlockVector3 SecondPoint = BlockVector3.at(tempRegion.getMaximumPoint().getBlockX(), 300, tempRegion.getMaximumPoint().getZ());
            ProtectedRegion tempProtectedRegion = new ProtectedCuboidRegion(senderClan.getPrefixClan(), FirstPoint, SecondPoint);

            ApplicableRegionSet tempRegionApp = tempRegionManager.getApplicableRegions(tempProtectedRegion);

            if (tempRegionApp.size() > 0) {
                if (tempRegionApp.size() == 1) {
                    ProtectedRegion tempProtected = tempRegionApp.getRegions().iterator().next();
                    if (!tempProtected.getId().equalsIgnoreCase(senderClan.getPrefixClan())) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                "&cSelected territory overlap another region!");
                        return false;
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cSelected territory overlap another region!");
                    return false;
                }
            }
            if (senderClan.getClanRegionId() != null) {
                ProtectedRegion tempPrevious = tempRegionManager.getRegion(senderClan.getClanRegionId());
                if (tempPrevious != null) {
                    tempRegionManager.removeRegion(senderClan.getPrefixClan(), RemovalStrategy.REMOVE_CHILDREN);
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&aYour previous region was deleted ...");
                }
            }
            DefaultDomain tempDomain = new DefaultDomain();

            for (ClanMember tempMember : senderClan.getMembers()) {
                tempDomain.addPlayer(tempMember.getPlayerName());
            }

            tempProtectedRegion.setMembers(tempDomain);

            tempRegionManager.addRegion(tempProtectedRegion);
            senderClan.setClanRegionId(tempProtectedRegion.getId());
            ChatSender.MessageTo(tempPlayer, "UselessClan",
                    "&aYou Successfully claim this territory :)");
            return true;
        }
    }

    private static class createUserCommand extends PlayerCommandBase {
        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan create %name&b - to create your own clan with name %name";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return true;
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan != null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant create clan while you have been in clan!");
                return false;
            }

            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about clan %name, use &a/Clan create %name&b, %name = name of your clan");
                return false;
            }

            if (args[1].length() < 3) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYour name is too short, name must be >3 symbols");
                return false;
            } else if (args[1].length() > 6) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYour name is too long, name must be <7 symbols");
                return false;
            }
            // check for bad symbols
            for (char tempChar : args[1].toCharArray()) {
                if (!(tempChar == 95 || (tempChar >= 65 && tempChar <= 90) ||
                        (tempChar >= 97 && tempChar <= 122) ||
                        (tempChar >= 48 && tempChar <= 57))) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInvalid clan name, use [A-Z; a-z; _; 0-9]");
                    return false;
                }
            }
            // check another clan name collision
            if (UselessClan.getMainManager().getServerClans().get(args[1]) != null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cClan with this name already exist!");
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
            UselessClan.getMainManager().CreateClan(args[1], tempPlayer);
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("Clan %s was created successfully!", args[1]));
            return true;
        }
    }

    private static class deleteUserCommand extends PlayerCommandBase {
        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan delete&b - to delete your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole == EClanRole.LEADER);
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

            if (args.length == 1) {
                if (SenderRole != EClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }
                UselessClan.getMainManager().DeleteClan(senderClan);
            }
            return true;
        }
    }

    private static class leaveUserCommand extends PlayerCommandBase {
        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan leave&b - to leave from your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return true;
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

            UselessClan.getMainManager().CalculateClanLevel(senderClan);
            if (SenderRole == EClanRole.LEADER) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant leave from clan, because you are Leader of this clan");
                return false;
            }
            senderClan.PlayerLeavedFromClan(tempPlayer);
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("You successfully leaved from &6%s", senderClan.getNameClan()));
            return true;
        }
    }

    private static class depositUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan deposit %value&b - to deposit money to your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (UselessClan.EconomyPtr != null);
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }

            if (args.length == 1) {
                if (UselessClan.EconomyPtr == null) return false;

                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about value of deposit, use &a/clan deposit %money");
            } else {
                if (UselessClan.EconomyPtr == null) return false;

                double moneyToDeposit = Double.parseDouble(args[1]);
                if (moneyToDeposit <= 0 || !UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToDeposit)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cWrong money count!");
                    return false;
                }
                senderClan.DepositMoneyToClan(moneyToDeposit);
                ClanMember tempMember = senderClan.getClanMember(tempPlayer.getName());
                tempMember.addGeneralPlayerDeposit(moneyToDeposit);

                UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToDeposit);
                senderClan.SendMessageForOnlinePlayers(String.format("player &a%s&b deposit &a%s&b to your clan!", tempPlayer.getName(), moneyToDeposit));
            }
            return true;
        }
    }

    private static class withdrawUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan withdraw %value&b - to withdraw money from your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (UselessClan.EconomyPtr != null && senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanWithdraw.ordinal());
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (args.length == 1) {
                if (UselessClan.EconomyPtr == null) return false;

                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (SenderRole.ordinal() < senderClan.getSettingsClan().RoleCanWithdraw.ordinal()) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about value of withdraw, use &a/clan withdraw %money");
            } else {
                if (UselessClan.EconomyPtr == null) return false;

                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (SenderRole.ordinal() < senderClan.getSettingsClan().RoleCanWithdraw.ordinal()) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }
                double moneyToWithdraw = Double.parseDouble(args[1]);
                if (moneyToWithdraw <= 0) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cWrong money count! Use [0;+inf)");
                    return false;
                }

                double tempValue = senderClan.WithdrawMoneyFromClan(moneyToWithdraw);
                if (tempValue == 0) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("&cYou cant withdraw &a%s&c from you clan", moneyToWithdraw));
                    return false;
                }

                ClanMember tempMember = senderClan.getClanMember(tempPlayer.getName());
                tempMember.addGeneralPlayerDeposit(-moneyToWithdraw);
                UselessClan.EconomyPtr.depositPlayer(getOfflinePlayer(tempPlayer.getName()), tempValue);
                senderClan.SendMessageForOnlinePlayers(String.format("player &a%s&b withdraw &a%s&b from clan balance", tempPlayer.getName(), tempValue));
            }
            return true;
        }
    }

    private static class homeUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan home&b - to teleport to home of your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return true;
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou havent Clan!");
                return false;
            }

            Location tempHome = senderClan.getHomeClan();
            if (tempHome == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYour clan doesnt have home!");
                return false;
            }

            if (!tempPlayer.getWorld().getName().equals("world")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant teleport clan home from this world!");
                return false;
            }

            tempPlayer.teleport(tempHome);
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&aYou teleported to clan home!");
            return true;
        }
    }

    private static class sethomeUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan sethome&b - to set home location of your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanSethome.ordinal());
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou havent Clan!");
                return false;
            }

            if (!(SenderRole.ordinal() >= senderClan.getSettingsClan().RoleCanSethome.ordinal() || SenderRole == EClanRole.LEADER)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }

            if (!tempPlayer.getWorld().getName().equals("world")) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant set clan home in this world!");
                return false;
            }

            Location tempLoc = tempPlayer.getLocation();
            senderClan.setHomeClan(tempLoc);
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&aClan home set successfully!");
            return true;
        }
    }

    private static class infoUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan info&b - to info about your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return true;
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou havent Clan!");
                return false;
            }

            ChatSender.MessageTo(tempPlayer, "UselessClan", "########## CLAN INFO ##########");
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Name: %s", senderClan.getNameClan()));
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Prefix: %s", senderClan.getPrefixClan()));
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Level: %s", senderClan.getClanLevel()));
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# LeaderName: %s", senderClan.getLeaderName()));
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Count of Members: %s", senderClan.getMembers().size()));
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Money: %d", (int) senderClan.getMoneyClan()));
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Your rank: %s", SenderRole.toString()));
            return true;
        }
    }

    private static class matesUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan mates&b - to execute list of clanmates";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return true;
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }

            ChatSender.MessageTo(tempPlayer, "UselessClan", "####### CLANMATES #######");
            for (ClanMember tempMember : senderClan.getMembers()) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", String.format(
                        "# %s &a%s %d", tempMember.getMemberRole().toString(), tempMember.getPlayerName(), (int) tempMember.getGeneralPlayerDeposit()));
            }
            return true;
        }
    }

    private static class acceptUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan accept %name&b - to accept %name for join to your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal());
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (args.length == 1) {
                if (senderClan != null) {
                    if (SenderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal()) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                "&cYou forgot about player %name, use &a/Clan kick %name&b, %name = name of player, which you want to kick");
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                }
            } else {
                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (!(SenderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal())) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }
                String AcceptedPlayerName = args[1];
                if (UselessClan.getMainManager().FindClanToPlayer(AcceptedPlayerName) != null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis player already in Clan");
                    return false;
                }
                if (!senderClan.HaveRequestFromPlayer(AcceptedPlayerName)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis player didn't send request to you Clan");
                    return false;
                }
                senderClan.PlayerJoinToClan(senderClan.getSettingsClan().DefaultJoinRole, AcceptedPlayerName);
                UselessClan.getMainManager().CalculateClanLevel(senderClan);
                senderClan.RemoveFromRequest(AcceptedPlayerName);
                Player AcceptedPlayer = getPlayer(AcceptedPlayerName);

                if (senderClan.getClanRegionId() != null) {
                    RegionContainer tempRegionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    World tempWorld = getServer().getWorld("world");
                    if (tempWorld == null) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cError cant add new player to region! #1");
                        return false;
                    }
                    RegionManager tempRegionManager = tempRegionContainer.get(BukkitAdapter.adapt(tempWorld));
                    if (tempRegionManager == null) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cError cant add new player to region! #2");
                        return false;
                    }
                    ProtectedRegion tempRegion = tempRegionManager.getRegion(senderClan.getClanRegionId());
                    if (tempRegion == null) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cError cant add new player to region! #3");
                        return false;
                    }
                    tempRegion.getMembers().addPlayer(AcceptedPlayerName);
                }
                if (AcceptedPlayer != null) {
                    UselessClan.getMainManager().RegisterOnlineClanPlayer(senderClan, AcceptedPlayer);
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            "Player &a%s&b joined to your clan!", AcceptedPlayerName));
                } else {
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            "Player &a%s&b accepted to your clan!", AcceptedPlayerName));
                }
            }
            return true;
        }
    }

    private static class joinUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan join %name&b - to send request for join the clan %name";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return true;
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            if (senderClan != null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou already have Clan!");
                return false;
            }

            if (args.length == 1) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about clan %name, use &a/Clan join %name&b, %name = name of clan");
            } else {
                Clan ClanToRequest = UselessClan.getMainManager().getClanByName(args[1]);
                if (ClanToRequest != null) {
                    if (!ClanToRequest.SendRequestForJoin(tempPlayer.getName())) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou already sent request for join to this clan!");
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInvalid clan name!");
                    return false;
                }
                ChatSender.MessageTo(tempPlayer, "UselessClan", "You send request for join to this clan, wait until leader or officer accept this request");
                ClanToRequest.SendMessageForOnlineOfficers(String.format("Player %s was send request for join to you clan, type &a/clan requests", tempPlayer.getName()));
            }
            return true;

        }

    }

    private static class kickUserCommand extends PlayerCommandBase {

        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan kick %name&b - to kick player %name from your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanKick.ordinal());
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (args.length == 1) {
                if (senderClan != null) {
                    if (SenderRole == senderClan.getSettingsClan().RoleCanKick) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                "&cYou forgot about player %name, use &a/Clan kick %name&b, %name = name of player, which you want to kick");
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                }
            } else {
                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (!(SenderRole.ordinal() >= senderClan.getSettingsClan().RoleCanKick.ordinal())) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }
                ClanMember tempMember = senderClan.getClanMember(args[1]);
                if (tempMember == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cCant find this player in your clan!");
                    return false;
                }
                senderClan.PlayerLeavedFromClan(tempMember.getPlayerName());
                UselessClan.getMainManager().CalculateClanLevel(senderClan);
                senderClan.SendMessageForOnlinePlayers(String.format(
                        "&cPlayer %s was kicked from your clan!", tempMember.getPlayerName()));

                if (senderClan.getClanRegionId() != null) {
                    RegionContainer tempRegionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    World tempWorld = getServer().getWorld("world");
                    if (tempWorld == null) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cError cant remove player from region! #1");
                        return false;
                    }
                    RegionManager tempRegionManager = tempRegionContainer.get(BukkitAdapter.adapt(tempWorld));
                    if (tempRegionManager == null) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cError cant remove player from region! #2");
                        return false;
                    }
                    ProtectedRegion tempRegion = tempRegionManager.getRegion(senderClan.getClanRegionId());
                    if (tempRegion == null) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cError cant remove player from region! #3");
                        return false;
                    }
                    tempRegion.getMembers().removePlayer(tempMember.getPlayerName());
                }
            }
            return true;
        }
    }

    private static class promoteUserCommand extends PlayerCommandBase {
        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan promote %name&b - to promote player %name of your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole == EClanRole.OFFICER || senderRole == EClanRole.LEADER);
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (args.length == 1) {
                if (senderClan != null) {
                    if (SenderRole == EClanRole.OFFICER || SenderRole == EClanRole.LEADER) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan",
                                "&cYou forgot about player %name, use &a/Clan promote %name");
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                }
            } else {
                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (!(SenderRole == EClanRole.OFFICER || SenderRole == EClanRole.LEADER)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }
                ClanMember tempClanMember = senderClan.getClanMember(args[1]);
                if (tempClanMember == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis player not in your clan!");
                    return false;
                }
                if (tempClanMember.getMemberRole() == EClanRole.ROOKIE) {
                    if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.MEMBER)) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis Player already have this rank!");
                        return false;
                    }
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            "&aPlayer %s was promoted to %s", tempClanMember.getPlayerName(), EClanRole.MEMBER));
                } else if (tempClanMember.getMemberRole() == EClanRole.MEMBER) {
                    if (SenderRole == EClanRole.LEADER) {
                        if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.OFFICER)) {
                            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis Player already have this rank!");
                            return false;
                        }
                        senderClan.SendMessageForOnlinePlayers(String.format(
                                "&aPlayer %s was promoted to %s", tempClanMember.getPlayerName(), EClanRole.OFFICER));
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    }
                } else if (tempClanMember.getMemberRole() == EClanRole.OFFICER) {
                    if (SenderRole == EClanRole.LEADER) {
                        senderClan.ChangeLeader(tempClanMember.getPlayerName());
                        senderClan.SendMessageForOnlinePlayers(String.format(
                                "&aLeader is changed! New leader of clan is %s", tempClanMember.getPlayerName()));
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Nothing changed, :(");
                }
            }
            return true;
        }
    }

    private static class demoteUserCommand extends PlayerCommandBase {
        @Override
        public @NotNull String commandDescription() {
            return "&a/Clan demote %name&b - to demote player %name of your clan";
        }

        @Override
        public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
            return (senderRole == EClanRole.OFFICER || senderRole == EClanRole.LEADER);
        }

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            EClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (args.length == 1) {
                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (SenderRole == EClanRole.LEADER) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan",
                            "&cYou forgot about player %name, use &a/Clan promote %name");
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                }
            } else {
                if (senderClan == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                    return false;
                }
                if (!(SenderRole == EClanRole.OFFICER || SenderRole == EClanRole.LEADER)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }

                ClanMember tempClanMember = senderClan.getClanMember(args[1]);
                if (tempClanMember == null) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis player not in your clan!");
                    return false;
                }
                if (tempClanMember.getMemberRole() == EClanRole.MEMBER) {
                    if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.ROOKIE)) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis Player already have this rank!");
                        return false;
                    }
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            "&cPlayer %s was demoted to %s", tempClanMember.getPlayerName(), EClanRole.ROOKIE));
                } else if (tempClanMember.getMemberRole() == EClanRole.OFFICER) {
                    if (SenderRole == EClanRole.LEADER) {
                        if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.MEMBER)) {
                            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis Player already have this rank!");
                            return false;
                        }
                        senderClan.SendMessageForOnlinePlayers(String.format(
                                "&cPlayer %s was demoted to %s", tempClanMember.getPlayerName(), EClanRole.MEMBER));
                    } else {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    }
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Nothing changed, :(");
                }
            }
            return true;
        }
    }

    private static class settingsUserCommand extends PlayerCommandBase {
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

}
