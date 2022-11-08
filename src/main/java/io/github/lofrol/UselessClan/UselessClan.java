package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.commands.ClanCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;


public final class UselessClan extends JavaPlugin {

    public ClanCommand MainCommand;
    public static Economy EconomyPtr = null;
    private ClanManager MainManager;

    @Override
    public void onEnable() {
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

        MainManager = new ClanManager(this);

        MainCommand = ClanCommand.CreateDefaultInts(MainManager);
        MainCommand.registerComamnd();
        getLogger().log(Level.INFO, "Loaded successfully!");
    }

    @Override
    public void onDisable() {

    }
}
