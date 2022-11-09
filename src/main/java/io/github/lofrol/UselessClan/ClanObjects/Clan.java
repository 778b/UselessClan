package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
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
    private Double MaxPrivateDistance;

    public Clan() {

    }

    // Creating by leader
    public Clan(String ClanName, String Leader)  {
        NameClan = ClanName;
        LeaderName =  Leader;
        members = new ArrayList<ClanMember>();
        members.add(new ClanMember(ClanRole.LEADER, Leader));
        MoneyClan = 0.d;
        DescriptionClan = "Description of your clan";
        //HomeClan = new Location();
        SettingsClan = new ClanSettings();
    }


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
        //Clan tempClanObj = new Clan();

        return new Clan();
    }
}
