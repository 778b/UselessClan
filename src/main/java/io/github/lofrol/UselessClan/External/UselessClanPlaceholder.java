package io.github.lofrol.UselessClan.External;

import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class UselessClanPlaceholder extends PlaceholderExpansion {
    private final UselessClan OwnerPlugin;

    public UselessClanPlaceholder(UselessClan owner) {
        OwnerPlugin = owner;
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
        return "1.0.0";
    }

    @Override
    public boolean persist() { return true; }

    @Override
    public @NotNull List<String> getPlaceholders() {
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
            return String.format("&r[%s%s&r]", ClanManager.ClanLevelColors[tempClan.getClanLevel()], tempClan.getPrefixClan());
        }
        else if (params.equalsIgnoreCase("role")) {
            Clan tempClan = OwnerPlugin.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";
            ClanMember tempMember = tempClan.getClanMember(player.getName());
            return String.format("&r[&6%s&r]", ClanManager.ClanRoleSolver(tempMember.getMemberRole()));
        }
        else if (params.equalsIgnoreCase("clanlevel")) {
            Clan tempClan = OwnerPlugin.getMainManager().FindClanToPlayer(player.getName());
            if (tempClan == null) return "";

            return String.format("&r[&6%d&r]", tempClan.getClanLevel());
        }
        return "";
    }
}
