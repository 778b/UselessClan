package io.github.lofrol.UselessClan.Configurations;


import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class DefaultLocalizationConfiguration extends YamlConfiguration {
    public DefaultLocalizationConfiguration() {
        set("InvalidLocalizationError", "DEPRECATED");
        setComments("InvalidLocalizationError", List.of(
                "THIS IS EXAMPLE FILE, PLUGIN WILL IGNORE IT",
                "Delete config and after server restart it will be appear",
                " ", " ",
                "               INSTRUCTION" ,
                "FOR CREATE YOUR OWN TRANSLATION OF PLUGIN",
                "0) Use UTF-8 files, or copy example",
                "1) Create new file in Localization folder",
                "2) Translation working by keywords, dont change them!",
                "If you change default keywords plugin will miss your translation",
                "Dont miss any %s or %d or etc, ITS VARIABLES!",
                "DONT USE TABS! Should use only spaces!",
                "3) Copy all keywords from this file to your own",
                "4) Write your translation after symbol-:",
                "5) Check translation in game and enjoy it",
                " "," ",
                "Just translated error message"));


        set("Main.ClanCommandWithoutArgs",          "Use command &a/Clan help&b, for access to clan system");
        set("Main.InvalidClanCommand",              "&cInvalid command. Use command &a/Clan help&c, for access to clan system");
        set("Main.InvalidPermissionToCommand",      "&cYou dont have permission for do that!");
        set("Main.ZeroPlayerInClanChat",            "&cOnly you are online from your clan");
        set("Main.ClanAdminCommandWithoutArgs",     "Use command ClanAdmin help, for access to clan system");
        set("Main.InvalidClanAdminCommand",         "&cInvalid command. Use command &a/ClanAdmin help&c, for access to clan system");
        set("Main.InvalidPermissionToAdminCommand", "&cYou cant use that command from this!");
        setComments("Main", List.of(" ", " "));

        set("Base.WrongRank",                       "&cYou rank is too low to do that!");
        set("Base.HavntClan",                       "&cYou havent Clan!");
        set("Base.AlreadyInClan",                   "&cYou already have Clan!");
        set("Base.VictimHaveAnotherClan",           "&cThis player not in your clan!");
        set("Base.NothingChanged",                  "Nothing changed, :(");
        setComments("Base", List.of(" ", " "));

        set("Enter.ClanAcceptWithoutArgs",          "&cYou forgot about player %name, use &a/Clan accept %name&b, %name = name of player, which request you want to accept");
        set("Enter.ClanDeclineWithoutArgs",         "&cYou forgot about player %name, use &a/Clan decline %name&b, %name = name of player, which request you want decline");
        set("Enter.HaveRequestsOnJoin",             "Your clan have %d requests for join! &a/Clan requests");
        set("Enter.VictimAlreadyInClan",            "&cThis player already in Clan");
        set("Enter.CantFindRequest",                "&cThis player didnt send request to you Clan");
        set("Enter.PlayerJoinedToClan",             "Player &a%s&b joined to your clan!");
        set("Enter.PlayerAcceptedToClan",           "Player &a%s&b accepted to your clan!");
        set("Enter.PlayersRequestDeclined",         "&aYou successfully decline request from %s");
        set("Enter.JoinWithoutArgs",                "&cYou forgot about clan %name, use &a/Clan join %name&b, %name = name of clan");
        set("Enter.JoinAlreadySent",                "&cYou already sent request for join to this clan!");
        set("Enter.InvalidClanName",                "&cInvalid clan name!");
        set("Enter.RequestSent",                    "You send request for join to this clan, wait until leader or officer accept this request");
        set("Enter.RequestOfficerNotify",           "Player %s was send request for join to you clan, type &a/Clan requests");
        set("Enter.KickWithoutArgs",                "&cYou forgot about player %name, use &a/Clan kick %name&b, %name = name of player, which you want to kick");
        set("Enter.CantKickHigher",                 "&cYou cant kick this member! You have too small rank.");
        set("Enter.VictimWrongClan",                "&cCant find this player in your clan!");
        set("Enter.PlayerKicked",                   "&cPlayer %s was kicked from your clan!");
        set("Enter.LeaderCantLeave",                "&cYou cant leave from clan, because you are Leader of this clan");
        set("Enter.SuccessLeave",                   "You successfully leaved from &6%s");
        set("Enter.ClanRequestsLabel",              "-------- CLAN REQUESTS --------");
        set("Enter.ZeroRequests",                   "0 requests for join to your clan");
        setComments("Enter", List.of(" ", " "));

        set("WG.ZeroLvlClanClaim",                  "&c0 level clan cant claim a territory!");
        set("WG.NoSelectedAreaToClaim",             "&cPlease select an area first.");
        set("WG.SelectedAreaIsTooBig",              "&cYour clan cant have more than &a%d&a distance between points, but you selected &a%s&a");
        set("WG.ClaimZoneOverlap",                  "&cSelected territory overlap another region!");
        set("WG.ClaimDeletionNotify",               "&aYour previous region was deleted ...");
        set("WG.ClaimSuccessfully",                 "&aYou Successfully claim this territory :)");
        setComments("WG", List.of(" ", " "));

        set("Create.MissedArgToCreate",             "&cYou forgot about clan %name, use &a/Clan create %name&b, %name = name of your clan");
        set("Create.AlreadyInClan",                 "&cYou cant create clan while you have been in clan!");
        set("Create.PrefixIsShorterThree",          "&cYour name is too short, name must be >3 symbols");
        set("Create.PrefixIsLongerSeven",           "&cYour name is too long, name must be <7 symbols");
        set("Create.InvalidPrefixSymbols",          "&cInvalid clan name, use [A-Z; a-z; _; 0-9]");
        set("Create.PrefixAlreadyExist",            "&cClan with this name already exist!");
        set("Create.NotEnoughMoneyToCreate",        "&cFor create your own clan you must have more than %s$");
        set("Create.SuccessCreateClan",             "Clan %s was created successfully!");
        set("Create.ClanDeleted",                   "&aYou successfully delete your clan!");
        setComments("Create", List.of(" ", " "));

        set("Rank.ClanDemoteWithoutArgs",           "&cYou forgot about player %name, use &a/Clan demote %name");
        set("Rank.ClanPromoteWithoutArgs",          "&cYou forgot about player %name, use &a/Clan promote %name");
        set("Rank.VictimHadThisRank",               "&cThis Player already have this rank!");
        set("Rank.LeaderChanged",                   "&aLeader is changed! New leader of clan is %s");
        set("Rank.PlayerDemoted",                   "&cPlayer %s was demoted to %s");
        set("Rank.PlayerPromote",                   "&aPlayer %s was promoted to %s");
        setComments("Rank", List.of(" ", " "));

        set("Economy.DepositWithoutArgs",           "&cYou forgot about value of deposit, use &a/Clan deposit %money");
        set("Economy.WithdrawWithoutArgs",          "&cYou forgot about value of withdraw, use &a/Clan withdraw %money");
        set("Economy.WrongDepositMoney",            "&cWrong money count!");
        set("Economy.WrongWithdrawMoney",           "&cWrong money count! Use [0;+inf)");
        set("Economy.NotEnoughMoney",               "&cYou cant withdraw &a%s&c from you clan");
        set("Economy.DepositPlayer",                "Player &a%s&b deposit &a%s&b to your clan!");
        set("Economy.WithdrawPlayer",               "Player &a%s&b withdraw &a%s&b from clan balance");
        setComments("Economy", List.of(" ", " "));

        set("Help.Label",                           "------------- CLAN HELP %d -------------");
        set("Help.ClanPageCommand",                 "Type &a/Clan help %d&b - to show commands in page %d");
        set("Help.WrongPage",                       "&cThis page of help isnt found");
        setComments("Help", List.of(" ", " "));

        set("Home.NoClanHome",                      "&cYour clan doesnt have home!");
        set("Home.WrongWorldToSet",                 "&cYou cant set clan home in this world!");
        set("Home.WrongWorldToTeleport",            "&cYou cant teleport clan home from this world!");
        set("Home.HomeSuccessSet",                  "&aClan home set successfully!");
        set("Home.HomeTeleportation",               "&aYou teleported to clan home!");
        setComments("Home", List.of(" ", " "));

        set("Info.Label",                           "---------- CLAN INFO ----------");
        set("Info.ClanName",                        "- Name: %s");
        set("Info.ClanPrefix",                      "- Prefix: %s");
        set("Info.ClanLevel",                       "- Level: %s");
        set("Info.LeaderName",                      "- LeaderName: %s");
        set("Info.MemberCount",                     "- Count of Members: %s");
        set("Info.Money",                           "- Money: %d");
        set("Info.Rank",                            "- Your rank: %s");
        setComments("Info", List.of(" ", " "));

        set("Mates.Label",                          "------- CLANMATES -------");
        setComments("Mates", List.of(" ", " "));

        set("Setting.WithoutArgs",                  "&cYou forgot about args, use &a/Clan setting help");
        set("Setting.WrongArgs",                    "&cYou have wrong args! Use &a/Clan setting help");
        set("Setting.LeaderError",                  "&cDefaultJoinRole cant be LEADER!");
        set("Setting.Label",                        "--- CLAN SETTINGS ---");
        set("Setting.SelectedWithoutArgs",          "&cNot enough arguments, try &a/Clan setting %s [1-4] 1 = Rookie, 4 = Leader");
        set("Setting.Info",                         "&a/Clan setting info - show info about setting clan");
        set("Setting.UseExample",                   "&cWrong arguments, example &a/Clan setting %s");
        set("Setting.Changed",                      "&a%s successfully changed to %s!");
        set("Setting.Description.RoleCanSetHome",   "&a/Clan setting RoleCanSetHome [Role] - set min role to change home location [1-4]");
        set("Setting.Description.RoleCanKick",      "&a/Clan setting RoleCanKick [Role] - set min role, which can kick members [1-4]");
        set("Settings.Description.DefaultJoinRole", "&a/Clan setting DefaultJoinRole [Role] - set min default join role [1-3]");
        set("Settings.Description.RoleCanWithdraw", "&a/Clan setting RoleCanWithdraw [Role] - set min role, which can kick withdraw money [1-4]");
        set("Settings.Description.RoleCanAccept",   "&a/Clan setting RoleCanAccept [Role] - set min role, which can accept/decline players to clan [1-4]");
        setComments("Setting", List.of(" ", " "));

        set("Top.Label",                            "&b---------- CLAN TOP %d ----------");
        set("Top.PageCommand",                      "Type &a/Clan top %d&b - to show commands in page %d");
        set("Top.WrongPage",                        "&cThis page of clans top isnt found");
        setComments("Top", List.of(" ", " "));

        set("Description.Accept",                   "&a/Clan accept %name&b - to accept %name for join to your clan");
        set("Description.Claim",                    "&a/Clan claim&b - to claim territory what you selected to clan territory");
        set("Description.Create",                   "&a/Clan create %name&b - to create your own clan with name %name");
        set("Description.Decline",                  "&a/Clan decline %name&b - to decline request for join from player %name");
        set("Description.Delete",                   "&a/Clan delete&b - to delete your clan");
        set("Description.Demote",                   "&a/Clan demote %name&b - to demote player %name of your clan");
        set("Description.Deposit",                  "&a/Clan deposit %value&b - to deposit money to your clan");
        set("Description.Help",                     "&a/Clan help&b - to call this menu");
        set("Description.Home",                     "&a/Clan home&b - to teleport to home of your clan");
        set("Description.Info",                     "&a/Clan info&b - to info about your clan");
        set("Description.Join",                     "&a/Clan join %name&b - to send request for join the clan %name");
        set("Description.Kick",                     "&a/Clan kick %name&b - to kick player %name from your clan");
        set("Description.Leave",                    "&a/Clan leave&b - to leave from your clan");
        set("Description.Mates",                    "&a/Clan mates&b - to show list of clanmates");
        set("Description.Promote",                  "&a/Clan promote %name&b - to promote player %name of your clan");
        set("Description.Requests",                 "&a/Clan requests&b - to see list of all requests to join your clan");
        set("Description.Sethome",                  "&a/Clan sethome&b - to set home location of your clan");
        set("Description.Settings",                 "&a/Clan settings help&b - to show help about settings of your clan");
        set("Description.Top",                      "&a/Clan top&b - top of all clans");
        set("Description.Withdraw",                 "&a/Clan withdraw %value&b - to withdraw money from your clan");
        setComments("Description", List.of(" ", " "));
    }
}
