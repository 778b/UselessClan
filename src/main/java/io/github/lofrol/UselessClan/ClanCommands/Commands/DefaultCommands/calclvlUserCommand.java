package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class calclvlUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.CalculateLevel";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (WorldGuardPlugin.inst() == null || senderClan == null) return false;
        return (senderRole.ordinal() >= EClanRole.OFFICER.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (UselessClan.EconomyPtr != null)  {
            if (!UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), 2000)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Economy.NotEnoughMoneyForCalculate");
                return false;
            }
            else {
                UselessClan.EconomyPtr.depositPlayer(getOfflinePlayer(tempPlayer.getName()), 2000);
            }
        }

        UselessClan.getMainManager().CalculateClanLevel(senderClan);
        ChatSender.MessageTo(tempPlayer, "&4UselessClan", "Treasure.SuccessfullyCalculation");
        return true;
    }
}
