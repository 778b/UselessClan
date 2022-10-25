package io.github.lofrol.uselessclan;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public class ClanManager {
    private UselessClan OwnerPlugin;
    public ClanManager(UselessClan owner) {
        ServerClans = new HashMap<String, Clan>();

        OwnerPlugin = owner;

    }

    public void serializeClans() {
        for (File tempFile : OwnerPlugin.getDataFolder().listFiles()) {
            tempFile.list();
        }
    }
    public void deserializeClans() {

    }

    Map<String, Clan> ServerClans;

}
