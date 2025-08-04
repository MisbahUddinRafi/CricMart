/***
 *
 *
 *  SYNC ISSUE BETWEEN LOADPLAYERS() AND LOADCLUBS()
 *
 *
 * */








package model;

import application.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDatabase {
    public List<Club> clubs = new ArrayList<>();


    // constructor:
    public ClubDatabase() throws FileNotFoundException{
        loadClubs();
        System.out.println("Club Database Loaded");
    }



    // ******************************************************
    //      LOAD CLUB INFO FROM DATABASE
    // ******************************************************

    private void loadClubs() throws FileNotFoundException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("clubs.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) continue;

                Club club = new Club();
                club.setUsername(data[0]);
                club.setClubName(data[1]);
                club.setShortForm(data[2]);
                club.setManagerName(data[3]);
                club.setBudget(Integer.parseInt(data[4]));
                club.setTrophiesCount(Integer.parseInt(data[5]));

                // players will be added to clubs when the players will be loaded from the player database

                clubs.add(club);
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error in reading file");
            e.printStackTrace();
        }
    }




    //********************************************************
    //               IS PRESENT
    // *******************************************************


    // check if a club is registered in the clubs.txt database:
    public boolean isPresentClub(String clubName) {
        for (Club club : clubs) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                return true;
            }
        }
        return false;
    }


    // add a new club to the clubs list:
    public boolean addNewClub(Club club) {
        if (isPresentClub(club.getClubName())) {
            System.out.println("Club " + club.getClubName() + " already exists");
            return false;
        }

        List<Player> clubPlayers = club.getPlayers();
        for (Player player : clubPlayers) {
            if (!Main.playerDatabase.isPresentPlayer(player.getName())) {
                club.removePlayer(player);              // this player is not registered
            }
        }
        clubs.add(club);
        return true;
    }


    public boolean addPlayerToClub(Player player, String clubName) {
        for (Club club : clubs) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                club.addNewPlayer(player);
                return true;
            }
        }
        System.out.println("Club " + clubName + " does not exist");
        return false;

    }



    // REMOVE A PLAYER FROM A CLUB:
    public boolean removePlayerFromClub(Player player) {
        for (Club club : clubs) {
            if (player.getClub().equalsIgnoreCase(club.getClubName())) {
                club.removePlayer(player);
                return true;
            }
        }
        return false;
    }








    public Club getClub(String clubName) {
        for (Club club : clubs) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                return club;
            }
        }
        return null;
    }


    public Club getClubByUsername(String username) {
        for (Club club : clubs) {
            if (club.getUsername().equalsIgnoreCase(username)) {
                return club;
            }
        }
        return null;
    }



    public List<Club> getClubs() {
        return clubs;
    }



    public List<Player> getPlayersOfClub(String clubName) {
        List<Player> players = new ArrayList<>();
        for (Club club : clubs) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                players.addAll(club.getPlayers());
                break;
            }
        }
        return players;
    }





    public boolean deleteClub(String clubName) {
        for (Club club : clubs) {
            if (club.getClubName().equalsIgnoreCase(clubName)) {
                // release all players of that club:
                List<Player> playersCopy = new ArrayList<>(club.getPlayers());
                for (Player player : playersCopy) {
                    club.removePlayer(player);
                }

                // finally remove the club:
                clubs.remove(club);
                return true;
            }
        }
        return false;
    }

    public synchronized void uploadInfoToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("clubs.txt"));    // writing mode
            for (Club c : clubs) {
                bw.write(c.toFileString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            System.out.println("clubs.txt file updated");


        } catch (IOException e) {
            System.out.println("Error in writing file");
            e.printStackTrace();
        }
    }




    public synchronized void  reloadFromFile() throws FileNotFoundException {
        clubs.clear();
        loadClubs();
    }








    public boolean updateClubBudget(String username, int newBudget) throws Exception {
        boolean clubFound = false;

        // First, update the budget in memory
        for (Club club : clubs) {
            if (club.getUsername().equalsIgnoreCase(username)) {
                int oldBudget = club.getBudget();
                club.setBudget(newBudget);
                clubFound = true;

                System.out.println("DEBUG: Updated " + club.getClubName() + " (username: " + username +
                        ") budget from $" + String.format("%,d", oldBudget) + " to $" + String.format("%,d", newBudget));
                break;
            }
        }

        if (!clubFound) {
            System.err.println("ERROR: Club with username '" + username + "' not found for budget update");
            return false;
        }

        // Then, persist the changes to file
        try {
            uploadInfoToFile();
            System.out.println("DEBUG: Budget update persisted to clubs.txt file");
            return true;

        } catch (Exception e) {
            System.err.println("ERROR: Failed to persist budget update to file: " + e.getMessage());
            throw new Exception("Failed to update budget in database file", e);
        }
    }




    public boolean updatePlayerClub(String playerName, String clubName, int finalBidPrice) {
        try {
            System.out.println("DEBUG: Attempting to assign player '" + playerName +
                    "' to club '" + clubName + "' for $" + String.format("%,d", finalBidPrice));

            // First, find the player in the main player database
            Player playerToUpdate = Main.playerDatabase.getPlayer(playerName);
            if (playerToUpdate == null) {
                System.err.println("ERROR: Player '" + playerName + "' not found in player database");
                return false;
            }

            // Find the target club
            Club targetClub = null;
            for (Club club : clubs) {
                if (club.getClubName().equalsIgnoreCase(clubName)) {
                    targetClub = club;
                    break;
                }
            }

            if (targetClub == null) {
                System.err.println("ERROR: Club '" + clubName + "' not found in club database");
                return false;
            }

            // Remove player from current club if they have one
            String currentClub = playerToUpdate.getClub();
            if (currentClub != null && !currentClub.equalsIgnoreCase("None") && !currentClub.trim().isEmpty()) {
                for (Club club : clubs) {
                    if (club.getClubName().equalsIgnoreCase(currentClub)) {
                        club.removePlayer(playerToUpdate);
                        System.out.println("DEBUG: Removed player '" + playerName + "' from previous club '" + currentClub + "'");
                        break;
                    }
                }
            }

            // Update player information
            playerToUpdate.setClub(clubName);
            playerToUpdate.setFinalBidPrice(finalBidPrice);

            // Add player to the new club
            targetClub.addNewPlayer(playerToUpdate);

            System.out.println("DEBUG: Successfully assigned player '" + playerName +
                    "' to club '" + clubName + "' for $" + String.format("%,d", finalBidPrice));

            // Update the player database file
            Main.playerDatabase.uploadInfoToFile();

            // Update the club database file
            uploadInfoToFile();

            System.out.println("DEBUG: Player club assignment persisted to database files");
            return true;

        } catch (Exception e) {
            System.err.println("ERROR: Failed to update player club assignment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
