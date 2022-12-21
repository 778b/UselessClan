package io.github.lofrol.UselessClan.ClanObjects;

import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Bukkit.getWorld;

public class Clan {

    /*
     *  Variables
     */
    private final String PrefixClan;

    private String NameClan;
    private final String DescriptionClan;

    private final List<ClanMember> Members;

    private final List<String> Requests;

    private final transient Map<Player, ClanMember> OnlineMembers;

    private String LeaderName;

    private Location HomeClan;

    private final ClanSettings SettingsClan;

    private double MoneyClan;
    private int ClanLevel;
    private String ClanRegionId;

    private boolean NeedToSave;

    // Creating by file loader
    private Clan(String ClanPrefix, String ClanName, String LeaderName,
                Double MoneyClan, Location HomeClan, List<String> Requests,
                List<ClanMember> Members, String DescriptionClan, ClanSettings SettingsClan,
                int level, String RegionName)  {
        this.PrefixClan = ClanPrefix;
        this.NameClan = ClanName;
        this.LeaderName =  LeaderName;
        this.Requests = Requests;
        this.Members = Members;
        this.MoneyClan = MoneyClan;
        this.DescriptionClan = DescriptionClan;
        this.HomeClan = HomeClan;
        this.SettingsClan = SettingsClan;
        this.ClanLevel = level;
        this.ClanRegionId = RegionName;

        this.OnlineMembers = new HashMap<>();
        NeedToSave = true;
    }

    // return null if didn't need to save
    public FileConfiguration SaveClanToConfig() {
        if (!NeedToSave) return null;

        FileConfiguration ClanConfig = new YamlConfiguration();

        ClanConfig.set("UselessClan.ClanPrefix", PrefixClan);
        ClanConfig.set("UselessClan.ClanName", NameClan);
        ClanConfig.set("UselessClan.ClanLeader", LeaderName);
        ClanConfig.set("UselessClan.Money", MoneyClan);
        ClanConfig.set("UselessClan.Description", DescriptionClan);
        ClanConfig.set("UselessClan.ClanLevel", ClanLevel);
        ClanConfig.set("UselessClan.ClanRegionName", ClanRegionId);


        ClanConfig.set("UselessClan.Requests.size", Requests.size());
        for (int i = 0; i< Requests.size(); ++i) {
            ClanConfig.set(String.format("UselessClan.Requests.%d", i), Requests.get(i));
        }

        ClanConfig.set("UselessClan.Home.exists", (HomeClan != null));
        if (HomeClan != null) {
            ClanConfig.set("UselessClan.Home.X",HomeClan.getBlockX());
            ClanConfig.set("UselessClan.Home.Y",HomeClan.getBlockY());
            ClanConfig.set("UselessClan.Home.Z",HomeClan.getBlockZ());
        }

        ClanConfig.set("UselessClan.Members.size", Members.size());
        for (int i = 0; i< Members.size(); ++i) {
            ClanMember tempMember = Members.get(i);
            String MasterStringMember = String.format("%s/%d/%s/",
                    tempMember.getPlayerName(), tempMember.getMemberRole().ordinal(), tempMember.getGeneralPlayerDeposit());
            ClanConfig.set(String.format("UselessClan.Members.%d", i), MasterStringMember);
        }

        ClanConfig.set("UselessClan.Settings", SettingsClan.getSerializationString());

        NeedToSave = false;
        return ClanConfig;
    }

