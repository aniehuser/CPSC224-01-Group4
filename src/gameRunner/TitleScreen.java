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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

        //add a Scene to stage
        primaryStage.setScene(new Scene(root, 2000, 2000, Color.BLACK));

        //create buttons
        createButtons(root, primaryStage);
        //add background
        BackgroundImage backgroundImage= new BackgroundImage(new Image("background_image.jpg",2000,2000,true,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        //add title text on scene
        Text text = new Text("Game of Yahtzee");
        text.setFont(Font.font ("Comic-sans", 64));
        text.setFill(Color.WHITE);
        text.setTranslateY(-350);

        root.getChildren().add(text);

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
                InstructionScreen.start(primaryStage);
            }
        });

        //move butttons so screen looks nice
        button1.setTranslateX(-500);
        button1.setTranslateY(200);
        button2.setTranslateX(500);
        button2.setTranslateY(200);

        //add new game button and instructions button
        root.getChildren().add(button1);
        root.getChildren().add(button2);
    }
}
