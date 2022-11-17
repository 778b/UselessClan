package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.External.UselessClanPlaceholder;
import io.github.lofrol.UselessClan.Listeners.UselessListeners;
import io.github.lofrol.UselessClan.commands.ClanCommand;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;


public final class UselessClan extends JavaPlugin {

    public ClanCommand MainCommand;
    public PlaceholderExpansion tempPlaceholderClan;
    public static Economy EconomyPtr = null;
    private ClanManager MainManager;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new UselessListeners(this), this);
        getLogger().log(Level.INFO, "Events was registered successfully!");

        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().log(Level.INFO, "Cant find Vault");
            return;
        }
        else {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                getLogger().log(Level.INFO, "Cant find Vault");
                return;
            }
            else {
                getLogger().log(Level.INFO, "Loaded Vault depends!");
                EconomyPtr = rsp.getProvider();
            }
        }

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            tempPlaceholderClan = new UselessClanPlaceholder(this);
            tempPlaceholderClan.register();
            String Multistring = "";
            for (String placeholder : tempPlaceholderClan.getPlaceholders()) {
                Multistring += " " + placeholder;
            }
            getLogger().log(Level.INFO, "Placeholders:"+ Multistring );
        }

        MainManager = new ClanManager(this);

        MainCommand = ClanCommand.CreateDefaultInts(MainManager);
        if (MainCommand.registerComamnd()) getLogger().log(Level.INFO, "Clan Command Loaded successfully!");
        else getLogger().log(Level.OFF, "Clan Command cant be loaded!");

        MainManager.LoadClans();

        getServer().getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                getMainManager().SaveClans();
                getLogger().log(Level.INFO, "Clans was saved by autosave");
            }
        });

        getLogger().log(Level.INFO, "Loaded successfully!");
    }

    @Override
    public void onDisable() {
        MainManager.SaveClans();
    }

    public ClanManager getMainManager() {
        return MainManager;
    }
}
