package model;

public class Constants {
    // CLUB RELATED:
    // Club Name
    public static final int CLUB_NAME_MIN_LENGTH = 3;
    public static final int CLUB_NAME_MAX_LENGTH = 32;

    // Short Form
    public static final int SHORT_FORM_MIN_LENGTH = 2;
    public static final int SHORT_FORM_MAX_LENGTH = 6;

    // Manager Name
    public static final int MANAGER_NAME_MIN_LENGTH = 3;
    public static final int MANAGER_NAME_MAX_LENGTH = 32;

    // Budget
    public static final int MIN_BUDGET = 10000000;

    // Trophies
    public static final int MAX_TROPHIES = 10;



    // PLAYER RELATED:

    // General field length and format constraints
    public static final int MAX_NAME_LENGTH = 32;
    public static final int MAX_COUNTRY_LENGTH = 32;

    // Age constraints
    public static final int MIN_AGE = 10;
    public static final int MAX_AGE = 50;

    // Height constraints (in meters)
    public static final double MIN_HEIGHT = 1.0;
    public static final double MAX_HEIGHT = 3.0;

    // Matches and stats
    public static final int MAX_MATCHES = 1000;
    public static final int MAX_BALLS = 10000;
    public static final int MAX_JERSEY_NUMBER = 999;

    // Base price
    public static final int MIN_BASE_PRICE = 2000000;
    public static final int MAX_BASE_PRICE = 20000000;



    // USER RELATED:
    public static final int MAX_USERNAME_LENGTH = 32;
    public static final int MIN_USERNAME_LENGTH = 4;

    public static final int MAX_PASSWORD_LENGTH = 32;
    public static final int MIN_PASSWORD_LENGTH = 6;

}
