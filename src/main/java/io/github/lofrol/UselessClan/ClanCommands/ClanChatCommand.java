package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanObjects.OnlinePlayerClan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ClanChatCommand extends Command {
    public static ClanChatCommand CreateDefaultInst() {
        return new ClanChatCommand("ClanChat", "Default command for clan chat",
                "Use &5/Clan help&r for learning more", Stream.of("ucc").collect(Collectors.toList()));
    }

    private ClanChatCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        int size = args.length;
        if (size == 0) return false;

        OnlinePlayerClan SenderClan = UselessClan.getMainManager().getOnlineClanPlayers().get(tempPlayer);
        if (SenderClan.getPlayerClan().getOnlineMembers().size() < 2) {
            ChatSender.MessageTo(tempPlayer,"UselessClan", "Main.ZeroPlayerInClanChat");
            return false;
        }
        // Only $maxLength length of message alloyed
        StringBuilder MegaString = new StringBuilder();
        int currentLength = 0;
        int maxLength = 200;
        for (String tempString : args) {
            currentLength += tempString.length();
            if (currentLength > maxLength) {
                int rest = currentLength - maxLength;
                MegaString.append(" ").append(tempString, 0, rest);
                break;
            }
            MegaString.append(" ").append(tempString);
        }

        SenderClan.getPlayerClan().SendMessageForOnlinePlayers(
                String.format("&6%s &2->&b%s",tempPlayer.getName(), MegaString));
        return true;
    }
}
