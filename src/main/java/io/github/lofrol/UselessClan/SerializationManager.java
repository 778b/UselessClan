package io.github.lofrol.UselessClan;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;

public class SerializationManager {
    private final UselessClan OwnerPlugin;
    public static final String ClanFolderName = "Clans";
    public  static final String DeletedClanFolder = "DeletedClans";
    public  static final String backupClanFolder = "Backups";

    public SerializationManager(UselessClan owner) {
        OwnerPlugin = owner;
    }

    public @NotNull File checkClanFolderOrCreate(String FolderName) {
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

    public @Nullable File FindFileInFolder(@NotNull File Folder, String FileName) {
        for (var tempFile : Objects.requireNonNull(Folder.listFiles())) {
            if (!tempFile.isFile()) continue;
            if (tempFile.getName().equals(FileName)) return tempFile;
        }
        return null;
    }

    public boolean checkFolderOrCreate(File Folder) {
        return (Folder.exists() || Folder.mkdir());
    }
}
