/**
 * Class to represent the title screen of Game of Yahtzee
 *
 * CPSC 224-01, Spring 2018
 * Project
 *
 * @author Carl Lundin
 *
 * @version v1.0
 */
package gameRunner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class TitleScreen extends Application {
    /**
     * Main function to run the title class
     * @param  args
     * @return none
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * on start run this method
     * @param //Stage primaryStage
     * @return none
     */
    @Override
    public void start(Stage primaryStage) {
        //set window title
        primaryStage.setTitle("Game Of Yahtzee");

        //root container
        StackPane root = new StackPane();
        root.getStylesheets().add("title.css");


        //add the background
        root.setId("pane");
        //root.setId("pane");
        //root.getStylesheets().add("title.css");

        //add the game of thrones text
        ImageView imageView = new ImageView();
        Image title = new Image(getClass().getClassLoader().getResourceAsStream("./res/game_of_yahtzee_gray.png"));
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);
        root.setPadding(new Insets(150, 0, 0,0));
        imageView.setEffect(new DropShadow(100, Color.WHITESMOKE));
        imageView.setImage(title);
        root.getChildren().addAll(imageView);

        //win or die imageView
        ImageView winOrDieImage = new ImageView();
        Image winOrDie = new Image(getClass().getClassLoader().getResourceAsStream("res/win_or_die_black_red.png"));
        winOrDieImage.setImage(winOrDie);
        StackPane.setAlignment(winOrDieImage, Pos.BOTTOM_CENTER);
        root.getChildren().addAll(winOrDieImage);

        //add a Scene to stage
        primaryStage.setScene(new Scene(root, 1100, 1000, Color.BLACK));

        //create buttons
        createButtons(root, primaryStage);

        //show Stage
        primaryStage.show();
    }
    /**
     * Creates buttons and adds them to the title screen
     * @param //stackpane root container for main layout
     * @return none
     */
    private void createButtons(StackPane root, Stage primaryStage){
        //Create button 1 for launching the game
        Button button1 = new Button();
        //set button style from title.css and add text to button
        button1.getStyleClass().add("rich-blue");
        button1.setText("Play Game");
        //add listener to button this opens the game window
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                //System.out.println("Hi");

                PlayersScreen playersScreen = new PlayersScreen();
                playersScreen.start(primaryStage);
            }
        });

        //create button2 that stores the instructions for the game
        Button button2 = new Button();
        //set button style from title.css and add text to button
        button2.getStyleClass().add("rich-blue");
        button2.setText("Instructions");
        //add listener to button this runs the instructions screen
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InstructionScreen instructionScreen = new InstructionScreen();
                //try {
                    instructionScreen.start(primaryStage);
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}
            }
        });

        //move butttons so screen looks nice
        button1.setTranslateX(-300);
        button1.setTranslateY(50);
        button2.setTranslateX(300);
        button2.setTranslateY(50);

        //add new game button and instructions button
        root.getChildren().add(button1);
        root.getChildren().add(button2);
    }
}
