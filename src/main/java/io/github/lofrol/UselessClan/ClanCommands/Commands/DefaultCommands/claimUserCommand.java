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
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.bukkit.Bukkit.getServer;

public class claimUserCommand extends PlayerCommandBase {


    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan claim&b - to claim territory what you selected to clan territory";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return (WorldGuardPlugin.inst() != null && senderRole.ordinal() >= EClanRole.OFFICER.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (WorldGuardPlugin.inst() == null) return false;

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
