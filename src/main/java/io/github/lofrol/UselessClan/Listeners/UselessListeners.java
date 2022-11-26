package io.github.lofrol.UselessClan.Listeners;

import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class UselessListeners implements Listener {

    private final UselessClan OwnerPlugin;

    public UselessListeners(UselessClan owner) {
        OwnerPlugin = owner;
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerJoin(PlayerJoinEvent event) {
        getServer().getScheduler().runTaskLater(OwnerPlugin, bukkitTask -> OwnerPlugin.getMainManager().OnPlayerJoin(event.getPlayer()), 200);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerQuit(PlayerQuitEvent event) {
        OwnerPlugin.getMainManager().OnPlayerLeave(event.getPlayer());
    }

}
