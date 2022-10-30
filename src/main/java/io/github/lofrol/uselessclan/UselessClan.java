package io.github.lofrol.uselessclan;

import io.github.lofrol.uselessclan.commands.ClanCommands;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class UselessClan extends JavaPlugin {

    public ClanCommands MainCommand;

    private ClanManager MainManager;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Loaded successfully!");

        MainManager = new ClanManager(this);

        MainCommand = ClanCommands.CreateDefaultInts(MainManager);
        MainCommand.registerComamnd();

    }

    @Override
    public void onDisable() {

    }
}
