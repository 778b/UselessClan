package io.github.lofrol.UselessClan.Listeners;

import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;


public class UselessListeners implements Listener {

    private final UselessClan OwnerPlugin;

    public UselessListeners(UselessClan owner) {
        OwnerPlugin = owner;
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerJoin(PlayerJoinEvent event) {
        OwnerPlugin.getMainManager().OnPlayerJoin(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerQuit(PlayerQuitEvent event) {
        OwnerPlugin.getMainManager().OnPlayerLeave(event.getPlayer());
    }

}
