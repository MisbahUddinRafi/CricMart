package model;

import java.io.Serializable;

public class Player implements Serializable {
    // personal info:
    private static final long serialVersionUID = 1L;
    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private int number;
    private int salary;

    // stats:
    private int matchesPlayed;
    private int runsScored;
    private int ballsFaced;
    private int fifties;
    private int centuries;
    private int hattricks;
    private double oversBowled;
    private int runsConceded;
    private int wicketsTaken;

    // auction:
    private int basePrice;
    private int finalBidPrice;




    // constructor:
    public Player() {
        this.name = "";
        this.country = "";
        this.age = 0;
        this.height = 0;
        this.club = "";
        this.position = "";
        this.number = -1;
        this.salary = 0;

        this.matchesPlayed = 0;
        this.runsScored = 0;
        this.ballsFaced = 0;
        this.fifties = 0;
        this.centuries = 0;
        this.hattricks = 0;
        this.oversBowled = 0;
        this.runsConceded = 0;
        this.wicketsTaken = 0;

        this.basePrice = 0;
        this.finalBidPrice = 0;
    }

    public Player(Player player) {
        this.name = player.name;
        this.country = player.country;
        this.age = player.age;
        this.height = player.height;
        this.club = player.club;
        this.position = player.position;
        this.number = player.number;
        this.salary = player.salary;

        this.matchesPlayed = player.matchesPlayed;
        this.runsScored = player.runsScored;
        this.ballsFaced = player.ballsFaced;
        this.fifties = player.fifties;
        this.centuries = player.centuries;
        this.hattricks = player.hattricks;
        this.oversBowled = player.oversBowled;
        this.runsConceded = player.runsConceded;
        this.wicketsTaken = player.wicketsTaken;

        this.basePrice = player.basePrice;
        this.finalBidPrice = player.finalBidPrice;
    }


    public Player(String name, String country, int age, double height, String club, String position, int number, int salary) {
        this();
        this.name = formatString(name);
        this.country = formatString(country);
        this.age = age;
        this.height = height;
        this.club = formatString(club);
        this.position = formatString(position);
        this.number = number;
        this.salary = salary;
    }

    // String formatting method: "  virat    kohli     " will be converted to "Virat Kohli"
    public static String formatString (String input) {
        if (input == null) return "";

        // split the input string by spaces :
        String[] words = input.split ("\\s+");
        StringBuilder result = new StringBuilder();         // will store the formatted result string

        for (String w : words) {
            if (!w.isEmpty()) {
                result.append(Character.toUpperCase(w.charAt(0)));         // first character is upper case
                result.append(w.substring(1));                   // the other characters remain unchanged
                result.append(" ");
            }
        }

        return result.toString().trim();
    }




    // getter methods:
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public String getClub() {
        return club;
    }

    public String getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }

    public int getSalary() {
        return salary;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public int getBallsFaced() {
        return ballsFaced;
    }

    public int getFifties() {
        return fifties;
    }

    public int getCenturies() {
        return centuries;
    }

    public int getHattricks() {
        return hattricks;
    }

    public double getOversBowled() {
        return oversBowled;
    }

    public int getRunsGiven() {
        return runsConceded;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getFinalBidPrice() {
        return finalBidPrice;
    }



    // calculated stats:
    public double getBattingAverage() {
        return (matchesPlayed > 0) ? (double) runsScored / matchesPlayed : 0.0;
    }

    public double getBattingStrikeRate() {
        return (ballsFaced > 0) ? (double) runsScored * 100 / ballsFaced : 0.0;
    }

    public double getBowlingAverage() {
        return (wicketsTaken > 0) ? (double) runsConceded / wicketsTaken : 0.0;
    }

    public double getEconomyRate() {
        return (oversBowled > 0) ? (double) runsConceded / oversBowled : 0.0;
    }







    // setters:
    public void setName(String name) {
        this.name = formatString(name);
    }

    public void setCountry(String country) {
        this.country = formatString(country);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setClub(String club) {
        this.club = formatString(club);
    }

    public void setPosition(String position) {
        this.position = formatString(position);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public void setBallsFaced(int ballsFaced) {
        this.ballsFaced = ballsFaced;
    }

    public void setFifties(int fifties) {
        this.fifties = fifties;
    }

    public void setCenturies(int centuries) {
        this.centuries = centuries;
    }

    public void setHattricks(int hattricks) {
        this.hattricks = hattricks;
    }

    public void setOversBowled(double oversBowled) {
        this.oversBowled = oversBowled;
    }

    public void setRunsGiven(int runsConceded) {
        this.runsConceded = runsConceded;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setFinalBidPrice(int finalBidPrice) {
        this.finalBidPrice = finalBidPrice;
    }



    public String printPlayer() {
        StringBuilder sb = new StringBuilder();

        sb.append("Player Info\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Country: ").append(country).append("\n");
        sb.append("Age: ").append(age).append("\n");
        sb.append("Height: ").append(height).append("\n");
        sb.append("Club: ").append(club.isEmpty() ? "Unsold" : club).append("\n");
        sb.append("Position: ").append(position).append("\n");

        sb.append("Number: ");
        if (number != -1) sb.append(number);
        sb.append("\n");

        sb.append("Salary: ").append(salary).append("\n");

        sb.append("\nPlayer Stats").append("\n");
        sb.append("Matches Played: ").append(matchesPlayed).append("\n");
        sb.append("Runs Scored: ").append(runsScored).append("\n");
        sb.append("Balls Faced: ").append(ballsFaced).append("\n");
        sb.append("Fifties: ").append(fifties).append("\n");
        sb.append("Centuries: ").append(centuries).append("\n");
        sb.append("Hattricks: ").append(hattricks).append("\n");
        sb.append("Overs Bowled: ").append(oversBowled).append("\n");
        sb.append("Runs Conceded: ").append(runsConceded).append("\n");
        sb.append("Wickets Taken: ").append(wicketsTaken).append("\n");


        sb.append("\nAuction Status").append("\n");
        if (club.isEmpty()) {
            sb.append("Status: Available for Auction").append("\n");
            sb.append("Base Price: ").append(basePrice).append("\n");
        } else {
            sb.append("Status: Sold").append("\n");
            sb.append("Final Bid Price: ").append(finalBidPrice).append("\n");
        }

        return sb.toString();
    }

    // print player details to a txt file:
    public String toFileString() {
        String info = this.getName();
        info += "," + this.getCountry();
        info += "," + this.getAge();
        info += "," + this.getHeight();
        info += "," + this.getClub();
        info += "," + this.getPosition();
        if (this.getNumber() != -1) info += "," + this.getNumber();
        else info += ",";
        info += "," + this.getSalary();

        info += "," + this.getMatchesPlayed();
        info += "," + this.getRunsScored();
        info += "," + this.getBallsFaced();
        info += "," + this.getFifties();
        info += "," + this.getCenturies();

        info += "," + this.getHattricks();
        info += "," + this.getOversBowled();
        info += "," + this.getRunsGiven();
        info += "," + this.getWicketsTaken();

        info += "," + this.getBasePrice();
        info += "," + this.getFinalBidPrice();

        return info;
    }
}




