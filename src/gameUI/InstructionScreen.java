package gameUI;

import gameUI.TitleScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


//TODO: Format the dice in instructions string
public class InstructionScreen {
    /**
     * start a screen reflecting our instructions screen
     * @param //Stage primaryStage
     * @return none
     */
    public void start(final Stage primaryStage)  {

        String dice = String.format("%-20s %s" , "Nights Watch Ranger" , "-- Jon Snow, 5% chance.\n" );
        String dice1 = String.format("%-22s %s" , "Kingsguard Knight"  ,   "-- Jaime Lannister, 4% chance.\n");
        String dice2 = String.format("%-24s %s" , "Faceless Man" , "-- Arya Stark, 3% chance.\n" );
        String dice3 = String.format("%-26s %s" , "Undead" , "-- Undead Dragon, 2% chance.\n");
        String dice4 = String.format("%-28s %s" , "Wildfire" , "-- Cersei, 1% chance.\n" );
        String dice5 = String.format("%-24s %s" , "White Walker" , "-- The Night King, .75% chance.\n" );
        String dice6 = String.format("%-26s %s" , "Drogon" , "-- Drogon, .5% chance.\n" );
        System.out.println(dice);
        System.out.println(dice1);
        System.out.println(dice2);
        System.out.println(dice3);
        System.out.println(dice4);
        System.out.println(dice5);
        System.out.println(dice6);

        final String instructions = "How To Win:\n" + "Players pick a faction to " +
                "belong to and fight the others by rolling the dice to become the ruling faction\n" +
                 "\n" +"Rules:\n" +
                "Each faction has a special rule, pick your faction wisely!\n" +
                "The game will be played with 7 dice that have 7 sides.\n" +
                 "Each side of the dice has a representation of a warrior or \n" +
                "character from Game of Thrones.  In each round, you may choose \nto reroll " +
                "any number of your dice two times unless otherwise specified by a special rule.\n" + "\nThe Dice:\n" +
                dice + dice1 +  dice2 + dice3 + dice4 + dice5 + dice6;

        final String scoring = "Scoring:\nEach turn you get pick one score to fill out on scorecard.\n" +
                "\nUpper Scoring:\n" + "Each dice representation has a chance of getting a rare of that dice.\n" +
                "When a rare is rolled an increasing multiplier is earned. The chance to get a rare is random\n" +
                "and can create havoc throughout the game. The score multiplier is increasing with a base\n "+
                "multiplier of 2 and unique to each type. So if a player gets two Arya Starks, the next Arya Stark\n" +
                "would be a multiplier of 8. Add to the total of the dice.\n" +
                "Lower Scoring:\n" + "3 of a kind: three of the same character, total of the dice\n" +
                "4 of a kind: four of the same character, total the dice\n" +
                "Full House: three of one troop, three of one troop, 50 points.\n" +
                "The North: 5 dice composed any combo of NightWatchers and Jon Snow, 30 points.\n" +
                "The South: 5 troops composing any combination of Kingsguard or Jamie Lannister, 35 points\n" +
                "Easteros: 5 troops composing any combination of Faceless Men or Arya Stark, 40 points.\n" +
                "The Dead: 5 troops composing any combination of Undead or Undead Dragon, 45 points.\n" +
                "The Crown: 5 troops composing any combination of Wildfire or Cersei, 50 points\n" +
                "The Others: 5 troops composing any combination of White Walker or the Night King, 55 points\n" +
                "Dragons: 5 troops composing any combination of Dragon or Drogon, 60 points\n" +
                "Yahtzee: 7 Troops of the same type, whether they be a special Troop or normal Troop, 100 points";

        //set title
        primaryStage.setTitle("Game Of Yahtzee Instructions");

        //Load the FXML file
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("instruction_scene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set the scene
        Scene scene = new Scene(root, 1150, 700, Color.BLACK);
        primaryStage.setScene(scene);

        //Set the imageView - Instructions Header
        ImageView imageView = (ImageView) scene.lookup("#instructionTitle");
        Image title = new Image(getClass().getClassLoader().getResourceAsStream("res/instructions_font_copy.png"));
        imageView.setEffect(new DropShadow(80, Color.WHITESMOKE));
        imageView.setImage(title);

        //Add effects to the text
        DropShadow ds = new DropShadow();
        ds.setOffsetY(2.5f);
        ds.setColor(Color.color(1.0f, 1.0f, 1.0f));

        //Set the text which is the instructions
        final Text text = (Text) scene.lookup("#text");
        text.setText(instructions);
        text.setEffect(ds);
        text.setFont(Font.font ("Verdana", 20));
        text.setFill(Color.WHITE);

        root.getStylesheets().add("title.css");
        primaryStage.show();

        //Next-Back button changes the text from instructions to scoring
        final Button nextButton = (Button) scene.lookup("#nextButton");
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (nextButton.getText().equals("Next")) {
                    //change to the scoring
                    text.setText(scoring);
                    nextButton.setText("Back");
                } else if (nextButton.getText().equals("Back")) {
                    text.setText(instructions);
                    nextButton.setText("Next");
                }
            }
        });

        //play button goes back to the title screen
        Button playButton = (Button) scene.lookup("#playButton");
        //playButton.getStyleClass().add("rich-blue");
        playButton.setText("Title Screen");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.start(primaryStage);
            }
        });
    }
}
