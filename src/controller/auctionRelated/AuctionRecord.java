package controller.auctionRelated;

import model.Player;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuctionRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String auctionId;
    private long timestamp;
    private int totalPlayersSold;
    private int totalAuctionDurationSeconds;
    private List<Player> playersSoldDetails;

    public AuctionRecord(int totalPlayersSold, int totalAuctionDurationSeconds, List<Player> playersSoldDetails) {
        this.auctionId = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        this.totalPlayersSold = totalPlayersSold;
        this.totalAuctionDurationSeconds = totalAuctionDurationSeconds;
        this.playersSoldDetails = new ArrayList<>();
        for (Player p : playersSoldDetails) {
            if (p.getClub() != null && !p.getClub().isEmpty()) {
                this.playersSoldDetails.add(p);
            }
        }
    }

    public String getAuctionId() {
        return auctionId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getTotalPlayersSold() {
        return totalPlayersSold;
    }

    public int getTotalAuctionDurationSeconds() {
        return totalAuctionDurationSeconds;
    }

    public List<Player> getPlayersSoldDetails() {
        return playersSoldDetails;
    }

    public String getFormattedTimestamp() {
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, java.time.ZoneOffset.UTC);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuctionID: ").append(auctionId).append(System.lineSeparator());
        sb.append("Timestamp: ").append(getFormattedTimestamp()).append(System.lineSeparator());
        sb.append("PlayersSold: ").append(totalPlayersSold).append(System.lineSeparator());
        sb.append("DurationSeconds: ").append(totalAuctionDurationSeconds).append(System.lineSeparator());
        sb.append("SoldPlayers:").append(System.lineSeparator());
        for (Player p : playersSoldDetails) {
            sb.append("  Name: ").append(p.getName())
            .append(", Club: ").append(p.getClub())
            .append(", Price: ").append(p.getFinalBidPrice())
            .append(System.lineSeparator());
        }
        sb.append("----").append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Auction ID: " + auctionId.substring(0, 8) + "..., Date: " + getFormattedTimestamp() +
                ", Players Sold: " + totalPlayersSold + ", Duration: " + totalAuctionDurationSeconds + "s";
    }
}