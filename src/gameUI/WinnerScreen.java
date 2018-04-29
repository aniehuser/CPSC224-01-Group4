package gameUI;

import factions.BaseFaction;
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
import java.util.Stack;

public class WinnerScreen {

    Faction wf;
    HBox hbox;
    StackPane root;

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
        System.out.println("Winning Faction:" + wf);
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

        Button quit = new Button("Quit");
        StackPane.setAlignment(quit, Pos.BOTTOM_RIGHT);
        quit.getStyleClass().add("rich-blue");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        Button viewScores = new Button("View Scores");
        StackPane.setAlignment(viewScores, Pos.BOTTOM_RIGHT);
        viewScores.setTranslateX(-120);
        viewScores.getStyleClass().add("rich-blue");
        viewScores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayScores(root, players);
            }
        });

        Button titleScreenButton = new Button("Return to Title Screen");
        StackPane.setAlignment(titleScreenButton, Pos.BOTTOM_LEFT);
        titleScreenButton.getStyleClass().add("rich-blue");
        titleScreenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
            //Text text = new Text(player.getScorer().toString());
            Text text = new Text(i + ". " +player.getName() + " scored " + (Integer.toString(player.getScorer().totalAllDice())) + " points.");
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

        //StackPane.setAlignment(vbox, Pos.CENTER_LEFT);

        root.setPadding(new Insets(25, 25, 25, 25));
        root.getChildren().addAll(titleScreenButton, quit, vbox, winnerText, viewScores);
        primaryStage.setScene(new Scene(root, 1150, 700, Color.BLACK));
        primaryStage.show();
    }

    private void displayScores(StackPane root, ArrayList<Player> players) {
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
        root.getChildren().add(hbox);
    }

 }
