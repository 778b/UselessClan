package io.github.lofrol.UselessClan.ClanObjects;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Bukkit.getWorld;

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

    public static Clan CreateClanFromConfig(FileConfiguration ClanConfig)  {
        String ClanPrefix = ClanConfig.getString("UselessClan.ClanPrefix");
        String ClanName = ClanConfig.getString("UselessClan.ClanName");
        String LeaderName = ClanConfig.getString("UselessClan.ClanLeader");
        double MoneyClan = ClanConfig.getDouble("UselessClan.Money");
        String DescriptionClan = ClanConfig.getString("UselessClan.Description");
        Location HomeClan = ClanConfig.getLocation("UselessClan.Home");
        List<String> Requests = ClanConfig.getStringList("UselessClan.Requests");
        List<ClanMember> Members = (List<ClanMember>)ClanConfig.getList("UselessClan.Members");
        ClanSettings SettingsClan = (ClanSettings)ClanConfig.get("UselessClan.Settings");

        if (ClanPrefix != null && ClanName != null && LeaderName != null && DescriptionClan != null &&
                HomeClan != null && Requests != null && Members != null && SettingsClan != null) {
            return new Clan(ClanPrefix, ClanName, LeaderName, MoneyClan,
                    HomeClan, Requests, Members, DescriptionClan, SettingsClan);
        }
        return null;
    }

    // Creating by file loader
    private Clan(String ClanPrefix, String ClanName, String LeaderName,
                Double MoneyClan, Location HomeClan, List<String> Requests,
                List<ClanMember> Members, String DescriptionClan, ClanSettings SettingsClan)  {
        this.PrefixClan = ClanPrefix;
        this.NameClan = ClanName;
        this.LeaderName =  LeaderName;
        this.Requests = Requests;
        this.Members = Members;
        this.MoneyClan = MoneyClan;
        this.DescriptionClan = DescriptionClan;
        this.HomeClan = HomeClan;
        this.SettingsClan = SettingsClan;

        this.OnlineMembers = new HashMap<>();
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
            tempPlayer.sendMessage(String.format("[%s] %s", PrefixClan, Message));
        }
    }

    public void SendMessageForOnlineOfficers(String Message) {
        for (Player tempPlayer : OnlineMembers.keySet()) {
            ClanRole tempRole = OnlineMembers.get(tempPlayer).getMemberRole();
            if (tempRole == ClanRole.OFFICER || tempRole == ClanRole.LEADER) {
                tempPlayer.sendMessage(String.format("[%s] %s", PrefixClan, Message));
            }
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

    public void PlayerJoinToClan(ClanRole Role, String PlayerName) {
        if (IsClanMember(PlayerName)) {
            return;
        }
        ClanMember tempClanMember = new ClanMember(Role, PlayerName);
        Player tempPlayer = getServer().getPlayer(PlayerName);
        if (tempPlayer != null) {
            OnlineMembers.put(tempPlayer, tempClanMember);
        }
        Members.add(tempClanMember);
        return;
    }
    public void PlayerLeavedFromClan(String PlayerName) {
        if (!IsClanMember(PlayerName)) return;

        Player tempPlayer = getServer().getPlayer(PlayerName);
        if (tempPlayer != null) {
            OnlineMembers.remove(tempPlayer);
        }
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                Members.remove(tempMember);
                return;
            }
        }
    }
    public void PlayerLeavedFromClan(Player player) {
        OnlineMembers.remove(player);
        Members.remove(getClanMember(player.getName()));
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
    public Location getHomeClan() { return HomeClan; }
    public List<String> getRequests() {
        return Requests;
    }
    public int getRequestCount() {
        return Requests.size();
    }

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

    public void setHomeClan(Location newHomeLocation) {
        HomeClan = newHomeLocation;
    }

    public void DepositMoneyToClan(Double moneyClan) {
        MoneyClan += moneyClan;
    }

    public void WithdrawMoneyFromClan(Double moneyClan) {
        MoneyClan -= moneyClan;
    }

    public void SerializeClan(File Folder) {

    }

    public static Clan DeserializeClan(File ClanFile) {
        //Clan tempClanObj = new Clan();

        return new Clan();
    }
}
