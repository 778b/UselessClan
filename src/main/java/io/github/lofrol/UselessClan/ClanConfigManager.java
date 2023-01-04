package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static java.util.Map.entry;

public class ClanConfigManager {
    private final UselessClan OwnerPlugin;

    private ClanPluginData ClanConfig;

    public ClanConfigManager(UselessClan owner) {
        OwnerPlugin = owner;

        ClanConfig = readConfigValues();
        if (ClanConfig == null) {
            setupDefaultConfig();
            ClanConfig = loadDefaultConfig();
            OwnerPlugin.getLogger().log(Level.SEVERE, "Config is corrupted or invalid, initialized new");
        }
        else {
            OwnerPlugin.getLogger().log(Level.INFO, "Config was load");
        }
    }

    public @Nullable ClanPluginData readConfigValues() {
        FileConfiguration tempConfig = OwnerPlugin.getConfig();

        String LocalizationKey = tempConfig.getString("Localization");
        if (LocalizationKey == null) return null;
        boolean NeedCalculateClanLevels = tempConfig.getBoolean("NeedCalculateClanLevels");

        int backupDelay = tempConfig.getInt("Delay.ClanBackup");
        int autoSaveDelay = tempConfig.getInt("Delay.AutoSave");
        int calcClanLvlDelay = tempConfig.getInt("Delay.CalculateClanLevel");
        int calcClanTopDelay = tempConfig.getInt("Delay.CalculateClansTop");

        EClanRole MinRoleCanSethome = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanSethome"));
        if (MinRoleCanSethome == EClanRole.NONE) return null;
        EClanRole MinRoleCanWithdraw = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanWithdraw"));
        if (MinRoleCanWithdraw == EClanRole.NONE) return null;
        EClanRole MinRoleCanKick = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanKick"));
        if (MinRoleCanKick == EClanRole.NONE) return null;
        EClanRole MinRoleCanAccept = EClanRole.fromInt(tempConfig.getInt("ClanSettings.MinRoleCanAccept"));
        if (MinRoleCanAccept == EClanRole.NONE) return null;
        EClanRole DefaultJoinRole = EClanRole.fromInt(tempConfig.getInt("ClanSettings.DefaultJoinRole"));
        if (DefaultJoinRole == EClanRole.NONE) return null;

        double FirstClanMoney = tempConfig.getDouble("DefaultClanSettings.FirstClanMoney");
        int StartClanLevel = tempConfig.getInt("DefaultClanSettings.StartClanLevel");
        List<String> ClanLevelsColors = tempConfig.getStringList("DefaultClanSettings.ClanLevelsColors");

        return new ClanPluginData(LocalizationKey, NeedCalculateClanLevels,
                backupDelay, autoSaveDelay, calcClanLvlDelay, calcClanTopDelay,
                MinRoleCanSethome, MinRoleCanWithdraw, MinRoleCanKick, MinRoleCanAccept,
                DefaultJoinRole, FirstClanMoney, StartClanLevel, ClanLevelsColors);
    }

    private @NotNull ClanPluginData loadDefaultConfig() {
        return new ClanPluginData(
                LocalizationDefault,
                NeedCalculateClanLevelsDefault,
                DelayClanBackupDefault,
                DelayAutoSaveDefault,
                DelayCalculateClanLevelDefault,
                DelayCalculateClansTopDefault,

                EClanRole.fromInt(ClanSettingsMinRoleCanSethomeDefault),
                EClanRole.fromInt(ClanSettingsMinRoleCanWithdrawDefault),
                EClanRole.fromInt(ClanSettingsMinRoleCanKickDefault),
                EClanRole.fromInt(ClanSettingsMinRoleCanAcceptDefault),
                EClanRole.fromInt(ClanSettingsDefaultJoinRoleDefault),

                DefaultClanSettingsFirstClanMoneyDefault,
                DefaultClanSettingsStartClanLevelDefault,
                ClanLevelsColorsDefault);
    }

    public void setupDefaultConfig() {
        FileConfiguration tempConfig = OwnerPlugin.getConfig();

        tempConfig.set("Localization", LocalizationDefault);
        tempConfig.set("NeedCalculateClanLevels", NeedCalculateClanLevelsDefault);

        tempConfig.set("Delay.ClanBackup", DelayClanBackupDefault);
        tempConfig.set("Delay.AutoSave", DelayAutoSaveDefault);
        tempConfig.set("Delay.CalculateClanLevel", DelayCalculateClanLevelDefault);
        tempConfig.set("Delay.CalculateClansTop", DelayCalculateClansTopDefault);

        tempConfig.set("ClanSettings.MinRoleCanSethome", ClanSettingsMinRoleCanSethomeDefault);
        tempConfig.set("ClanSettings.MinRoleCanWithdraw", ClanSettingsMinRoleCanWithdrawDefault);
        tempConfig.set("ClanSettings.MinRoleCanKick", ClanSettingsMinRoleCanKickDefault);
        tempConfig.set("ClanSettings.MinRoleCanAccept", ClanSettingsMinRoleCanAcceptDefault);
        tempConfig.set("ClanSettings.DefaultJoinRole", ClanSettingsDefaultJoinRoleDefault);

        tempConfig.set("DefaultClanSettings.FirstClanMoney", DefaultClanSettingsFirstClanMoneyDefault);
        tempConfig.set("DefaultClanSettings.StartClanLevel", DefaultClanSettingsStartClanLevelDefault);
        tempConfig.set("DefaultClanSettings.ClanLevelsColors", ClanLevelsColorsDefault);

        OwnerPlugin.saveConfig();
    }


