package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class kickUserCommand extends PlayerCommandBase {

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
