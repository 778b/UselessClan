package io.github.lofrol.UselessClan.Configurations;

import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.LocalizationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClanConfigConfiguration extends YamlConfiguration {

    private ClanConfigConfiguration() {}

    public static @NotNull ClanConfigConfiguration tryLoadDifferentConfig(FileConfiguration tempConfig) {
        var tempThis = new ClanConfigConfiguration();

        tempThis.LocalizationKey = tempConfig.getString("Localization");
        if (tempThis.LocalizationKey == null) {
            tempThis.initLocalizationDefault();
        }
        else {
            tempThis.set("Localization", tempThis.LocalizationKey);
        }

        String tempNeedCalculateClanLevels = tempConfig.getString("NeedCalculateClanLevels");
        if (tempNeedCalculateClanLevels == null) {
            tempThis.initNeedCalculateClanLevelsDefault();
        }
        else {
            tempThis.NeedCalculateClanLevels = Boolean.parseBoolean(tempNeedCalculateClanLevels);
            tempThis.set("NeedCalculateClanLevels", tempThis.NeedCalculateClanLevels);
        }


        String tempBackupDelay = tempConfig.getString("Delay.ClanBackup");
        if (tempBackupDelay == null) {
            tempThis.initDelayClanBackupDefault();
        }
        else {
            tempThis.backupDelay = Integer.parseInt(tempBackupDelay);
            tempThis.set("Delay.ClanBackup", tempThis.backupDelay);
        }


        String tempAutoSaveDelay  = tempConfig.getString("Delay.AutoSave");
        if (tempAutoSaveDelay  == null) {
            tempThis.initDelayAutoSaveDefault();
        }
        else {
            tempThis.autoSaveDelay = Integer.parseInt(tempAutoSaveDelay);
            tempThis.set("Delay.AutoSave", tempThis.autoSaveDelay);
        }

        String tempCalcClanLvlDelay = tempConfig.getString("Delay.CalculateClanLevel");
        if (tempCalcClanLvlDelay  == null) {
            tempThis.initDelayCalculateClanLevelDefault();
        }
        else {
            tempThis.calcClanLvlDelay = Integer.parseInt(tempCalcClanLvlDelay);
            tempThis.set("Delay.CalculateClanLevel", tempThis.calcClanLvlDelay);
        }

        String tempCalcClanTopDelay = tempConfig.getString("Delay.CalculateClansTop");
        if (tempCalcClanTopDelay  == null) {
            tempThis.initDelayCalculateClansTopDefault();
        }
        else {
            tempThis.calcClanTopDelay = Integer.parseInt(tempCalcClanTopDelay);
            tempThis.set("Delay.CalculateClansTop", tempThis.calcClanTopDelay);
        }

        tempThis.MinRoleCanSethome = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanSethome"));
        if (tempThis.MinRoleCanSethome == EClanRole.NONE) {
            tempThis.initClanSettingsMinRoleCanSethomeDefault();
        }
        else {
            tempThis.set("ClanSettings.MinRoleCanSethome", tempThis.MinRoleCanSethome.ordinal());
        }

        tempThis.MinRoleCanWithdraw = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanWithdraw"));
        if (tempThis.MinRoleCanWithdraw == EClanRole.NONE) {
            tempThis.initClanSettingsMinRoleCanWithdrawDefault();
        }
        else {
            tempThis.set("ClanSettings.MinRoleCanWithdraw", tempThis.MinRoleCanWithdraw.ordinal());
        }

        tempThis.MinRoleCanKick = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanKick"));
        if (tempThis.MinRoleCanKick == EClanRole.NONE) {
            tempThis.initClanSettingsMinRoleCanKickDefault();
        }
        else {
            tempThis.set("ClanSettings.MinRoleCanKick", tempThis.MinRoleCanKick.ordinal());
        }

        tempThis.MinRoleCanAccept = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanAccept"));
        if (tempThis.MinRoleCanAccept == EClanRole.NONE) {
            tempThis.initClanSettingsMinRoleCanAcceptDefault();
        }
        else {
            tempThis.set("ClanSettings.MinRoleCanAccept", tempThis.MinRoleCanAccept.ordinal());
        }

        tempThis.DefaultJoinRole = EClanRole.fromInt(tempConfig.getInt("ClanSettings.DefaultJoinRole"));
        if (tempThis.DefaultJoinRole == EClanRole.NONE || tempThis.DefaultJoinRole == EClanRole.LEADER) {
            tempThis.initClanSettingsDefaultJoinRoleDefault();
        }
        else {
            tempThis.set("ClanSettings.DefaultJoinRole", tempThis.DefaultJoinRole.ordinal());
        }

        String tempClanMoney = tempConfig.getString("DefaultClanSettings.FirstClanMoney");
        if (tempClanMoney == null) {
            tempThis.initDefaultClanSettingsFirstClanMoneyDefault();
        }
        else {
            tempThis.FirstClanMoney = Double.parseDouble(tempClanMoney);
            tempThis.set("DefaultClanSettings.FirstClanMoney", tempThis.FirstClanMoney);
        }

        String tempMoneyToCreate = tempConfig.getString("DefaultClanSettings.MoneyToCreateClan");
        if (tempMoneyToCreate == null) {
            tempThis.initDefaultClanSettingsMoneyToCreateDefault();
        }
        else {
            tempThis.MoneyToCreate = Double.parseDouble(tempMoneyToCreate);
            tempThis.set("DefaultClanSettings.MoneyToCreateClan", tempThis.MoneyToCreate);
        }

        String tempStartClanLevel = tempConfig.getString("DefaultClanSettings.StartClanLevel");
        if (tempStartClanLevel == null) {
            tempThis.initDefaultClanSettingsStartClanLevelDefault();
        }
        else {
            tempThis.StartClanLevel = Integer.parseInt(tempStartClanLevel);
            tempThis.set("DefaultClanSettings.StartClanLevel", tempThis.StartClanLevel);
        }

        tempThis.ClanLevelsColors = tempConfig.getStringList("DefaultClanSettings.ClanLevelsColors");
        if (tempThis.ClanLevelsColors.size() < 1) {
            tempThis.initClanLevelsColorsDefault();
        }
        else {
            tempThis.set("DefaultClanSettings.ClanLevelsColors",tempThis.ClanLevelsColors);
        }

        return tempThis;
    }

    private boolean NeedCalculateClanLevels;
    private String LocalizationKey;
    private int backupDelay;
    private int autoSaveDelay;
    private int calcClanLvlDelay;
    private int calcClanTopDelay;
    private EClanRole MinRoleCanSethome;
    private EClanRole MinRoleCanWithdraw;
    private EClanRole MinRoleCanKick;
    private EClanRole MinRoleCanAccept;
    private EClanRole DefaultJoinRole;
    private double FirstClanMoney;
    private double MoneyToCreate;
    private int StartClanLevel;
    private List<String> ClanLevelsColors;


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


    private void initLocalizationDefault() {
        var tempDefault = LocalizationManager.DefaultLocalization;
        set("Localization", tempDefault);
        setComments("Localization", List.of(" "));
        LocalizationKey = tempDefault;
    }
    private void initNeedCalculateClanLevelsDefault() {
        var tempDefault = true;
        set("NeedCalculateClanLevels", tempDefault);
        NeedCalculateClanLevels = tempDefault;
    }
    private void initDelayClanBackupDefault() {
        var tempDefault = 864000;
        set("Delay.ClanBackup", tempDefault);
        backupDelay = tempDefault;
    }
    private void initDelayAutoSaveDefault() {
        var tempDefault = 24000;
        set("Delay.AutoSave", tempDefault);
        autoSaveDelay = tempDefault;
    }
    private void initDelayCalculateClanLevelDefault() {
        var tempDefault = 12000;
        set("Delay.CalculateClanLevel", tempDefault);
        calcClanLvlDelay = tempDefault;
    }
    private void initDelayCalculateClansTopDefault() {
        var tempDefault = 12000;
        set("Delay.CalculateClansTop", tempDefault);
        calcClanTopDelay = tempDefault;
    }
    private void initClanSettingsMinRoleCanSethomeDefault() {
        var tempDefault = 4;
        set("ClanSettings.MinRoleCanSethome", tempDefault);
        MinRoleCanSethome = EClanRole.fromInt(tempDefault);
    }
    private void initClanSettingsMinRoleCanWithdrawDefault() {
        var tempDefault = 2;
        set("ClanSettings.MinRoleCanWithdraw", tempDefault);
        MinRoleCanWithdraw = EClanRole.fromInt(tempDefault);
    }
    private void initClanSettingsMinRoleCanKickDefault() {
        var tempDefault = 3;
        set("ClanSettings.MinRoleCanKick", tempDefault);
        MinRoleCanKick = EClanRole.fromInt(tempDefault);
    }
    private void initClanSettingsMinRoleCanAcceptDefault() {
        var tempDefault = 3;
        set("ClanSettings.MinRoleCanAccept", tempDefault);
        MinRoleCanAccept = EClanRole.fromInt(tempDefault);
    }
    private void initClanSettingsDefaultJoinRoleDefault() {
        var tempDefault = 1;
        set("ClanSettings.DefaultJoinRole", tempDefault);
        DefaultJoinRole = EClanRole.fromInt(tempDefault);
    }
    private void initDefaultClanSettingsFirstClanMoneyDefault() {
        var tempDefault = 0;
        set("DefaultClanSettings.FirstClanMoney", (double) tempDefault);
        FirstClanMoney = tempDefault;
    }
    private void initDefaultClanSettingsMoneyToCreateDefault() {
        var tempDefault = 10000;
        set("DefaultClanSettings.MoneyToCreateClan", (double) tempDefault);
        MoneyToCreate = tempDefault;
    }
    private void initDefaultClanSettingsStartClanLevelDefault() {
        var tempDefault = 0;
        set("DefaultClanSettings.StartClanLevel", tempDefault);
        StartClanLevel = tempDefault;
    }
    private void initClanLevelsColorsDefault() {
        var tempList = List.of("&f", "&a", "&2", "&3", "&9", "&1", "&e", "&6", "&d", "&5", "&0");
        set("DefaultClanSettings.ClanLevelsColors", tempList);
        ClanLevelsColors = tempList;
    }
}