    /*
     *   Getters
     */
    public ClanPluginData getClanConfig() {
        return ClanConfig;
    }


    /*
     *   Default static values
     */
    public static class ClanPluginData {
        private final String LocalizationKey;
        private final boolean NeedCalculateClanLevels;

        private final int backupDelay;
        private final int autoSaveDelay;
        private final int calcClanLvlDelay;
        private final int calcClanTopDelay;

        private final EClanRole MinRoleCanSethome;
        private final EClanRole MinRoleCanWithdraw;
        private final EClanRole MinRoleCanKick;
        private final EClanRole MinRoleCanAccept;
        private final EClanRole DefaultJoinRole;

        private final double FirstClanMoney;
        private final int StartClanLevel;
        private final List<String> ClanLevelsColors;

        public ClanPluginData(String loc, boolean needCalc, int backup, int autosave, int calcLvl, int calcTop,
                              EClanRole sethomeRole, EClanRole withdrawRole, EClanRole kickRole, EClanRole acceptRole,
                              EClanRole defaultRole, double firstMoney, int startLevel, List<String> clanLevelsColors) {
            LocalizationKey = loc;
            NeedCalculateClanLevels = needCalc;

            backupDelay = backup;
            autoSaveDelay = autosave;
            calcClanLvlDelay = calcLvl;
            calcClanTopDelay = calcTop;

            MinRoleCanSethome = sethomeRole;
            MinRoleCanWithdraw = withdrawRole;
            MinRoleCanKick = kickRole;
            MinRoleCanAccept = acceptRole;
            DefaultJoinRole = defaultRole;

            FirstClanMoney = firstMoney;
            StartClanLevel = startLevel;
            ClanLevelsColors = clanLevelsColors;
        }

        public String getLocalizationKey() {
            return LocalizationKey;
        }

        public boolean isNeedCalculateClanLevels() {
            return NeedCalculateClanLevels;
        }

        public int getBackupDelay() {
            return backupDelay;
        }

        public int getAutoSaveDelay() {
            return autoSaveDelay;
        }

        public int getCalcClanLvlDelay() {
            return calcClanLvlDelay;
        }

        public int getCalcClanTopDelay() {
            return calcClanTopDelay;
        }

        public EClanRole getMinRoleCanSethome() {
            return MinRoleCanSethome;
        }

        public EClanRole getMinRoleCanWithdraw() {
            return MinRoleCanWithdraw;
        }

        public EClanRole getMinRoleCanKick() {
            return MinRoleCanKick;
        }

        public EClanRole getMinRoleCanAccept() {
            return MinRoleCanAccept;
        }

        public EClanRole getDefaultJoinRole() {
            return DefaultJoinRole;
        }

        public double getFirstClanMoney() {
            return FirstClanMoney;
        }

        public int getStartClanLevel() {
            return StartClanLevel;
        }

        public List<String> getClanLevelsColors() {
            return ClanLevelsColors;
        }

    }

    private static final String LocalizationDefault = "en-US";
    private static final boolean NeedCalculateClanLevelsDefault = true;
    private static final int DelayClanBackupDefault = 864000;
    private static final int DelayAutoSaveDefault = 24000;
    private static final int DelayCalculateClanLevelDefault = 12000;
    private static final int DelayCalculateClansTopDefault = 12000;
    private static final int ClanSettingsMinRoleCanSethomeDefault = 4;
    private static final int ClanSettingsMinRoleCanWithdrawDefault = 2;
    private static final int ClanSettingsMinRoleCanKickDefault = 3;
    private static final int ClanSettingsMinRoleCanAcceptDefault = 3;
    private static final int ClanSettingsDefaultJoinRoleDefault = 1;
    private static final double DefaultClanSettingsFirstClanMoneyDefault = 0;
    private static final int DefaultClanSettingsStartClanLevelDefault = 0;
    private static final List<String> ClanLevelsColorsDefault =
            List.of("&f", "&a", "&2", "&3", "&9", "&1", "&e", "&6", "&d", "&5", "&0");

    private static final Map<String, Object> DefaultUselessClanConfiguration = Map.ofEntries(
            entry("Localization", LocalizationDefault),
            entry("NeedCalculateClanLevels", NeedCalculateClanLevelsDefault),

            entry("Delay.ClanBackup", DelayClanBackupDefault),
            entry("Delay.AutoSave", DelayAutoSaveDefault),
            entry("Delay.CalculateClanLevel", DelayCalculateClanLevelDefault),
            entry("Delay.CalculateClansTop", DelayCalculateClansTopDefault),

            entry("ClanSettings.MinRoleCanSethome", ClanSettingsMinRoleCanSethomeDefault),
            entry("ClanSettings.MinRoleCanWithdraw", ClanSettingsMinRoleCanWithdrawDefault),
            entry("ClanSettings.MinRoleCanKick", ClanSettingsMinRoleCanKickDefault),
            entry("ClanSettings.MinRoleCanAccept", ClanSettingsMinRoleCanAcceptDefault),
            entry("ClanSettings.DefaultJoinRole", ClanSettingsDefaultJoinRoleDefault),

            entry("DefaultClanSettings.FirstClanMoney", DefaultClanSettingsFirstClanMoneyDefault),
            entry("DefaultClanSettings.StartClanLevel", DefaultClanSettingsStartClanLevelDefault),
            entry("DefaultClanSettings.ClanLevelsColors", ClanLevelsColorsDefault)
    );


}