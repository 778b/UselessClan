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
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class sethomeUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Sethome";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanSethome.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (!tempPlayer.getWorld().getName().equals("world")) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.WrongWorldToSet");
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
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.WrongRegionToSet");
                return false;
            }
        }

        senderClan.setHomeClan(tempLoc);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.HomeSuccessSet");
        return true;
    }
}
