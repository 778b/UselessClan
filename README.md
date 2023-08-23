# UselessClan
#### Simple plugin for clans to your server. 
#### Support clan-player economy, clan roles, requests, joins, clan home.
#### Also have clans level, which calculated by clan treasure
#### Clan regions, which increase with clan level.
#
## Currently support API:
### **Requred!**
#### PlaceholderAPI:   %rel_UselessClan_Prefix% && %rel_UselessClan_Role%
#### WorldGuard:       For Clan region system
### **Not requred**
#### VaultAPI:         Economy system of clans
#
## Permissions
> #### Any players had access for all default plugin commands
> #### For access to officers and leaders commands they must get role by creating a clan or promoted
> #### UselessClan.Admin            - to access all admin commands
> #### UselessClan.Admin.Reload     - to access to reload plugin config
#
## Plugin Commands
> ## Default Player commands:
> #### /Clan version               - to show version of the plugin
> #### /clan create [name]         - to create clan with prefix [name]
> #### /clan join [clanname]       - to send request for join to [clanname] clan
> #### /Clan top                   - to show top list of clans
> #### /Clan mates                 - to show list of clanmates
> #### /Clan leave                 - to leave from your clan
> #### /Clan deposit [value]       - to deposite money to your clan
> #### /Clan withdraw [value]      - to withdraw money from your clan
> #### /Clan info                  - to show info about your clan
> #### /Clan home                  - to teleport to clans home
#
> ## Officers and Leader commands
> #### /Clan claim                 - to claim clan region, increase clan lvl for claim more territory
> #### /Clan sethome               - to set clan home
> #### /Clan settreasure           - to set clan treasure, which used for lvl calculation
> #### /Clan calclvl               - to force calculate clan level for 1000$ (editable in config)
> #### /Clan requests              - to see list of all requests to join your clan
> #### /Clan rename [name]         - to change NAME of your clan, prefix wasnt change.
> #### /Clan redesc [description]  - to change description of your clan
> #### /Clan settings              - to access to clan settings
> > ### *WARNING! All Roles settings up as minimal role*
> > ### *If u setup any permission for ROOKIE - all clan members will can use it*
> > #### /Clan settings help                        - to show all commands for clan settings.
> > #### /Clan settings info                        - to show all settings of your clan.
> > #### /Clan settings DefaultJoinRole [new role]  - to change role, which will give new players in your clan.
> > #### /Clan settings RoleCanSetHome [new role]   - to change minimal role, which can set clan home. 
> > #### /Clan settings RoleCanKick [new role]      - to change minimal role, which can kick player in your clan (Player must have upper role than player to kick)
> > #### /Clan settings RoleCanWithdraw [new role]  - to change minimal role, which can withdraw money from clan bank
> > #### /Clan settings RoleCanAccept [new role]    - to change minimal role, which can accept requests to your clan by command /clan accept [playername]
> #### /Clan accept [playername]   - to accept [playername] for join to your clan
> #### /Clan decline [playername]  - to decline [playername] for join to your clan
> #### /Clan kick [playername]     - to kick player [playername] from your clan
> #### /Clan promote [playername]  - to promote player [playername] of your clan
> #### /Clan demote [playername]   - to demote player [playername] of your clan
> #### /Clan delete                - to delete clan(remove clan and save last config to DeletedCLan folder)
#
> ## Admin's commands
> #### /ClAd calclvl %name         - to force calculate clan level
> #### /ClAd list                  - list of all clans
> #### /ClAd info %name            - to show info of any clan
> #### /ClAd debuginfo %name       - to show debuginfo of any clan
> #### /ClAd home %name            - to teleport to any clan home, if it exist of course
> #### /ClAd treasure %name        - to teleport to any clan treasure, if it exist of course
> #### /ClAd forcejoin %name       - to join of any clan
> #### /ClAd requests %name        - to show requests of any clan
> #### /ClAd mates %name           - to show mates of any clan
> #### /ClAd delete %name          - to delete 0f any clan
> #### /ClAd deposit %name %money  - to add money to any clan
> #### /ClAd withdraw %name %money - to remove money from any clan
#
## Plugin Config
#### *You can create your own localization file and change this field for filename*
#### *Just copy ExampleEnglish and read instructions in it*
> #### Localization: ExampleEnglish
#### *Server calculating clan levels asynchronous, but u can disable it*
#### *P.S. You can change how often it will do it in setting under*
> #### NeedCalculateClanLevels: true
#### *You can create extension plugin for custom logic of clan calculation*
#### *Just use ClanManagerExtension class and UselessClan.getMainManager().Extension to set it*
> #### UseExtendCalculateClanLevels: false
> #### Delay:
#### *How often plugin will do backups of all clans*
> > ####  ClanBackup: 864000
#### *How often plugin will do saves of all clans*
> > ####  AutoSave: 24000
#### *How often plugin will calculate level of all clans*
> > ####  CalculateClanLevel: 12333
#### *How often plugin will sort top of all clans*
> > ####  CalculateClansTop: 12000
#### *Default settings for new clan*
#### *1 - Rookie, 2 - Member, 3 - Officer, 4 - Leader*
> #### ClanSettings:
> > ####  MinRoleCanSethome: 4
> > ####  MinRoleCanWithdraw: 2
> > ####  MinRoleCanKick: 3
> > ####  MinRoleCanAccept: 3
> > ####  DefaultJoinRole: 1
> #### DefaultClanSettings:
#### ***WARNING**Money settings work only with Vault plugin*
#### *Money of clan, which will be on clan balance after creation*
> > ####  FirstClanMoney: 0.0
#### *Money of clan, which players need to create clan*
> > ####  MoneyToCreateClan: 10000.0
#### *Default level of new clan after creation*
> > ####  StartClanLevel: 0
#### *Color settings for all clans levels*
#### *Your can add new levels if you need, just add symbols under*
> > ####  ClanLevelsColors:
> > ####  - '&7'    - Gray
> > ####  - '&a'    - Green
> > ####  - '&2'    - Dark Green
> > ####  - '&3'    - Dark Aqua
> > ####  - '&9'    - Blue
> > ####  - '&1'    - Dark Blue
> > ####  - '&e'    - Yellow
> > ####  - '&6'    - Gold
> > ####  - '&d'    - Light Purple
> > ####  - '&5'    - Dark Purple
> > ####  - '&0'    - Black

## IN ACTIVE DEVELOPMENT

