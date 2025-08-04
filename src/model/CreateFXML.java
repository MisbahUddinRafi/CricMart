package model;

import application.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class CreateFXML {

    public static void createPlayerFXML(Player player) {
        try {
            String path = "src/view/PlayerCard.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            if (Main.playerDatabase == null) {
                return;
            }
//            Player player = Main.playerDatabase.getPlayer(playerName);
            String playerName = player.getName();
            if (player == null) {
                System.out.println("Player " + playerName + " not found");
                return;
            }


            // if the current user is admin, then some fields can be edited:
            boolean editable;
            if (Session.getUserType().equalsIgnoreCase("admin")) {
                editable = true;
            } else {
                editable = false;
            }

            writer.write(STR."""
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.Cursor?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Font?>

<StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.cardrelatedcontrollers.PlayerCardController">

    <!-- Background image -->
    <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
        <image>
            <Image url="@images/image2.png" />
        </image>
    </ImageView>

    <BorderPane prefHeight="600.0" prefWidth="900.0">

        <top>
            <!-- Back Button in the top left corner -->
            <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack" style="-fx-background-color: transparent;" styleClass="logout-button">
                <graphic>
                    <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                        <Image url="@images/backicon.png" />
                    </ImageView>
                </graphic>
            </Button>
        </top>
      <center>
            <AnchorPane BorderPane.alignment="CENTER">
                <children>
                    <TextField editable="false" fx:id="playerNameField" alignment="CENTER" layoutX="371.0" layoutY="281.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getName()}" />


                    <Label layoutX="23.0" layoutY="83.0" prefHeight="23.0" prefWidth="89.0" text="Country: " textFill="#aeded1" AnchorPane.leftAnchor="23.0" AnchorPane.rightAnchor="751.6666666666667">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="countryNameField" layoutX="134.0" layoutY="75.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getCountry()}">
                        <cursor>
                            <Cursor fx:constant="DEFAULT" />
                        </cursor>
                    </TextField>


                    <Label layoutX="622.0" layoutY="81.0" text="Age: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="ageField" layoutX="715.0" layoutY="75.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getAge()}" />


                    <Label layoutX="23.0" layoutY="241.0" text="Matches: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="matchesPlayedField" layoutX="132.0" layoutY="234.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getMatchesPlayed()}" />


                    <Label layoutX="23.0" layoutY="291.0" text="Balls: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="ballsFacedField" layoutX="132.0" layoutY="284.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getBallsFaced()}" />


                    <Label layoutX="23.0" layoutY="391.0" text="Fifties: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="fiftiesField" layoutX="132.0" layoutY="384.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getFifties()}" />


                    <Label layoutX="23.0" layoutY="341.0" text="Runs: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="runsScoredField" layoutX="132.0" layoutY="334.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getRunsScored()}" />


                    <Label layoutX="599.0" layoutY="388.0" text="Runs Given: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="runsConcededField" layoutX="715.0" layoutY="382.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getRunsGiven()}" />


                    <Label layoutX="25.0" layoutY="132.0" text="Position: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="false" fx:id="positionField" layoutX="134.0" layoutY="125.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getPosition()}" />


                    <Label layoutX="617.0" layoutY="132.0" text="Height: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="false" fx:id="heightField" layoutX="715.0" layoutY="127.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getHeight()}" />


                    <Label layoutX="617.0" layoutY="182.0" text="Jersey: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="jerseyNumberField" layoutX="715.0" layoutY="177.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getNumber()}" />


                    <Label layoutX="616.0" layoutY="287.0" text="Wickets: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="wicketsField" layoutX="714.0" layoutY="283.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getWicketsTaken()}" />


                    <Label layoutX="19.0" layoutY="440.0" text="Centuries: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="centuriesField" layoutX="132.0" layoutY="431.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getCenturies()}" />


                    <Label layoutX="617.0" layoutY="237.0" text="Overs: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="overField" layoutX="715.0" layoutY="233.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getOversBowled()}" />


                    <Label layoutX="617.0" layoutY="339.0" text="Hat-tricks: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="hattricksField" layoutX="714.0" layoutY="335.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getHattricks()}" />


                    <Label layoutX="603.0" layoutY="440.0" text="Base Price: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0" />
                        </font>
                    </Label>
                    <TextField editable="\{editable}" fx:id="basePriceField" layoutX="715.0" layoutY="434.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getBasePrice()}" />





                    <ImageView fitHeight="150.0" fitWidth="200.0" layoutX="371.0" layoutY="113.0" pickOnBounds="true" preserveRatio="true">
                        <image>
                            <Image url="@images/profileIcon1.png" />
                        </image>
                    </ImageView>


               <TextField editable="false" fx:id="clubNameField" alignment="CENTER" layoutX="338.0" layoutY="324.0" prefHeight="35.0" prefWidth="215.0" styleClass="display-field" text="\{player.getClub()}">
                  <cursor>
                     <Cursor fx:constant="DEFAULT" />
                  </cursor>
                  <font>
                     <Font name="System Bold" size="24.0" />
                  </font>
               </TextField>

               <Label layoutX="21.0" layoutY="182.0" text="Salary: " textFill="#aeded1">
                  <font>
                     <Font name="Arial" size="20.0" />
                  </font>
               </Label>
               <TextField editable="\{editable}" fx:id="salaryField" layoutX="134.0" layoutY="176.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{player.getSalary()}" />



            """);


            if (editable) {
                writer.write(STR."""
                        <Button fx:id="applyChangesButton" layoutX="258.0" layoutY="479.0" mnemonicParsing="false" onAction="#updatePlayerInfoByAdmin" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Apply Changes" />
                        
                        <Button fx:id="deletePlayerButton" layoutX="457.0" layoutY="479.0" mnemonicParsing="false" onAction="#onDeletePlayerByAdmin" prefHeight="41.0" prefWidth="175.0" styleClass="logout-button" text="Delete Player" />
                        
                        """);
            }


            // is the owner of the player's club is watching, then add release player option button:
            String usertype = Session.getUserType();
            if (usertype.equalsIgnoreCase("club")) {
                String username = Session.getUsername();
                Club club = Main.clubDatabase.getClubByUsername(username);

                if (club.isPresentPlayer(playerName)) {
                    writer.write(STR."""
                            <Button fx:id="releasePlayerButton" layoutX="362.0" layoutY="472.0" mnemonicParsing="false" onAction="#onReleasePlayerByClub" prefHeight="41.0" prefWidth="175.0" styleClass="logout-button" text="Release Player" />
                            
                            """);
                }
            }


            writer.write(STR."""
                                    </children>
                                </AnchorPane>
                          </center>
                        </BorderPane>
                    </StackPane>
                    """);

            System.out.println("player " + playerName + " fxml file created");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating file");
        }
    }


    // CLUB CARD FXML CREATION:
    public static void createClubFXML(Club club) {
        try {

            String path = "src/view/ClubCard.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            if (Main.playerDatabase == null) {
                return;
            }

            String clubName = club.getClubName();
            if (club == null) {
                System.out.println("Club " + clubName + " not found");
                return;
            }


            // if the current user is admin or the club owner, then some fields can be edited:
            boolean editable;
            if (Session.getUserType().equalsIgnoreCase("admin") ||
                    Session.getUserType().equalsIgnoreCase("club") &&
                            Session.getUsername().equals(club.getUsername())) {
                editable = true;
            } else {
                editable = false;
            }

            writer.write(STR."""
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.Cursor?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Font?>

<StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1"
           xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.cardrelatedcontrollers.ClubCardController">

    <!-- Background image -->
    <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
        <image>
            <Image url="@images/image2.png"/>
        </image>
    </ImageView>

    <BorderPane prefHeight="600.0" prefWidth="900.0">

        <top>
            <!-- Back Button in the top left corner -->
            <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack"
                    style="-fx-background-color: transparent;" styleClass="logout-button">
                <graphic>
                    <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                        <Image url="@images/backicon.png"/>
                    </ImageView>
                </graphic>
            </Button>
        </top>
        <left>
            <AnchorPane BorderPane.alignment="CENTER">
                <children>
                    <TextField fx:id="shortFormField" alignment="CENTER" editable="\{editable}" layoutX="370.0"
                               layoutY="250.0" prefHeight="35.0" prefWidth="150.0" styleClass="display-field"
                               text=\"\{club.getShortForm()}"/>


                    <Label layoutX="18.0" layoutY="144.0" prefHeight="23.0" prefWidth="89.0" text="Manager:"
                           textFill="#aeded1" AnchorPane.leftAnchor="18.0" AnchorPane.rightAnchor="757.3333333333334">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="managerNameField" editable="\{editable}" layoutX="111.0" layoutY="137.0"
                               prefHeight="38.0" prefWidth="175.0" styleClass="display-field" text=\"\{club.getManagerName()}">
                        <cursor>
                            <Cursor fx:constant="DEFAULT"/>
                        </cursor>
                    </TextField>


                    <Label layoutX="574.0" layoutY="144.0" text="Total Batsman:" textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="batsmanCountField" editable="false" layoutX="715.0" layoutY="137.0" prefHeight="35.0"
                               prefWidth="150.0" styleClass="display-field" text=\"\{club.getBatsmanCount()}"/>


                    <Label layoutX="20.0" layoutY="410.0" text="Players: " textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>


                    <Label layoutX="18.0" layoutY="214.0" text="Budget:" textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="budgetField" editable="\{editable}" layoutX="111.0" layoutY="207.0" prefHeight="38.0"
                               prefWidth="175.0" styleClass="display-field" text=\"\{club.getBudget()}"/>


                    <Label layoutX="574.0" layoutY="214.0" text="Total Bowler:" textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="bowlerCountField" editable="false" layoutX="715.0" layoutY="207.0" prefHeight="35.0"
                               prefWidth="150.0" styleClass="display-field" text=\"\{club.getBowlerCount()}"/>


                    <Label layoutX="555.0" layoutY="344.0" text="Total AllRounder:" textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="allrounderField" editable="false" layoutX="718.0" layoutY="337.0"
                               prefHeight="35.0" prefWidth="150.0" styleClass="display-field" text=\"\{club.getAllRounderCount()}"/>


                    <Label layoutX="533.0" layoutY="403.0" prefHeight="38.0" prefWidth="188.0"
                           text="Total WicketKeeper:" textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="wicketkeeperField" editable="false" layoutX="718.0" layoutY="404.0" prefHeight="35.0"
                               prefWidth="150.0" styleClass="display-field" text=\"\{club.getWicketKeeperCount()}"/>


                    <ImageView fitHeight="150.0" fitWidth="200.0" layoutX="370.0" layoutY="91.0" pickOnBounds="true"
                               preserveRatio="true">
                        <image>
                            <Image url="@images/profileIcon1.png"/>
                        </image>
                    </ImageView>
                    <TextField fx:id="clubNameField" alignment="CENTER" editable="\{editable}" layoutX="316.0"
                               layoutY="300.0" prefHeight="38.0" prefWidth="257.0" styleClass="display-field"
                               text=\"\{club.getClubName()}">
                        <cursor>
                            <Cursor fx:constant="DEFAULT"/>
                        </cursor>
                        <font>
                            <Font name="System Bold" size="24.0"/>
                        </font>
                    </TextField>
                    <Label layoutX="20.0" layoutY="343.0" text="Trophies:" textFill="#aeded1">
                        <font>
                            <Font name="Arial" size="20.0"/>
                        </font>
                    </Label>
                    <TextField fx:id="trophiesField" editable="\{editable}" layoutX="111.0" layoutY="337.0" prefHeight="38.0"
                               prefWidth="175.0" styleClass="display-field" text=\"\{club.getTrophiesCount()}"/>
                   \s
                   \s
                    <Button onAction="#goToAllPlayersOfClub" layoutX="111.0" layoutY="399.0" mnemonicParsing="false" prefHeight="41.0" prefWidth="175.0"
                            styleClass="sidebar-button" text="See All Players"/>

                    """);


            // add these options if the current user is admin:

            if (editable) {
                writer.write(STR."""
                        <Button fx:id="applyChangesButton" layoutX="258.0" layoutY="464.0" mnemonicParsing="false" onAction="#updateClubInfo" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Apply Changes" />
                        
                        <Button fx:id="deleteClubButton" layoutX="457.0" layoutY="464.0" mnemonicParsing="false" onAction="#onDeleteClub" prefHeight="41.0" prefWidth="175.0" styleClass="logout-button" text="Delete Club" />
                        """);
            }


            writer.write(STR."""
                    
                                    </children>
                                </AnchorPane>
                            </left>
                        </BorderPane>
                    </StackPane>
                    """);

            System.out.println("Club card created for " + club.getClubName());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating club file");
        }
    }


    public static void createCountryFXML(List<Player> countryPlayers) {
        try {
            String path = "src/view/CountryCard.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            if (Main.playerDatabase == null) {
                return;
            }


            String countryName = countryPlayers.get(0).getCountry();
            if (countryPlayers.isEmpty()) {
                System.out.println("Country " + countryName + " not found");
                return;
            }

            // sort by name: (ascending order)
            Comparator<Player> comparator = Comparator.comparing(Player::getName);
            countryPlayers.sort(comparator);

            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.ScrollPane?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css"
                               xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1"
                               fx:controller="controller.cardrelatedcontrollers.CountryCardController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Top Bar -->
                                <AnchorPane prefHeight="100.0" minHeight="100.0" maxHeight="100.0">
                                    <children>
                                        <Button fx:id="backButton" layoutX="20.0" layoutY="20.0" onAction="#goToBack"
                                                style="-fx-background-color: transparent;" styleClass="logout-button">
                                            <graphic>
                                                <ImageView fitHeight="40.0" fitWidth="40.0" pickOnBounds="true" preserveRatio="true">
                                                    <Image url="@images/backicon.png"/>
                                                </ImageView>
                                            </graphic>
                                        </Button>

                                          <Label fx:id="countryNameLabel" layoutX="300.0" layoutY="25.0"
                                                                             prefWidth="300.0" prefHeight="50.0" alignment="CENTER"
                                                                             styleClass="title-button"
                                                                             style="-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';"
                                                                             text="\{countryName}" />
                                    </children>
                                </AnchorPane>
                            </top>

                            <center>
                                <ScrollPane fitToWidth="true" prefHeight="500.0" style="-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent;">
                                    <content>
                                        <AnchorPane fx:id="playerClubListAnchorPane" minHeight="500.0" prefWidth="880.0" >
                    """);

            int startY = 10;
            int gapY = 50;
            int buttonHeight = 40;
            int playerButtonWidth = 250;
            int clubButtonWidth = 350;

            // Calculate center X position for the buttons in 880px wide anchorpane
            int totalWidth = playerButtonWidth + clubButtonWidth + 20;
            int startX = (880 - totalWidth) / 2;

            writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Player" styleClass="title-button" style="-fx-font-size: 15px; -fx-font-weight: bold;" />
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{clubButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Club" styleClass="title-button" style="-fx-font-size: 15px; -fx-font-weight: bold;" />
""");

            startY += gapY;
            int playerCount = 1;
            for (Player player : countryPlayers) {
                String playerName = player.getName();
                String clubName = player.getClub();

                writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{playerName}" styleClass="sidebar-button"  onAction="#showPlayerCard"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{clubButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{clubName}" styleClass="sidebar-button"  onAction="#showClubCard"/>
""");
                startY += gapY;
                playerCount++;
            }

            writer.write(STR."""
                                        </AnchorPane>
                                    </content>
                                </ScrollPane>
                            </center>
                    
                        </BorderPane>
                    </StackPane>
                    """);

            System.out.println("country " + countryName + " fxml file created");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating country file");
        }
    }


    public static void createClubPlayerListFXML(String clubName) {
        try {
            String path = "src/view/ClubPlayerList.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            if (Main.playerDatabase == null) {
                return;
            }
            List<Player> clubPlayers = Main.clubDatabase.getPlayersOfClub(clubName);

            if (clubPlayers.isEmpty()) {
                System.out.println("Club " + clubName + " not found");
                return;
            }

            Comparator<Player> comparator = Comparator.comparing(Player::getName);
            clubPlayers.sort(comparator);

            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.ScrollPane?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css"
                               xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1"
                               fx:controller="controller.ClubPlayerListController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Top Bar -->
                                <AnchorPane prefHeight="100.0" minHeight="100.0" maxHeight="100.0">
                                    <children>
                                        <Button fx:id="backButton" layoutX="20.0" layoutY="20.0" onAction="#goToBack"
                                                style="-fx-background-color: transparent;" styleClass="logout-button">
                                            <graphic>
                                                <ImageView fitHeight="40.0" fitWidth="40.0" pickOnBounds="true" preserveRatio="true">
                                                    <Image url="@images/backicon.png"/>
                                                </ImageView>
                                            </graphic>
                                        </Button>

                                          <Label fx:id="countryNameLabel" layoutX="300.0" layoutY="25.0"
                                                                             prefWidth="300.0" prefHeight="50.0" alignment="CENTER"
                                                                             styleClass="title-button"
                                                                             style="-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';"
                                                                             text="\{clubName}" />
                                    </children>
                                </AnchorPane>
                            </top>

                            <center>
                                <ScrollPane fitToWidth="true" prefHeight="500.0" style="-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent;">
                                    <content>
                                        <AnchorPane fx:id="playerClubListAnchorPane" minHeight="500.0" prefWidth="880.0" >
                    """);

            int startY = 10;
            int gapY = 50;
            int buttonHeight = 40;
            int playerButtonWidth = 250;
            int positionButtonWidth = 350;

            // Calculate center X position for the buttons in 880px wide anchorpane
            int totalWidth = playerButtonWidth + positionButtonWidth + 20;
            int startX = (880 - totalWidth) / 2;


            writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Player" styleClass="title-button"  style="-fx-font-size: 15px; -fx-font-weight: bold;"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{positionButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Position" styleClass="title-button" style="-fx-font-size: 15px; -fx-font-weight: bold;" />
