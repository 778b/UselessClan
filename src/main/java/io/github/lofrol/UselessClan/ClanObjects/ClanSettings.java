package io.github.lofrol.UselessClan.ClanObjects;

import org.jetbrains.annotations.NotNull;

public class ClanSettings {

    public EClanRole DefaultJoinRole;
    public EClanRole RoleCanKick;

    public EClanRole RoleCanSethome;

    public EClanRole RoleCanWithdraw;

    public EClanRole RoleCanAccept;


    public ClanSettings() {
        DefaultJoinRole = EClanRole.ROOKIE;
        RoleCanSethome = EClanRole.LEADER;
        RoleCanWithdraw = EClanRole.MEMBER;
        RoleCanKick = EClanRole.OFFICER;
        RoleCanAccept = EClanRole.OFFICER;
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
                EClanRole TempRole = EClanRole.fromString(param.toString());
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
