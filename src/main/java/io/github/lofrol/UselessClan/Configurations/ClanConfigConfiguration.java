package io.github.lofrol.UselessClan.Configurations;

import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Map;

public class ClanConfigConfiguration extends YamlConfiguration {
    public ClanConfigConfiguration() {
        set("Localization", LocalizationDefault);
        set("NeedCalculateClanLevels", NeedCalculateClanLevelsDefault);

        set("Delay.ClanBackup", DelayClanBackupDefault);
        set("Delay.AutoSave", DelayAutoSaveDefault);
        set("Delay.CalculateClanLevel", DelayCalculateClanLevelDefault);
        set("Delay.CalculateClansTop", DelayCalculateClansTopDefault);

        set("ClanSettings.MinRoleCanSethome", ClanSettingsMinRoleCanSethomeDefault);
        set("ClanSettings.MinRoleCanWithdraw", ClanSettingsMinRoleCanWithdrawDefault);
        set("ClanSettings.MinRoleCanKick", ClanSettingsMinRoleCanKickDefault);
        set("ClanSettings.MinRoleCanAccept", ClanSettingsMinRoleCanAcceptDefault);
        set("ClanSettings.DefaultJoinRole", ClanSettingsDefaultJoinRoleDefault);

        set("DefaultClanSettings.FirstClanMoney", DefaultClanSettingsFirstClanMoneyDefault);
        set("DefaultClanSettings.MoneyToCreateClan", DefaultClanSettingsMoneyToCreateDefault);
        set("DefaultClanSettings.StartClanLevel", DefaultClanSettingsStartClanLevelDefault);
        set("DefaultClanSettings.ClanLevelsColors", ClanLevelsColorsDefault);
    }

    public static @Nullable ClanConfigConfiguration tryLoadDifferentConfig(FileConfiguration tempConfig) {
        var tempThis = new ClanConfigConfiguration(tempConfig);

        tempThis.LocalizationKey = tempConfig.getString("Localization");
        if (tempThis.LocalizationKey == null) return null;

        String tempNeedCalculateClanLevels = tempConfig.getString("NeedCalculateClanLevels");
        if (tempNeedCalculateClanLevels == null) return null;
        tempThis.NeedCalculateClanLevels = Boolean.parseBoolean(tempNeedCalculateClanLevels);

        String tempBackupDelay = tempConfig.getString("Delay.ClanBackup");
        if (tempBackupDelay == null) return null;
        tempThis.backupDelay = Integer.parseInt(tempBackupDelay);

        String tempAutoSaveDelay  = tempConfig.getString("Delay.AutoSave");
        if (tempAutoSaveDelay  == null) return null;
        tempThis.autoSaveDelay = Integer.parseInt(tempAutoSaveDelay);

        String tempCalcClanLvlDelay = tempConfig.getString("Delay.CalculateClanLevel");
        if (tempCalcClanLvlDelay  == null) return null;
        tempThis.calcClanLvlDelay = Integer.parseInt(tempCalcClanLvlDelay);

        String tempCalcClanTopDelay = tempConfig.getString("Delay.CalculateClanLevel");
        if (tempCalcClanTopDelay  == null) return null;
        tempThis.calcClanTopDelay = Integer.parseInt(tempCalcClanTopDelay);

        tempThis.MinRoleCanSethome = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanSethome"));
        if (tempThis.MinRoleCanSethome == EClanRole.NONE) return null;
        tempThis.MinRoleCanWithdraw = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanWithdraw"));
        if (tempThis.MinRoleCanWithdraw == EClanRole.NONE) return null;
        tempThis.MinRoleCanKick = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanKick"));
        if (tempThis.MinRoleCanKick == EClanRole.NONE) return null;
        tempThis.MinRoleCanAccept = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanAccept"));
        if (tempThis.MinRoleCanAccept == EClanRole.NONE) return null;
        tempThis.DefaultJoinRole = EClanRole.fromInt(tempConfig.getInt("ClanSettings.DefaultJoinRole"));
        if (tempThis.DefaultJoinRole == EClanRole.NONE) return null;

        String tempClanMoney = tempConfig.getString("DefaultClanSettings.FirstClanMoney");
        if (tempClanMoney == null) return null;
        tempThis.FirstClanMoney = Double.parseDouble(tempClanMoney);

        String tempMoneyToCreate = tempConfig.getString("DefaultClanSettings.MoneyToCreateClan");
        if (tempMoneyToCreate == null) return null;
        tempThis.MoneyToCreate = Double.parseDouble(tempMoneyToCreate);

        String tempStartClanLevel = tempConfig.getString("DefaultClanSettings.StartClanLevel");
        if (tempStartClanLevel == null) return null;
        tempThis.StartClanLevel = Integer.parseInt(tempStartClanLevel);

        tempThis.ClanLevelsColors = tempConfig.getStringList("DefaultClanSettings.ClanLevelsColors");
        if (tempThis.ClanLevelsColors.size() < 1) return null;

        return tempThis;
    }

