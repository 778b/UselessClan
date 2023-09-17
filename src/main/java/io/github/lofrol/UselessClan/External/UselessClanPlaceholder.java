package io.github.lofrol.UselessClan.External;

import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class UselessClanPlaceholder extends PlaceholderExpansion {

    public UselessClanPlaceholder() {
    }

    @Override
    public @NotNull String getAuthor() {
        return "778b";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "UselessClan";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1";
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public @NotNull List<String> getPlaceholders() {
        List<String> tempList = new ArrayList<>();
        tempList.add("UselessClan_prefix");
        tempList.add("UselessClan_role");
        tempList.add("UselessClan_clanlevel");
        return tempList;
    }
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        if(params.equalsIgnoreCase("prefix")) {
            Clan tempClan = UselessClan.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";

            return String.format("&f[%s%s&f]&r", ClanManager.ClanLevelColors.get(tempClan.getClanLevel()), tempClan.getPrefixClan());
        }
        else if (params.equalsIgnoreCase("role")) {
            Clan tempClan = UselessClan.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";
            ClanMember tempMember = tempClan.getClanMember(player.getName());

            return String.format("&f[&6%s&f]&r", ClanManager.ClanRoleSolver(tempMember.getMemberRole()));
        }
        else if (params.equalsIgnoreCase("clanlevel")) {
            Clan tempClan = UselessClan.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";

            return String.format("&f[%s%d&f]&r",
                    ClanManager.ClanLevelColors.get(tempClan.getClanLevel()), tempClan.getClanLevel());
        }
        return "";
    }
}
