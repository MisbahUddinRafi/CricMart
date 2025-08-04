package controller;

import model.Player;
import java.io.Serializable;
import java.util.List;

public class AuctionData implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Player> players;
    private int currentIndex;
    private int timeLeft;
    private int currentBid;
    private String currentBidder;
    private boolean isBiddingPaused;
    private boolean auctionStarted;
    private boolean auctionEnded;
    private Player currentPlayer;
    private int curPrice;
    private boolean currentPlayerSold;
    private String winningClubName;
    private int finalSalePrice;

    public AuctionData(List<Player> players, int currentIndex, int timeLeft,
                       int currentBid, String currentBidder, boolean isBiddingPaused,
                       boolean auctionStarted, boolean auctionEnded, Player currentPlayer,
                       boolean currentPlayerSold, String winningClubName, int finalSalePrice) {
        this.players = players;
        this.currentIndex = currentIndex;
        this.timeLeft = timeLeft;
        this.currentBid = currentBid;
        this.currentBidder = currentBidder;
        this.isBiddingPaused = isBiddingPaused;
        this.auctionStarted = auctionStarted;
        this.auctionEnded = auctionEnded;
        this.currentPlayer = currentPlayer;
        this.currentPlayerSold = currentPlayerSold;
        this.winningClubName = winningClubName;
        this.finalSalePrice = finalSalePrice;
    }

    // Getters and setters
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isCurrentPlayerSold() {
        return currentPlayerSold;
    }

    public String getWinningClubName() {
        return winningClubName;
    }

    public int getFinalSalePrice() {
        return finalSalePrice;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public String getCurrentBidder() {
        return currentBidder;
    }

    public void setCurrentBidder(String currentBidder) {
        this.currentBidder = currentBidder;
    }

    public boolean isBiddingPaused() {
        return isBiddingPaused;
    }

    public void setBiddingPaused(boolean isBiddingPaused) {
        this.isBiddingPaused = isBiddingPaused;
    }

    public boolean isAuctionStarted() {
        return auctionStarted;
    }

    public void setAuctionStarted(boolean auctionStarted) {
        this.auctionStarted = auctionStarted;
    }

    public boolean isAuctionEnded() {
        return auctionEnded;
    }

    public void setAuctionEnded(boolean auctionEnded) {
        this.auctionEnded = auctionEnded;
    }

    public int getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(int curPrice) {
        this.curPrice = curPrice;
    }

    public void addToCurPrice(int amount) {
        this.curPrice += amount;
    }

    @Override
    public String toString() {
        return "AuctionData{" +
                "players=" + players +
                ", currentIndex=" + currentIndex +
                ", timeLeft=" + timeLeft +
                ", currentBid=" + currentBid +
                ", currentBidder='" + currentBidder + '\'' +
                ", isBiddingPaused=" + isBiddingPaused +
                ", auctionStarted=" + auctionStarted +
                ", auctionEnded=" + auctionEnded +
                ", currentPlayer=" + currentPlayer +
                ", curPrice=" + curPrice +
                '}';
    }
}