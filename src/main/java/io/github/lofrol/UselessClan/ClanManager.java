package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanObjects.*;
import io.github.lofrol.UselessClan.Extensions.ClanManagerExtension;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import io.github.lofrol.UselessClan.Utils.TopClanCounter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static io.github.lofrol.UselessClan.SerializationManager.*;
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

    private final UselessClan OwnerPlugin;

    public ClanManagerExtension Extension;
    private final Map<Player, OnlinePlayerClan> OnlineClanPlayers;
    private final Map<String, Clan> ServerClans;
    private final TopClanCounter TopClans;
    public ClanManager(UselessClan owner, ClanManagerExtension extension) {
        ServerClans = new HashMap<>();
        OnlineClanPlayers = new HashMap<>();
        TopClans = new TopClanCounter();

        Extension = extension;
        OwnerPlugin = owner;
    }

    public static String ClanRoleSolver(EClanRole role) {
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

    public void CalculateAllClansLevels() {
        for (Clan tempClan : ServerClans.values()) {
            CalculateClanLevel(tempClan);
        }
    }
    public void CreateClan(String ClanName, Player LeaderPlayer) {
        Clan NewClan = new Clan(ClanName, LeaderPlayer.getName());
        ServerClans.put(ClanName, NewClan);

        NewClan.PlayerJoinToClan(EClanRole.LEADER, LeaderPlayer.getName());
        RegisterOnlineClanPlayer(NewClan, LeaderPlayer);
    }

    public void DeleteClan(Clan clanToDelete) {
        Clan ClanToDelete = ServerClans.get(clanToDelete.getPrefixClan());
        if (ClanToDelete == null) return;

        ServerClans.remove(clanToDelete.getPrefixClan());
        for (Map.Entry<Player, OnlinePlayerClan> tempEntry : OnlineClanPlayers.entrySet()) {
            if (tempEntry.getValue().getPlayerClan().getPrefixClan().equals(clanToDelete.getPrefixClan())) {
                OnlineClanPlayers.remove(tempEntry.getKey());
            }
        }
        RemoveClanToDeletedClanFolder(clanToDelete);
    }


    /*
     *  Serialize Functions
     */
    public void LoadClans() {

        File tempDir = UselessClan.getSerilManager().checkClanFolderOrCreate(ClanFolderName);

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

        File tempDir = UselessClan.getSerilManager().checkClanFolderOrCreate(ClanFolderName);

        OwnerPlugin.getLogger().log(Level.INFO, "Starting save clans to plugin folder...");
        for (Clan TempClan : ServerClans.values()) {
            try {
                SaveClan(TempClan, tempDir);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void SaveClan(Clan clanToSave, File clanFolder) throws IOException {
        File tempClanFile = new File(clanFolder, String.format("%s.yml", clanToSave.getPrefixClan()));

        FileConfiguration ClanConfig = clanToSave.SaveClanToConfig();
        if (ClanConfig == null) {
            OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was skipped save", clanToSave.getNameClan()));
            return;
        }

        ClanConfig.save(tempClanFile);

        OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was saved", clanToSave.getNameClan()));
    }

    private void RemoveClanToDeletedClanFolder(Clan clanToDelete) {
        File tempDeleteDir = UselessClan.getSerilManager().checkClanFolderOrCreate(DeletedClanFolder);
        File tempClanDir = UselessClan.getSerilManager().checkClanFolderOrCreate(ClanFolderName);

        FileConfiguration ClanConfig = clanToDelete.SaveClanToConfig();
        boolean isDeleted = false;


        for (File tempClanFile : Objects.requireNonNull(tempClanDir.listFiles())) {
            if (tempClanFile.getName().equals(String.format("%s.yml", clanToDelete.getPrefixClan()))) {
                if (tempClanFile.delete()) {
                    OwnerPlugin.getLogger().log(Level.INFO, String.format("Previous config of %s was delete", clanToDelete.getPrefixClan()));
                }
                isDeleted = true;
                break;
            }
        }

        if (!isDeleted) {
            OwnerPlugin.getLogger().log(Level.SEVERE, String.format("%s isnt finded!", clanToDelete.getPrefixClan()));
            return;
        }
        OwnerPlugin.getLogger().log(Level.INFO, String.format("%s is finded!", clanToDelete.getPrefixClan()));


        File newClanFile = new File(tempDeleteDir, String.format("%s.yml", clanToDelete.getPrefixClan()));
        try {
            ClanConfig.save(newClanFile);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        OwnerPlugin.getLogger().log(Level.FINE, String.format("%s was deleted successfully", clanToDelete.getPrefixClan()));
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
    public TopClanCounter getTopClans() { return TopClans; }

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

    public void RegisterOnlineClanPlayer(Clan playerClan, Player player) {
        EClanRole playerRole = playerClan.getMemberRole(player.getName());

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
        EClanRole playerRole = tempClan.getMemberRole(player.getName());

        RegisterOnlineClanPlayer(tempClan, player);
        getServer().getScheduler().runTaskLater(OwnerPlugin, () -> {
            if (playerRole == EClanRole.LEADER || playerRole == EClanRole.OFFICER) {
                ChatSender.MessageTo(player,"UselessClan",
                        String.format("Your clan have %d requests for join! ./clan requests", tempClan.getRequestCount()));
            }
        }, 200);

        OwnerPlugin.getLogger().log(Level.INFO, String.format( "Clan member %s Join to server, his clan is %s", player.getName(), tempClan.getNameClan()));
    }
    public void OnPlayerLeave(Player player) {
        OnlinePlayerClan tempOnlinePlayer = OnlineClanPlayers.get(player);
        if (tempOnlinePlayer == null) return;

        tempOnlinePlayer.getPlayerClan().getOnlineMembers().remove(player);
        OnlineClanPlayers.remove(player);
        OwnerPlugin.getLogger().log(Level.INFO, String.format("Clan member %s leaved from server", player.getName()));
    }


    public void createClansBackups() {
        OwnerPlugin.getLogger().log(Level.INFO, "Starting backup of server clans...");
        File tempDir = UselessClan.getSerilManager().checkClanFolderOrCreate(backupClanFolder);

        Calendar tempDate = Calendar.getInstance();

        File todayBackupFile = new File(tempDir, String.format("%d-%d-%d_%d-%d_ClansBackup",
                tempDate.get(Calendar.DAY_OF_MONTH), tempDate.get(Calendar.MONTH), tempDate.get(Calendar.YEAR),
                tempDate.get(Calendar.HOUR), tempDate.get(Calendar.MINUTE)));

        UselessClan.getSerilManager().checkFolderOrCreate(todayBackupFile);

        for (Clan tempClan : ServerClans.values()) {
            File tempClanFile = new File(todayBackupFile, String.format("%s.yml", tempClan.getPrefixClan()));
            FileConfiguration tempConfig = tempClan.SaveClanToConfig();
            if (tempConfig == null) continue;
            try {
                tempConfig.save(tempClanFile);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        OwnerPlugin.getLogger().log(Level.FINE, "Backup successfully created!");
    }
}
