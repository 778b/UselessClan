package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.commands.ClanCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public final class UselessClan extends JavaPlugin {

    public ClanCommand MainCommand;

    private ClanManager MainManager;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Loaded successfully!");

        MainManager = new ClanManager(this);

        MainCommand = ClanCommand.CreateDefaultInts(MainManager);
        MainCommand.registerComamnd();

    }

    @Override
    public void onDisable() {

    }
}
