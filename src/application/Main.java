package application;

import javafx.application.Application;              // import the javafx class Application
import javafx.fxml.FXMLLoader;                      // to load .fxml files
import javafx.scene.Scene;                          // Scene = container for all UI elements (buttons, layouts etc.)
import javafx.stage.Stage;                          // Stage = main window of JavaFX application
import model.ClubDatabase;
import model.PlayerDatabase;
import model.UserDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {             // Main class must extend the built-in Application class and must override the start() method

    public static Stage primaryStage;               // used to switch scenes
    public static ClubDatabase clubDatabase;
    public static PlayerDatabase playerDatabase;
    private static List<Scene> sceneStack = new ArrayList<>();

    public static void main(String[] args) throws IOException {        // starting point of the JavaFX app
        clubDatabase = new ClubDatabase();
        if (clubDatabase == null) {
            System.out.println("Club database is null");
            System.exit(0);
        }

        playerDatabase = new PlayerDatabase();
        if (playerDatabase == null) {
            System.out.println("Error: clubDatabase and playerDatabase are null!");
            System.exit(0);
        }

        UserDatabase.loadUsers();


        // background daemon thread for continuously updating the database:
        Thread backgroundUpdater = new Thread(() -> {
            while (true) {
                try {
                    synchronized (clubDatabase) {
                        clubDatabase.uploadInfoToFile();
                        clubDatabase.reloadFromFile();
                    }

                    synchronized (playerDatabase) {
                        playerDatabase.uploadInfoToFile();
                        playerDatabase.reloadFromFile();
                    }

                    System.out.println("Database saved and refreshed");
                    Thread.sleep(12000); // every 12 seconds
                } catch (Exception e) {
                    System.err.println("Error in background updater: " + e.getMessage());
                }
            }
        });
        backgroundUpdater.setDaemon(true);
        backgroundUpdater.start();

        launch(args);                               // initialize javafx and call the start() method
    }

    @Override
    public void start(Stage stage) throws Exception {       // main GUI setup method
        primaryStage = stage;
        setRoot("LoginPage.fxml");                  // Login Page will be shown at the starting (primaryStage will show the LoginPage.fxml as the starting scene)
//        setRoot("AdminDashboard.fxml");
//        setRoot("ViewerDashboard.fxml");
//        setRoot("ClubDashboard.fxml");

        primaryStage.setTitle("CricMart");
        primaryStage.show();
    }


    // setRoot method is used to switch scenes in the stage:
    public static void setRoot(String fxmlFile) {
        try {
            System.out.println("primaryStage: " + primaryStage);                  // added for debugging
            File newFile = new File("src/view/" + fxmlFile);    // load the FXML file from the view package(folder)
            if (newFile == null) {
                System.err.println("FXML file not found: /view/" + fxmlFile);     // added for debugging
                return;
            }
            System.out.println("Loading FXML from: " + newFile);                  // added for debugging
            FXMLLoader loader = new FXMLLoader(newFile.toURI().toURL());

            Scene scene = new Scene(loader.load());                         // load the fxml file (scene/ UI to be shown)
            sceneStack.addLast(scene);
            primaryStage.setScene(scene);                                   // set the newly loaded scene in the stage
            System.out.println("Scene loaded successfully!");            // added for debugging
        } catch (Exception e) {
            System.err.println("Failed to load FXML: " + fxmlFile);       // added for debugging
            e.printStackTrace();
        }
    }



    // go to the previous page:
    public static void goBack() {
        if(sceneStack.size() > 1) {
            sceneStack.remove(sceneStack.size() - 1);
            primaryStage.setScene(sceneStack.get(sceneStack.size() - 1));
        }
    }


    // delete the previous scene:
    public static void deletePreviousSceneFromStack() {
        sceneStack.remove(sceneStack.size() - 2);

    }


    // update the databases before exiting the program:
    @Override
    public void stop() throws Exception {
        playerDatabase.uploadInfoToFile();
        clubDatabase.uploadInfoToFile();
        UserDatabase.updateUserDatabase();

    }

    public static void updateDatabase() throws Exception {
        playerDatabase.uploadInfoToFile();
        clubDatabase.uploadInfoToFile();
        UserDatabase.updateUserDatabase();

    }


    public static void setStageTitle (String title) {
        primaryStage.setTitle(title);
    }


}
