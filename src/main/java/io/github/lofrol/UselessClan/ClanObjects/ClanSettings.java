package io.github.lofrol.UselessClan.ClanObjects;

import org.jetbrains.annotations.NotNull;

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

    public void InitializeSettingsFromString(@NotNull String settings) {
        StringBuilder param = new StringBuilder();
        int Stage = 0;
        for (char tc: settings.toCharArray()) {
            if (tc == '/') {
                ClanRole TempRole = null;
                switch (Integer.parseInt(param.toString())) {
                    case 1 -> TempRole = ClanRole.ROOKIE;
                    case 2 -> TempRole = ClanRole.MEMBER;
                    case 3 -> TempRole = ClanRole.OFFICER;
                    case 4 -> TempRole = ClanRole.LEADER;
                    default -> TempRole = ClanRole.NONE;
                }
                if (Stage == 0) {
                    DefaultJoinRole = TempRole;
                    ++Stage;
                }
                else if (Stage == 1) {
                    HomeChangerMinRole = TempRole;
                    ++Stage;
                }
                else if (Stage == 2) {
                    MinRoleForWithdraw = TempRole;
                    ++Stage;
                }
                else if (Stage == 3) {
                    RoleCanKick = TempRole;
                    ++Stage;
                }
                param = new StringBuilder();
            }
            else param.append(tc);
        }

    }
    public ClanRole DefaultJoinRole;
    public ClanRole RoleCanKick;

    public ClanRole HomeChangerMinRole;

    public ClanRole MinRoleForWithdraw;



}
