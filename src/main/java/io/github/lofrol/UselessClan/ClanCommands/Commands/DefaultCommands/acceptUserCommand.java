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
                            "&cYou forgot about player %name, use &a/Clan accept %name&b, %name = name of player, which request you want to accept");
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
                senderClan.RemoveFromRequest(AcceptedPlayerName);
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
            }
            else {
                senderClan.SendMessageForOnlinePlayers(String.format(
                        "Player &a%s&b accepted to your clan!", AcceptedPlayerName));
            }
        }
        return true;
    }
}
