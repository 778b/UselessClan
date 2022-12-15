package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanObjects.*;
import io.github.lofrol.UselessClan.Extensions.ClanManagerExtension;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public final class ClanManager {
    public final static String[] ClanLevelColors = {
            "&f",       //0 lvl
            "&a",       //1 lvl
            "&2",       //2 lvl
            "&3",       //3 lvl
            "&9",       //4 lvl
            "&1",       //5 lvl
            "&e",       //6 lvl
            "&6",       //7 lvl
            "&d",       //8 lvl
            "&5",       //9 lvl
            "&0",       //10 lvl
    };

    private static final String ClanFolderName = "Clans";
    private static final String DeletedClanFolder = "DeletedClans";

    private final UselessClan OwnerPlugin;

    public ClanManagerExtension Extension;
    private final Map<Player, OnlinePlayerClan> OnlineClanPlayers;
    private final Map<String, Clan> ServerClans;

    public ClanManager(UselessClan owner, ClanManagerExtension extension) {
        ServerClans = new HashMap<>();
        OnlineClanPlayers = new HashMap<>();

        Extension = extension;
        OwnerPlugin = owner;
    }

    public static String ClanRoleSolver(ClanRole role) {
        return switch (role) {
            case ROOKIE -> "/";
            case MEMBER -> "//";
            case OFFICER -> "+";
            case LEADER -> "#";
            default -> "";
        };
    }
    public void CalculateClanLevel(Clan ClanToLevel) {
        // Overriding by extensions if needed
        Extension.CalculateClanLevel(ClanToLevel);
    }
    public void CreateClan(String ClanName, Player LeaderPlayer) {
        Clan NewClan = new Clan(ClanName, LeaderPlayer.getName());
        ServerClans.put(ClanName, NewClan);

        NewClan.PlayerJoinToClan(ClanRole.LEADER, LeaderPlayer.getName());
        RegisterOnlineClanPlayer(NewClan, LeaderPlayer);
    }

    public void DeleteClan(String ClanName) {
        Clan ClanToDelete = ServerClans.get(ClanName);
        if (ClanToDelete == null) return;

        ServerClans.remove(ClanName);
        for (Map.Entry<Player, OnlinePlayerClan> tempEntry : OnlineClanPlayers.entrySet()) {
            if (tempEntry.getValue().getPlayerClan().getPrefixClan().equals(ClanName)) {
                OnlineClanPlayers.remove(tempEntry.getKey());
            }
        }
        RemoveClanToDeletedClanFolder(ClanName);
    }


    /*
     *  Serialize Functions
     */
    public void LoadClans() {

        File tempDir = checkClanFolderOrCreate(ClanFolderName);

        for (File tempClanFile : Objects.requireNonNull(tempDir.listFiles())) {
            FileConfiguration ClanConfig = new YamlConfiguration();

            try {
                ClanConfig.load(tempClanFile);
            }
            catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            Clan tempClan = Clan.CreateClanFromConfig(ClanConfig);
            if (tempClan == null) continue;
            OwnerPlugin.getLogger().log(Level.FINE, String.format("Clan %s was loaded successfully!", tempClan.getPrefixClan()));
            ServerClans.put(tempClan.getPrefixClan(), tempClan);
        }
        OwnerPlugin.getLogger().log(Level.FINE, "Clans was loaded successfully!");
    }

    public void SaveClans() {
        try {
            File tempDir = checkClanFolderOrCreate(ClanFolderName);

            OwnerPlugin.getLogger().log(Level.INFO, "Starting save clans to plugin folder...");
            for (Clan TempClan : ServerClans.values()) {
                SaveClan(TempClan, tempDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void SaveClan(Clan clanToSave, File clanFolder) throws IOException {
        File tempClanFile = new File(clanFolder, String.format("%s.yml", clanToSave.getPrefixClan()));

        /*
        if (tempClanFile.exists()) {
            if (tempClanFile.delete()) {
                OwnerPlugin.getLogger().log(Level.FINE, String.format("%s delete previous config", clanToSave.getNameClan()));
            }
            OwnerPlugin.getLogger().log(Level.FINE, String.format("%s didnt find previous config", clanToSave.getNameClan()));
        }
        */

        FileConfiguration ClanConfig = clanToSave.SaveClanToConfig();
        if (ClanConfig == null) {
            OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was skipped save", clanToSave.getNameClan()));
            return;
        }

        ClanConfig.save(tempClanFile);

        OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was saved", clanToSave.getNameClan()));
    }
    public void SaveClanDefaultFolder(Clan clanToSave) {
        File tempDir = checkClanFolderOrCreate(ClanFolderName);
        try {
            SaveClan(clanToSave, tempDir);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void RemoveClanToDeletedClanFolder(String ClanName) {
        File tempDeleteDir = checkClanFolderOrCreate(DeletedClanFolder);
        File tempClanDir = checkClanFolderOrCreate(ClanFolderName);

        FileConfiguration ClanConfig = new YamlConfiguration();
        String newClanName = null;


        for (File tempClanFile : Objects.requireNonNull(tempClanDir.listFiles())) {
            if (tempClanFile.getName().equals(String.format("%s.yml", ClanName))) {

                try {
                    ClanConfig.load(tempClanFile);
                }
                catch (IOException | InvalidConfigurationException e) {
                    OwnerPlugin.getLogger().log(Level.SEVERE, String.format("%s have exception on delete! #1", ClanName));
                    throw new RuntimeException(e);
                }

                newClanName = tempClanFile.getName();
                tempClanFile.deleteOnExit();
                break;
            }
        }

        if (newClanName == null) {
            OwnerPlugin.getLogger().log(Level.SEVERE, String.format("%s isnt finded!", ClanName));
            return;
        }
        OwnerPlugin.getLogger().log(Level.INFO, String.format("%s is finded!", ClanName));

        File newClanFile = new File(tempDeleteDir, newClanName);
        try {
            ClanConfig.save(newClanFile);
        }
        catch (IOException e) {
            OwnerPlugin.getLogger().log(Level.SEVERE, String.format("%s have exception on delete! #2", ClanName));
            throw new RuntimeException(e);
        }

        OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was deleted successfully", ClanName));
    }


    // Return Clans folder
    private File checkClanFolderOrCreate(String FolderName) {
        OwnerPlugin.getLogger().log(Level.INFO, "Finding plugin folder...");

        File PluginDir = OwnerPlugin.getDataFolder();
        if (!PluginDir.exists()) {
            if (PluginDir.mkdir()) {
                OwnerPlugin.getLogger().log(Level.INFO, "Creating folder of plugin...");
            }
            else {
                OwnerPlugin.getLogger().log(Level.SEVERE, "Cant create plugin folder!");
            }
        }
        File tempDir = new File(OwnerPlugin.getDataFolder(), FolderName);

        if (!tempDir.exists()) {
            OwnerPlugin.getLogger().log(Level.WARNING, String.format("%s folder not found!", FolderName));
            if (tempDir.mkdir()) {
                OwnerPlugin.getLogger().log(Level.INFO, String.format("Creating %s folder in plugin dir...", FolderName));
            }
            else {
                OwnerPlugin.getLogger().log(Level.SEVERE, "Cant create clan folder!");
            }
        }
        return tempDir;
    }


    /*
     *  Getters Functions
     */
    public Map<String, Clan> getServerClans() {
        return ServerClans;
    }
    public Map<Player, OnlinePlayerClan> getOnlineClanPlayers() {
        return OnlineClanPlayers;
    }
    public Clan getClanByName(String nameOfClan) {
        return ServerClans.get(nameOfClan);
    }
    public UselessClan getOwnerPlugin() {
        return OwnerPlugin;
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

    public void RegisterOnlineClanPlayer(Clan playerClan, Player player) {
        ClanRole playerRole = playerClan.getMemberRole(player.getName());

        ClanMember tempMember = new ClanMember(playerRole, player.getName());
        playerClan.getOnlineMembers().put(player, tempMember);

        OnlinePlayerClan tempClanPlayer = new OnlinePlayerClan(playerClan);
        OnlineClanPlayers.put(player, tempClanPlayer);
    }


    /*
     *  Listeners Functions
     */
    public void OnPlayerJoin(Player player) {
        Clan tempClan = FindClanToPlayer(player.getName());
        if (tempClan == null) {
            OwnerPlugin.getLogger().log(Level.INFO, String.format("%s Not available Clan.", player.getName()));
            return;
        }
        ClanRole playerRole = tempClan.getMemberRole(player.getName());

        RegisterOnlineClanPlayer(tempClan, player);
        getServer().getScheduler().runTaskLater(OwnerPlugin, () -> {
            if (playerRole == ClanRole.LEADER || playerRole == ClanRole.OFFICER) {
                ChatSender.MessageTo(player,"UselessClan",
                        String.format("Your clan have %d requests for join! ./clan requests", tempClan.getRequestCount()));
            }
        }
                , 200);

        OwnerPlugin.getLogger().log(Level.INFO, String.format(  "Clan member %s Join to server, his clan is %s", player.getName(), tempClan.getNameClan()));
    }
    public void OnPlayerLeave(Player player) {
        OnlinePlayerClan tempOnlinePlayer = OnlineClanPlayers.get(player);
        if (tempOnlinePlayer == null) return;

        tempOnlinePlayer.getPlayerClan().getOnlineMembers().remove(player);
        OnlineClanPlayers.remove(player);
        OwnerPlugin.getLogger().log(Level.INFO, String.format("Clan member %s leaved from server", player.getName()));
    }

}
