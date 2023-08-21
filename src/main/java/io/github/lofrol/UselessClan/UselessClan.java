package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanCommands.PluginCommand;
import io.github.lofrol.UselessClan.Extensions.ClanManagerExtension;
import io.github.lofrol.UselessClan.External.UselessClanPlaceholder;
import io.github.lofrol.UselessClan.ClanCommands.ClanAdminCommand;
import io.github.lofrol.UselessClan.ClanCommands.ClanChatCommand;
import io.github.lofrol.UselessClan.ClanCommands.ClanCommand;
import io.github.lofrol.UselessClan.Listeners.UselessListeners;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;


public final class UselessClan extends JavaPlugin {
    @Nullable
    public static Economy EconomyPtr = null;

    private static ClanManager MainManager;

    private static ClanConfigManager ConfigManager;

    private static LocalizationManager LocalManager;

    private static SerializationManager SerilManager;

    private static Set<BukkitTask> ActiveTasks;

    private static Set<Command> RegisteredCommands;

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

        if (RegisteredCommands == null) registerAllCommands();

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
        for (var tempPlayer : getServer().getOnlinePlayers()) {
            MainManager.OnPlayerLeave(tempPlayer);
        }
        MainManager.getServerClans().clear();
        MainManager.getOnlineClanPlayers().clear();
        for (var tempTask : ActiveTasks) {
            tempTask.cancel();
        }
        ActiveTasks.clear();
        reloadConfig();

        onEnable();
        for (var tempPlayer : getServer().getOnlinePlayers()) {
            MainManager.OnPlayerJoin(tempPlayer);
        }
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
            getLogger().log(Level.INFO, "Cant find Vault, Vault is required!");
        }
        else {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                getLogger().log(Level.INFO, "Vault not initialized!");
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

    private void registerAllCommands() {
        RegisteredCommands = new HashSet<>();
        Command tempClanCommand = ClanCommand.CreateDefaultInst();
        if (getServer().getCommandMap().register("[UselessClan]", tempClanCommand)) {
            getLogger().log(Level.INFO, "Clan Command Loaded successfully!");
            RegisteredCommands.add(tempClanCommand);
        }
        else getLogger().log(Level.SEVERE, "Clan Command cant be loaded!");

        Command tempClanAdminCommand = ClanAdminCommand.CreateDefaultInts();
        if (getServer().getCommandMap().register("[UselessClan]", tempClanAdminCommand)) {
            getLogger().log(Level.INFO, "Admin Clan Command Loaded successfully!");
            RegisteredCommands.add(tempClanAdminCommand);
        }
        else getLogger().log(Level.SEVERE, "Admin Clan Command cant be loaded!");

        Command tempClanChatCommand = ClanChatCommand.CreateDefaultInst();
        if (getServer().getCommandMap().register("[UselessClan]", tempClanChatCommand)) {
            getLogger().log(Level.INFO, "Clan Chat Command Loaded successfully!");
            RegisteredCommands.add(tempClanChatCommand);
        }
        else getLogger().log(Level.SEVERE, "Clan Chat Command cant be loaded!");

        Command tempPluginCommand = PluginCommand.CreateDefaultInst();
        if (getServer().getCommandMap().register("[UselessClan]", tempPluginCommand)) {
            getLogger().log(Level.INFO, "Clan Plugin Command Loaded successfully!");
            RegisteredCommands.add(tempPluginCommand);
        }
        else getLogger().log(Level.SEVERE, "Clan Plugin Command cant be loaded!");
    }

    private void runServerTasks() {
        ActiveTasks = new HashSet<>();
        ActiveTasks.add(getServer().getScheduler().runTaskTimer(this, () -> {
            getMainManager().SaveClans();
            getLogger().log(Level.INFO, "Clans was saved by AutoSave"); },
                getConfigManager().getClanConfig().getAutoSaveDelay(),
                getConfigManager().getClanConfig().getAutoSaveDelay()));

        if (getConfigManager().getClanConfig().isNeedCalculateClanLevels()) {
            ActiveTasks.add(getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
                    getMainManager().CalculateAllClansLevels(
                            getConfigManager().getClanConfig().isUseExtendCalculateClanLevels());
                },
                    getConfigManager().getClanConfig().getCalcClanLvlDelay(),
                    getConfigManager().getClanConfig().getCalcClanLvlDelay()));
        }

        ActiveTasks.add(getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            getMainManager().createClansBackups(); },
                0, getConfigManager().getClanConfig().getBackupDelay()));

        ActiveTasks.add(getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            getMainManager().getTopClans().CalculateTop(); },
                0, getConfigManager().getClanConfig().getCalcClanTopDelay()));
    }
}
