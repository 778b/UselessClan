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

public class ClanChatCommand extends Command {

    private ClanManager ManagerPtr;

    public static ClanChatCommand CreateDefaultInts(ClanManager manager) {
        ClanChatCommand tempInst = new ClanChatCommand("ClanChat", "Default command for clan chat",
                "Use &5/Clan help&r for learning more", Stream.of("clanchat", "uselessclanchat", "ucc", "UCC").collect(Collectors.toList()));

        tempInst.ManagerPtr = manager;

        return tempInst;
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    protected ClanChatCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player tempPlayer = (Player) (sender);
        if (tempPlayer == null) return false;



        int size = args.length;
        if (size == 0) return false;

        //ManagerPtr.getServerClans().get()) {
        //}

        return true;
    }
}
