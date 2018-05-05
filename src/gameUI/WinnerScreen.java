package gameUI;

import factions.Faction;
import gameRunner.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import static gameUI.TitleScreen.game;

/**
 * Class to control ui options after a game has been played
 *
 * CPSC 224-01, Spring 2018
 * Programming Project
 *
 * @author Carl Lundin
 *
 * @version v1 5/4/18
 */
public class WinnerScreen {

    private Faction wf; //faction object for the winner in order to set the correct background
    private HBox hbox; //hbox container for the text's strings of the player's score lines
    private StackPane root; //root stackpane that contains all smaller ui elements

    /** start
     * @params Stage, players
     * */
    public void start(Stage primaryStage, ArrayList<Player> players) {
        primaryStage.setTitle("Game of Yahtzee - Game Over");
        root = new StackPane();
        hbox = new HBox();
        root.getStylesheets().add("title.css");

        //Set the background to the winner
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScorer().totalAllDice() > winner.getScorer().totalAllDice()) ;
            winner = player;
        }

        //get the winners faction and set the background based on winning faction
        wf = winner.getFaction().getFactionType();

        if (wf == Faction.STARKS) {
            root.setId("pane3");
        } else if (wf == Faction.LANNISTERS) {
            root.setId("pane4");
        } else if (wf == Faction.WHITE_WALKERS) {
            root.setId("pane6");
        } else if (wf == Faction.BARATHEON) {
            root.setId("pane9");
        } else if (wf == Faction.CHILDREN_OF_THE_FOREST) {
            root.setId("pane8");
        } else if (wf == Faction.GREYJOYS) {
            root.setId("pane7");
        } else if (wf == Faction.TARGARYEN) {
            root.setId("pane5");
        }


        //create a button to exit the game when pressed by the user
        Button quit = new Button("Quit");
        StackPane.setAlignment(quit, Pos.BOTTOM_RIGHT);
        quit.getStyleClass().add("rich-blue");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.end();
                System.exit(0);
            }
        });

        //add text's containing a player's score lines to a hbox container
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setTranslateY(40);
        hbox.setSpacing(20);

        for (Player player :players) {
            Text text = new Text(player.getName() + "\n" +player.getScorer().toString());
            if (wf == Faction.GREYJOYS) {
                text.setFill(Color.FORESTGREEN);
            } else {
                text.setFill(Color.WHITESMOKE);
            }
            text.setStyle("-fx-font-size: 16");
            text.setFont(Font.font("serif"));
            hbox.getChildren().add(text);
        }

        //create a button that will return the player to the title screen
        Button returnToTitleButton = new Button("Return to Title Screen");
        StackPane.setAlignment(returnToTitleButton, Pos.BOTTOM_LEFT);
        returnToTitleButton.getStyleClass().add("rich-blue");
        returnToTitleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.end();
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.start(primaryStage);
            }
        });

        Text winnerText = new Text("Congrats " + winner.getName() +  "! " + wf.toString() + " takes the throne!");
        if (wf == Faction.GREYJOYS || wf == Faction.CHILDREN_OF_THE_FOREST) {
            winnerText.setFill(Color.BLACK);
        } else {
            winnerText.setFill(Color.WHITESMOKE);
        }
        winnerText.setStyle("-fx-font-size: 47");
        winnerText.setFont(Font.font("serif"));
        StackPane.setAlignment(winnerText, Pos.TOP_LEFT);

        VBox vbox = new VBox();
        int i = 1;
        for (Player player :players) {
            Text text = new Text(i + ". " +player.getName() + " scored " + (player.getPlayerScoreByKey("Grand Total") + " points."));
            if (wf == Faction.GREYJOYS || wf == Faction.CHILDREN_OF_THE_FOREST) {
                text.setFill(Color.BLACK);
            } else {
                text.setFill(Color.WHITESMOKE);
            }
            text.setStyle("-fx-font-size: 30");
            text.setFont(Font.font("serif"));
            vbox.getChildren().add(text);
            i++;
        }
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setTranslateY(55);

        root.setPadding(new Insets(25, 25, 25, 25));
        root.getChildren().addAll(vbox, hbox, winnerText, returnToTitleButton, quit);
        primaryStage.setScene(new Scene(root, 1150, 700, Color.BLACK));
        primaryStage.show();
    }
 }
