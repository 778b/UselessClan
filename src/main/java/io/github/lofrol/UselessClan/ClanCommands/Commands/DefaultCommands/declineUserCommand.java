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

public class declineUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan accept %name&b - to decline request for join from player %name";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
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
                if (!havePermission(tempPlayer, senderClan, SenderRole)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                    return false;
                }

                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cYou forgot about player %name, use &a/Clan decline %name&b, %name = name of player, which request you want decline");

            }
            else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
            }
        }
        else {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            if (!havePermission(tempPlayer, senderClan, SenderRole)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }
            String AcceptedPlayerName = args[1];
            if (!senderClan.HaveRequestFromPlayer(AcceptedPlayerName)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis player didn't send request to you Clan");
                return false;
            }
            if (!senderClan.RemoveFromRequest(AcceptedPlayerName)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        String.format("&cYou don't have request from player %s!", AcceptedPlayerName));
                return false;
            }
            ChatSender.MessageTo(tempPlayer, "UselessClan",
                    String.format("&aYou successfully decline request from %s", AcceptedPlayerName));
        }
        return true;
    }
}