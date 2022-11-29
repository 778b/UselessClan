package io.github.lofrol.UselessClan.ClanObjects;

import org.jetbrains.annotations.NotNull;

public class OnlinePlayerClan {
    private final Clan PlayerClan;

    public OnlinePlayerClan(@NotNull Clan playerClan) {
        PlayerClan = playerClan;

    }

    public Clan getPlayerClan() {
        return PlayerClan;
    }

}
