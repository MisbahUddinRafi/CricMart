package model;


// load the information of players from the database (players.txt file)
// create Player objects and store them in a list
// create a list of all the clubs
// create a list of all countries

// save the player information in the players.txt file before closing the system
// Search Player options:
//        (1) By Player Name
//        (2) By Club and Country
//        (3) By Position
//        (4) By Salary Range
//        (5) Country-wise player count
//        (6) Back to Main Menu
//
// Search Club options:
//      (1) searchBySalary: Player(s) with the maximum salary of a club
//      (2) searPlayer(s) with the maximum age of a club
//      (3) Player(s) with the maximum height of a club
//      (4) Total yearly salary of a club
//      (5) Back to Main Menu



import application.Main;

import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class PlayerDatabase {
    // List for string player objects:
    List<Player> players = new ArrayList<>();

    // List of unsold players:
    List<Player> unsoldPlayers = new ArrayList<>();

    // List for storing clubs:
    List<String> clubs = new ArrayList<>();

    // List for storing countries:
    List<String> countries = new ArrayList<>();

    // List of all Batsman:
    List<Player> batsmans = new ArrayList<>();

    // List of all Bowlers:
    List<Player> bowlers = new ArrayList<>();

    // List of all AllRounders:
    List<Player> allRounders = new ArrayList<>();

    // List of all WicketKeepers:
    List<Player> wicketKeepers = new ArrayList<>();




    public PlayerDatabase() throws IOException {
        loadPlayers();      // load the player information from the players.txt file
        System.out.println("Player Database Loaded");
    }



    private void loadPlayers() throws IOException {
        // ******************************************************** //
        //      READ PLAYER INFORMATION FROM players.txt FILE       //
        // ******************************************************** //

        // create a buffered reader object to read the player information from players.txt file:
        BufferedReader br = new BufferedReader(new FileReader("players.txt"));


        // continuously add players to the list from the text file:
        String line;
        while(true) {
            // read a line from the input file:
            try {
                line = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (line == null) {
                break;
            }

            // create a Player object:
            Player newPlayer = new Player();
            if (newPlayer == null) continue;

            String[] tokens = line.split(",");
            if (tokens.length < 19) {
                continue;
            }

            newPlayer.setName(tokens[0]);
            newPlayer.setCountry(tokens[1]);
            newPlayer.setAge(Integer.parseInt(tokens[2]));
            newPlayer.setHeight(Double.parseDouble(tokens[3]));
            newPlayer.setClub(tokens[4]);
            newPlayer.setPosition(tokens[5]);
            if (!tokens[6].isEmpty()) {
                newPlayer.setNumber(Integer.parseInt(tokens[6]));
            } else {
                newPlayer.setNumber(-1);
            }
            newPlayer.setSalary(Integer.parseInt(tokens[7]));

            // stats:
            newPlayer.setMatchesPlayed(Integer.parseInt(tokens[8]));
            newPlayer.setRunsScored(Integer.parseInt(tokens[9]));
            newPlayer.setBallsFaced(Integer.parseInt(tokens[10]));
            newPlayer.setFifties(Integer.parseInt(tokens[11]));
            newPlayer.setCenturies(Integer.parseInt(tokens[12]));
            newPlayer.setHattricks(Integer.parseInt(tokens[13]));
            newPlayer.setOversBowled(Double.parseDouble(tokens[14]));
            newPlayer.setRunsGiven(Integer.parseInt(tokens[15]));
            newPlayer.setWicketsTaken(Integer.parseInt(tokens[16]));

            // auction details:
            newPlayer.setBasePrice(Integer.parseInt(tokens[17]));
            newPlayer.setFinalBidPrice(Integer.parseInt(tokens[18]));



            // check if player is from a valid/ registered club:
            if (newPlayer.getClub().isEmpty()) {            // no club, means player is unsold
                players.add(newPlayer);
                unsoldPlayers.add(newPlayer);
                System.out.println("Player " + newPlayer.getName() + " added to unsold players");
            }
            else if (Main.clubDatabase != null && Main.clubDatabase.isPresentClub(newPlayer.getClub())) {        // club is present in the club database, so it is a valid/ registered club
                players.add(newPlayer);
                Main.clubDatabase.addPlayerToClub(newPlayer, newPlayer.getClub());
            }
            else {
                System.out.println("Player " + newPlayer.getName() + " club " +  newPlayer.getClub() + " not found");
                continue;       // player's club is not registered, so we can not add this player
            }


            // now check if we got a new club or not:
            if (!clubs.contains(newPlayer.getClub())) {
                clubs.add(newPlayer.getClub());
            }

            // check if we got a new country or not:
            if (!countries.contains(newPlayer.getCountry())) {
                countries.add(newPlayer.getCountry());
            }


            // update the position based player lists:
            if (newPlayer.getPosition().equalsIgnoreCase("Batsman")) {
                batsmans.add(newPlayer);
            }
            else if (newPlayer.getPosition().equalsIgnoreCase("Bowler")) {
                bowlers.add(newPlayer);
            }
            else if (newPlayer.getPosition().equalsIgnoreCase("AllRounder")) {
                allRounders.add(newPlayer);
            }
            else if (newPlayer.getPosition().equalsIgnoreCase("WicketKeeper")) {
                wicketKeepers.add(newPlayer);
            }


        }


        // ********** PLAYER READING FINISHED ********** //

    }



    // **************************************************** //
    //                     IS PRESENT                       //
    // **************************************************** //

    public boolean isPresentClub (String club) {
        for (String c : clubs) {
            if (c.equalsIgnoreCase(club)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPresentPlayer (String name) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPresentCountry (String country) {
        for (String c : countries) {
            if (c.equalsIgnoreCase(country)) {
                return true;
            }
        }
        return false;
    }





    // **************************************************** //
    //                  SEARCH PLAYER                       //
    // **************************************************** //

    public String searchByPlayerName (String searchName) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(searchName)) {
                System.out.println();
                System.out.println(player.getName() + " found");
                player.printPlayer();
                System.out.println();
                return player.printPlayer();
            }
        }

        System.out.println();
        System.out.println("Player " + searchName + " not found");
        return null;
    }


    public void searchByClub (String searchCountry, String searchClub) {
        if (!isPresentCountry(searchCountry)) {
            System.out.println();
            System.out.println("Country " + searchCountry + " not found");
            return;
        }

        int playerCount = 0;
        if (searchClub.equalsIgnoreCase("Any")) {
            System.out.println();
            System.out.println("Players from country " + searchCountry + " are: ");
            for (Player player : players) {
                if (player.getCountry().equalsIgnoreCase(searchCountry)) {
                    playerCount++;
                    System.out.println("Player " + playerCount + ": ");
                    player.printPlayer();
                    System.out.println();
                }
            }
            System.out.println("Total players from country " + searchCountry + " are: " + playerCount);
            return;
        }

        if (!isPresentClub(searchClub)) {
            System.out.println();
            System.out.println("Club " + searchClub + " not found");
            return;
        }

        System.out.println();
        System.out.println("Players from country " + searchCountry + " in the club " + searchClub + " are: ");
        for (Player player : players) {
            if (player.getCountry().equalsIgnoreCase(searchCountry) && player.getClub().equalsIgnoreCase(searchClub)) {
                playerCount++;
                System.out.println("Player " + playerCount + ": ");
                player.printPlayer();
                System.out.println();
            }
        }
        System.out.println("Total players from country " + searchCountry + " in the club " + searchClub + " are: " + playerCount);


    }



    public void searchByPosition (String searchPosition) {
        List<Player> positionPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getPosition().equalsIgnoreCase(searchPosition)) {
                positionPlayers.add(player);
            }
        }

        if (!positionPlayers.isEmpty()) {
            System.out.println();
            System.out.println("Players with position " + searchPosition + " found");
            int count = 0;
            for (Player player : positionPlayers) {
                count++;
                System.out.println("Player " + count + ": ");
                player.printPlayer();
                System.out.println();
            }

            System.out.println("Total players with position " + searchPosition + " are: " + count);

        } else {
            System.out.println();
            System.out.println("Players with position " + searchPosition + " not found");
        }

    }



    public void searchBySalaryRange (int startingSalary, int endingSalary) {
        List<Player> salaryPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getSalary() >= startingSalary && player.getSalary() <= endingSalary) {
                salaryPlayers.add(player);
            }
        }

        if (!salaryPlayers.isEmpty()) {
            int count = 0;
            System.out.println();
            System.out.println("Players between salary range " + startingSalary + " to " + endingSalary + " are: ");
            for (Player player : salaryPlayers) {
                count++;
                System.out.println("Player " + count + ": ");
                player.printPlayer();
                System.out.println();
            }
            System.out.println("Total players in this salary range are: " + count);

        } else {
            System.out.println();
            System.out.println("Players between given salary range not found");
        }
    }


    public void countryWisePlayerCount () {
        int totalCountry = countries.size();
        int[] playerCount = new int[totalCountry];      // store the player count per country

        for (Player player : players) {
            String country = player.getCountry();
            int index = countries.indexOf(country);
            playerCount[index] += 1;
        }

        // print all the countries player count:
        System.out.println();
        System.out.println("Country Wise Player Count: ");
        int index = 0;
        for (String country : countries) {
            System.out.println((index+1) + ". " + country + ": " + playerCount[index] + " players");
            index++;
        }


    }


    // return the list of players of a certain club:
    public List<Player> getPlayersOfClub (String clubName) {
        List<Player> clubPlayers = new ArrayList<>();

        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(clubName)) {
                clubPlayers.add(player);
            }
        }
        return clubPlayers;
    }




    // return the list of players of a certain country:
    public List<Player> getPlayersOfCountry (String countryName) {
        List<Player> countryPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getCountry().equalsIgnoreCase(countryName)) {
                countryPlayers.add(player);
            }
        }
        return countryPlayers;
    }


    // return the list of players of a certain position:
    public List<Player> getPlayersOfPosition (String positionName) {
        if (positionName.equalsIgnoreCase("Batsman")) return batsmans;
        else if (positionName.equalsIgnoreCase("Bowler")) return bowlers;
        else if (positionName.equalsIgnoreCase("AllRounder")) return allRounders;
        else if (positionName.equalsIgnoreCase("WicketKeeper")) return wicketKeepers;
        else {
            throw new RuntimeException("Position " + positionName + " not found");
        }
    }



    public List<Player> getPlayersOfSalaryRange (int startingSalary, int endingSalary) {
        List<Player> salaryPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getSalary() >= startingSalary && player.getSalary() <= endingSalary) {
                salaryPlayers.add(player);
            }
        }

        return salaryPlayers;
    }



    // **************************************************** //
    //                      SEARCH CLUB                     //
    // **************************************************** //

    public void maxSalaryPlayerInClub (String searchClub) {
        if (!isPresentClub(searchClub)) {
            System.out.println();
            System.out.println("Club not found");
            return;
        }

        // find the max salary in this club:
        int maxSalary = 0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub)) {
                if (player.getSalary() > maxSalary) {
                    maxSalary = player.getSalary();
                }
            }
        }


        // print all the players with the max salary in this club:
        System.out.println();
        int count = 0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub) && player.getSalary() == maxSalary) {
                count++;
                System.out.println("Player " + count + ": ");
                player.printPlayer();           // print the player information
                System.out.println();
            }
        }
        System.out.println("Max salary in Club: " + searchClub + " is " + maxSalary);
        System.out.println("Total players with max salary: " + count);
        System.out.println();

    }



    public void maxAgePlayerInClub (String searchClub) {
        if (!isPresentClub(searchClub)) {
            System.out.println();
            System.out.println("Club not found");
            return;
        }

        // find the max salary in this club:
        int maxAge = 0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub)) {
                if (player.getAge() > maxAge) {
                    maxAge = player.getAge();
                }
            }
        }


        // print all the players with the max salary in this club:
        System.out.println();
        int count = 0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub) && player.getAge() == maxAge) {
                count++;
                System.out.println("Player " + count + ": ");
                player.printPlayer();           // print the player information
                System.out.println();
            }
        }
        System.out.println("Max age in Club: " + searchClub + " is " + maxAge);
        System.out.println("Total players with max age: " + count);
        System.out.println();

    }



    public void maxHeightPlayerInClub (String searchClub) {
        if (!isPresentClub(searchClub)) {
            System.out.println();
            System.out.println("Club not found");
            return;
        }


        // find the max salary in this club:
        double maxHeight = 0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub)) {
                if (player.getHeight() > maxHeight) {
                    maxHeight = player.getHeight();
                }
            }
        }


        // print all the players with the max salary in this club:
        System.out.println();
        int count = 0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub) && player.getHeight() == maxHeight) {
                count++;
                System.out.println("Player " + count + ": ");
                player.printPlayer();           // print the player information
                System.out.println();
            }
        }
        System.out.println("Max Height in Club: " + searchClub + " is " + maxHeight);
        System.out.println("Total players with max height: " + count);
        System.out.println();

    }



    public void totalSalaryOfClub (String searchClub) {
        if (!isPresentClub(searchClub)) {
            System.out.println();
            System.out.println("Club not found");
            return;
        }

        int totalSalary = 0;
        int totalPlayers =0;
        for (Player player : players) {
            if (player.getClub().equalsIgnoreCase(searchClub)) {
                totalSalary += player.getSalary();
                totalPlayers++;
            }
        }

        System.out.println();
        System.out.println("Total salary in Club: " + searchClub + " is " + totalSalary);
        System.out.println("Total players in this club: " + totalPlayers);
        System.out.println();

    }



    // **************************************************** //
    //                 ADD OR REMOVE PLAYER                 //
    // **************************************************** //

    public boolean addNewPlayer(Player newPlayer) {
        if (isPresentPlayer(newPlayer.getName())) {
            System.out.println();
            System.out.println("Player already exists");
            return false;
        }

        if (newPlayer.getClub().isEmpty()) {
            players.add(newPlayer);
            unsoldPlayers.add(newPlayer);
        }
        else if (Main.clubDatabase != null && Main.clubDatabase.isPresentClub(newPlayer.getClub())) {
            players.add(newPlayer);
        }
        else {
            System.out.println("Club " + newPlayer.getClub() + " is not registered");
            return false;
        }


        // update the position based player lists:
        String position = newPlayer.getPosition();
        if (position.equalsIgnoreCase("Batsman")) batsmans.add(newPlayer);
        else if (position.equalsIgnoreCase("Bowler")) bowlers.add(newPlayer);
        else if (position.equalsIgnoreCase("AllRounder")) allRounders.add(newPlayer);
        else if (position.equalsIgnoreCase("WicketKeeper")) wicketKeepers.add(newPlayer);


        if (!isPresentCountry(newPlayer.getCountry())) {
            countries.add(newPlayer.getCountry());
        }

        System.out.println();
        System.out.println("Player " + newPlayer.getName() + " added");
        System.out.println();

        return true;
    }


    // update the club of a player when he is removed from a club:
    // check if player exists in database
    // check if new club name exists in database
    public boolean updatePlayerClub (Player player, String newClubName) {
        if (isPresentPlayer(player.getName())) {
            if (newClubName.isEmpty() || Main.clubDatabase != null && Main.clubDatabase.isPresentClub(newClubName)) {
                System.out.println("Player " + player.getName() + "'s club updated from " + player.getClub() + " to " + newClubName);
                player.setClub(newClubName);

                // update the unsold player list:
                if (newClubName.isEmpty() && !unsoldPlayers.contains(player)) unsoldPlayers.add(player);
                else unsoldPlayers.remove(player);

                return true;
            } else {
                System.out.println(newClubName + " is not registered");
                return false;
            }
        } else {
            System.out.println("Player " + player.getName() + " is not registered");
            return false;
        }
    }


    public boolean removePlayer (Player player) {
        if (isPresentPlayer(player.getName())) {
            String clubName = player.getClub();
            String position = player.getPosition();

            players.remove(player);

            // club update:
            if (clubName.isEmpty()) unsoldPlayers.remove(player);
            else if (Main.clubDatabase != null) Main.clubDatabase.removePlayerFromClub (player);       // remove the player from its current club

            // position based player list update:
            if (position.equalsIgnoreCase("Batsman")) batsmans.remove(player);
            else if (position.equalsIgnoreCase("Bowler")) bowlers.remove(player);
            else if (position.equalsIgnoreCase("AllRounder")) allRounders.remove(player);
            else if (position.equalsIgnoreCase("WicketKeeper")) wicketKeepers.remove(player);

            System.out.println("Player " + player.getName() + " removed from database");
            return true;
        }

        System.out.println("Player " + player.getName() + " is not registered");
        return false;
    }

    // **************************************************** //
    //            UPLOAD PLAYER INFO TO FILE                //
    // **************************************************** //

    // writing mode:
    public synchronized void uploadInfoToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("players.txt"));
            for (Player player : players) {
                bw.write(player.toFileString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            System.out.println("players.txt file updated");

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    public synchronized void reloadFromFile() throws IOException {
        players.clear();
        unsoldPlayers.clear();
        clubs.clear();
        countries.clear();
        batsmans.clear();
        bowlers.clear();
        allRounders.clear();
        wicketKeepers.clear();

        loadPlayers(); // reuse your existing logic
    }



    public List<Player> getPlayers() {
        return players;
    }



    public Player getPlayer (String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) return player;
        }
        return null;
    }


    public List<Player> getUnsoldPlayers() {
        return unsoldPlayers;
    }




    public boolean addNewCountry (String newCountry) {
        if (!isPresentCountry(newCountry)) {
            countries.add(newCountry);
            return true;
        }
        return false;
    }
}

