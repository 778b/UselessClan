package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class acceptUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Accept";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }
        EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        if (args.length == 1) {
            if (havePermission(tempPlayer, senderClan, SenderRole)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.ClanAcceptWithoutArgs");
            }
        }
        else {
            String AcceptedPlayerName = args[1];
            if (UselessClan.getMainManager().FindClanToPlayer(AcceptedPlayerName) != null) {
                senderClan.RemoveFromRequest(AcceptedPlayerName);
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.VictimAlreadyInClan");
                return false;
            }
            if (!senderClan.HaveRequestFromPlayer(AcceptedPlayerName)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.CantFindRequest");
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
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "&cError cant add new player to region! #1");
                    return false;
                }
                RegionManager tempRegionManager = tempRegionContainer.get(BukkitAdapter.adapt(tempWorld));
                if (tempRegionManager == null) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "&cError cant add new player to region! #2");
                    return false;
                }
                ProtectedRegion tempRegion = tempRegionManager.getRegion(senderClan.getClanRegionId());
                if (tempRegion == null) {
                    ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", "&cError cant add new player to region! #3");
                    return false;
                }
                var tempMembers = tempRegion.getMembers();
                tempMembers.addPlayer(AcceptedPlayerName);
                tempRegion.setMembers(tempMembers);
            }
            if (AcceptedPlayer != null) {
                UselessClan.getMainManager().RegisterOnlineClanPlayer(senderClan, AcceptedPlayer);
                senderClan.SendMessageForOnlinePlayers(String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Enter.PlayerJoinedToClan"), AcceptedPlayerName));
            }
            else {
                senderClan.SendMessageForOnlinePlayers(String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Enter.PlayerAcceptedToClan"), AcceptedPlayerName));
            }
        }
        return true;
    }
}
