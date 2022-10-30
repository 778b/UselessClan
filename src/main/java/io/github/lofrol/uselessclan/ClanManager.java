package io.github.lofrol.uselessclan;

import io.github.lofrol.uselessclan.ClanObjects.Clan;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class ClanManager {
    private static String ClanFolder = "Clans";
    private UselessClan OwnerPlugin;
    public ClanManager(UselessClan owner) {
        ServerClans = new HashMap<String, Clan>();

        OwnerPlugin = owner;

    }

    public void serializeClans() throws IOException {
        boolean isCreateNew = true;
        File tempFolder = null;
        for (File tempForFile : OwnerPlugin.getDataFolder().listFiles()) {
            if (!tempForFile.isDirectory()) continue;

            if (tempForFile.getName().equalsIgnoreCase(ClanFolder)) {
                OwnerPlugin.getLogger().log(Level.INFO, "On Serialization: Finded clan folder");
                tempFolder = tempForFile;
                isCreateNew = false;
                break;
            }
        }
        if (isCreateNew) {
            tempFolder = new File(OwnerPlugin.getDataFolder(), ClanFolder);
        }
        if (tempFolder == null) {
            OwnerPlugin.getLogger().log(Level.WARNING, "On Serialization: Clan folder is NULL!");
            return;
        }
        for (Map.Entry<String, Clan> entry : ServerClans.entrySet()) {

            entry.getValue().SerializeClan(tempFolder);
        }
    }
    public void deserializeClans() {
        boolean isCreateNew = true;
        File tempFolder = null;
        for (File tempForFile : OwnerPlugin.getDataFolder().listFiles()) {
            if (!tempForFile.isDirectory()) continue;

            if (tempForFile.getName().equalsIgnoreCase(ClanFolder)) {
                OwnerPlugin.getLogger().log(Level.INFO, "On Deserialization: Finded clan folder");
                tempFolder = tempForFile;
                isCreateNew = false;
                break;
            }
        }
        if (isCreateNew) {
            tempFolder = new File(OwnerPlugin.getDataFolder(), ClanFolder);
        }
        if (tempFolder == null) {
            OwnerPlugin.getLogger().log(Level.WARNING, "On Deserialization: Clan folder is NULL!");
            return;
        }
        else {
            for (File tempForFile :tempFolder.listFiles()) {
                if (!tempForFile.isFile()) continue;

                Clan tempClan = Clan.DeserializeClan(tempForFile);
                if (tempClan == null) continue;
                else {

                    ServerClans.put(tempClan.getNameClan(),tempClan);
                    OwnerPlugin.getLogger().log(Level.INFO, "On Deserialization: Loaded clan - " + tempClan.getNameClan());
                }
            }
        }
    }

    // Name of clan for best performance in search clan
    private Map<String, Clan> ServerClans;

}
