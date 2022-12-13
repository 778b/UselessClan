package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.*;

public final class ClanCommand extends Command {

    private ClanManager ManagerPtr;

    private Map<String, CommandBase> ClanCommands;
    public static ClanCommand CreateDefaultInst(ClanManager manager) {
        ClanCommand tempInst = new ClanCommand("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerCommand() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    private ClanCommand(@NotNull String name, @NotNull String description,
                          @NotNull String usageMessage,
                          @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
        ClanCommands = new HashMap<>();

    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        Clan SenderClan = ManagerPtr.FindClanToPlayer(tempPlayer.getName());
        ClanRole SenderRole = null;
        if (SenderClan != null) {
            SenderRole = SenderClan.getMemberRole(tempPlayer.getName());
        }

        int size = args.length;
        if (size == 0) {
            // just only /Clan
            ChatSender.MessageTo(tempPlayer,"UselessClan", "Use command &a/Clan help&b, for access to clan system");
        }
        else if (size == 1) {
            if (args[0].equalsIgnoreCase("top")) {
            }
            else if (args[0].equalsIgnoreCase("version")) {
                PluginDescriptionFile tempDescr =  ManagerPtr.getOwnerPlugin().getDescription();
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("Plugin version is %s" ,tempDescr.getVersion()));
                return true;
            }
            else if (args[0].equalsIgnoreCase("create")) {
            }
            else if (args[0].equalsIgnoreCase("join")) {
            }
            else if (args[0].equalsIgnoreCase("deposit")) {
            }
            else if (args[0].equalsIgnoreCase("withdraw")) {
            }
            else if (args[0].equalsIgnoreCase("leave")) {
            }
            else if (args[0].equalsIgnoreCase("home")) {
            }
            else if (args[0].equalsIgnoreCase("sethome")) {
            }
            else if (args[0].equalsIgnoreCase("mates")) {
            }
            else if (args[0].equalsIgnoreCase("delete")) {
            }
            else if (args[0].equalsIgnoreCase("info")) {
            }
            else if (args[0].equalsIgnoreCase("requests")) {
            }
            else if (args[0].equalsIgnoreCase("accept")) {
            }
            else if (args[0].equalsIgnoreCase("kick")) {
            }
            else if (args[0].equalsIgnoreCase("promote")) {
            }
            else if (args[0].equalsIgnoreCase("demote")) {
            }
            else if (args[0].equalsIgnoreCase("claim")) {
            }
            else {
                ChatSender.MessageTo(tempPlayer,"UselessClan",
                        "&cInvalid command. Use command &a/Clan help&b, for access to clan system");
                return false;
            }
        }
        else if (size == 2) {
            if (args[0].equalsIgnoreCase("create")) {
            }
            else if (args[0].equalsIgnoreCase("join")) {
            }
            else if (args[0].equalsIgnoreCase("deposit")) {
            }
            else if (args[0].equalsIgnoreCase("withdraw")) {
            }
            else if (args[0].equalsIgnoreCase("accept")) {
            }
            else {
                ChatSender.MessageTo(tempPlayer,"UselessClan",
                        "&cInvalid command. Use command &a/Clan help&c, for access to clan system");
                return false;
            }
        }
        return true;
    }
}
