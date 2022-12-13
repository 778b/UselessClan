package io.github.lofrol.UselessClan.ClanCommands.Commands.UserCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class requestClanCommands {
    public static void setupCommands(Map<String, CommandBase> commands) {
        commands.put("help",        new helpUserCommand());
        commands.put("requests",    new requestsUserCommand());
        commands.put("top",         new topUserCommand());
    }
    public static class helpUserCommand extends PlayerCommandBase {

        @Override
        public boolean executeCommand(Player tempPlayer,Clan senderClan, String[] args) {
            ClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            ChatSender.MessageTo(tempPlayer,"UselessClan", "############# CLAN HELP #############");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan help&b - to call this menu");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan top&b - top of all clans");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan create %name&b - to create your own clan with name %name");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan leave&b - to leave from your clan");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan join %name&b - to send request for join the clan %name");
            if (UselessClan.EconomyPtr != null) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan deposit %value&b - to deposit money to your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan withdraw %value&b - to withdraw money from your clan");
            }
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan info&b - to info about your clan");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan mates&b - to execute list of clanmates");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan home&b - to teleport to home of your clan");
            if (senderClan == null) return false;
            if (SenderRole == ClanRole.OFFICER || SenderRole == ClanRole.LEADER) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan claim&b - to claim territory what you selected to clan territory");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan requests&b - to see list of all requests to join your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan accept %name&b - to accept %name for join to your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan kick %name&b - to kick player %name from your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan promote %name&b - to promote player %name of your clan");
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan demote %name&b - to demote player %name of your clan");
            }
            if (SenderRole.ordinal() >= senderClan.getSettingsClan().HomeChangerMinRole.ordinal()) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&a/Clan sethome&b - to set home location of your clan");
            }
            return true;
        }
    }

    public static class requestsUserCommand extends PlayerCommandBase {

        @Override
        public boolean executeCommand(Player tempPlayer,Clan senderClan, String[] args) {
            ClanRole SenderRole = null;
            if (senderClan != null) {
                SenderRole = senderClan.getMemberRole(tempPlayer.getName());
            }

            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou havent Clan!");
                return false;
            }
            if (SenderRole == ClanRole.LEADER || SenderRole == ClanRole.OFFICER) {
                if (senderClan.getRequests().size() > 0) {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "######## CLAN REQUESTS ########");
                    for (String tempMemberName: senderClan.getRequests()) {
                        ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# %s", tempMemberName));
                    }
                }
                else {
                    ChatSender.MessageTo(tempPlayer,"UselessClan", "0 requests for join to your clan");
                }
            }
            else {
                ChatSender.MessageTo(tempPlayer,"UselessClan", "&cYou rank is too low to do that!");
                return false;
            }
            return true;
        }
    }

    public static class topUserCommand extends PlayerCommandBase {

        @Override
        public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
            ChatSender.MessageTo(tempPlayer,"UselessClan", "########## CLAN TOP ##########");
            ChatSender.MessageTo(tempPlayer,"UselessClan", "# ClanName              Bank #");
            for (
                    Clan tempClan : UselessClan.getMainManager().getServerClans().values()) {
                ChatSender.MessageTo(tempPlayer,"UselessClan", String.format("# %s          %s ", tempClan.getNameClan(), tempClan.getMoneyClan()));
            }
            return true;
        }
    }
}
