package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.entity.Player;

public class ClanMember {
    public ClanMember(ClanRole role, String player) {
        MemberRole = role;
        PlayerName = player;
        GeneralPlayerDeposit = 0.d;
    }

    private ClanRole MemberRole;
    private final String PlayerName;
    private Double GeneralPlayerDeposit;

    public ClanRole getMemberRole() {
        return MemberRole;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public Double getGeneralPlayerDeposit() {
        return GeneralPlayerDeposit;
    }

    public void setMemberRole(ClanRole memberRole) {
        MemberRole = memberRole;
    }

    public void addGeneralPlayerDeposit(Double generalPlayerDeposit) {
        GeneralPlayerDeposit += generalPlayerDeposit;
    }
}
