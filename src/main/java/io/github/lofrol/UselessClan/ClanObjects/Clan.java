package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.Location;

import java.io.File;
import java.util.List;

public class Clan {
    /*
     *  Variables
     */
    private String PrefixClan;

    private String NameClan;
    private String DescriptionClan;

    private List<ClanMember> members;

    private String LeaderName;
    private Location HomeClan;

    private ClanSettings SettingsClan;

    private Double MoneyClan;



    /*
     *  Functions
     */
    public String getNameClan() {
        return NameClan;
    }

    public String getDescriptionClan() {
        return DescriptionClan;
    }

    public String getLeaderName() {
        return LeaderName;
    }

    public void SerializeClan(File Folder) {

    }

    public static Clan DeserializeClan(File ClanFile) {
        Clan tempClanObj = new Clan();

        return tempClanObj;
    }
}
