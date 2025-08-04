package model;

import application.Main;

import java.util.ArrayList;
import java.util.List;

import static model.Player.formatString;

public class Club {

    // member variables:
    private String clubName;
    private String shortForm;
    private String managerName;
    private String username;
    private String password;

    private int budget;
    private int batsmanCount;
    private int bowlerCount;
    private int allRounderCount;
    private int wicketKeeperCount;
    private int trophiesCount;

    List<Player> players;



    // method to capitalize all characters of the short form of a club:
    private String formatShortForm(String input) {
        if (input == null) return "";

        return input.trim().toUpperCase();
    }


    // method to create a short form of the team name:
    private String createShortForm(String input) {
        if (input == null) return "";
        String[] words = input.split ("\\s+");
        StringBuilder result = new StringBuilder();

        for (String w : words) {
            if (!w.isEmpty()) {
                result.append(Character.toUpperCase(w.charAt(0)));
            }
        }
        return result.toString().trim();
    }


    public Club () {
        clubName = "";
        shortForm = "";
        managerName = "";
        username = "";
        password = "";

        budget = 0;
        batsmanCount = 0;
        bowlerCount = 0;
        allRounderCount = 0;
        wicketKeeperCount = 0;
        trophiesCount = 0;

        players = new ArrayList<>();
    }


    // this constructor will be used when a new club is signed:
    public Club (String clubName, String username, String password) {
        this.clubName = formatString(clubName);
        this.username = username;
        this.password = password;
        shortForm = createShortForm(clubName);
        managerName = "";

        budget = 0;
        batsmanCount = 0;
        bowlerCount = 0;
        allRounderCount = 0;
        wicketKeeperCount = 0;
        trophiesCount = 0;

        players = new ArrayList<>();
    }




    // ********************************************* //
    //                   GETTERS                     //
    // ********************************************* //

    public String getClubName() {
        return clubName;
    }

    public int getWicketKeeperCount() {
        return wicketKeeperCount;
    }

    public int getBowlerCount() {
        return bowlerCount;
    }

    public int getBatsmanCount() {
        return batsmanCount;
    }

    public int getBudget() {
        return budget;
    }

    public int getAllRounderCount() {
        return allRounderCount;
    }

    public int getTrophiesCount() {
        return trophiesCount;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getUsername() {
        return username;
    }

    public String getShortForm() {
        return shortForm;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalPlayers() {return players.size();}




    // ********************************************* //
    //                   SETTERS                     //
    // ********************************************* //

    public void setClubName(String clubName) {
        this.clubName = formatString(clubName);
        for (Player player : players) {
            player.setClub(clubName);
            Main.playerDatabase.updatePlayerClub(player, clubName);
        }
    }

    public void setTrophiesCount(int trophiesCount) {
        this.trophiesCount = trophiesCount;
    }

    public void setWicketKeeperCount(int wicketKeeperCount) {
        this.wicketKeeperCount = wicketKeeperCount;
    }

    public void setAllRounderCount(int allRounderCount) {
        this.allRounderCount = allRounderCount;
    }

    public void setBowlerCount(int bowlerCount) {
        this.bowlerCount = bowlerCount;
    }

    public void setBatsmanCount(int batsmanCount) {
        this.batsmanCount = batsmanCount;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setManagerName(String managerName) {
        this.managerName = formatString(managerName);
    }

    public void setShortForm(String shortForm) {
        this.shortForm = formatShortForm(shortForm);
    }

    public void setPlayers(List<Player> players) {
        List<Player> registeredPlayers = new ArrayList<>();

        for (Player p : players) {
            if (Main.playerDatabase != null && Main.playerDatabase.isPresentPlayer(p.getName())) {
                registeredPlayers.add(p);
                Main.playerDatabase.updatePlayerClub(p, clubName);
            }
        }

        this.players = registeredPlayers;
        updatePositionedPlayerCount(this.players);
    }




    // ****************************************************** //
    //            ADD OR REMOVE PLAYER IN THE TEAM            //
    // ****************************************************** //

    public boolean isPresentPlayer(String playerName) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }


    // add an individual player to the team:
    public boolean addNewPlayer(Player player) {
        // check if the player is registered in player database or not:
        if (Main.playerDatabase != null && !Main.playerDatabase.isPresentPlayer(player.getName())) {
            System.out.println("Player " + player.getName() + " is not a registered player");
            return false;
        }

        // check if the player already exists in the club:
        if (!isPresentPlayer(player.getName())) {
            players.add(player);

            // update the club of the player:
            if (Main.playerDatabase != null) {
                Main.playerDatabase.updatePlayerClub(player, clubName);
            }

            // update the position based player count of this club:
            updatePositionedPlayerCount(player.getPosition());
            System.out.println("Player " + player.getName() + " added to the club " + clubName);
            return true;
        }

        System.out.println("Player " + player.getName() + " is already present in the club " + clubName);
        return false;
    }



    // remove an individual player from the team:
    // also update the players current club:
    public boolean removePlayer(Player player) {
        if (isPresentPlayer(player.getName())) {

            // update the position based player count:
            String position = player.getPosition();
            if (position.equalsIgnoreCase("Batsman")) batsmanCount--;
            else if (position.equalsIgnoreCase("Bowler")) bowlerCount--;
            else if (position.equalsIgnoreCase("AllRounder")) allRounderCount--;
            else if (position.equalsIgnoreCase("WicketKeeper")) wicketKeeperCount--;

            // update the club name of this player:
            if (Main.playerDatabase != null) Main.playerDatabase.updatePlayerClub(player, "");

            // remove this player from the club's player list:
            players.remove(player);
            System.out.println("Player " + player.getName() + " removed from the club " + clubName);

            return true;
        }

        System.out.println("Player " + player.getName() + " is not present in the club " + clubName);
        return false;
    }


    // Update for a single player input:
    private void updatePositionedPlayerCount(String position) {
        if (position == null) return;
        if (position.equalsIgnoreCase("Batsman")) batsmanCount++;
        else if (position.equalsIgnoreCase("Bowler")) bowlerCount++;
        else if (position.equalsIgnoreCase("AllRounder")) allRounderCount++;
        else if (position.equalsIgnoreCase("WicketKeeper")) wicketKeeperCount++;
    }


    // update for all players in the team:
    private void updatePositionedPlayerCount(List<Player> players) {
        batsmanCount = 0;
        bowlerCount = 0;
        allRounderCount = 0;
        wicketKeeperCount = 0;
        for (Player p : players) {
            String position = p.getPosition();
            updatePositionedPlayerCount(position);
        }
    }



    // print the information of the club:
    public void printClub() {
        String result = "Name: " + clubName + "\n";
        result += "Short Form: " + shortForm + "\n";
        result += "Manager Name: " + managerName + "\n";
        result += "Budget: " + budget + "\n";
        result += "Total Batsman: " + batsmanCount + "\n";
        result += "Total Bowler: " + bowlerCount + "\n";
        result += "Total All-Rounder: " + allRounderCount + "\n";
        result += "Total Wicket-Keeper: " + wicketKeeperCount + "\n";
        result += "Total Trophies: " + trophiesCount + "\n";

        System.out.println(result);
        System.out.println("All Players of This Club: ");
        for (Player p : players) {
            p.printPlayer();
            System.out.println();
        }
        System.out.println("Total Players in This Club: " + players.size());
        System.out.println();
    }



    // print the information of the club in the file:
    public String toFileString() {
        String result = username;
        result += "," + clubName;
        result += "," + shortForm;
        result += "," + managerName;
        result += "," + budget;
        result += "," + trophiesCount;
        return result;
    }
}
