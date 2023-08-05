package io.github.lofrol.UselessClan;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.lofrol.UselessClan.ClanObjects.*;
import io.github.lofrol.UselessClan.Extensions.ClanManagerExtension;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import io.github.lofrol.UselessClan.Utils.TopClanCounter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static io.github.lofrol.UselessClan.SerializationManager.*;
import static org.bukkit.Bukkit.getServer;

public final class ClanManager {
    public static List<String> ClanLevelColors;

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

        ClanManager.ClanLevelColors = UselessClan.getConfigManager().getClanConfig().getClanLevelsColors();
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
    public void CalculateClanLevel(@NotNull Clan ClanToLevel) {
        if (UselessClan.getConfigManager().getClanConfig().isUseExtendCalculateClanLevels()) {
            CalculateClanLevelExtension(ClanToLevel);
        }
        else {
            CalculateClanLevelDefault(ClanToLevel);
        }
    }

    public void CalculateClanLevelExtension(@NotNull Clan ClanToLevel) {
        // Overriding by extensions if needed
        Extension.CalculateClanLevel(ClanToLevel);
    }

    public void CalculateClanLevelDefault(@NotNull Clan ClanToLevel) {
        Location tempTreasure = ClanToLevel.getTreasureClan();
        if (tempTreasure == null) {
            ClanToLevel.setClanLevel(0);
            return;
        }
        float radius = 5;
        float tempX = tempTreasure.getBlockX() + radius + 1;
        float tempY = tempTreasure.getBlockY() + 5;
        float tempZ = tempTreasure.getBlockZ() + radius + 1;
        int GoldBlockCount = 0;
        int DiamondBlockCount = 0;
        int EmeraldBlockCount = 0;
        
        for (int i = tempTreasure.getBlockX() - (int)radius; i < tempX; ++i) {
            for (int j = tempTreasure.getBlockY(); j < tempY; ++j) {
                for (int k = tempTreasure.getBlockZ() - (int)radius; k < tempZ; ++ k) {
                    Block tempBlock = tempTreasure.getWorld().getBlockAt(i,j,k);
                    if (tempBlock.getType() == Material.GOLD_BLOCK) {
                        GoldBlockCount++;
                    }
                    else if (tempBlock.getType() == Material.DIAMOND_BLOCK) {
                        DiamondBlockCount++;
                    }
                    else if (tempBlock.getType() == Material.EMERALD_BLOCK) {
                        EmeraldBlockCount++;
                    }
                }
            }
        }
        int MaxRating = 1200;
        float Rating = (GoldBlockCount + (DiamondBlockCount * 2) + (EmeraldBlockCount * 3)) * 1.5f;
        float level = Rating / MaxRating * 10.f;

        getServer().getLogger().log(Level.INFO,
                String.format("[UselessClan] Calculated level of clan %s, Points = %d, Max Points = %d",
                        ClanToLevel.getPrefixClan(), (int)Rating, MaxRating));
        ClanToLevel.setClanLevel((int)level);
    }

    public void CalculateAllClansLevels(boolean isDefault) {
        if (isDefault) {
            for (Clan tempClan : ServerClans.values()) {
                CalculateClanLevelDefault(tempClan);
            }
        }
        else {
            for (Clan tempClan : ServerClans.values()) {
                CalculateClanLevelExtension(tempClan);
            }
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
        if (OnlineClanPlayers.entrySet().toArray()[0] instanceof Player tempPlayer) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager tempRegionManager = container.get(BukkitAdapter.adapt(tempPlayer.getWorld()));
            if (tempRegionManager != null) {
                tempRegionManager.removeRegion(clanToDelete.getClanRegionId(),RemovalStrategy.REMOVE_CHILDREN);
            }
        }

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

        FileConfiguration ClanConfig = clanToSave.SaveClanToConfig(false);
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

        FileConfiguration ClanConfig = clanToDelete.SaveClanToConfig(true);
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
                ChatSender.NonTranslateMessageTo(player,"UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage(
                                "Enter.HaveRequestsOnJoin"), tempClan.getRequestCount()));
            }
        }, 200);

        OwnerPlugin.getLogger().log(Level.INFO, String.format(
                "Clan member %s Join to server, his clan is %s", player.getName(), tempClan.getNameClan()));
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
            FileConfiguration tempConfig = tempClan.SaveClanToConfig(false);
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
