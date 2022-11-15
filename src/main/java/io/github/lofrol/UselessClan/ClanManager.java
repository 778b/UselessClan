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

    public void LoadClans() throws IOException, InvalidConfigurationException {
        OwnerPlugin.getLogger().log(Level.INFO, "Starting load clans from plugin folder...");
        File PluginFolder = OwnerPlugin.getDataFolder();
        File tempDir = new File(OwnerPlugin.getDataFolder(), ClanFolder);

        if (!tempDir.exists()) {
            OwnerPlugin.getLogger().log(Level.WARNING, String.format("%s folder not found!", ClanFolder));
            if (tempDir.createNewFile()) {
                OwnerPlugin.getLogger().log(Level.INFO, String.format("Creating %s folder in plugin dir", ClanFolder));
            }
            else {
                OwnerPlugin.getLogger().log(Level.WARNING, "Cant create clan folder!");
            }
        }

        for (File tempClanFile: tempDir.listFiles()) {
            String ClanName = tempClanFile.getName();
            FileConfiguration ClanConfig = new YamlConfiguration();
            ClanConfig.load(tempClanFile);

            Clan tempClan = Clan.CreateClanFromConfig(ClanConfig);
            if (tempClan == null) continue;
            else ServerClans.put(tempClan.getPrefixClan(), tempClan);
        }


        //Clan(String ClanPrefix, String ClanName, String LeaderName,
        //        Double MoneyClan, Location HomeClan, List<String> Requests,
        //        List<ClanMember> Members, String DescriptionClan, ClanSettings SettingsClan);

        OwnerPlugin.getLogger().log(Level.FINE, "Clans was loaded successfully!");
    }

    public void SaveClans() {
        ClanConfig.set("UselessClan.Requests",new ClanMember(ClanRole.ROOKIE,"petya"));
    }

    public void serializeClans() throws IOException {
        boolean isCreateNew = true;
        File tempFolder = null;
        for (File tempForFile : OwnerPlugin.getDataFolder().listFiles()) {
            if (!tempForFile.isDirectory()) continue;

            if (tempForFile.getName().equalsIgnoreCase(ClanFolder)) {
                OwnerPlugin.getLogger().log(Level.INFO, "On Serialization: Finded clan folder");
                tempFolder = tempForFile;
                isCreateNew = false;
                break;
            }
        }
        if (isCreateNew) {
            tempFolder = new File(OwnerPlugin.getDataFolder(), ClanFolder);
        }
        if (tempFolder == null) {
            OwnerPlugin.getLogger().log(Level.WARNING, "On Serialization: Clan folder is NULL!");
            return;
        }
        for (Map.Entry<String, Clan> entry : ServerClans.entrySet()) {

            entry.getValue().SerializeClan(tempFolder);
        }
    }
    public void deserializeClans() {
        boolean isCreateNew = true;
        File tempFolder = null;
        for (File tempForFile : OwnerPlugin.getDataFolder().listFiles()) {
            if (!tempForFile.isDirectory()) continue;

            if (tempForFile.getName().equalsIgnoreCase(ClanFolder)) {
                OwnerPlugin.getLogger().log(Level.INFO, "On Deserialization: Finded clan folder");
                tempFolder = tempForFile;
                isCreateNew = false;
                break;
            }
        }
        if (isCreateNew) {
            tempFolder = new File(OwnerPlugin.getDataFolder(), ClanFolder);
        }
        if (tempFolder == null) {
            OwnerPlugin.getLogger().log(Level.WARNING, "On Deserialization: Clan folder is NULL!");
            return;
        }
        else {
            for (File tempForFile :tempFolder.listFiles()) {
                if (!tempForFile.isFile()) continue;

                Clan tempClan = Clan.DeserializeClan(tempForFile);
                if (tempClan == null) continue;
                else {

                    ServerClans.put(tempClan.getNameClan(),tempClan);
                    OwnerPlugin.getLogger().log(Level.INFO, "On Deserialization: Loaded clan - " + tempClan.getNameClan());
                }
            }
        }
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
            player.sendMessage(String.format("Your clan have %d requests for join to Clan", tempClan.getRequestCount()));
        }


        OwnerPlugin.getLogger().log(Level.INFO, String.format(  "Clan member %s Join to server, his clan is %s", player.getName(), tempClan.getNameClan()));
    }
    public void OnPlayerLeave(Player player) {
        if (!OnlineClanPlayers.containsKey(player)) return;
        OnlineClanPlayers.remove(player);
        OwnerPlugin.getLogger().log(Level.INFO, String.format("Clan member %s leaved from server", player.getName()));
    }

}
