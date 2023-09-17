package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

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
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class claimUserCommand extends PlayerCommandBase {


    @Override
    public @NotNull String commandDescription() {
        return "Description.Claim";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (WorldGuardPlugin.inst() == null || senderClan == null) return false;
        return (senderRole.ordinal() >= EClanRole.OFFICER.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (WorldGuardPlugin.inst() == null) return false;

        EClanRole SenderRole = null;
        if (senderClan != null) {
            SenderRole = senderClan.getMemberRole(tempPlayer.getName());
        }

        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (SenderRole.ordinal() < EClanRole.OFFICER.ordinal()) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.WrongRank");
            return false;
        }

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager tempRegionManager = container.get(BukkitAdapter.adapt(tempPlayer.getWorld()));

        LocalPlayer tempLocalPlayer = WorldGuardPlugin.inst().wrapPlayer(tempPlayer);

        LocalSession tempLocalSession = WorldEdit.getInstance().getSessionManager().get(tempLocalPlayer);

        if (tempLocalSession == null || tempLocalSession.getSelectionWorld() == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "WG.NoSelectedAreaToClaim");
            return false;
        }
        Region tempRegion;

        try {
            tempRegion = tempLocalSession.getRegionSelector(tempLocalSession.getSelectionWorld()).getRegion();
        } catch (IncompleteRegionException e) {
            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "<red>Internal error, RIP #1");
            throw new RuntimeException(e);
        }

        if (tempRegionManager == null) {
            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "<red>Internal error, RIP #2");
            return false;
        }
        if (tempRegion == null) {
            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "<red>Internal error, RIP #3");
            return false;
        }

        {
            double tempDistance = Math.sqrt(Math.pow(tempRegion.getMaximumPoint().getBlockX() - tempRegion.getMinimumPoint().getBlockX(), 2)
                    + Math.pow(tempRegion.getMaximumPoint().getBlockZ() - tempRegion.getMinimumPoint().getBlockZ(), 2));
            // @todo distance from config
            if (tempDistance > (senderClan.getClanLevel() + 1) * 75) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "WG.SelectedAreaIsTooBig"), (senderClan.getClanLevel() + 1) * 75, (int)tempDistance));
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
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "WG.ClaimZoneOverlap");
                    return false;
                }
            }
            else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "WG.ClaimZoneOverlap");
                return false;
            }
        }
        if (senderClan.getClanRegionId() != null) {
            ProtectedRegion tempPrevious = tempRegionManager.getRegion(senderClan.getClanRegionId());
            if (tempPrevious != null) {
                tempRegionManager.removeRegion(senderClan.getPrefixClan(), RemovalStrategy.REMOVE_CHILDREN);
                ChatSender.MessageTo(tempPlayer, "UselessClan", "WG.ClaimDeletionNotify");
            }
        }

        DefaultDomain tempDomain = new DefaultDomain();

        for (ClanMember tempMember : senderClan.getMembers()) {
            tempDomain.addPlayer(tempMember.getPlayerName());
        }

        tempProtectedRegion.setMembers(tempDomain);

        tempRegionManager.addRegion(tempProtectedRegion);

        {
            Location tempHome = senderClan.getHomeClan();
            if (tempHome != null) {
                ApplicableRegionSet tempHomeRegion = tempRegionManager.getApplicableRegions(
                        BlockVector3.at(tempHome.getX(), tempHome.getY(), tempHome.getZ()));
                boolean isHomeClanTerritory = false;
                for (var tempReg : tempHomeRegion.getRegions()) {
                    if (tempReg.getId().equals(senderClan.getClanRegionId())) {
                        isHomeClanTerritory = true;
                    }
                }
                if (!isHomeClanTerritory) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.ClanHomeDelete");
                }
            }

            Location tempTreasure = senderClan.getTreasureClan();
            if (tempTreasure != null) {
                ApplicableRegionSet tempTreasureRegion = tempRegionManager.getApplicableRegions(
                        BlockVector3.at(tempTreasure.getX(), tempTreasure.getY(), tempTreasure.getZ()));
                boolean isTreasureClanTerritory = false;
                for (var tempReg : tempTreasureRegion.getRegions()) {
                    if (tempReg.getId().equals(senderClan.getClanRegionId())) {
                        isTreasureClanTerritory = true;
                    }
                }
                if (!isTreasureClanTerritory) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Treasure.ClanTreasureDelete");
                }
            }
        }

        senderClan.setClanRegionId(tempProtectedRegion.getId());
        ChatSender.MessageTo(tempPlayer, "UselessClan", "WG.ClaimSuccessfully");
        return true;
    }
}
