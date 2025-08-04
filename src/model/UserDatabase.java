package model;

import java.io.*;
import java.util.*;

public class UserDatabase {
    private static final String FILE = "LoginInfo.txt";
    private static List<User> users = new ArrayList<>();

    public static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
            System.out.println("Loaded " + users.size() + " users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static boolean addUser(User user) {

        users.add(user);
        System.out.println("User added: " + user.toString());
        return true;

    }

    public static boolean validateUser(String type, String username, String password) {
        for (User user : getAllUsers()) {
            if (user.getType().equals(type) &&
                    user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isPresentUsername (String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }



    public static boolean updateUsername(String oldUsername, String newUsername) {
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setUsername(newUsername);
                return true;
            }
        }
        return false;
    }


    public static boolean updatePassword(String oldUsername, String newPassword) {
        for (User user : users) {
            if (user.getUsername().equals(oldUsername)) {
                user.setPassword(newPassword);
                return true;
            }
        }
        return false;
    }



    public static boolean deleteUser(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                users.remove(user);
                System.out.println("User deleted: " + user.toString());
                return true;
            }
        }
        return false;
    }


    public static boolean updateUserDatabase() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("LoginInfo.txt"));
            for (User user : users) {
                bw.write(user.getType() + ",");
                bw.write(user.getUsername() + ",");
                bw.write(user.getPassword());
                bw.newLine();
            }
            bw.close();
            System.out.println("Login Database updated");
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