    private ClanConfigConfiguration(FileConfiguration tempConfig) {
        setDefaults(tempConfig);
    }


    private String LocalizationKey          = LocalizationDefault;
    private boolean NeedCalculateClanLevels = NeedCalculateClanLevelsDefault;
    private int backupDelay                 = DelayClanBackupDefault;
    private int autoSaveDelay               = DelayAutoSaveDefault;
    private int calcClanLvlDelay            = DelayCalculateClanLevelDefault;
    private int calcClanTopDelay            = DelayCalculateClansTopDefault;
    private EClanRole MinRoleCanSethome     = EClanRole.fromInt(ClanSettingsMinRoleCanSethomeDefault);
    private EClanRole MinRoleCanWithdraw    = EClanRole.fromInt(ClanSettingsMinRoleCanWithdrawDefault);
    private EClanRole MinRoleCanKick        = EClanRole.fromInt(ClanSettingsMinRoleCanKickDefault);
    private EClanRole MinRoleCanAccept      = EClanRole.fromInt(ClanSettingsMinRoleCanAcceptDefault);
    private EClanRole DefaultJoinRole       = EClanRole.fromInt(ClanSettingsDefaultJoinRoleDefault);
    private double FirstClanMoney           = DefaultClanSettingsFirstClanMoneyDefault;
    private double MoneyToCreate            = DefaultClanSettingsMoneyToCreateDefault;
    private int StartClanLevel              = DefaultClanSettingsStartClanLevelDefault;
    private List<String> ClanLevelsColors   = ClanLevelsColorsDefault;


    /*
    *   Getters section
    * */
    public String getLocalizationKey() { return LocalizationKey; }
    public boolean isNeedCalculateClanLevels() { return NeedCalculateClanLevels; }
    public int getBackupDelay() { return backupDelay; }
    public int getAutoSaveDelay() { return autoSaveDelay; }
    public int getCalcClanLvlDelay() { return calcClanLvlDelay; }
    public int getCalcClanTopDelay() { return calcClanTopDelay; }
    public EClanRole getMinRoleCanSethome() { return MinRoleCanSethome; }
    public EClanRole getMinRoleCanWithdraw() { return MinRoleCanWithdraw; }
    public EClanRole getMinRoleCanKick() { return MinRoleCanKick; }
    public EClanRole getMinRoleCanAccept() { return MinRoleCanAccept; }
    public EClanRole getDefaultJoinRole() { return DefaultJoinRole; }
    public double getFirstClanMoney() { return FirstClanMoney; }
    public double getMoneyToCreate() { return MoneyToCreate; }
    public int getStartClanLevel() { return StartClanLevel; }
    public List<String> getClanLevelsColors() { return ClanLevelsColors; }



    /*
    *   Section of default values
    * */
    public static final String LocalizationDefault = "ExampleEnglish";
    public static final boolean NeedCalculateClanLevelsDefault = true;
    public static final int DelayClanBackupDefault = 864000;
    public static final int DelayAutoSaveDefault = 24000;
    public static final int DelayCalculateClanLevelDefault = 12000;
    public static final int DelayCalculateClansTopDefault = 12000;
    public static final int ClanSettingsMinRoleCanSethomeDefault = 4;
    public static final int ClanSettingsMinRoleCanWithdrawDefault = 2;
    public static final int ClanSettingsMinRoleCanKickDefault = 3;
    public static final int ClanSettingsMinRoleCanAcceptDefault = 3;
    public static final int ClanSettingsDefaultJoinRoleDefault = 1;
    public static final double DefaultClanSettingsFirstClanMoneyDefault = 0;
    public static final double DefaultClanSettingsMoneyToCreateDefault = 10000;
    public static final int DefaultClanSettingsStartClanLevelDefault = 0;
    public static final List<String> ClanLevelsColorsDefault =
            List.of("&f", "&a", "&2", "&3", "&9", "&1", "&e", "&6", "&d", "&5", "&0");
}
