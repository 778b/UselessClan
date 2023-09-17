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
        return "Description.Kick";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanKick.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }
        EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        if (args.length == 1) {
            if (SenderRole == senderClan.getSettingsClan().RoleCanKick) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.KickWithoutArgs");
            }
        }
        else {
            ClanMember tempMember = senderClan.getClanMember(args[1]);
            if (tempMember == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.VictimWrongClan");
                return false;
            }

            if (SenderRole.ordinal() <= tempMember.getMemberRole().ordinal()) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.CantKickHigher");
                return false;
            }

            senderClan.PlayerLeavedFromClan(tempMember.getPlayerName());
            UselessClan.getMainManager().CalculateClanLevel(senderClan);
            senderClan.SendMessageForOnlinePlayers(String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Enter.PlayerKicked"), tempMember.getPlayerName()));

            if (senderClan.getClanRegionId() != null) {
                RegionContainer tempRegionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                World tempWorld = getServer().getWorld("world");
                if (tempWorld == null) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "<red>Error cant remove player from region! #1");
                    return false;
                }
                RegionManager tempRegionManager = tempRegionContainer.get(BukkitAdapter.adapt(tempWorld));
                if (tempRegionManager == null) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "<red>Error cant remove player from region! #2");
                    return false;
                }
                ProtectedRegion tempRegion = tempRegionManager.getRegion(senderClan.getClanRegionId());
                if (tempRegion == null) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "<red>Error cant remove player from region! #3");
                    return false;
                }
                tempRegion.getMembers().removePlayer(tempMember.getPlayerName());
            }
        }
        return true;
    }
}
