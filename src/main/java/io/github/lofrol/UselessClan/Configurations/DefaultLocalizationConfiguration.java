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


        set("Main.ClanCommandWithoutArgs",          "Use command <green>/Clan help<Aqua>, for access to clan system");
        set("Main.InvalidClanCommand",              "<red>Invalid command. Use command <green>/Clan help<red>, for access to clan system");
        set("Main.InvalidPermissionToCommand",      "<red>You dont have permission for do that!");
        set("Main.ZeroPlayerInClanChat",            "<red>Only you are online from your clan");
        set("Main.ClanAdminCommandWithoutArgs",     "Use command ClanAdmin help, for access to clan system");
        set("Main.InvalidClanAdminCommand",         "<red>Invalid command. Use command <green>/ClanAdmin help<red>, for access to clan system");
        set("Main.InvalidPermissionToAdminCommand", "<red>You cant use that command from this!");
        setComments("Main", List.of(" ", " "));

        set("Base.WrongRank",                       "<red>You rank is too low to do that!");
        set("Base.HavntClan",                       "<red>You havent Clan!");
        set("Base.AlreadyInClan",                   "<red>You already have Clan!");
        set("Base.VictimHaveAnotherClan",           "<red>This player not in your clan!");
        set("Base.NothingChanged",                  "Nothing changed, :(");
        setComments("Base", List.of(" ", " "));

        set("Enter.ClanAcceptWithoutArgs",          "<red>You forgot about player %name, use <green>/Clan accept %name<Aqua>, %name = name of player, which request you want to accept");
        set("Enter.ClanDeclineWithoutArgs",         "<red>You forgot about player %name, use <green>/Clan decline %name<Aqua>, %name = name of player, which request you want decline");
        set("Enter.HaveRequestsOnJoin",             "Your clan have %d requests for join! <green>/Clan requests");
        set("Enter.VictimAlreadyInClan",            "<red>This player already in Clan");
        set("Enter.CantFindRequest",                "<red>This player didnt send request to you Clan");
        set("Enter.PlayerJoinedToClan",             "Player <green>%s<Aqua> joined to your clan!");
        set("Enter.PlayerAcceptedToClan",           "Player <green>%s<Aqua> accepted to your clan!");
        set("Enter.PlayersRequestDeclined",         "<green>You successfully decline request from %s");
        set("Enter.JoinWithoutArgs",                "<red>You forgot about clan %name, use <green>/Clan join %name<Aqua>, %name = name of clan");
        set("Enter.JoinAlreadySent",                "<red>You already sent request for join to this clan!");
        set("Enter.InvalidClanName",                "<red>Invalid clan name!");
        set("Enter.RequestSent",                    "You send request for join to this clan, wait until leader or officer accept this request");
        set("Enter.RequestOfficerNotify",           "Player %s was send request for join to you clan, type <green>/Clan requests");
        set("Enter.KickWithoutArgs",                "<red>You forgot about player %name, use <green>/Clan kick %name<Aqua>, %name = name of player, which you want to kick");
        set("Enter.CantKickHigher",                 "<red>You cant kick this member! You have too small rank.");
        set("Enter.VictimWrongClan",                "<red>Cant find this player in your clan!");
        set("Enter.PlayerKicked",                   "<red>Player %s was kicked from your clan!");
        set("Enter.LeaderCantLeave",                "<red>You cant leave from clan, because you are Leader of this clan");
        set("Enter.SuccessLeave",                   "You successfully leaved from <gold>%s");
        set("Enter.Admin.SuccessJoin",              "<green>You successfully join to clan %s!");
        set("Enter.Admin.MissedArgToForceJoin",     "<red>You forgot about clan %name, use /ClAd forcejoin %name, %name = name of clan");
        setComments("Enter", List.of(" ", " "));

        set("WG.ZeroLvlClanClaim",                  "<red>0 level clan cant claim a territory!");
        set("WG.NoSelectedAreaToClaim",             "<red>Please select an area first");
        set("WG.SelectedAreaIsTooBig",              "<red>Your clan cant have more than <green>%d<green> distance between points, but you selected <green>%s<green>");
        set("WG.ClaimZoneOverlap",                  "<red>Selected territory overlap another region!");
        set("WG.ClaimDeletionNotify",               "<green>Your previous region was deleted ...");
        set("WG.ClaimSuccessfully",                 "<green>You Successfully claim this territory :)");
        setComments("WG", List.of(" ", " "));

        set("Create.MissedArgToCreate",             "<red>You forgot about clan %name, use <green>/Clan create %name<Aqua>, %name = name of your clan");
        set("Create.AlreadyInClan",                 "<red>You cant create clan while you have been in clan!");
        set("Create.PrefixIsShorterThree",          "<red>Your name is too short, name must be >3 symbols");
        set("Create.PrefixIsLongerSeven",           "<red>Your name is too long, name must be <7 symbols");
        set("Create.InvalidPrefixSymbols",          "<red>Invalid clan name, use [A-Z; a-z; _; 0-9]");
        set("Create.PrefixAlreadyExist",            "<red>Clan with this name already exist!");
        set("Create.NotEnoughMoneyToCreate",        "<red>For create your own clan you must have more than %s$");
        set("Create.SuccessCreateClan",             "<green>Clan %s was created successfully!");
        set("Create.ClanDeleted",                   "<green>You successfully delete your clan!");
        set("Create.Admin.MissingArgToDelete",      "<red>You forgot about clan %name, use /ClAd delete %name, %name = name of clan");
        set("Create.Admin.ClanDelete",              "<green>Clan %s was delete");
        setComments("Create", List.of(" ", " "));

        set("Rank.ClanDemoteWithoutArgs",           "<red>You forgot about player %name, use <green>/Clan demote %name");
        set("Rank.ClanPromoteWithoutArgs",          "<red>You forgot about player %name, use <green>/Clan promote %name");
        set("Rank.VictimHadThisRank",               "<red>This Player already have this rank!");
        set("Rank.LeaderChanged",                   "<green>Leader is changed! New leader of clan is %s");
        set("Rank.PlayerDemoted",                   "<red>Player %s was demoted to %s");
        set("Rank.PlayerPromote",                   "<green>Player %s was promoted to %s");
        setComments("Rank", List.of(" ", " "));

        set("Economy.DepositWithoutArgs",           "<red>You forgot about value of deposit, use <green>/Clan deposit %money");
        set("Economy.WithdrawWithoutArgs",          "<red>You forgot about value of withdraw, use <green>/Clan withdraw %money");
        set("Economy.WrongDepositMoney",            "<red>Wrong money count!");
        set("Economy.WrongWithdrawMoney",           "<red>Wrong money count! Use [0;+inf)");
        set("Economy.NotEnoughMoney",               "<red>You cant withdraw <green>%s<red> from you clan");
        set("Economy.DepositPlayer",                "<green>Player <green>%s<Aqua> deposit <green>%s<Aqua> to your clan!");
        set("Economy.WithdrawPlayer",               "<green>Player <green>%s<Aqua> withdraw <green>%s<Aqua> from clan balance");
        // @todo 1000$ must be in plugin config
        set("Economy.NotEnoughMoneyForCalculate",   "<red>You need 1000$ for force calculate clan level!");
        set("Economy.Admin.DepositWithoutArgs",     "<red>Not enough args, use /ClAd deposit %name %money!");
        set("Economy.Admin.MissingArgToLevel",      "<red>You forgot about clan %level, use /ClAd level %name %level, %level = level to give");
        set("Economy.Admin.WrongLvl",               "<red>Wrong level number!");
        set("Economy.Admin.SuccessLvlChange",       "<green>Level of clan %s was changed to %d");
        setComments("Economy", List.of(" ", " "));

        set("Help.Label",                           "--------- CLAN HELP %d ---------");
        set("Help.NoCommands",                      "<red>No commands found");
        set("Help.ClanPageCommand",                 "<Aqua>Type <green>/Clan help %d<Aqua> - to show commands in page %d");
        set("Help.WrongPage",                       "<red>This page of help isnt found");
        setComments("Help", List.of(" ", " "));

        set("Home.NoClanHome",                      "<red>Your clan doesnt have home!");
        set("Home.WrongWorldToSet",                 "<red>You cant set clan home in this world!");
        set("Home.WrongWorldToTeleport",            "<red>You cant teleport clan home from this world!");
        set("Home.HomeSuccessSet",                  "<green>Clan home set successfully!");
        set("Home.HomeTeleportation",               "<green>You teleported to clan home!");
        set("Home.WrongRegionToSet",                "<red>You cant set clan home out of clan region!");
        set("Home.ClanHomeDelete",                  "<red>Your clan home removed, because it is not in region!");
        set("Home.Admin.MissedArgsToHome",          "<red>You forgot about clan %name, use /ClAd home %name, %name = name of clan");
        setComments("Home", List.of(" ", " "));

        set("Treasure.TreasureSuccessSet",          "<green>Clan treasure set successfully!");
        set("Treasure.WrongWorldToSet",             "<red>You cant set clan treasure in this world!");
        set("Treasure.SuccessfullyCalculation",     "<green>Clan level successfully calculated!");
        set("Treasure.WrongRegionToSet",            "<red>You cant set clan treasure out of clan region!");
        set("Treasure.ClanTreasureDelete",          "<red>Your clan treasure removed, because it is not in region!");
        set("Treasure.Admin.NoClanTreasure",        "<red>This clan doesnt have treasure!");
        set("Treasure.Admin.MissedArgsToHome",      "<red>You forgot about clan %name, use /ClAd treasure %name, %name = name of clan");
        set("Treasure.Admin.LevelCalculation",      "<green>Calculated level of clan %s");
        set("Treasure.Admin.MissedArgToCalculate",  "<red>You forgot about clan %name, use /ClAd calclvl %name, %name = name of clan");
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
        set("Info.Admin.ListUnit",                  "- Name: <green>%s<Aqua>, level: <green>%d<Aqua>");
        set("Info.Admin.Label",                     "--------- CLAN %s INFO ---------");
        set("Info.Admin.LabelDebug",                "------ DEBUG CLAN %s INFO ------");
        set("Info.Admin.MissedArg",                 "<red>You forgot about clan %name, use /ClAd info %name, %name = name of clan");
        set("Info.Admin.MissedArgDebug",            "<red>You forgot about clan %name, use /ClAd debuginfo %name, %name = name of clan");
        setComments("Info", List.of(" ", " "));

        set("Rename.ClanRenameWithoutArgs",             "<red>You forgot about new clan %name, use <green>/Clan rename %name<Aqua>, %name = new clan name");
        set("Rename.ClanRenameIncorrectLength",         "<red>Your clan name had incorrect length!");
        set("Rename.ClanRenameIncorrectSymbols",        "<red>Your clan name had incorrect symbols!");
        set("Rename.ClanRenameSuccessful",              "<green>Clan name successfully changed!");
        set("Rename.ClanRedescWithoutArgs",             "<red>You forgot about new clan %description, use <green>/Clan redesc %description<Aqua>, %%description = new clan description");
        set("Rename.ClanRedescIncorrectLength",         "<red>Your clan description had incorrect length!");
        set("Rename.ClanRedescIncorrectSymbols",        "<red>Your clan description had incorrect symbols!");
        set("Rename.ClanRedescSuccessful",              "<green>Clan description successfully changed!");
        setComments("Rename", List.of(" ", " "));

        set("Mates.Label",                          "------- CLANMATES -------");
        set("Mates.Unit",                           "- %s <green>%s");
        set("Mates.Admin.MissingArgToMates",        "<red>You forgot about clan %name, use /ClAd mates %name, %name = name of clan");
        setComments("Mates", List.of(" ", " "));

        set("Requests.Label",                          "-------- CLAN REQUESTS -------");
        set("Requests.Unit",                           "- %s");
        set("Requests.ZeroRequests",                   "No requests for join to your clan");
        set("Requests.Admin.Label",                    "----- CLAN %s REQUESTS ----");
        set("Requests.Admin.MissingArgToMates",        "<red>You forgot about clan %name, use /ClAd requests %name, %name = name of clan");
        setComments("Requests", List.of(" ", " "));

        set("Setting.WithoutArgs",                  "<red>You forgot about args, use <green>/Clan setting help");
        set("Setting.WrongArgs",                    "<red>You have wrong args! Use <green>/Clan setting help");
        set("Setting.LeaderError",                  "<red>DefaultJoinRole cant be LEADER!");
        set("Setting.Label",                        "--- CLAN SETTINGS ---");
        set("Setting.SelectedWithoutArgs",          "<red>Not enough arguments, try <green>/Clan setting %s [1-4] 1 = Rookie, 4 = Leader");
        set("Setting.Info",                         "<green>/Clan setting info - show info about setting clan");
        set("Setting.UseExample",                   "<red>Wrong arguments, example <green>/Clan setting %s");
        set("Setting.Changed",                      "<green>%s successfully changed to %s!");

        set("Setting.Description.Info",             "<green>/Clan setting info - To see clan settings");
        set("Setting.Description.RoleCanSetHome",   "<green>/Clan setting RoleCanSetHome [Role] - set min role to change home location [1-4]");
        set("Setting.Description.RoleCanKick",      "<green>/Clan setting RoleCanKick [Role] - set min role, which can kick members [1-4]");
        set("Setting.Description.DefaultJoinRole",  "<green>/Clan setting DefaultJoinRole [Role] - set min default join role [1-3]");
        set("Setting.Description.RoleCanWithdraw",  "<green>/Clan setting RoleCanWithdraw [Role] - set min role, which can kick withdraw money [1-4]");
        set("Setting.Description.RoleCanAccept",    "<green>/Clan setting RoleCanAccept [Role] - set min role, which can accept/decline players to clan [1-4]");
        setComments("Setting", List.of(" ", " "));

        set("Top.Label",                            "<Aqua>--------- CLAN TOP %d ---------");
        set("Top.PageCommand",                      "Type <green>/Clan top %d<Aqua> - to show commands in page %d");
        set("Top.WrongPage",                        "<red>This page of clans top isnt found");
        setComments("Top", List.of(" ", " "));

        set("Description.Accept",                   "<green>/Clan accept %name<Aqua> - to accept %name for join to your clan");
        set("Description.Claim",                    "<green>/Clan claim<Aqua> - to claim territory what you selected to clan territory");
        set("Description.Create",                   "<green>/Clan create %name<Aqua> - to create your own clan with name %name");
        set("Description.Decline",                  "<green>/Clan decline %name<Aqua> - to decline request for join from player %name");
        set("Description.Delete",                   "<green>/Clan delete<Aqua> - to delete your clan");
        set("Description.Rename",                   "<green>/Clan rename %name<Aqua> - to change name of your clan (Prefix wont change)");
        set("Description.Redescription",            "<green>/Clan redesc %description<Aqua> - to change description of your clan");
        set("Description.Demote",                   "<green>/Clan demote %name<Aqua> - to demote player %name of your clan");
        set("Description.Deposit",                  "<green>/Clan deposit %value<Aqua> - to deposit money to your clan");
        set("Description.Help",                     "<green>/Clan help<Aqua> - to call this menu");
        set("Description.Home",                     "<green>/Clan home<Aqua> - to teleport to home of your clan");
        set("Description.Info",                     "<green>/Clan info<Aqua> - to info about your clan");
        set("Description.Join",                     "<green>/Clan join %name<Aqua> - to send request for join the clan %name");
        set("Description.Kick",                     "<green>/Clan kick %name<Aqua> - to kick player %name from your clan");
        set("Description.Leave",                    "<green>/Clan leave<Aqua> - to leave from your clan");
        set("Description.Mates",                    "<green>/Clan mates<Aqua> - to show list of clanmates");
        set("Description.Promote",                  "<green>/Clan promote %name<Aqua> - to promote player %name of your clan");
        set("Description.Requests",                 "<green>/Clan requests<Aqua> - to see list of all requests to join your clan");
        set("Description.Sethome",                  "<green>/Clan sethome<Aqua> - to set home location of your clan");
        set("Description.Settreasure",              "<green>/Clan settreasure<Aqua> - to set treasure location of your clan");
        // @todo add 1000$ for PluginConfig
        set("Description.CalculateLevel",           "<green>/Clan calclvl<Aqua> - to force recalculate clan level for 1000$");
        set("Description.Settings",                 "<green>/Clan setting help<Aqua> - to show help about settings of your clan");
        set("Description.Top",                      "<green>/Clan top<Aqua> - top of all clans");
        set("Description.Withdraw",                 "<green>/Clan withdraw %value<Aqua> - to withdraw money from your clan");

        set("Description.Admin.Calclvl",            "<green>/ClAd calclvl %clan<Aqua> - force recalculate %name clan level");
        set("Description.Admin.DebugInfo",          "<green>/ClAd debuginfo %clan<Aqua> - show debug info about clan %name");
        set("Description.Admin.Delete",             "<green>/ClAd calclvl %clan<Aqua> - force delete clan %name");
        set("Description.Admin.ForceJoin",          "<green>/ClAd forcejoin %clan<Aqua> - force join to clan %name");
        set("Description.Admin.Help",               "<green>/ClAd help - to show this menu");
        set("Description.Admin.Treasure",           "<green>/ClAd treasure %clan<Aqua> - teleport to treasure of clan %name");
        set("Description.Admin.Home",               "<green>/ClAd home %clan<Aqua> - teleport to home of clan %name");
        set("Description.Admin.Info",               "<green>/ClAd info %clan<Aqua> - show info about clan %name");
        set("Description.Admin.Level",              "<green>/ClAd level %clan<Aqua> %level - to set %level for clan %name");
        set("Description.Admin.List",               "<green>/ClAd list - to show all server clans");
        set("Description.Admin.Mates",              "<green>/ClAd mates %clan<Aqua> - to show all mates in clan %name");
        set("Description.Admin.Requests",           "<green>/ClAd requests %clan<Aqua> - to show all requests in clan %name");
        set("Description.Admin.Withdraw",           "<green>/ClAd withdraw %clan %value<Aqua> - to withdraw money from your clan");
        set("Description.Admin.Deposit",            "<green>/ClAd deposit %clan %value<Aqua> - to deposit money to your clan");

        set("Description.General.Version",          "<green>/UselessClan version - to show plugin version");
        set("Description.General.Reload",           "<green>/UselessClan reload - to reload plugin config");
        setComments("Description", List.of(" ", " "));
    }
}
