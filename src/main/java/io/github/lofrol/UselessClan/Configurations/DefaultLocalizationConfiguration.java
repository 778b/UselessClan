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


        set("Main.ClanCommandWithoutArgs",          "Use command <Green>/Clan help<Aqua>, for access to clan system");
        set("Main.InvalidClanCommand",              "<Red>Invalid command. Use command <Green>/Clan help<Red>, for access to clan system");
        set("Main.InvalidPermissionToCommand",      "<Red>You dont have permission for do that!");
        set("Main.ZeroPlayerInClanChat",            "<Red>Only you are online from your clan");
        set("Main.ClanAdminCommandWithoutArgs",     "Use command ClanAdmin help, for access to clan system");
        set("Main.InvalidClanAdminCommand",         "<Red>Invalid command. Use command <Green>/ClanAdmin help<Red>, for access to clan system");
        set("Main.InvalidPermissionToAdminCommand", "<Red>You cant use that command from this!");
        setComments("Main", List.of(" ", " "));

        set("Base.WrongRank",                       "<Red>You rank is too low to do that!");
        set("Base.HavntClan",                       "<Red>You havent Clan!");
        set("Base.AlreadyInClan",                   "<Red>You already have Clan!");
        set("Base.VictimHaveAnotherClan",           "<Red>This player not in your clan!");
        set("Base.NothingChanged",                  "Nothing changed, :(");
        setComments("Base", List.of(" ", " "));

        set("Enter.ClanAcceptWithoutArgs",          "<Red>You forgot about player %name, use <Green>/Clan accept %name<Aqua>, %name = name of player, which request you want to accept");
        set("Enter.ClanDeclineWithoutArgs",         "<Red>You forgot about player %name, use <Green>/Clan decline %name<Aqua>, %name = name of player, which request you want decline");
        set("Enter.HaveRequestsOnJoin",             "Your clan have %d requests for join! <Green>/Clan requests");
        set("Enter.VictimAlreadyInClan",            "<Red>This player already in Clan");
        set("Enter.CantFindRequest",                "<Red>This player didnt send request to you Clan");
        set("Enter.PlayerJoinedToClan",             "Player <Green>%s<Aqua> joined to your clan!");
        set("Enter.PlayerAcceptedToClan",           "Player <Green>%s<Aqua> accepted to your clan!");
        set("Enter.PlayersRequestDeclined",         "<Green>You successfully decline request from %s");
        set("Enter.JoinWithoutArgs",                "<Red>You forgot about clan %name, use <Green>/Clan join %name<Aqua>, %name = name of clan");
        set("Enter.JoinAlreadySent",                "<Red>You already sent request for join to this clan!");
        set("Enter.InvalidClanName",                "<Red>Invalid clan name!");
        set("Enter.RequestSent",                    "You send request for join to this clan, wait until leader or officer accept this request");
        set("Enter.RequestOfficerNotify",           "Player %s was send request for join to you clan, type <Green>/Clan requests");
        set("Enter.KickWithoutArgs",                "<Red>You forgot about player %name, use <Green>/Clan kick %name<Aqua>, %name = name of player, which you want to kick");
        set("Enter.CantKickHigher",                 "<Red>You cant kick this member! You have too small rank.");
        set("Enter.VictimWrongClan",                "<Red>Cant find this player in your clan!");
        set("Enter.PlayerKicked",                   "<Red>Player %s was kicked from your clan!");
        set("Enter.LeaderCantLeave",                "<Red>You cant leave from clan, because you are Leader of this clan");
        set("Enter.SuccessLeave",                   "You successfully leaved from <Gold>%s");
        set("Enter.Admin.SuccessJoin",              "<Green>You successfully join to clan %s!");
        set("Enter.Admin.MissedArgToForceJoin",     "<Red>You forgot about clan %name, use /ClAd forcejoin %name, %name = name of clan");
        setComments("Enter", List.of(" ", " "));

        set("WG.ZeroLvlClanClaim",                  "<Red>0 level clan cant claim a territory!");
        set("WG.NoSelectedAreaToClaim",             "<Red>Please select an area first");
        set("WG.SelectedAreaIsTooBig",              "<Red>Your clan cant have more than <Green>%d<Green> distance between points, but you selected <Green>%s<Green>");
        set("WG.ClaimZoneOverlap",                  "<Red>Selected territory overlap another region!");
        set("WG.ClaimDeletionNotify",               "<Green>Your previous region was deleted ...");
        set("WG.ClaimSuccessfully",                 "<Green>You Successfully claim this territory :)");
        setComments("WG", List.of(" ", " "));

        set("Create.MissedArgToCreate",             "<Red>You forgot about clan %name, use <Green>/Clan create %name<Aqua>, %name = name of your clan");
        set("Create.AlreadyInClan",                 "<Red>You cant create clan while you have been in clan!");
        set("Create.PrefixIsShorterThree",          "<Red>Your name is too short, name must be >3 symbols");
        set("Create.PrefixIsLongerSeven",           "<Red>Your name is too long, name must be <7 symbols");
        set("Create.InvalidPrefixSymbols",          "<Red>Invalid clan name, use [A-Z; a-z; _; 0-9]");
        set("Create.PrefixAlreadyExist",            "<Red>Clan with this name already exist!");
        set("Create.NotEnoughMoneyToCreate",        "<Red>For create your own clan you must have more than %s$");
        set("Create.SuccessCreateClan",             "<Green>Clan %s was created successfully!");
        set("Create.ClanDeleted",                   "<Green>You successfully delete your clan!");
        set("Create.Admin.MissingArgToDelete",      "<Red>You forgot about clan %name, use /ClAd delete %name, %name = name of clan");
        set("Create.Admin.ClanDelete",              "<Green>Clan %s was delete");
        setComments("Create", List.of(" ", " "));

        set("Rank.ClanDemoteWithoutArgs",           "<Red>You forgot about player %name, use <Green>/Clan demote %name");
        set("Rank.ClanPromoteWithoutArgs",          "<Red>You forgot about player %name, use <Green>/Clan promote %name");
        set("Rank.VictimHadThisRank",               "<Red>This Player already have this rank!");
        set("Rank.LeaderChanged",                   "<Green>Leader is changed! New leader of clan is %s");
        set("Rank.PlayerDemoted",                   "<Red>Player %s was demoted to %s");
        set("Rank.PlayerPromote",                   "<Green>Player %s was promoted to %s");
        setComments("Rank", List.of(" ", " "));

        set("Economy.DepositWithoutArgs",           "<Red>You forgot about value of deposit, use <Green>/Clan deposit %money");
        set("Economy.WithdrawWithoutArgs",          "<Red>You forgot about value of withdraw, use <Green>/Clan withdraw %money");
        set("Economy.WrongDepositMoney",            "<Red>Wrong money count!");
        set("Economy.WrongWithdrawMoney",           "<Red>Wrong money count! Use [0;+inf)");
        set("Economy.NotEnoughMoney",               "<Red>You cant withdraw <Green>%s<Red> from you clan");
        set("Economy.DepositPlayer",                "<Green>Player <Green>%s<Aqua> deposit <Green>%s<Aqua> to your clan!");
        set("Economy.WithdrawPlayer",               "<Green>Player <Green>%s<Aqua> withdraw <Green>%s<Aqua> from clan balance");
        // @todo 1000$ must be in plugin config
        set("Economy.NotEnoughMoneyForCalculate",   "<Red>You need 1000$ for force calculate clan level!");
        set("Economy.Admin.DepositWithoutArgs",     "<Red>Not enough args, use /ClAd deposit %name %money!");
        set("Economy.Admin.MissingArgToLevel",      "<Red>You forgot about clan %level, use /ClAd level %name %level, %level = level to give");
        set("Economy.Admin.WrongLvl",               "<Red>Wrong level number!");
        set("Economy.Admin.SuccessLvlChange",       "<Green>Level of clan %s was changed to %d");
        setComments("Economy", List.of(" ", " "));

        set("Help.Label",                           "--------- CLAN HELP %d ---------");
        set("Help.NoCommands",                      "<Red>No commands found");
        set("Help.ClanPageCommand",                 "<Aqua>Type <Green>/Clan help %d<Aqua> - to show commands in page %d");
        set("Help.WrongPage",                       "<Red>This page of help isnt found");
        setComments("Help", List.of(" ", " "));

        set("Home.NoClanHome",                      "<Red>Your clan doesnt have home!");
        set("Home.WrongWorldToSet",                 "<Red>You cant set clan home in this world!");
        set("Home.WrongWorldToTeleport",            "<Red>You cant teleport clan home from this world!");
        set("Home.HomeSuccessSet",                  "<Green>Clan home set successfully!");
        set("Home.HomeTeleportation",               "<Green>You teleported to clan home!");
        set("Home.WrongRegionToSet",                "<Red>You cant set clan home out of clan region!");
        set("Home.ClanHomeDelete",                  "<Red>Your clan home removed, because it is not in region!");
        set("Home.Admin.MissedArgsToHome",          "<Red>You forgot about clan %name, use /ClAd home %name, %name = name of clan");
        setComments("Home", List.of(" ", " "));

        set("Treasure.TreasureSuccessSet",          "<Green>Clan treasure set successfully!");
        set("Treasure.WrongWorldToSet",             "<Red>You cant set clan treasure in this world!");
        set("Treasure.SuccessfullyCalculation",     "<Green>Clan level successfully calculated!");
        set("Treasure.WrongRegionToSet",            "<Red>You cant set clan treasure out of clan region!");
        set("Treasure.ClanTreasureDelete",          "<Red>Your clan treasure removed, because it is not in region!");
        set("Treasure.Admin.NoClanTreasure",        "<Red>This clan doesnt have treasure!");
        set("Treasure.Admin.MissedArgsToHome",      "<Red>You forgot about clan %name, use /ClAd treasure %name, %name = name of clan");
        set("Treasure.Admin.LevelCalculation",      "<Green>Calculated level of clan %s");
        set("Treasure.Admin.MissedArgToCalculate",  "<Red>You forgot about clan %name, use /ClAd calclvl %name, %name = name of clan");
        setComments("Treasure", List.of(" ", " "));

        set("Info.Label",                           "--------- CLAN INFO ---------");
        set("Info.ClanName",                        "- Name: %s");
        set("Info.ClanDescription",                 "- Description: %s");
        set("Info.ClanPrefix",                      "- Prefix: %s");
        set("Info.ClanLevel",                       "- Level: %s");
        set("Info.LeaderName",                      "- LeaderName: %s");
        set("Info.MemberCount",                     "- Count of Members: %s");
        set("Info.Money",                           "- Money: %d");
        set("Info.Rank",                            "- Your rank: %s");
        set("Info.Home",                            "- Home: %s");
        set("Info.Treasure",                        "- Treasure: %s");
        set("Info.RequestCount",                    "- Count of Requests: %s");
        set("Info.Admin.ListLabel",                 "--------- CLAN LIST ---------");
        set("Info.Admin.ListUnit",                  "- Name: <Green>%s<Aqua>, level: <Green>%d<Aqua>");
        set("Info.Admin.Label",                     "--------- CLAN %s INFO ---------");
        set("Info.Admin.LabelDebug",                "------ DEBUG CLAN %s INFO ------");
        set("Info.Admin.MissedArg",                 "<Red>You forgot about clan %name, use /ClAd info %name, %name = name of clan");
        set("Info.Admin.MissedArgDebug",            "<Red>You forgot about clan %name, use /ClAd debuginfo %name, %name = name of clan");
        setComments("Info", List.of(" ", " "));

        set("Rename.ClanRenameWithoutArgs",             "<Red>You forgot about new clan %name, use <Green>/Clan rename %name<Aqua>, %name = new clan name");
        set("Rename.ClanRenameIncorrectLength",         "<Red>Your clan name had incorrect length!");
        set("Rename.ClanRenameIncorrectSymbols",        "<Red>Your clan name had incorrect symbols!");
        set("Rename.ClanRenameSuccessful",              "<Green>Clan name successfully changed!");
        set("Rename.ClanRedescWithoutArgs",             "<Red>You forgot about new clan %description, use <Green>/Clan redesc %description<Aqua>, %%description = new clan description");
        set("Rename.ClanRedescIncorrectLength",         "<Red>Your clan description had incorrect length!");
        set("Rename.ClanRedescIncorrectSymbols",        "<Red>Your clan description had incorrect symbols!");
        set("Rename.ClanRedescSuccessful",              "<Green>Clan description successfully changed!");
        setComments("Rename", List.of(" ", " "));

        set("Mates.Label",                          "------- CLANMATES -------");
        set("Mates.Unit",                           "- %s <Green>%s");
        set("Mates.Admin.MissingArgToMates",        "<Red>You forgot about clan %name, use /ClAd mates %name, %name = name of clan");
        setComments("Mates", List.of(" ", " "));

        set("Requests.Label",                          "-------- CLAN REQUESTS -------");
        set("Requests.Unit",                           "- %s <Green>%s");
        set("Requests.ZeroRequests",                   "No requests for join to your clan");
        set("Requests.Admin.Label",                    "----- CLAN %s REQUESTS ----");
        set("Requests.Admin.MissingArgToMates",        "<Red>You forgot about clan %name, use /ClAd requests %name, %name = name of clan");
        setComments("Requests", List.of(" ", " "));

        set("Setting.WithoutArgs",                  "<Red>You forgot about args, use <Green>/Clan setting help");
        set("Setting.WrongArgs",                    "<Red>You have wrong args! Use <Green>/Clan setting help");
        set("Setting.LeaderError",                  "<Red>DefaultJoinRole cant be LEADER!");
        set("Setting.Label",                        "--- CLAN SETTINGS ---");
        set("Setting.SelectedWithoutArgs",          "<Red>Not enough arguments, try <Green>/Clan setting %s [1-4] 1 = Rookie, 4 = Leader");
        set("Setting.Info",                         "<Green>/Clan setting info - show info about setting clan");
        set("Setting.UseExample",                   "<Red>Wrong arguments, example <Green>/Clan setting %s");
        set("Setting.Changed",                      "<Green>%s successfully changed to %s!");

        set("Setting.Description.Info",             "<Green>/Clan setting info - To see clan settings");
        set("Setting.Description.RoleCanSetHome",   "<Green>/Clan setting RoleCanSetHome [Role] - set min role to change home location [1-4]");
        set("Setting.Description.RoleCanKick",      "<Green>/Clan setting RoleCanKick [Role] - set min role, which can kick members [1-4]");
        set("Setting.Description.DefaultJoinRole",  "<Green>/Clan setting DefaultJoinRole [Role] - set min default join role [1-3]");
        set("Setting.Description.RoleCanWithdraw",  "<Green>/Clan setting RoleCanWithdraw [Role] - set min role, which can kick withdraw money [1-4]");
        set("Setting.Description.RoleCanAccept",    "<Green>/Clan setting RoleCanAccept [Role] - set min role, which can accept/decline players to clan [1-4]");
        setComments("Setting", List.of(" ", " "));

        set("Top.Label",                            "<Aqua>--------- CLAN TOP %d ---------");
        set("Top.PageCommand",                      "Type <Green>/Clan top %d<Aqua> - to show commands in page %d");
        set("Top.WrongPage",                        "<Red>This page of clans top isnt found");
        setComments("Top", List.of(" ", " "));

        set("Description.Accept",                   "<Green>/Clan accept %name<Aqua> - to accept %name for join to your clan");
        set("Description.Claim",                    "<Green>/Clan claim<Aqua> - to claim territory what you selected to clan territory");
        set("Description.Create",                   "<Green>/Clan create %name<Aqua> - to create your own clan with name %name");
        set("Description.Decline",                  "<Green>/Clan decline %name<Aqua> - to decline request for join from player %name");
        set("Description.Delete",                   "<Green>/Clan delete<Aqua> - to delete your clan");
        set("Description.Rename",                   "<Green>/Clan rename %name<Aqua> - to change name of your clan (Prefix wont change)");
        set("Description.Redescription",            "<Green>/Clan redesc %description<Aqua> - to change description of your clan");
        set("Description.Demote",                   "<Green>/Clan demote %name<Aqua> - to demote player %name of your clan");
        set("Description.Deposit",                  "<Green>/Clan deposit %value<Aqua> - to deposit money to your clan");
        set("Description.Help",                     "<Green>/Clan help<Aqua> - to call this menu");
        set("Description.Home",                     "<Green>/Clan home<Aqua> - to teleport to home of your clan");
        set("Description.Info",                     "<Green>/Clan info<Aqua> - to info about your clan");
        set("Description.Join",                     "<Green>/Clan join %name<Aqua> - to send request for join the clan %name");
        set("Description.Kick",                     "<Green>/Clan kick %name<Aqua> - to kick player %name from your clan");
        set("Description.Leave",                    "<Green>/Clan leave<Aqua> - to leave from your clan");
        set("Description.Mates",                    "<Green>/Clan mates<Aqua> - to show list of clanmates");
        set("Description.Promote",                  "<Green>/Clan promote %name<Aqua> - to promote player %name of your clan");
        set("Description.Requests",                 "<Green>/Clan requests<Aqua> - to see list of all requests to join your clan");
        set("Description.Sethome",                  "<Green>/Clan sethome<Aqua> - to set home location of your clan");
        set("Description.Settreasure",              "<Green>/Clan settreasure<Aqua> - to set treasure location of your clan");
        // @todo add 1000$ for PluginConfig
        set("Description.CalculateLevel",           "<Green>/Clan calclvl<Aqua> - to force recalculate clan level for 1000$");
        set("Description.Settings",                 "<Green>/Clan setting help<Aqua> - to show help about settings of your clan");
        set("Description.Top",                      "<Green>/Clan top<Aqua> - top of all clans");
        set("Description.Withdraw",                 "<Green>/Clan withdraw %value<Aqua> - to withdraw money from your clan");

        set("Description.Admin.Calclvl",            "<Green>/ClAd calclvl %clan<Aqua> - force recalculate %name clan level");
        set("Description.Admin.DebugInfo",          "<Green>/ClAd debuginfo %clan<Aqua> - show debug info about clan %name");
        set("Description.Admin.Delete",             "<Green>/ClAd calclvl %clan<Aqua> - force delete clan %name");
        set("Description.Admin.ForceJoin",          "<Green>/ClAd forcejoin %clan<Aqua> - force join to clan %name");
        set("Description.Admin.Help",               "<Green>/ClAd help - to show this menu");
        set("Description.Admin.Treasure",           "<Green>/ClAd treasure %clan<Aqua> - teleport to treasure of clan %name");
        set("Description.Admin.Home",               "<Green>/ClAd home %clan<Aqua> - teleport to home of clan %name");
        set("Description.Admin.Info",               "<Green>/ClAd info %clan<Aqua> - show info about clan %name");
        set("Description.Admin.Level",              "<Green>/ClAd level %clan<Aqua> %level - to set %level for clan %name");
        set("Description.Admin.List",               "<Green>/ClAd list - to show all server clans");
        set("Description.Admin.Mates",              "<Green>/ClAd mates %clan<Aqua> - to show all mates in clan %name");
        set("Description.Admin.Requests",           "<Green>/ClAd requests %clan<Aqua> - to show all requests in clan %name");
        set("Description.Admin.Withdraw",           "<Green>/ClAd withdraw %clan %value<Aqua> - to withdraw money from your clan");
        set("Description.Admin.Deposit",            "<Green>/ClAd deposit %clan %value<Aqua> - to deposit money to your clan");

        set("Description.General.Version",          "<Green>/UselessClan version - to show plugin version");
        set("Description.General.Reload",           "<Green>/UselessClan reload - to reload plugin config");
        setComments("Description", List.of(" ", " "));
    }
}
