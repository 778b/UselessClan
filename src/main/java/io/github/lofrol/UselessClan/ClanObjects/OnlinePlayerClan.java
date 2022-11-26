package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OnlinePlayerClan {
    private Clan PlayerClan = null;
    private Player PlayerObj = null;

    public OnlinePlayerClan(@NotNull Clan playerClan, @NotNull Player player) {
        PlayerClan = playerClan;
        PlayerObj = player;
    }

    public Clan getPlayerClan() {
        return PlayerClan;
    }

    public Player getPlayerObj() {
        return PlayerObj;
    }
}