    public static Clan CreateClanFromConfig(FileConfiguration ClanConfig)  {
        String ClanPrefix = ClanConfig.getString("UselessClan.ClanPrefix");
        String ClanName = ClanConfig.getString("UselessClan.ClanName");
        String LeaderName = ClanConfig.getString("UselessClan.ClanLeader");
        double MoneyClan = ClanConfig.getDouble("UselessClan.Money");
        String DescriptionClan = ClanConfig.getString("UselessClan.Description");
        int ClanLevel = ClanConfig.getInt("UselessClan.ClanLevel");
        String ClanRegion = ClanConfig.getString("UselessClan.ClanRegionName");

        // Home start
        Location HomeClan = null;
        if (ClanConfig.getBoolean("UselessClan.Home.exists")) {
            int tempX = ClanConfig.getInt("UselessClan.Home.X");
            int tempY = ClanConfig.getInt("UselessClan.Home.Y");
            int tempZ = ClanConfig.getInt("UselessClan.Home.Z");
            World tempWorld = getServer().getWorld("world");
            HomeClan = new Location(tempWorld, tempX,tempY,tempZ);
        }
        // Home end

        // Requests start
        List<String> TempRequest = new ArrayList<>();
        int tempSize = ClanConfig.getInt("UselessClan.Requests.size");
        for (int i = 0; i< tempSize; ++i) {
            String tempString = ClanConfig.getString(String.format("UselessClan.Requests.%d", i));
            if (tempString == null) continue;
            TempRequest.add(tempString);
        }
        // Requests end

        // Members start
        List<ClanMember> TempMembers = new ArrayList<>();
        tempSize = ClanConfig.getInt("UselessClan.Members.size");
        for (int i = 0; i< tempSize; ++i) {
            String rawMemberString = ClanConfig.getString(String.format("UselessClan.Members.%d", i));
            if (rawMemberString == null) continue;
            String TempName = null;
            EClanRole TempRole = null;
            double TempDeposit = 0;

            StringBuilder param = new StringBuilder();
            int Stage = 0;
            for (char tc: rawMemberString.toCharArray()) {
                if (tc == '/') {
                    if (Stage == 0) {
                        TempName = param.toString();
                        param = new StringBuilder();
                        ++Stage;
                    }
                    else if (Stage == 1) {
                        switch (Integer.parseInt(param.toString())) {
                            case 1 -> TempRole = EClanRole.ROOKIE;
                            case 2 -> TempRole = EClanRole.MEMBER;
                            case 3 -> TempRole = EClanRole.OFFICER;
                            case 4 -> TempRole = EClanRole.LEADER;
                            default -> TempRole = EClanRole.NONE;
                        }
                        param = new StringBuilder();
                        ++Stage;
                    }
                    else if (Stage == 2) {
                        TempDeposit = Double.parseDouble(param.toString());
                        param = new StringBuilder();
                        ++Stage;
                    }
                }
                else param.append(tc);
            }
            TempMembers.add(new ClanMember(TempRole, TempName, TempDeposit));
        }
        // Members end

        // Settings start
        ClanSettings TempSettings = new ClanSettings();
        String MasterSettingsString = ClanConfig.getString("UselessClan.Settings");
        if (MasterSettingsString == null) {
            getServer().getLogger().log(Level.SEVERE, "Cant read UselessClan.Settings");
            return null;
        }
        TempSettings.InitializeSettingsFromString(MasterSettingsString);
        // Settings end

        if (ClanPrefix != null && ClanName != null && LeaderName != null && DescriptionClan != null) {
            return new Clan(ClanPrefix, ClanName, LeaderName, MoneyClan,
                    HomeClan, TempRequest, TempMembers, DescriptionClan,
                    TempSettings, ClanLevel, ClanRegion);
        }
        return null;
    }

    // Creating by leader
    public Clan(String ClanPrefix, String CreatorName)  {
        NameClan = ClanPrefix;
        PrefixClan = ClanPrefix;
        LeaderName =  CreatorName;
        Requests = new ArrayList<>();
        Members = new ArrayList<>();
        OnlineMembers = new HashMap<>();
        ClanLevel = 0;
        MoneyClan = 0.d;
        ClanRegionId = null;
        DescriptionClan = "Description of your clan";
        HomeClan = null;
        SettingsClan = new ClanSettings();

        NeedToSave = true;
    }

    public void SendMessageForOnlinePlayers(String Message) {
        for (Player tempPlayer : OnlineMembers.keySet()) {
            ChatSender.MessageTo(tempPlayer, PrefixClan, Message);
        }
    }

