package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void log(String message) {
        try (PrintWriter out = new PrintWriter(new FileWriter("log.txt", true))) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            out.println("[" + time + "] " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
