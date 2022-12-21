package io.github.lofrol.UselessClan.ClanObjects;


public class ClanMember {
    public ClanMember(EClanRole role, String player) {
        MemberRole = role;
        PlayerName = player;
        GeneralPlayerDeposit = 0.d;
    }

    public ClanMember(EClanRole role, String player, double deposit) {
        MemberRole = role;
        PlayerName = player;
        GeneralPlayerDeposit = deposit;
    }

    private EClanRole MemberRole;
    private final String PlayerName;
    private double GeneralPlayerDeposit;

    public EClanRole getMemberRole() {
        return MemberRole;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public double getGeneralPlayerDeposit() {
        return GeneralPlayerDeposit;
    }

    public void setMemberRole(EClanRole memberRole) {
        MemberRole = memberRole;
    }

    public void addGeneralPlayerDeposit(Double generalPlayerDeposit) {
        GeneralPlayerDeposit += generalPlayerDeposit;
    }

}
