package controller.auctionRelated;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionHistoryManager {
    private static final String HISTORY = "auction_history.txt";
    private List<AuctionRecord> auctionRecords = new ArrayList<>();

    public void addAuctionRecord(AuctionRecord record) {
        auctionRecords.add(record);
        saveToFile();
    }

    public List<AuctionRecord> getAllRecords() {
        return new ArrayList<>(auctionRecords);
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HISTORY))) {
            for (AuctionRecord record : auctionRecords) {
                bw.write(record.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving auction history: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        auctionRecords.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(HISTORY))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals("----")) {
                    System.out.println(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(line).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading auction history: " + e.getMessage());
        }
    }
}