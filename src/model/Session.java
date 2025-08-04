package model;

public class Session {
    public static String currentUsername;
    public static String currentUserType;

    public static void set(String username, String type) {
        currentUsername = username;
        currentUserType = type;
    }

    public static void clear() {
        currentUsername = null;
        currentUserType = null;
    }

    public static String getUsername() {
        return currentUsername;
    }

    public static String getUserType() {
        return currentUserType;
    }
}
