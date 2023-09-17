package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.Configurations.ClanConfigConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ClanConfigManager {
    private final UselessClan OwnerPlugin;

    private final ClanConfigConfiguration ClanConfig;

    public ClanConfigManager(UselessClan owner) {
        OwnerPlugin = owner;

        ClanConfig = readConfigValues();

        saveConfig(ClanConfig);
    }

    private @NotNull ClanConfigConfiguration readConfigValues() {
        FileConfiguration tempFileConfig = OwnerPlugin.getConfig();
        var tempConfiguration = ClanConfigConfiguration.tryLoadDifferentConfig(tempFileConfig);
        OwnerPlugin.getLogger().log(Level.INFO, "Config was load");
        return tempConfiguration;
    }


    public void saveConfig(FileConfiguration config) {
        File tempFolder = OwnerPlugin.getDataFolder();
        UselessClan.getSerilManager().checkFolderOrCreate(tempFolder);
        File newConfigFile = new File(tempFolder, "config.yml");

        try {
            config.save(newConfigFile);
        }
        catch (IOException e) {
            OwnerPlugin.getLogger().log(Level.SEVERE, "Could not save config to " + newConfigFile.getName());
        }
    }


    /*
     *   Getters
     */
    public ClanConfigConfiguration getClanConfig() {
        return ClanConfig;
    }

    public String getServerVersion() {
        return OwnerPlugin.getPluginMeta().getVersion();
    }
}