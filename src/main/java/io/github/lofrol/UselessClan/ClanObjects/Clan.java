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

    @Deprecated
    public Clan() {

    }

    // Creating by leader
    public Clan(String ClanPrefix, String CreatorName)  {
        NameClan = ClanPrefix;
        PrefixClan = ClanPrefix;
        LeaderName =  CreatorName;
        Requests = new ArrayList<>();
        Members = new ArrayList<>();
        OnlineMembers = new HashMap<>();
        PlayerJoinToClan(ClanRole.LEADER, CreatorName);
        MoneyClan = 0.d;
        DescriptionClan = "Description of your clan";
        HomeClan = null;
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
    public String getPrefixClan() { return PrefixClan; }
    public Double getMoneyClan() { return MoneyClan; }

    /*
     *  Setters FUnctions
     */
    public boolean setPrefixClan(String prefixClan) {
        if (prefixClan.length() >= 3 && prefixClan.length() <=5) {
            for (char tempChar : prefixClan.toCharArray()) {
                // check for 0-9 or A-Z or a-z
                if (!((tempChar >= 48 && tempChar <= 57) ||
                        (tempChar >= 65 && tempChar <= 90) ||
                        (tempChar >= 97 && tempChar <= 122)))
                {
                    return false;
                }
            }
            PrefixClan = prefixClan;
            return true;
        }
        return false;
    }



    public void SerializeClan(File Folder) {

    }

    public static Clan DeserializeClan(File ClanFile) {
        //Clan tempClanObj = new Clan();

        return new Clan();
    }
}
