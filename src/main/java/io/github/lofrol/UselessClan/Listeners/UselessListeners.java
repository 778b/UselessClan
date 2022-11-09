package io.github.lofrol.UselessClan.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class UselessListeners implements Listener {

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player p =  event.getPlayer();
        p.sendMessage(ChatColor.AQUA + "This is a test!");
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        Player p =  event.getPlayer();
        p.sendMessage(ChatColor.AQUA + "This is a test!");
    }

}
