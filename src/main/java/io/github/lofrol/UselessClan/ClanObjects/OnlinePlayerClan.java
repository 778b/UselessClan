package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OnlinePlayerClan {
    private Clan PlayerClan = null;

    public OnlinePlayerClan(@NotNull Clan playerClan) {
        PlayerClan = playerClan;

    }

    public Clan getPlayerClan() {
        return PlayerClan;
    }

}
