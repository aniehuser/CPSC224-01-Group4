package gameUI;

import gameRunner.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class WinnerScreen {


    public void start(Stage primaryStage, ArrayList<Player> players) {
        primaryStage.setTitle("Game of Yahtzee - Game Over");
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        Button titleScreenButton = new Button("Return to TitleScreen");
        titleScreenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.start(primaryStage);
            }
        });

        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScorer().totalAllDice() > winner.getScorer().totalAllDice()) ;
            winner = player;
        }

        Text winnerText = new Text("The winner is " + winner.getName() + " with a score of " + winner.getScorer().getScore("Grand"));
        VBox vbox = new VBox();
        for (Player player :players) {
            vbox.getChildren().add(new Text(player.getScorer().toString()));
        }

        StackPane root = new StackPane();
        root.getChildren().addAll(titleScreenButton, quit, vbox, winnerText);
        primaryStage.setScene(new Scene(root, 1100, 1000, Color.BLACK));

    }
}
