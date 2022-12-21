package io.github.lofrol.UselessClan.Utils;

import org.jetbrains.annotations.NotNull;

public class TopListClan {
    public String ClanName;
    public int ClanMoney;
    public int ClanLevel;

    public TopListClan(@NotNull String name, int money, int level) {
        ClanName = name;
        ClanMoney = money;
        ClanLevel = level;
    }
}
