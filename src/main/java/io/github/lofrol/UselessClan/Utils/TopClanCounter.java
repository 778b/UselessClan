package io.github.lofrol.UselessClan.Utils;

import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TopClanCounter {
    private final List<TopListClan> TopClans = new ArrayList<>();
    public void CalculateTop() {
        TopClans.clear();

        for (Clan tempClan : UselessClan.getMainManager().getServerClans().values()) {
            TopClans.add(new TopListClan(tempClan.getNameClan(), (int)tempClan.getMoneyClan(), tempClan.getClanLevel()));
        }

        TopClans.sort(new Comparator<>() {
            @Override
            public int compare(TopListClan o1, TopListClan o2) {
                if (o1.ClanLevel > o2.ClanLevel) {
                    return -1;
                }
                else if (o1.ClanLevel < o2.ClanLevel) {
                    return 1;
                }
                if (o1.ClanMoney > o2.ClanMoney) {
                    return -1;
                }
                return 0;
            }
        });
    }
    public List<TopListClan> getSortedClans() {
        return TopClans;
    }

}
