package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.Extensions.ClanManagerExtension;
import io.github.lofrol.UselessClan.External.UselessClanPlaceholder;
import io.github.lofrol.UselessClan.ClanCommands.ClanAdminCommand;
import io.github.lofrol.UselessClan.ClanCommands.ClanChatCommand;
import io.github.lofrol.UselessClan.ClanCommands.ClanCommand;
import io.github.lofrol.UselessClan.Listeners.UselessListeners;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;


public final class UselessClan extends JavaPlugin {
    @Nullable
    public static Economy EconomyPtr = null;

    private static ClanManager MainManager;

    private static ClanConfigManager ConfigManager;

    private static LocalizationManager LocalManager;

    private static SerializationManager SerilManager;

    @Override
    public void onEnable() {
        if (!checkHardDepends()) {
            getPluginLoader().disablePlugin(this);
        }

        SerilManager = new SerializationManager(this);
        ConfigManager = new ClanConfigManager(this);

        LocalManager = new LocalizationManager(this);
        MainManager = new ClanManager(this, new ClanManagerExtension());

        getServer().getPluginManager().registerEvents(new UselessListeners(), this);

        if (ClanCommand.CreateDefaultInst()) getLogger().log(Level.INFO, "Clan Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Clan Command cant be loaded!");
        if (ClanAdminCommand.CreateDefaultInts()) getLogger().log(Level.INFO, "Admin Clan Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Admin Clan Command cant be loaded!");
        if (ClanChatCommand.CreateDefaultInst()) getLogger().log(Level.INFO, "Clan Chat Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Clan Chat Command cant be loaded!");

        MainManager.LoadClans();
        runServerTasks();

        getLogger().log(Level.INFO, "Loaded successfully!");
    }

    @Override
    public void onDisable() {
        MainManager.SaveClans();
    }


    public void reloadPlugin() {
        onDisable();
        // @todo online players, che tam s top clan
        onEnable();
    }
    /*
    *   Getters section
    */
    public static ClanManager getMainManager() { return MainManager; }
    public static SerializationManager getSerilManager() { return SerilManager; }
    public static LocalizationManager getLocalManager() { return LocalManager; }
    public static ClanConfigManager getConfigManager() { return ConfigManager; }


    /*
     *   Tasks section
     */
    private boolean checkHardDepends() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().log(Level.SEVERE, "Cant find Vault, Vault is required!");
            return false;
        }
        else {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                getLogger().log(Level.SEVERE, "Vault not initialized!");
                return false;
            }
            else {
                getLogger().log(Level.INFO, "Loaded Vault depends!");
                EconomyPtr = rsp.getProvider();
            }
        }

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().log(Level.SEVERE, "Cant find PlaceholderAPI, PlaceholderAPI is required!");
            return false;
        }
        else {
            PlaceholderExpansion tempPlaceholderClan = new UselessClanPlaceholder();
            tempPlaceholderClan.register();
            StringBuilder MultiString = new StringBuilder();
            for (String placeholder : tempPlaceholderClan.getPlaceholders()) {
                MultiString.append(" ").append(placeholder);
            }
            getLogger().log(Level.INFO, "Placeholders:"+ MultiString );
        }

        if(getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            getLogger().log(Level.SEVERE, "Cant find WorldGuard, WorldGuard is required!");
            return false;
        }

        return true;
    }

    private void runServerTasks() {
        getServer().getScheduler().runTaskTimer(this, () -> {
            getMainManager().SaveClans();
            getLogger().log(Level.INFO, "Clans was saved by AutoSave"); },
                getConfigManager().getClanConfig().getAutoSaveDelay(),
                getConfigManager().getClanConfig().getAutoSaveDelay());

        if (getConfigManager().getClanConfig().isNeedCalculateClanLevels()) {
            getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
                    getMainManager().CalculateAllClansLevels(
                            getConfigManager().getClanConfig().isUseExtendCalculateClanLevels());
                },
                    getConfigManager().getClanConfig().getCalcClanLvlDelay(),
                    getConfigManager().getClanConfig().getCalcClanLvlDelay());
        }

        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            getMainManager().createClansBackups(); },
                0, getConfigManager().getClanConfig().getBackupDelay());

        getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            getMainManager().getTopClans().CalculateTop(); },
                0, getConfigManager().getClanConfig().getCalcClanTopDelay());
    }
}
