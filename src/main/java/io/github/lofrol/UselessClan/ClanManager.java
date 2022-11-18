package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanObjects.*;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class ClanManager {

    private static final String ClanFolder = "Clans";
    public final UselessClan OwnerPlugin; //@todo private

    private final Map<Player, OnlinePlayerClan> OnlineClanPlayers;
    private final Map<String, Clan> ServerClans;

    public ClanManager(UselessClan owner) {
        ServerClans = new HashMap<>();
        OnlineClanPlayers = new HashMap<>();

        OwnerPlugin = owner;
    }

    public void LoadClans() {
        try {
            File tempDir = checkPluginFolderOrCreate();

            if (tempDir.listFiles() != null) {
                for (File tempClanFile : tempDir.listFiles()) {
                    FileConfiguration ClanConfig = new YamlConfiguration();
                    ClanConfig.load(tempClanFile);

                    Clan tempClan = Clan.CreateClanFromConfig(ClanConfig);
                    if (tempClan == null) continue;
                    else {
                        OwnerPlugin.getLogger().log(Level.FINE, String.format("Clan %s was loaded successfully!", tempClan.getPrefixClan()));
                        ServerClans.put(tempClan.getPrefixClan(), tempClan);
                    }
                }
            }

            OwnerPlugin.getLogger().log(Level.FINE, "Clans was loaded successfully!");
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void SaveClans() {
        try {
            File tempDir = checkPluginFolderOrCreate();

            OwnerPlugin.getLogger().log(Level.INFO, "Starting save clans to plugin folder...");
            for (Clan TempClan : ServerClans.values()) {
                File tempClanFile = new File(tempDir, String.format("%s.yml", TempClan.getPrefixClan()));

                FileConfiguration ClanConfig = TempClan.SaveClanToConfig();
                if (ClanConfig == null) {
                    OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was skipped save", TempClan.getNameClan()));
                    continue;
                }

                ClanConfig.save(tempClanFile);

                OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was saved", TempClan.getNameClan()));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Return Clans folder
    private File checkPluginFolderOrCreate() {
        OwnerPlugin.getLogger().log(Level.INFO, "Finding plugin folder...");

        File PluginDir = OwnerPlugin.getDataFolder();
        if (!PluginDir.exists()) {
            if (PluginDir.mkdir()) {
                OwnerPlugin.getLogger().log(Level.INFO, "Creating folder of plugin...");
            } else {
                OwnerPlugin.getLogger().log(Level.SEVERE, "Cant create plugin folder!");
            }
        }
        File tempDir = new File(OwnerPlugin.getDataFolder(), ClanFolder);

        if (!tempDir.exists()) {
            OwnerPlugin.getLogger().log(Level.WARNING, String.format("%s folder not found!", ClanFolder));
            if (tempDir.mkdir()) {
                OwnerPlugin.getLogger().log(Level.INFO, String.format("Creating %s folder in plugin dir...", ClanFolder));
            } else {
                OwnerPlugin.getLogger().log(Level.SEVERE, "Cant create clan folder!");
            }
        }
        return tempDir;
    }


    public Map<String, Clan> getServerClans() {
        return ServerClans;
    }
    public Map<Player, OnlinePlayerClan> getOnlineClanPlayers() {
        return OnlineClanPlayers;
    }
    public Clan getClanByName(String nameOfClan) {
        return ServerClans.get(nameOfClan);
    }
    public Clan FindClanToPlayer(String PlayerName) {
        for (Clan tempClan : ServerClans.values()) {
            for (ClanMember tempMember : tempClan.getMembers()) {
                if (tempMember.getPlayerName().equals(PlayerName)) {
                    return tempClan;
                }
            }
        }
        return null;
    }
    public void OnPlayerJoin(Player player) {
        Clan tempClan = FindClanToPlayer(player.getName());
        if (tempClan == null) {
            OwnerPlugin.getLogger().log(Level.INFO, String.format("%s Not available Clan.", player.getName()));
            return;
        }
        ClanRole playerRole = tempClan.getMemberRole(player.getName());
        OnlinePlayerClan tempClanPlayer = new OnlinePlayerClan(tempClan);
        OnlineClanPlayers.put(player, tempClanPlayer);

        if (playerRole == ClanRole.LEADER || playerRole == ClanRole.OFFICER) {
            player.sendMessage(String.format("Your clan have %d requests for join! ./clan requests", tempClan.getRequestCount()));
        }


        OwnerPlugin.getLogger().log(Level.INFO, String.format(  "Clan member %s Join to server, his clan is %s", player.getName(), tempClan.getNameClan()));
    }
    public void OnPlayerLeave(Player player) {
        if (!OnlineClanPlayers.containsKey(player)) return;
        OnlineClanPlayers.remove(player);
        OwnerPlugin.getLogger().log(Level.INFO, String.format("Clan member %s leaved from server", player.getName()));
    }

}
