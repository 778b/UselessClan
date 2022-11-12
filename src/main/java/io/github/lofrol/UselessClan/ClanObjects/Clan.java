package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
        MoneyClan = 0.d;
        DescriptionClan = "Description of your clan";
        HomeClan = null;
        SettingsClan = new ClanSettings();
    }

    public void SendMessageForOnlinePlayers(String Message) {
        for (Player tempPlayer : OnlineMembers.keySet()) {
            tempPlayer.sendMessage("[" + PrefixClan + "] " + Message);
        }
    }

    public boolean IsClanMember(String PlayerName) {
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                return true;
            }
        }
        return false;
    }

    public ClanMember getClanMember(String PlayerName) {
        if (!IsClanMember(PlayerName)) return null;

        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                return tempMember;
            }
        }
        return null;
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

    public boolean SendRequestForJoin(String playerName) {
        for (String tempRequest : Requests) {
            if (tempRequest.equals(playerName)) {
                return false;
            }
        }
        Requests.add(playerName);
        return true;
    }

    public void ChangeLeader(String NewLeaderName) {
        LeaderName = NewLeaderName;
        for (ClanMember tempMember : Members) {
            if (tempMember.getMemberRole() == ClanRole.LEADER) {
                tempMember.setMemberRole(ClanRole.OFFICER);
            }
        }
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(NewLeaderName)) {
                tempMember.setMemberRole(ClanRole.LEADER);
            }
        }
    }

    public boolean ChangeMemberRole(String MemberName, ClanRole newRole) {
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(MemberName)) {
                if (tempMember.getMemberRole() == newRole) return false;
                tempMember.setMemberRole(newRole);
                break;
            }
        }
        return true;
    }

    public boolean PlayerJoinToClan(ClanRole Role, String PlayerName) {
        if (IsClanMember(PlayerName)) {
            return false;
        }

        ClanMember tempClanMember = new ClanMember(Role, PlayerName);
        Player tempPlayer = getServer().getPlayer(PlayerName);
        if (tempPlayer != null) {
            OnlineMembers.put(tempPlayer, tempClanMember);
        }
        Members.add(tempClanMember);
        getServer().getLogger().log(Level.INFO, "Player "+ PlayerName + " is joined to clan "+ this.getNameClan());
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