    public void SendMessageForOnlineOfficers(String Message) {
        String FormattedPrefix = "&9" + PrefixClan;
        for (Player tempPlayer : OnlineMembers.keySet()) {
            EClanRole tempRole = OnlineMembers.get(tempPlayer).getMemberRole();
            if (tempRole == EClanRole.OFFICER || tempRole == EClanRole.LEADER) {
                ChatSender.MessageTo(tempPlayer, FormattedPrefix, Message);
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
    public @NotNull EClanRole getMemberRole(String PlayerName) {
        if (!IsClanMember(PlayerName)) return EClanRole.NONE;

        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(PlayerName)) {
                return tempMember.getMemberRole();
            }
        }
        return EClanRole.NONE;
    }

    public boolean SendRequestForJoin(String playerName) {
        if (HaveRequestFromPlayer(playerName)) return false;
        Requests.add(playerName);
        NeedToSave = true;
        return true;
    }

    public boolean HaveRequestFromPlayer(String playerName) {
        for (String tempRequest : Requests) {
            if (tempRequest.equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    public void ChangeLeader(String NewLeaderName) {
        LeaderName = NewLeaderName;
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(NewLeaderName)) {
                tempMember.setMemberRole(EClanRole.LEADER);
                continue;
            }
            if (tempMember.getMemberRole() == EClanRole.LEADER) {
                tempMember.setMemberRole(EClanRole.OFFICER);
            }
        }
        NeedToSave = true;
        sortMembers();
    }

    public boolean ChangeMemberRole(String MemberName, EClanRole newRole) {
        for (ClanMember tempMember : Members) {
            if (tempMember.getPlayerName().equals(MemberName)) {
                if (tempMember.getMemberRole() == newRole) return false;
                tempMember.setMemberRole(newRole);
                break;
            }
        }
        NeedToSave = true;
        sortMembers();
        return true;
    }

    public void PlayerJoinToClan(EClanRole Role, String PlayerName) {
        if (IsClanMember(PlayerName)) {
            return;
        }
        ClanMember tempClanMember = new ClanMember(Role, PlayerName);
        Player tempPlayer = getServer().getPlayer(PlayerName);
        if (tempPlayer != null) {
            OnlineMembers.put(tempPlayer, tempClanMember);
        }
        Members.add(tempClanMember);
        NeedToSave = true;
        sortMembers();
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
                NeedToSave = true;
                return;
            }
        }
        sortMembers();
    }
    public void PlayerLeavedFromClan(Player player) {
        OnlineMembers.remove(player);
        Members.remove(getClanMember(player.getName()));
        NeedToSave = true;
        sortMembers();
    }

    public void RemoveFromRequest(String PlayerName) {
        Requests.remove(PlayerName);
    }

    private void sortMembers() {
        Members.sort(new Comparator<ClanMember>() {
            @Override
            public int compare(ClanMember o1, ClanMember o2) {
                if (o1.getMemberRole().ordinal() > o2.getMemberRole().ordinal()) {
                    return -1;
                }
                else if (o1.getMemberRole().ordinal() < o2.getMemberRole().ordinal()) {
                    return 1;
                }
                return 0;
            }
        });
    }



    /*
     *  Getters Functions
     */
    public String getNameClan() { return NameClan; }

    public String getDescriptionClan() { return DescriptionClan; }

    public String getLeaderName() { return LeaderName; }

    public @NotNull ClanSettings getSettingsClan() { return SettingsClan; }
    public List<ClanMember> getMembers() { return Members; }
    public Map<Player, ClanMember> getOnlineMembers() { return OnlineMembers; }
    public String getPrefixClan() { return PrefixClan; }
    public double getMoneyClan() { return MoneyClan; }
    public Location getHomeClan() { return HomeClan; }
    public List<String> getRequests() { return Requests; }
    public int getRequestCount() { return Requests.size(); }
    public String getClanRegionId() { return ClanRegionId; }

    public int getClanLevel() { return ClanLevel; }


    /*
     *  Setters Functions
     */
    public boolean setClanName(String newNameClan) {
        if (newNameClan.length() >= 5 && newNameClan.length() <=15) {
            for (char tempChar : newNameClan.toCharArray()) {
                // check for 0-9 or A-Z or a-z or space or _
                if (!((tempChar >= 48 && tempChar <= 57) ||
                        (tempChar >= 65 && tempChar <= 90) ||
                        (tempChar >= 97 && tempChar <= 122) ||
                        tempChar == ' ' || tempChar == '_')) {
                    return false;
                }
            }
            NameClan = newNameClan;
            NeedToSave = true;
            return true;
        }
        return false;
    }

    public void setHomeClan(Location newHomeLocation) {
        HomeClan = newHomeLocation;
        NeedToSave = true;
    }

    public void DepositMoneyToClan(double moneyClan) {
        MoneyClan += moneyClan;
        NeedToSave = true;
    }

    public double WithdrawMoneyFromClan(double moneyClan) {
        double tempMoney = moneyClan;
        if (MoneyClan - moneyClan >= 0 ) {
            MoneyClan -= moneyClan;
        }
        else {
            tempMoney = MoneyClan;
            MoneyClan = 0;
        }
        NeedToSave = true;
        return tempMoney;
    }

    public void setClanRegionId(String regionName) { ClanRegionId = regionName; }

    public void setClanLevel(int clanLevel) {
        ClanLevel = clanLevel;
    }
}
