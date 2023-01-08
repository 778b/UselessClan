package io.github.lofrol.UselessClan.ClanObjects;

import io.github.lofrol.UselessClan.UselessClan;
import org.jetbrains.annotations.NotNull;

public class ClanSettings {

    public EClanRole DefaultJoinRole;
    public EClanRole RoleCanKick;

    public EClanRole RoleCanSethome;

    public EClanRole RoleCanWithdraw;

    public EClanRole RoleCanAccept;


    public ClanSettings() {
        DefaultJoinRole =   UselessClan.getConfigManager().getClanConfig().getDefaultJoinRole();
        RoleCanSethome =    UselessClan.getConfigManager().getClanConfig().getMinRoleCanSethome();
        RoleCanWithdraw =   UselessClan.getConfigManager().getClanConfig().getMinRoleCanWithdraw();
        RoleCanKick =       UselessClan.getConfigManager().getClanConfig().getMinRoleCanKick();
        RoleCanAccept =     UselessClan.getConfigManager().getClanConfig().getMinRoleCanAccept();
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
