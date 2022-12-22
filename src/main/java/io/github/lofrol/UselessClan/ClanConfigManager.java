package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands.helpUserCommand;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public class ClanConfigManager {
    private UselessClan OwnerPlugin;

    public ClanConfigManager(UselessClan owner) {
        OwnerPlugin = owner;
    }

    public void readConfigValues() {
        FileConfiguration tempConfig = OwnerPlugin.getConfig();

        // @todo
    }

    public void setupDefaultConfig() {
        FileConfiguration tempConfig = OwnerPlugin.getConfig();

        tempConfig.addDefaults(DefaultUselessClanConfiguration);

        try {
            tempConfig.save(OwnerPlugin.getDataFolder());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ClanPluginData {
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


        public ClanPluginData(String loc, boolean needCalc, int backup, int autosave, int calcLvl, int calcTop,
                              EClanRole sethomeRole, EClanRole withdrawRole, EClanRole kickRole, EClanRole acceptRole,
                              EClanRole defaultRole, double firstMoney, int startLevel) {
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
        }
    }

    private static final Map<String, Object> DefaultUselessClanConfiguration = Map.ofEntries(
            entry("Localization", "en-US"),
            entry("NeedCalculateClanLevels", true),

            entry("Delay.ClanBackup", 864000),
            entry("Delay.AutoSave", 24000),
            entry("Delay.CalculateClanLevel", 12000),
            entry("Delay.CalculateClansTop", 12000),

            entry("ClanSettings.MinRoleCanSethome", 4),
            entry("ClanSettings.MinRoleCanWithdraw", 2),
            entry("ClanSettings.MinRoleCanKick", 3),
            entry("ClanSettings.MinRoleCanAccept", 3),
            entry("ClanSettings.DefaultJoinRole", 1),

            entry("DefaultClanSettings.FirstClanMoney", 0),
            entry("DefaultClanSettings.StartClanLevel", 0)
    );
}