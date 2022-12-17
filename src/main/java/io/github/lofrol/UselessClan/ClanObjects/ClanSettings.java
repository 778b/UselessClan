package io.github.lofrol.UselessClan.ClanObjects;

public class ClanSettings {
    public ClanSettings() {
        DefaultJoinRole = ClanRole.ROOKIE;
        HomeChangerMinRole = ClanRole.LEADER;
        MinRoleForWithdraw = ClanRole.MEMBER;
        RoleCanKick = ClanRole.OFFICER;
    }

    public String getSerializationString() {
        return String.format("%d/%d/%d/%d/",
                DefaultJoinRole.ordinal(), HomeChangerMinRole.ordinal(),
                MinRoleForWithdraw.ordinal(), RoleCanKick.ordinal());
    }
    public ClanRole DefaultJoinRole;
    public ClanRole RoleCanKick;

    public ClanRole HomeChangerMinRole;

    public ClanRole MinRoleForWithdraw;



}
