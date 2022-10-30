package io.github.lofrol.uselessclan.commands;

import io.github.lofrol.uselessclan.ClanManager;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public class ClanCommands extends Command {

    private ClanManager ManagerPtr;
    public static ClanCommands CreateDefaultInts(ClanManager manager) {
        ClanCommands tempInst = new ClanCommands("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    protected ClanCommands(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        int size = args.length;
        if (size == 0) {
            // just only /Clan
            sender.sendMessage("Use command /Clan help, for access to clan system");
        }
        else if (size == 1) {
            // /Clan help/create/info/mates/join/accept/leave/top
            sender.sendMessage("arg[0] = " + args[0] + " size = " + size);
            if (args[0].equalsIgnoreCase( "help")) {
                sender.sendMessage("######################### CLAN HELP #########################");
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
                sender.sendMessage("For create your own clan you must have more than 10000$");
            }
            else if (args[0].equalsIgnoreCase("join")) {
                sender.sendMessage("You send request for join to this clan, wait until leader or officer accept this request");
            }
            else if (args[0].equalsIgnoreCase("accept")) {
                sender.sendMessage("You accept this player to join your clan!");
            }
            else {
                sender.sendMessage("3Invalid command. Use command /Clan help, for access to clan system");
            }
        }
        else {
            sender.sendMessage("4Invalid command. Use command /Clan help, for access to clan system");
        }
        //for (String k : args) {
        //sender.sendMessage(k + " " + size);
        //}
        return true;
    }
}
