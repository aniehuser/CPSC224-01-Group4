package gameRunner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PlayersScreen {
    /**
     * start a screen reflecting our amount of players selection screen
     * @param //Stage primaryStage
     * @return none
     */
    public void start(Stage primaryStage){
        Button player1 = new Button();
        Button player2 = new Button();
        Button player3 = new Button();
        Button player4 = new Button();
        Button titleScreen = new Button();

        player1.setTranslateX(-500);
        player1.setTranslateY(200);
        player2.setTranslateX(500);
        player2.setTranslateY(200);
        player3.setTranslateX(-500);
        player3.setTranslateY(-200);
        player4.setTranslateX(500);
        player4.setTranslateY(-200);


        //set window title
        primaryStage.setTitle("Game Of Yahtzee");

        //root container
        StackPane root = new StackPane();
        root.getStylesheets().add("title.css");

        //set button style from title.css and add text to button
        player1.getStyleClass().add("rich-blue");
        player1.setText("One player");
        player2.getStyleClass().add("rich-blue");
        player2.setText("Two players");
        player3.getStyleClass().add("rich-blue");
        player3.setText("Three players");
        player4.getStyleClass().add("rich-blue");
        player4.setText("Four players");
        titleScreen.getStyleClass().add("rich-blue");
        titleScreen.setText("Title Screen");
        //add our scene with buttons and root to stage
        primaryStage.setScene(new Scene(root, 1920, 1200,Color.BLACK));

        //add button to root
        root.getChildren().add(player1);
        root.getChildren().add(player2);
        root.getChildren().add(player3);
        root.getChildren().add(player4);
        root.getChildren().add(titleScreen);

        primaryStage.show();

        player1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
               //set one player
            }
        });
        player2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //set two players
            }
        });
        player3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //set three players
            }
        });
        player4.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //set four players
            }
        });
        titleScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TitleScreen titleScreen1 = new TitleScreen();
                titleScreen1.start(primaryStage);
            }
        });
    }
}
