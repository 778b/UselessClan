package io.github.lofrol.UselessClan;

import java.io.File;
import java.util.logging.Level;

public class SerializationManager {
    private final UselessClan OwnerPlugin;
    public static final String ClanFolderName = "Clans";
    public  static final String DeletedClanFolder = "DeletedClans";
    public  static final String backupClanFolder = "backups";

    public SerializationManager(UselessClan owner) {
        OwnerPlugin = owner;
    }

    public File checkClanFolderOrCreate(String FolderName) {
        if (!checkFolderOrCreate(OwnerPlugin.getDataFolder())) {
            OwnerPlugin.getLogger().log(Level.SEVERE, "Cant create plugin folder!");
        }

        File tempDir = new File(OwnerPlugin.getDataFolder(), FolderName);

        if (checkFolderOrCreate(tempDir)) {
            OwnerPlugin.getLogger().log(Level.INFO, String.format("Checked %s folder for exist", FolderName));
        }
        else {
            OwnerPlugin.getLogger().log(Level.SEVERE, String.format("Cant create %s folder!", FolderName));
        }
        return tempDir;
    }

    public boolean checkFolderOrCreate(File Folder) {
        return (Folder.exists() || Folder.mkdir());
    }
}
