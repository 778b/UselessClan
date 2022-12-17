package io.github.lofrol.UselessClan.ClanObjects;

import org.jetbrains.annotations.NotNull;

public class ClanSettings {

    public ClanRole DefaultJoinRole;
    public ClanRole RoleCanKick;

    public ClanRole RoleCanSethome;

    public ClanRole RoleCanWithdraw;

    public ClanRole RoleCanAccept;


    public ClanSettings() {
        DefaultJoinRole = ClanRole.ROOKIE;
        RoleCanSethome = ClanRole.LEADER;
        RoleCanWithdraw = ClanRole.MEMBER;
        RoleCanKick = ClanRole.OFFICER;
        RoleCanAccept = ClanRole.OFFICER;
    }

    public String getSerializationString() {
        return String.format("%d/%d/%d/%d/%d/",
                DefaultJoinRole.ordinal(), RoleCanSethome.ordinal(),
                RoleCanWithdraw.ordinal(), RoleCanKick.ordinal(),
                RoleCanAccept.ordinal());
    }

    public void InitializeSettingsFromString(@NotNull String settings) {
        StringBuilder param = new StringBuilder();
        int Stage = 0;
        for (char tc: settings.toCharArray()) {
            if (tc == '/') {
                ClanRole TempRole = ClanRole.fromString(param.toString());
                if (Stage == 0) {
                    DefaultJoinRole = TempRole;
                    ++Stage;
                }
                else if (Stage == 1) {
                    RoleCanSethome = TempRole;
                    ++Stage;
                }
                else if (Stage == 2) {
                    RoleCanWithdraw = TempRole;
                    ++Stage;
                }
                else if (Stage == 3) {
                    RoleCanKick = TempRole;
                    ++Stage;
                }
                else if (Stage == 4) {
                    RoleCanAccept = TempRole;
                    ++Stage;
                }
                param = new StringBuilder();
            }
            else param.append(tc);
        }
    }
}
