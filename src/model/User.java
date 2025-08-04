/*
* ADD EDIT USERNAME OPTION
* ADD UPDATE PASSWORD OPTION
*
* */
package model;
import static model.Constants.*;


public class User {
    private String type;
    private String username;
    private String password;

    public User(String type, String username, String password) {
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public String toFileString() {
        return type + "," + username + "," + password;
    }

    public String getType() { return type; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }


    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }


    public static boolean isValidUsername (String username) {
        // no space allowed (but allowed for clubs)
        // minimum length is 4 and maximum length is 32
        // only alphabets and digits allowed

        if (username == null) return false;
        if (username.length() < MIN_USERNAME_LENGTH || MAX_USERNAME_LENGTH < username.length()) return false;
        if (username.contains(",")) return false;
        return username.matches("[a-zA-Z0-9]+");            // Check allowed characters: letters and digits only
    }

    public static boolean isValidPassword (String password) {
        // no space allowed, no comma allowed
        // minimum length is 6 and maximum length is 32
        // all types of characters are allowed

        if (password == null) return false;
        if (password.length() < MIN_PASSWORD_LENGTH|| MAX_PASSWORD_LENGTH < password.length()) return false;
        if (password.contains(",") || password.contains(" ")) return false;
        return true;
    }




}
