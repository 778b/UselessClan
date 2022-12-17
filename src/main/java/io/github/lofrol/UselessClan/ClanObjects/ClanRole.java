package io.github.lofrol.UselessClan.ClanObjects;

public enum ClanRole {
    NONE,
    ROOKIE,
    MEMBER,
    OFFICER,
    LEADER;

    public static ClanRole fromString(String param) {
        ClanRole TempRole = null;
        switch (Integer.parseInt(param)) {
            case 1 -> TempRole = ClanRole.ROOKIE;
            case 2 -> TempRole = ClanRole.MEMBER;
            case 3 -> TempRole = ClanRole.OFFICER;
            case 4 -> TempRole = ClanRole.LEADER;
            default -> TempRole = ClanRole.NONE;
        }
        return TempRole;
    }

}

