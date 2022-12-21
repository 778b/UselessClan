package io.github.lofrol.UselessClan.ClanObjects;

public enum EClanRole {
    NONE,
    ROOKIE,
    MEMBER,
    OFFICER,
    LEADER;

    public static EClanRole fromString(String param) {
        EClanRole TempRole = null;
        switch (Integer.parseInt(param)) {
            case 1 -> TempRole = EClanRole.ROOKIE;
            case 2 -> TempRole = EClanRole.MEMBER;
            case 3 -> TempRole = EClanRole.OFFICER;
            case 4 -> TempRole = EClanRole.LEADER;
            default -> TempRole = EClanRole.NONE;
        }
        return TempRole;
    }

}

