package io.github.lofrol.UselessClan.Listeners;

import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;


public final class UselessListeners implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerJoin(PlayerJoinEvent event) {
        UselessClan.getMainManager().OnPlayerJoin(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void PlayerQuit(PlayerQuitEvent event) {
        UselessClan.getMainManager().OnPlayerLeave(event.getPlayer());
    }

}
