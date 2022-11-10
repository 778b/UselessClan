package io.github.lofrol.UselessClan.Listeners;

import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class UselessListeners implements Listener {

    private UselessClan OwnerPlugin;

    public UselessListeners(UselessClan owner) {
        OwnerPlugin = owner;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        OwnerPlugin.getLogger().log(Level.INFO, event.getPlayer().getName() + " Join to server");
        OwnerPlugin.getMainManager().OnPlayerJoin(event.getPlayer());
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        OwnerPlugin.getLogger().log(Level.INFO, event.getPlayer().getName() + " Leaved from server");
        OwnerPlugin.getMainManager().OnPlayerLeave(event.getPlayer());
    }

}
