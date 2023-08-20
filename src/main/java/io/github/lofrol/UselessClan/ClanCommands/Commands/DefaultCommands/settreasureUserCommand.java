package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class settreasureUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Settreasure";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole == EClanRole.LEADER);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (!tempPlayer.getWorld().getName().equals("world")) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Treasure.WrongWorldToSet");
            return false;
        }

        Location tempLoc = tempPlayer.getLocation();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager tempRegionManager = container.get(BukkitAdapter.adapt(tempPlayer.getWorld()));

        if (tempRegionManager == null) {
            return false;
        }

        ApplicableRegionSet tempRegion = tempRegionManager.getApplicableRegions(BlockVector3.at(tempLoc.getX(),tempLoc.getY(),tempLoc.getZ()));

        {
            boolean isClanTerritory = false;
            for (var tempReg : tempRegion.getRegions()) {
                if (tempReg.getId().equals(senderClan.getClanRegionId())) {
                    isClanTerritory = true;
                }
            }

            if (!isClanTerritory) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Treasure.WrongRegionToSet");
                return false;
            }
        }

        senderClan.setTreasureClan(tempLoc);
        //UselessClan.getMainManager().CalculateClanLevel(senderClan);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Treasure.TreasureSuccessSet");
        return true;
    }
}
