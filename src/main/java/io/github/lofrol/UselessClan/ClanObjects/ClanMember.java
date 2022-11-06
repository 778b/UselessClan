package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.entity.Player;

public class ClanMember {
    public ClanMember(ClanRole role, String player) {
        MemberRole = role;
        PlayerName = player;
        GeneralPlayerDeposit = 0.d;
    }
    private ClanRole MemberRole;
    private String PlayerName;

    private Double GeneralPlayerDeposit;
}
