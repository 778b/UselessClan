package io.github.lofrol.UselessClan.External;

import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UselessClanPlaceholder extends PlaceholderExpansion {
    UselessClan OwnerPlugin = null;

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
    public String onRequest(final OfflinePlayer player, @NotNull final String params) {
        if(params.equalsIgnoreCase("prefix")) {
            Clan tempClan = OwnerPlugin.getMainManager().FindClanToPlayer(player.getName());
            return tempClan.getPrefixClan();
        }
        return "";
    }
}
