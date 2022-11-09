package io.github.lofrol.UselessClan.commands;

import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.lofrol.UselessClan.UselessClan.EconomyPtr;
import static org.bukkit.Bukkit.getServer;

public class ClanCommand extends Command {

    private ClanManager ManagerPtr;
    public static ClanCommand CreateDefaultInts(ClanManager manager) {
        ClanCommand tempInst = new ClanCommand("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    protected ClanCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player tempPlayer = ((Player)sender);

        int size = args.length;
        if (size == 0) {
            // just only /Clan
            sender.sendMessage("Use command /Clan help, for access to clan system");
        }
        else if (size == 1) {
            // /Clan help/create/info/mates/join/accept/leave/top
            if (args[0].equalsIgnoreCase( "help")) {
                sender.sendMessage("##################### CLAN HELP #####################");
                sender.sendMessage("/Clan help         - to call this menu");
                sender.sendMessage("/Clan top          - top of all clans");
                sender.sendMessage("/Clan create %name - to create your own clan with name %name");
                sender.sendMessage("/Clan leave        - to leave from your clan");
                sender.sendMessage("/Clan join %name   - to send request(join) of your join for clan %name");
                sender.sendMessage("/Clan info         - to info about your clan");
                sender.sendMessage("/Clan accept %name - to accept %name for join to your clan");
            }
            else if (args[0].equalsIgnoreCase("top")) {
                sender.sendMessage("########## CLAN TOP ##########");
                for (Clan tempClan : ManagerPtr.getServerClans().values()) {
                    sender.sendMessage(tempClan.getNameClan());
                }
            }
            else if (args[0].equalsIgnoreCase("create")) {
                sender.sendMessage("You forgot about clan %name, use /Clan create %name, %name = name of your clan");
            }
            else if (args[0].equalsIgnoreCase("join")) {
                sender.sendMessage("You forgot about clan %name, use /Clan join %name, %name = name of clan");
            }
            else if (args[0].equalsIgnoreCase("mates")) {
                sender.sendMessage("########## CLANMATES ##########");
            }
            else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("########## CLAN INFO ##########");
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                sender.sendMessage("You forgot about player %name, use /Clan accept %name, %name = name of player, which you want to accept");
            }
            else {
                sender.sendMessage("2Invalid command. Use command /Clan help, for access to clan system");
            }
        }
        else if (size == 2) {
            if (args[0].equalsIgnoreCase("create")) {

                if (args[1].length() < 4) {
                    sender.sendMessage("Your name is too short, name must be >4 symbols");
                    return false;
                }

                // check for bad symbols
                boolean success = true;
                for (char tempChar : args[1].toCharArray()) {
                    if (!(tempChar == 32 || tempChar == 95 || (tempChar >= 65 && tempChar <= 90) || (tempChar >= 97 && tempChar <= 122))) {
                        success = false;
                        break;
                    }
                }

                if (!success) {
                    sender.sendMessage("Invalid clan name, use [A-Z; a-z; space; _]");
                    return false;
                }
                else {
                    // check another clan name collision
                    if (ManagerPtr.getServerClans().get(args[1]) != null) {
                        sender.sendMessage("Clan with this name already exist!");
                        return false;
                    }
                }
                // Creating new clan
                double moneyToClan = 10000;
                if (!UselessClan.EconomyPtr.has(tempPlayer, moneyToClan)) {
                    sender.sendMessage("For create your own clan you must have more than " + moneyToClan + "$");
                    return false;
                }
                EconomyResponse response = UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, 10000.d);

                ManagerPtr.getServerClans().put(args[1], new Clan(args[1], tempPlayer.getName()));
                sender.sendMessage("Clan " + args[1] + " was created successfully!");
            }
            else if (args[0].equalsIgnoreCase("join")) {
                sender.sendMessage("You send request for join to this clan, wait until leader or officer accept this request");
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                sender.sendMessage("You accept this player to join your clan!");
            }
            else {
                sender.sendMessage("Invalid command. Use command /Clan help, for access to clan system");
            }
        }
        else {
            sender.sendMessage("Invalid command. Use command /Clan help, for access to clan system");
        }
        return true;
    }
}