""");
            startY += gapY;


            int playerCount = 1;
            for (Player player : clubPlayers) {
                String playerName = player.getName();
                String position = player.getPosition();

                writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{playerName}" styleClass="sidebar-button"  onAction="#showPlayerCard"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{positionButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{position}" styleClass="sidebar-button"/>
""");
                startY += gapY;
                playerCount++;
            }

            writer.write(STR."""
                                        </AnchorPane>
                                    </content>
                                </ScrollPane>
                            </center>
                    
                        </BorderPane>
                    </StackPane>
                    """);

            System.out.println("club " + clubName + " player list fxml file created");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating club player list file");
        }
    }


    public static void createPositionPlayerListFXML(List<Player> positionPlayers) {
        try {
            String path = "src/view/PositionPlayerList.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            if (Main.playerDatabase == null) {
                return;
            }

            String positionName = positionPlayers.get(0).getName();
            if (positionPlayers.isEmpty()) {
                System.out.println("Position " + positionName + " not found");
                return;
            }


            // sort the player list by name in ascending order:
            Comparator<Player> comparator = Comparator.comparing(Player::getName);
            positionPlayers.sort(comparator);

            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.ScrollPane?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css"
                               xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1"
                               fx:controller="controller.PositionPlayerListController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Top Bar -->
                                <AnchorPane prefHeight="100.0" minHeight="100.0" maxHeight="100.0">
                                    <children>
                                        <Button fx:id="backButton" layoutX="20.0" layoutY="20.0" onAction="#goToBack"
                                                style="-fx-background-color: transparent;" styleClass="logout-button">
                                            <graphic>
                                                <ImageView fitHeight="40.0" fitWidth="40.0" pickOnBounds="true" preserveRatio="true">
                                                    <Image url="@images/backicon.png"/>
                                                </ImageView>
                                            </graphic>
                                        </Button>

                                          <Label fx:id="positionNameLabel" layoutX="300.0" layoutY="25.0"
                                                                             prefWidth="300.0" prefHeight="50.0" alignment="CENTER"
                                                                             styleClass="title-button"
                                                                             style="-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';"
                                                                             text="\{positionName}" />
                                    </children>
                                </AnchorPane>
                            </top>

                            <center>
                                <ScrollPane fitToWidth="true" prefHeight="500.0" style="-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent;">
                                    <content>
                                        <AnchorPane fx:id="playerClubListAnchorPane" minHeight="500.0" prefWidth="880.0">
                    """);

            int startY = 10;
            int gapY = 50;
            int buttonHeight = 40;
            int playerButtonWidth = 250;
            int clubButtonWidth = 350;

            // Calculate center X position for the buttons in 880px wide anchorpane
            int totalWidth = playerButtonWidth + clubButtonWidth + 20;
            int startX = (880 - totalWidth) / 2;


            writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Player" styleClass="title-button"  style="-fx-font-size: 15px; -fx-font-weight: bold;"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{clubButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Club" styleClass="title-button"  style="-fx-font-size: 15px; -fx-font-weight: bold;"/>
""");

            startY += gapY;

            int playerCount = 1;
            for (Player player : positionPlayers) {
                String playerName = player.getName();
                String clubName = player.getClub();

                writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{playerName}" styleClass="sidebar-button"  onAction="#showPlayerCard"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{clubButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{clubName}" styleClass="sidebar-button"  onAction="#showClubCard"/>
""");
                startY += gapY;
                playerCount++;
            }

            writer.write(STR."""
                                        </AnchorPane>
                                    </content>
                                </ScrollPane>
                            </center>
                    
                        </BorderPane>
                    </StackPane>
                    """);

            System.out.println("position " + positionName + " fxml file created");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating position file");
        }
    }




    public static void createSalaryBasedPlayerListFXML(List<Player> salaryPlayers, int startingSalary, int endingSalary) {
        try {
            String path = "src/view/SalaryBasedPlayerList.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            if (Main.playerDatabase == null) {
                return;
            }

            if (salaryPlayers.isEmpty()) {
                System.out.println("No players between " + startingSalary + " and " + endingSalary);
                return;
            }


            // sort the player list by salary (ascending order):
            Comparator<Player> comparator = Comparator.comparingInt(Player::getSalary);
            salaryPlayers.sort(comparator);

            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.ScrollPane?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css"
                               xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1"
                               fx:controller="controller.SalaryBasedPlayerListController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Top Bar -->
                                <AnchorPane prefHeight="100.0" minHeight="100.0" maxHeight="100.0">
                                    <children>
                                        <Button fx:id="backButton" layoutX="20.0" layoutY="20.0" onAction="#goToBack"
                                                style="-fx-background-color: transparent;" styleClass="logout-button">
                                            <graphic>
                                                <ImageView fitHeight="40.0" fitWidth="40.0" pickOnBounds="true" preserveRatio="true">
                                                    <Image url="@images/backicon.png"/>
                                                </ImageView>
                                            </graphic>
                                        </Button>

                                          <Label fx:id="salaryRangeLabel" layoutX="250.0" layoutY="25.0"
                                                                             prefWidth="400.0" prefHeight="50.0" alignment="CENTER"
                                                                             styleClass="title-button"
                                                                             style="-fx-font-size: 24px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';"
                                                                             text="\{startingSalary} to \{endingSalary}" />
                                    </children>
                                </AnchorPane>
                            </top>

                            <center>
                                <ScrollPane fitToWidth="true" prefHeight="500.0" style="-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent;">
                                    <content>
                                        <AnchorPane fx:id="playerClubListAnchorPane" minHeight="500.0" prefWidth="880.0">
                    """);

            int startY = 10;
            int gapY = 50;
            int buttonHeight = 40;
            int playerButtonWidth = 200;
            int clubButtonWidth = 350;
            int salaryButtonWidth = 200;

            // Calculate center X position for the buttons in 880px wide anchorpane
            int totalWidth = playerButtonWidth + 20 + clubButtonWidth + 20 + salaryButtonWidth;
            int startX = (880 - totalWidth) / 2;


            writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Player Name" styleClass="title-button" style="-fx-font-size: 15px; -fx-font-weight: bold;" />
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{clubButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Club Name" styleClass="title-button" style="-fx-font-size: 15px; -fx-font-weight: bold;" />
                            <Button layoutX="\{startX + playerButtonWidth + 20 + clubButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{salaryButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="Salary" styleClass="title-button" style="-fx-font-size: 15px; -fx-font-weight: bold;" />
""");

            startY += gapY;

            int playerCount = 1;
            for (Player player : salaryPlayers) {
                String playerName = player.getName();
                String clubName = player.getClub();
                int salary = player.getSalary();

                writer.write(STR."""
                            <Button layoutX="\{startX}.0" layoutY="\{startY}.0" prefWidth="\{playerButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{playerName}" styleClass="sidebar-button"  onAction="#showPlayerCard"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{clubButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{clubName}" styleClass="sidebar-button"  onAction="#showClubCard"/>
                            <Button layoutX="\{startX + playerButtonWidth + 20 + clubButtonWidth + 20}.0" layoutY="\{startY}.0" prefWidth="\{salaryButtonWidth}.0" prefHeight="\{buttonHeight}.0" text="\{salary}" styleClass="sidebar-button"/>
""");
                startY += gapY;
                playerCount++;
            }

            writer.write(STR."""
                                        </AnchorPane>
                                    </content>
                                </ScrollPane>
                            </center>
                    
                        </BorderPane>
                    </StackPane>
                    """);

            System.out.println("salary range " + startingSalary + " to " + endingSalary + " fxml file created");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating position file");
        }
    }


    /************************************************************************
     * ***********************************************************************
     *               USER PROFILE RELATED FXML FILE CREATION
     * ************************************************************************
     * ***********************************************************************
     * */

    public static void createViewerProfileFXML(String username) {
        try {
            String path = "src/view/ViewerProfile.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));


            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.Cursor?>
                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.TextField?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.userprofilerelatedcontrollers.ViewerProfileController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
                            <image>
                                <Image url="@images/image2.png" />
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Back Button in the top left corner -->
                                <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack" style="-fx-background-color: transparent;" styleClass="logout-button">
                                    <graphic>
                                        <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                                            <Image url="@images/backicon.png" />
                                        </ImageView>
                                    </graphic>
                                </Button>
                            </top>


                            <center>
                                <AnchorPane BorderPane.alignment="CENTER">
                                    <children>


                                        <Label layoutX="323.0" layoutY="102.0" prefHeight="23.0" prefWidth="99.0" text="User type: " textFill="#aeded1" AnchorPane.leftAnchor="323.0" AnchorPane.rightAnchor="447.00000000000006">
                                            <font>
                                                <Font name="Arial" size="20.0" />
                                            </font>
                                        </Label>
                                        <TextField fx:id="usertypeField" alignment="CENTER" editable="false" layoutX="430.0" layoutY="95.0" prefHeight="38.0" prefWidth="175.0" styleClass="display-field" text="Viewer">
                                            <cursor>
                                                <Cursor fx:constant="DEFAULT" />
                                            </cursor>
                                            <font>
                                                <Font size="18.0" />
                                            </font>
                                        </TextField>


                                        <Label layoutX="315.0" layoutY="160.0" prefHeight="23.0" prefWidth="115.0" text="Username: " textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0" />
                                            </font>
                                        </Label>
                                        <TextField fx:id="usernameField" alignment="CENTER" editable="false" layoutX="429.0" layoutY="153.0" prefHeight="38.0" prefWidth="175.0" styleClass="display-field" text="\{username}">
                                            <font>
                                                <Font size="18.0" />
                                            </font>
                                        </TextField>


                                        <Button layoutX="465.0" layoutY="227.0" mnemonicParsing="false" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Edit username" onAction="#goToEditUsername"/>
                                        <Button layoutX="250.0" layoutY="227.0" mnemonicParsing="false" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Edit password" onAction="#goToEditPassword"/>
                                        <Button layoutX="358.0" layoutY="290.0" mnemonicParsing="false" prefHeight="41.0" prefWidth="175.0" styleClass="logout-button" text="Delete account" onAction="#onDeleteAccount"/>


                                        <Button fx:id="logoutButton" layoutX="773.0" layoutY="467.0" onAction="#handleLogOut" prefHeight="30" prefWidth="80" style="-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-weight: bold;" styleClass="logout-button" text="Logout" />
                                    </children>
                                </AnchorPane>
                            </center>
                        </BorderPane>
                    </StackPane>

                    """);

            writer.close();
            System.out.println("Created Viewer Profile FXML file for " + username);
        } catch (Exception e) {
            System.out.println("Error creating Viewer Profile FXML file for " + username);
            throw new RuntimeException(e);
        }
    }


    public static void createAdminProfileFXML(String username) {
        try {
            String path = "src/view/AdminProfile.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));


            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.Cursor?>
                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.TextField?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1"
                               xmlns:fx="http://javafx.com/fxml/1"
                               fx:controller="controller.userprofilerelatedcontrollers.AdminProfileController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Back Button in the top left corner -->
                                <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack"
                                        style="-fx-background-color: transparent;" styleClass="logout-button">
                                    <graphic>
                                        <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                                            <Image url="@images/backicon.png"/>
                                        </ImageView>
                                    </graphic>
                                </Button>
                            </top>


                            <center>
                                <AnchorPane BorderPane.alignment="CENTER">
                                    <children>


                                        <Label layoutX="322.0" layoutY="102.0" prefHeight="23.0" prefWidth="99.0" text="User type: "
                                               textFill="#aeded1" AnchorPane.leftAnchor="322.0" AnchorPane.rightAnchor="448.6666666666666">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="usertypeField" alignment="CENTER" editable="false" layoutX="429.0" layoutY="95.0"
                                                   prefHeight="38.0" prefWidth="175.0" styleClass="display-field" text="Admin">
                                            <cursor>
                                                <Cursor fx:constant="DEFAULT"/>
                                            </cursor>
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>


                                        <Label layoutX="314.0" layoutY="160.0" prefHeight="23.0" prefWidth="115.0" text="Username: "
                                               textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="usernameField" alignment="CENTER" editable="false" layoutX="428.0" layoutY="153.0"
                                                   prefHeight="38.0" prefWidth="175.0" styleClass="display-field" text="\{username}">
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>


                                        <Button layoutX="472.0" layoutY="231.0" mnemonicParsing="false" onAction="#goToEditUsername"
                                                prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Edit username"/>

                                        <Button layoutX="257.0" layoutY="231.0" mnemonicParsing="false" onAction="#goToEditPassword"
                                                prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Edit password"/>


                                        <Button fx:id="logoutButton" layoutX="773.0" layoutY="467.0" onAction="#handleLogOut"
                                                prefHeight="30" prefWidth="80"
                                                style="-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-weight: bold;"
                                                styleClass="logout-button" text="Logout"/>

                                        <Button layoutX="472.0" layoutY="285.0" mnemonicParsing="false" onAction="#goToEditClubInfo"
                                                prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Edit Club Info"/>

                                        <Button layoutX="257.0" layoutY="285.0" mnemonicParsing="false" onAction="#goToEditPlayerInfo"
                                                prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button" text="Edit Player Info"/>

                                        <Button layoutX="362.0" layoutY="347.0" mnemonicParsing="false" onAction="#onDeleteAccount"
                                                prefHeight="41.0" prefWidth="175.0" styleClass="logout-button" text="Delete account"/>


                                    </children>
                                </AnchorPane>
                            </center>
                        </BorderPane>
                    </StackPane>

                    """);

            writer.close();
            System.out.println("Created Admin Profile FXML file for " + username);
        } catch (Exception e) {
            System.out.println("Error creating Admin Profile FXML file for " + username);
            throw new RuntimeException(e);
        }
    }


    public static void createClubProfileFXML(String username) {
        try {
            String path = "src/view/ClubProfile.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            Club club = Main.clubDatabase.getClubByUsername(username);

            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.Cursor?>
                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.TextField?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1"
                               xmlns:fx="http://javafx.com/fxml/1"
                               fx:controller="controller.userprofilerelatedcontrollers.ClubProfileController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Back Button in the top left corner -->
                                <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack"
                                        style="-fx-background-color: transparent;" styleClass="logout-button">
                                    <graphic>
                                        <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                                            <Image url="@images/backicon.png"/>
                                        </ImageView>
                                    </graphic>
                                </Button>
                            </top>


                            <center>
                                <AnchorPane BorderPane.alignment="CENTER">
                                    <children>


                                        <Label layoutX="322.0" layoutY="102.0" prefHeight="23.0" prefWidth="99.0" text="User type: "
                                               textFill="#aeded1" AnchorPane.leftAnchor="322.0" AnchorPane.rightAnchor="448.6666666666666">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="usertypeField" alignment="CENTER" editable="false" layoutX="429.0" layoutY="95.0"
                                                   prefHeight="38.0" prefWidth="175.0" styleClass="display-field" text="Club">
                                            <cursor>
                                                <Cursor fx:constant="DEFAULT"/>
                                            </cursor>
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>


                                        <Label layoutX="96.0" layoutY="159.0" prefHeight="23.0" prefWidth="115.0" text="Username: "
                                               textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="usernameField" alignment="CENTER" editable="false" layoutX="210.0" layoutY="152.0"
                                                   prefHeight="39.0" prefWidth="200.0" styleClass="display-field" text="\{club.getUsername()}">
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>


                                        <Button fx:id="editUsernameButton" layoutX="472.0" layoutY="282.0" mnemonicParsing="false"
                                                onAction="#goToEditUsername" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button"
                                                text="Edit username"/>
                                        <Button fx:id="editPasswordButton" layoutX="254.0" layoutY="282.0" mnemonicParsing="false"
                                                onAction="#goToEditPassword" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button"
                                                text="Edit password"/>
                                        <Button layoutX="362.0" layoutY="398.0" mnemonicParsing="false" onAction="#onDeleteAccount"
                                                prefHeight="41.0" prefWidth="175.0" styleClass="logout-button" text="Delete account"/>


                                        <Button fx:id="logoutButton" layoutX="773.0" layoutY="467.0" onAction="#handleLogOut"
                                                prefHeight="30" prefWidth="80"
                                                style="-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-weight: bold;"
                                                styleClass="logout-button" text="Logout"/>

                                        <Button fx:id="editClubInfoButton" layoutX="472.0" layoutY="336.0" mnemonicParsing="false"
                                                onAction="#goToEditClubInfo" prefHeight="41.0" prefWidth="175.0" styleClass="sidebar-button"
                                                text="Edit Club Info"/>

                                        <Button fx:id="releasePlayerButton" layoutX="257.0" layoutY="336.0" mnemonicParsing="false"
                                                onAction="#goToReleasePlayer" prefHeight="41.0" prefWidth="175.0"
                                                styleClass="sidebar-button" text="Release Player"/>


                                        <Label layoutX="485.0" layoutY="159.0" prefHeight="23.0" prefWidth="115.0" text="Club Name:"
                                               textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="clubNameField" alignment="CENTER" editable="false" layoutX="599.0" layoutY="152.0"
                                                   prefHeight="39.0" prefWidth="250.0" styleClass="display-field" text="\{club.getClubName()}">
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>
                                        <Label layoutX="451.0" layoutY="210.0" prefHeight="23.0" prefWidth="147.0" text="Manager Name:"
                                               textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="managerNameField" alignment="CENTER" editable="false" layoutX="599.0"
                                                   layoutY="202.0" prefHeight="39.0" prefWidth="250.0" styleClass="display-field"
                                                   text="\{club.getManagerName()}">
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>
                                        <Label layoutX="96.0" layoutY="210.0" prefHeight="23.0" prefWidth="115.0" text="ShortForm:"
                                               textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                                        <TextField fx:id="shortFormField" alignment="CENTER" editable="false" layoutX="210.0"
                                                   layoutY="203.0" prefHeight="39.0" prefWidth="200.0" styleClass="display-field"
                                                   text="\{club.getShortForm()}">
                                            <font>
                                                <Font size="18.0"/>
                                            </font>
                                        </TextField>
                                    </children>
                                </AnchorPane>
                            </center>
                        </BorderPane>
                    </StackPane>

                    """);

            writer.close();
            System.out.println("Created Club Profile FXML file for " + club.getClubName());
        } catch (Exception e) {
            System.out.println("Error creating Club Profile FXML file for " + username);
            throw new RuntimeException(e);
        }
    }




    // EDIT USERNAME PAGE FXML CREATION:
    public static void createEditUsernamePageFXML(String username) {
        try {
            String path = "src/view/EditUsernamePage.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));


            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>

                    <?import javafx.scene.Cursor?>
                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.TextField?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>

                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1" xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.userprofilerelatedcontrollers.EditUsernamePageController">

                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
                            <image>
                                <Image url="@images/image2.png" />
                            </image>
                        </ImageView>

                        <BorderPane prefHeight="600.0" prefWidth="900.0">

                            <top>
                                <!-- Back Button in the top left corner -->
                                <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack" style="-fx-background-color: transparent;" styleClass="logout-button">
                                    <graphic>
                                        <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                                            <Image url="@images/backicon.png" />
                                        </ImageView>
                                    </graphic>
                                </Button>
                            </top>


                            <center>
                                <AnchorPane BorderPane.alignment="CENTER">
                                    <children>


                                        <Label layoutX="284.0" layoutY="103.0" prefHeight="23.0" prefWidth="166.0" text="Old username: " textFill="#aeded1" AnchorPane.leftAnchor="284.0" AnchorPane.rightAnchor="450.6666666666667">
                                            <font>
                                                <Font name="Arial" size="20.0" />
                                            </font>
                                        </Label>
                                        <TextField fx:id="oldUsernameField" alignment="CENTER" editable="false" layoutX="430.0" layoutY="95.0" prefHeight="39.0" prefWidth="275.0" styleClass="display-field" text="\{username}">
                                            <cursor>
                                                <Cursor fx:constant="DEFAULT" />
                                            </cursor>
                                            <font>
                                                <Font size="18.0" />
                                            </font>
                                        </TextField>


                                        <Label layoutX="273.0" layoutY="161.0" prefHeight="23.0" prefWidth="151.0" text="New Username: " textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0" />
                                            </font>
                                        </Label>
                                        <TextField fx:id="newUsernameField" alignment="CENTER" layoutX="429.0" layoutY="153.0" prefHeight="39.0" prefWidth="275.0" promptText="Enter new username" styleClass="input-field">
                                            <font>
                                                <Font size="18.0" />
                                            </font>
                                        </TextField>


                                        <Button layoutX="378.0" layoutY="246.0" mnemonicParsing="false" onAction="#onConfirmNewUsername" prefHeight="41.0" prefWidth="199.0" styleClass="sidebar-button" text="Confirm" />


                                    </children>
                                </AnchorPane>
                            </center>
                        </BorderPane>
                    </StackPane>


                    """);

            writer.close();
            System.out.println("Created Edit Username FXML file for " + username);
        } catch (Exception e) {
            System.out.println("Error creating Edit Username FXML file for " + username);
            throw new RuntimeException(e);
        }
    }


    // EDIT PASSWORD PAGE FXML CREATION:
    public static void createEditPasswordPageFXML(String username) {
        try {
            String path = "src/view/EditPasswordPage.fxml";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));


            writer.write(STR."""
                    <?xml version="1.0" encoding="UTF-8"?>
                    
                    <?import javafx.scene.control.Button?>
                    <?import javafx.scene.control.Label?>
                    <?import javafx.scene.control.PasswordField?>
                    <?import javafx.scene.image.Image?>
                    <?import javafx.scene.image.ImageView?>
                    <?import javafx.scene.layout.AnchorPane?>
                    <?import javafx.scene.layout.BorderPane?>
                    <?import javafx.scene.layout.StackPane?>
                    <?import javafx.scene.text.Font?>
                    
                    <StackPane prefHeight="600.0" prefWidth="900.0" stylesheets="@style1.css" xmlns="http://javafx.com/javafx/23.0.1"
                               xmlns:fx="http://javafx.com/fxml/1" fx:controller="controller.userprofilerelatedcontrollers.EditPasswordPageController">
                    
                        <!-- Background image -->
                        <ImageView fitHeight="600" fitWidth="900" preserveRatio="false">
                            <image>
                                <Image url="@images/image2.png"/>
                            </image>
                        </ImageView>
                    
                        <BorderPane prefHeight="600.0" prefWidth="900.0">
                    
                            <top>
                                <!-- Back Button in the top left corner -->
                                <Button fx:id="backButton" layoutX="30" layoutY="30" onAction="#goToBack"
                                        style="-fx-background-color: transparent;" styleClass="logout-button">
                                    <graphic>
                                        <ImageView fitHeight="60" fitWidth="60" pickOnBounds="true" preserveRatio="true">
                                            <Image url="@images/backicon.png"/>
                                        </ImageView>
                                    </graphic>
                                </Button>
                            </top>
                    
                    
                            <center>
                                <AnchorPane BorderPane.alignment="CENTER">
                                    <children>
                    
                    
                                        <Label layoutX="284.0" layoutY="103.0" prefHeight="23.0" prefWidth="166.0" text="Old Password:"
                                               textFill="#aeded1" AnchorPane.leftAnchor="284.0" AnchorPane.rightAnchor="450.6666666666667">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                    
                    
                                        <Label layoutX="273.0" layoutY="161.0" prefHeight="23.0" prefWidth="151.0" text="New Password: "
                                               textFill="#aeded1">
                                            <font>
                                                <Font name="Arial" size="20.0"/>
                                            </font>
                                        </Label>
                    
                    
                                        <PasswordField fx:id="newPasswordField" alignment="CENTER" layoutX="430.0" layoutY="155.0"
                                                       prefHeight="35.0" prefWidth="274.0" promptText="Enter new password"
                                                       styleClass="input-field"/>
                                        <PasswordField fx:id="oldPasswordField" alignment="CENTER" layoutX="430.0" layoutY="97.0"
                                                       prefHeight="35.0" prefWidth="274.0" promptText="Enter old password"
                                                       styleClass="input-field"/>
                    
                                        <Button layoutX="378.0" layoutY="246.0" mnemonicParsing="false" onAction="#onConfirmNewPassword"
                                                prefHeight="41.0" prefWidth="199.0" styleClass="sidebar-button" text="Confirm"/>
                    
                                    </children>
                                </AnchorPane>
                            </center>
                        </BorderPane>
                    </StackPane>
                    
                    
                    
                    """);

            writer.close();
            System.out.println("Created Edit password FXML file for " + username);
        } catch (Exception e) {
            System.out.println("Error creating Edit password FXML file for " + username);
            throw new RuntimeException(e);
        }
    }


}

