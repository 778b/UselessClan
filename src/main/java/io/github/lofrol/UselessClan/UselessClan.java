package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.Extensions.ClanManagerExtension;
import io.github.lofrol.UselessClan.External.UselessClanPlaceholder;
import io.github.lofrol.UselessClan.Listeners.UselessListeners;
import io.github.lofrol.UselessClan.ClanCommands.ClanAdminCommand;
import io.github.lofrol.UselessClan.ClanCommands.ClanChatCommand;
import io.github.lofrol.UselessClan.ClanCommands.ClanCommand;
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
            PlaceholderExpansion tempPlaceholderClan = new UselessClanPlaceholder(this);
            tempPlaceholderClan.register();
            StringBuilder MultiString = new StringBuilder();
            for (String placeholder : tempPlaceholderClan.getPlaceholders()) {
                MultiString.append(" ").append(placeholder);
            }
            getLogger().log(Level.INFO, "Placeholders:"+ MultiString );
        }

        MainManager = new ClanManager(this, new ClanManagerExtension());

        if (ClanCommand.CreateDefaultInst(MainManager).registerCommand()) getLogger().log(Level.INFO, "Clan Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Clan Command cant be loaded!");
        if (ClanAdminCommand.CreateDefaultInts(MainManager).registerComamnd()) getLogger().log(Level.INFO, "Admin Clan Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Admin Clan Command cant be loaded!");
        if (ClanChatCommand.CreateDefaultInst(MainManager).registerCommand()) getLogger().log(Level.INFO, "Clan Chat Command Loaded successfully!");
        else getLogger().log(Level.SEVERE, "Clan Chat Command cant be loaded!");

        MainManager.LoadClans();

        getServer().getScheduler().runTaskTimer(this, () -> {
            getMainManager().SaveClans();
            getLogger().log(Level.INFO, "Clans was saved by AutoSave");
        }, 24000, 24000);

        getLogger().log(Level.INFO, "Loaded successfully!");
    }

    @Override
    public void onDisable() {
        MainManager.SaveClans();
    }

    public static ClanManager getMainManager() {
        return MainManager;
    }
}
