package io.github.lofrol.UselessClan.commands;

import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class ClanAdminCommand extends Command {

    private ClanManager ManagerPtr;

    public static ClanAdminCommand CreateDefaultInts(ClanManager manager) {
        ClanAdminCommand tempInst = new ClanAdminCommand("ClanAdmin", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clanadmin", "ClanAdmin", "clad", "ClAd").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    protected ClanAdminCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player tempPlayer = (Player)(sender);
        if (tempPlayer == null) return false;

        if (!tempPlayer.hasPermission("UselessClan.Admin")) return false;

        int size = args.length;
        if (size == 0) {
            // just only /ClanAdmin
            sender.sendMessage("Use command /ClanAdmin help, for access to clan system");
            return true;
        }
        else if (size == 1) {
            // /Clan help/list/info/delete/force
            sender.sendMessage("arg[0] = " + args[0] + " size = " + size);
            if (args[0].equalsIgnoreCase( "help")) {
                sender.sendMessage("######################### CLAN HELP #########################");
                sender.sendMessage("/ClAd                                - to call this menu");
                sender.sendMessage("/ClAd list                           - list of all clans");
                sender.sendMessage("/ClAd info   %name                   - to info any clan");
                sender.sendMessage("/ClAd delete %name                   - to info any clan");
                sender.sendMessage("/ClAd force  %name %setting %args    - to force modify clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage("########## CLAN TOP ##########");
                return true;
            }
            else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("You forgot about clan %name, use /ClAd info %name, %name = name of clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                sender.sendMessage("You forgot about clan %name, use /ClAd delete %name, %name = name of clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("force")) {
                sender.sendMessage("You forgot about arguments, use /ClAd force %name %setting %args," +
                        "%name = name of clan, %setting = setting to change, %args = new value or arguments");
                return true;
            }
        }
        else if (size == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("Admin Info about this clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                sender.sendMessage("You deleted a clan");
                return true;
            }
            else if (args[0].equalsIgnoreCase("force")) {
                sender.sendMessage("You forgot about arguments, use /ClAd force %name %setting %args," +
                        "%name = name of clan, %setting = setting to change, %args = new value or arguments");
                return true;
            }
        }
        else if (size > 3) {
            // /clanadmin force %clanName %setting arg == 4 > 3
            if (args[0].equalsIgnoreCase("force")) {
                Clan FindedClan = null;
                for (Clan tempClan: ManagerPtr.getServerClans().values())
                {
                    if (args[1].equalsIgnoreCase(tempClan.getNameClan())) {
                        FindedClan = tempClan;
                        break;
                    }
                }
                if (FindedClan != null) {
                    sender.sendMessage("You forgot about arguments, use /ClAd force %name %setting %args," +
                            "%name = name of clan, %setting = setting to change, %args = new value or arguments");
                    return true;
                }
            }
        }
        sender.sendMessage("Invalid command. Use command /Clan help, for access to clan system");
        return false;
    }
}
