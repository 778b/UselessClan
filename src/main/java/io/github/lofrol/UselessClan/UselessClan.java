package io.github.lofrol.UselessClan;

import com.sk89q.worldguard.WorldGuard;
import io.github.lofrol.UselessClan.External.UselessClanPlaceholder;
import io.github.lofrol.UselessClan.Listeners.UselessListeners;
import io.github.lofrol.UselessClan.commands.ClanAdminCommand;
import io.github.lofrol.UselessClan.commands.ClanChatCommand;
import io.github.lofrol.UselessClan.commands.ClanCommand;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.logging.Level;


public final class UselessClan extends JavaPlugin {
    public PlaceholderExpansion tempPlaceholderClan;
    @Nullable
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
            StringBuilder Multistring = new StringBuilder();
            for (String placeholder : tempPlaceholderClan.getPlaceholders()) {
                Multistring.append(" ").append(placeholder);
            }
            getLogger().log(Level.INFO, "Placeholders:"+ Multistring );
        }

        MainManager = new ClanManager(this);

        if (ClanCommand.CreateDefaultInts(MainManager).registerComamnd()) getLogger().log(Level.INFO, "Clan Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Clan Command cant be loaded!");
        if (ClanAdminCommand.CreateDefaultInts(MainManager).registerComamnd()) getLogger().log(Level.INFO, "Admin Clan Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Admin Clan Command cant be loaded!");
        if (ClanChatCommand.CreateDefaultInts(MainManager).registerComamnd()) getLogger().log(Level.INFO, "Clan Chat Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Clan Chat Command cant be loaded!");

        MainManager.LoadClans();

        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                getMainManager().SaveClans();
                getLogger().log(Level.INFO, "Clans was saved by autosave");
            }
        }, 24000, 24000);

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
