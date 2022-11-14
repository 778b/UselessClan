package io.github.lofrol.UselessClan.External;

import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UselessClanPlaceholder extends PlaceholderExpansion {
    private UselessClan OwnerPlugin = null;

    public UselessClanPlaceholder(UselessClan owner) {
        OwnerPlugin = owner;
    }

    @Override
    public String getAuthor() {
        return "778b";
    }

    @Override
    public String getIdentifier() {
        return "UselessClan";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public List<String> getPlaceholders() {
        List<String> tempList = new ArrayList<>();
        tempList.add("UselessClan_prefix");
        tempList.add("UselessClan_role");
        return tempList;
    }
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        if(params.equalsIgnoreCase("prefix")) {
            Clan tempClan = OwnerPlugin.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";
            return String.format("&r[&6%s&r]", tempClan.getPrefixClan());
        }
        else if (params.equalsIgnoreCase("role")) {
            Clan tempClan = OwnerPlugin.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";
            ClanMember tempMember = tempClan.getClanMember(player.getName());
            return String.format("&r[&6%s&r]", ClanMember.ClanRoleSolver(tempMember.getMemberRole()));
        }
        return "";
    }
}
