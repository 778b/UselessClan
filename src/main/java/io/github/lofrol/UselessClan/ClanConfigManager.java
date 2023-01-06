package io.github.lofrol.UselessClan;

import com.sk89q.worldedit.util.collection.DoubleArrayList;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Configurations.ClanConfigConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static java.util.Map.entry;

public class ClanConfigManager {
    private final UselessClan OwnerPlugin;

    private final ClanConfigConfiguration ClanConfig;

    public ClanConfigManager(UselessClan owner) {
        OwnerPlugin = owner;

        ClanConfig = readConfigValues();
    }

    private @NotNull ClanConfigConfiguration readConfigValues() {
        FileConfiguration tempConfig = OwnerPlugin.getConfig();
        var tempConfiguration = ClanConfigConfiguration.tryLoadDifferentConfig(tempConfig);
        if (tempConfiguration == null) {
            OwnerPlugin.getLogger().log(Level.SEVERE, "Config is corrupted or invalid, initialized new");
            return setupDefaultConfig();
        }
        OwnerPlugin.getLogger().log(Level.INFO, "Config was load");
        return tempConfiguration;
    }


    public @NotNull ClanConfigConfiguration setupDefaultConfig() {
        var tempClanConfig = new ClanConfigConfiguration();
        File tempFolder = OwnerPlugin.getDataFolder();
        UselessClan.getSerilManager().checkFolderOrCreate(tempFolder);
        File newConfigFile = new File(tempFolder, "config.yml");

        try {
            tempClanConfig.save(newConfigFile);
        }
        catch (IOException e) {
            OwnerPlugin.getLogger().log(Level.SEVERE, "Could not save config to " + newConfigFile.getName());
        }

        return tempClanConfig;
    }


    /*
     *   Getters
     */
    public ClanConfigConfiguration getClanConfig() {
        return ClanConfig;
    }
}