package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.Configurations.ClanConfigConfiguration;
import io.github.lofrol.UselessClan.Configurations.DefaultLocalizationConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class LocalizationManager {
    private final UselessClan OwnerPlugin;

    private final String CurrentLocalization;
    private final boolean isDefaultLocalization;
    private final YamlConfiguration LocalizationTable = new YamlConfiguration();
    public final DefaultLocalizationConfiguration DefaultLocalizationTable = new DefaultLocalizationConfiguration();

    //private Map<String, >
    public LocalizationManager(UselessClan owner) {
        OwnerPlugin = owner;
        final String defaultLocalizationName = ClanConfigConfiguration.LocalizationDefault;

        if (!checkDefaultConfigurationFile()) {
            OwnerPlugin.getLogger().log(Level.SEVERE,
                    "Cant find default localization file, created new!");
        }


        String localizationKey = UselessClan.getConfigManager().getClanConfig().getLocalizationKey();

        if (localizationKey.equals(defaultLocalizationName)) {
            OwnerPlugin.getLogger().log(Level.INFO,
                    String.format("Successfully load %s localization", defaultLocalizationName));
            CurrentLocalization = defaultLocalizationName;
            isDefaultLocalization = true;
            return;
        }

        if (selectLocalization(localizationKey)) {
            OwnerPlugin.getLogger().log(Level.INFO,
                    String.format("Successfully load %s localization", localizationKey));
            CurrentLocalization = localizationKey;
            isDefaultLocalization = false;
            return;
        }

        OwnerPlugin.getLogger().log(Level.SEVERE,
                String.format("Cant load %s localization, loaded default", localizationKey));
        CurrentLocalization = defaultLocalizationName;
        isDefaultLocalization = true;

    }

    public boolean selectLocalization(String localizationKey) {
        File findedLocalization = UselessClan.getSerilManager().FindFileInFolder(
                UselessClan.getSerilManager().checkClanFolderOrCreate(
                        "Localization"), String.format("%s.yml", localizationKey));

        if (findedLocalization == null) {
            return false;
        }
        try {
            LocalizationTable.load(findedLocalization);
        }
        catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    private boolean checkDefaultConfigurationFile() {
        File findedLocalization = new File(
                UselessClan.getSerilManager().checkClanFolderOrCreate(
                        "Localization"), String.format(
                "%s.yml", ClanConfigConfiguration.LocalizationDefault));
        if (findedLocalization.exists()) return true;
        try {
            DefaultLocalizationTable.save(findedLocalization);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public @NotNull String getLocalizationMessage(String messageKey) {
        String tempLocalizationText = null;
        if (!isDefaultLocalization) {
            tempLocalizationText = LocalizationTable.getString(messageKey);
        }
        if (tempLocalizationText == null) {
            tempLocalizationText = DefaultLocalizationTable.getString(messageKey);
            return tempLocalizationText != null ? tempLocalizationText : String.format("&cLocalization error, key = %s", messageKey);
        }
        return tempLocalizationText;
    }

    public String getCurrentLocalization() { return CurrentLocalization; }

}
