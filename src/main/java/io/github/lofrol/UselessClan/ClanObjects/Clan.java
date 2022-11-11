package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class Clan {
    /*
     *  Variables
     */
    private String PrefixClan;

    private String NameClan;
    private String DescriptionClan;

    private List<ClanMember> Members;
    private List<String> Requests;

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
        Requests = new ArrayList<>();
        Members = new ArrayList<>();
        OnlineMembers = new HashMap<>();
        //Join leader to clan by function
        Members.add(new ClanMember(ClanRole.LEADER, Leader));
        MoneyClan = 0.d;
        DescriptionClan = "Description of your clan";
        //HomeClan = new Location();
        SettingsClan = new ClanSettings();
    }

    public boolean IsClanMember(String PlayerName) {
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) return true;
        }
        return false;
    }

    public boolean MemberRoleCheck(String PlayerName, ClanRole RoleToCheck) {
        if (!IsClanMember(PlayerName)) return false;

        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                return tempMember.getMemberRole().equals(RoleToCheck);
            }
        }

        return false;
    }

    public ClanRole getMemberRole(String PlayerName) {
        if (!IsClanMember(PlayerName)) return ClanRole.NONE;

        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                return tempMember.getMemberRole();
            }
        }
        return ClanRole.NONE;
    }

    public boolean PlayerJoinToClan(ClanRole Role, String PlayerName) {
        if (!IsClanMember(PlayerName)) return false;

        ClanMember tempClanMember = new ClanMember(Role, PlayerName);
        Player tempPlayer = getServer().getPlayer(PlayerName);
        if (tempPlayer != null) {
            OnlineMembers.put(tempPlayer, tempClanMember);
        }
        Members.add(tempClanMember);
        return true;
    }
    public boolean PlayerLeavedFromClan(String PlayerName) {
        if (!IsClanMember(PlayerName)) return false;

        Player tempPlayer = getServer().getPlayer(PlayerName);
        if (tempPlayer != null) {
            OnlineMembers.remove(tempPlayer);
        }
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                Members.remove(tempMember);
                return true;
            }
        }

        return true;
    }
    public boolean PlayerLeavedFromClan(Player player) {
        OnlineMembers.remove(player);
        Members.remove(player);
        return true;
    }



    /*
     *  Getters Functions
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

    public ClanSettings getSettingsClan() {
        return SettingsClan;
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
