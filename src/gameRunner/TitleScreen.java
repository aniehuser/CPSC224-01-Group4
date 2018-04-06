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
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Of Yahtzee");

        //root container
        StackPane root = new StackPane();
        root.getStylesheets().add("title.css");

        primaryStage.setScene(new Scene(root, 1920, 1200));

        //create buttons
        createButtons(root);
        //add background
        BackgroundImage backgroundImage= new BackgroundImage(new Image("background_image.jpg",1920,1200,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        root.setBackground(background);

        //add text
        Text text = new Text("Game of Yahtzee");
        text.setFont(Font.font ("Comic-sans", 64));
        text.setFill(Color.WHITE);
        text.setTranslateY(-250);

        root.getChildren().add(text);

        primaryStage.show();
    }
    private void createButtons(StackPane root){
        Button button1 = new Button();
        button1.getStyleClass().add("rich-blue");
        button1.setText("Play Game");
        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hi");
            }
        });
        Button button2 = new Button();
        button2.setText("Instructions");
        button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Tony");
            }
        });


        button2.getStyleClass().add("rich-blue");

        button1.setTranslateX(-500);
        button1.setTranslateY(200);
        button2.setTranslateX(500);
        button2.setTranslateY(200);

        //add new game button and instructions button
        root.getChildren().add(button1);
        root.getChildren().add(button2);
    }
}
