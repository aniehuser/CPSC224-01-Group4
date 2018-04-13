package gameRunner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InstructionScreen {
    /**
     * start a screen reflecting our instructions screen
     * @param //Stage primaryStage
     * @return none
     */
    public static void start(Stage primaryStage){
        // return to title screen button
        //add a blue style to it with the title screen text
        Button button1 = new Button();
        button1.getStyleClass().add("rich-blue");
        button1.setText("Title Screen");
        //set window title
        primaryStage.setTitle("Game Of Yahtzee");

        //root container
        StackPane root = new StackPane();
        root.getStylesheets().add("title.css");

        //add listener to button this runs the instructions screen
        primaryStage.setScene(new Scene(root, 2000, 2000, Color.BLACK));

        //add background
        BackgroundImage backgroundImage= new BackgroundImage(new Image("instructions.png",2000,2000,true,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        //add button to root
        root.getChildren().add(button1);

        primaryStage.show();
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.start(primaryStage);
            }
        });
    }
}
