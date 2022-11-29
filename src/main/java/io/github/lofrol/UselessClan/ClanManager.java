package io.github.lofrol.UselessClan;

import io.github.lofrol.UselessClan.ClanObjects.*;
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

public class ClanManager {
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

    private static final String ClanFolder = "Clans";

    private final UselessClan OwnerPlugin;

    private final Map<Player, OnlinePlayerClan> OnlineClanPlayers;
    private final Map<String, Clan> ServerClans;

    public ClanManager(UselessClan owner) {
        ServerClans = new HashMap<>();
        OnlineClanPlayers = new HashMap<>();

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

    public void LoadClans() {
        try {
            File tempDir = checkPluginFolderOrCreate();

            for (File tempClanFile : Objects.requireNonNull(tempDir.listFiles())) {
                FileConfiguration ClanConfig = new YamlConfiguration();
                ClanConfig.load(tempClanFile);

                Clan tempClan = Clan.CreateClanFromConfig(ClanConfig);
                if (tempClan == null) continue;
                OwnerPlugin.getLogger().log(Level.FINE, String.format("Clan %s was loaded successfully!", tempClan.getPrefixClan()));
                ServerClans.put(tempClan.getPrefixClan(), tempClan);
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
