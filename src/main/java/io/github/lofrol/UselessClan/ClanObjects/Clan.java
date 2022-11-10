package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clan {
    /*
     *  Variables
     */
    private String PrefixClan;

    private String NameClan;
    private String DescriptionClan;

    private List<ClanMember> Members;

    private Map<Player, ClanMember> OnlineMembers;

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
        Members = new ArrayList<>();
        OnlineMembers = new HashMap<>();
        //Join leader to clan by function
        Members.add(new ClanMember(ClanRole.LEADER, Leader));
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

    public List<ClanMember> getMembers() {
        return Members;
    }
    public Map<Player, ClanMember> getOnlineMembers() {
        return OnlineMembers;
    }


    public void SerializeClan(File Folder) {

    }

    public static Clan DeserializeClan(File ClanFile) {
        //Clan tempClanObj = new Clan();

        return new Clan();
    }
}
