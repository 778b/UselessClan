package io.github.lofrol.UselessClan.ClanObjects;

import org.jetbrains.annotations.NotNull;

public class PlayerClan {
    private Clan PlayerClan = null;

    public PlayerClan(@NotNull Clan playerClan) {
        PlayerClan = playerClan;
    }

    public Clan getPlayerClan() {
        return PlayerClan;
    }
}
