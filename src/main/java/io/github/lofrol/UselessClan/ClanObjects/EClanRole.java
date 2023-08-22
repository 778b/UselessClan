package io.github.lofrol.UselessClan.ClanObjects;

public enum EClanRole {
    NONE,
    ROOKIE,
    MEMBER,
    OFFICER,
    LEADER;

    public static EClanRole fromString(String param) {
        return fromInt(Integer.parseInt(param));
    }

    public static EClanRole fromInt(int param) {
        EClanRole TempRole;
        switch (param) {
            case 1 -> TempRole = EClanRole.ROOKIE;
            case 2 -> TempRole = EClanRole.MEMBER;
            case 3 -> TempRole = EClanRole.OFFICER;
            case 4 -> TempRole = EClanRole.LEADER;
            default -> TempRole = EClanRole.NONE;
        }
        return TempRole;
    }

}

