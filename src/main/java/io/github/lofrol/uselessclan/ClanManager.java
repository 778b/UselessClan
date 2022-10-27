package io.github.lofrol.uselessclan;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class ClanManager {
    private UselessClan OwnerPlugin;
    public ClanManager(UselessClan owner) {
        ServerClans = new HashMap<String, Clan>();

        OwnerPlugin = owner;

    }

    public void serializeClans() throws IOException {
        boolean isCreateNew = true;
        for (File tempFile : OwnerPlugin.getDataFolder().listFiles()) {
            if (tempFile.getName().equalsIgnoreCase("clan.yml")) {
                OwnerPlugin.getLogger().log(Level.INFO, "Finded clan.yml");
                isCreateNew = false;
                break;
            }
        }
        if (isCreateNew) {
           File tempfile = new File(OwnerPlugin.getDataFolder(), "ClanConfig");
        }
    }
    public void deserializeClans() {

    }

    Map<String, Clan> ServerClans;

}
