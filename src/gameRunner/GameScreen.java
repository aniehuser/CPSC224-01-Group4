package gameRunner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import static javafx.application.Application.STYLESHEET_MODENA;

public class GameScreen {
    private Text playerText;
    private Text hand;
    private TextField keep;
    GridPane handChoice;
    StackPane root = new StackPane();

    public static final Game game = new Game();

    public void start(Stage primaryStage, ArrayList<String> names){
        primaryStage.setTitle("Game Of Yahtzee");
        playerText = new Text(names.get(0) + "'s turn" );
        hand = new Text();
        handChoice = new GridPane();
        hand.setTranslateY(400);

        System.out.println(playerText.getText());
        playerText.setStyle("-fx-text-fill: black; -fx-font-size: 16;");
        playerText.setVisible(true);

        Button quit = new Button();
        Button title = new Button();
        quit.setText("Quit");
        title.setText("TitleScreen");
        quit.setTranslateX(200);
        title.setTranslateX(-200);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        title.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.start(primaryStage);
            }
        });


        //StackPane.setAlignment(handChoice, Pos.BOTTOM_CENTER);
        handChoice.setAlignment(Pos.TOP_CENTER);
        handChoice.setPadding(new Insets(20, 20, 20, 20));
        handChoice.setHgap(40.0);
        //StackPane.setAlignment(handChoice, Pos.BOTTOM_LEFT);
        root.setPadding(new Insets(20, 20, 30, 20));
        root.getChildren().addAll(playerText, quit, title, hand, handChoice);

        //set the scene with root, show the stage
        primaryStage.setScene(new Scene(root, 1100, 1000, Color.BLACK));
        primaryStage.show();




        game.start();
        //if problem starting game, ends program
        if(!game.isValidInstance()){
            System.out.println("Invalid Instance of Game primaryStageobject");
            System.exit(0);
        }

        ArrayList<Player> players = new ArrayList<>();
        int playerNum = 1;

        for (String string: names) {
            players.add(new Player(game.getDieSides(),game.getDieNum(),game.getRollsPerRound(), playerNum, string));
            playerNum++;
        }
        System.out.println(names);
        // loop game until total rounds played == maxRounds (18). Do not increment round until
        // every player has completed their round. I may edit the Game object to make these checks
        // easier
        while(game.isGameOver()){
            gameLoop(players);
            // after a round is over, increment the Game object's round to track game state.
            game.incrementRound();
        }

        // close globals
        game.end();

    }
    private void gameLoop(ArrayList<Player> players){
        for (Player p: players) {
            playerText.setText(p.getName() + "'s turn");
            // start a round by calling p.rollInit()
            p.rollInit();

            // get player's hand object by calling getHand()
            System.out.println(p.getHand().toString());
            Die[] playerHand = p.getDie();
            hand.setText(p.getHand().toString());
            System.out.println(p.getHand().getRolls().length);
            for (int i = 0; i < p.getHand().getRolls().length; i++) {
                handChoice.add(new ToggleButton(playerHand[i].toString()), i, 0, 1, p.getHand().getRolls().length);
            }


//            HBox hbox = new HBox(toggleButton1, toggleButton2, toggleButton3, toggleButton4);


            //check if the round is over before rolling again.
            // NOTE:: This check occurs automatically inside of rollOnce(). Method just
            // returns without performing any functionality if constraints not met.
            while(!p.isRoundOver()){
                //After rollinit(), will use rollOnce() until the round is over. rollOnce()
                // checks if the user keeps all cards and automatically sets the round to be
                // over. genKeep() generates a random boolean array which specifies which die to
                // keep. True to keep, false to reroll. indices of input directly correspond to indice
                // of die to keep
                    playerRoll(p);
            }

            // if round is over, then do end of round calculations. In actual game, this should be implemented
            // as a do while loop, until the user picks a score that they havent already chosen.
            if(p.isRoundOver()){
                // to print out players current score, call the player objects toString() method
                System.out.println(p.toString());

                for (Node button: handChoice.getChildren()) {
                    ToggleButton playerButton = (ToggleButton) button;
                    playerButton.setDisable(true);
                }

                // you can get the possible scores by calling getScorer. You can output this data using
                // the Score objects toString() method
                System.out.println(p.getScorer().toString());

                // have player indicate which score they want to keep. the keys are strings as follows:
                // {"1","2","3","4","5","6","7", "3 of a Kind", "4 of a Kind", "Full House", "The North",
                // "The South", "Easteros", "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee" }
                String keepScore = "3 of a Kind";

                // first check to make sure the player hasnt already assigned the score to their scorecard.
                // this step is important because due to our special rules, i will not be doing checks in
                // setScore(key) to make sure that an already assigned score
                if(p.isScoreSet(keepScore)){

                    // set the player's score by inputting the string key of what the user chose
                    p.setScore(keepScore);
                }

                // output updated score again using toString.
                System.out.println(p.toString());
            }
        }
    }
    private void playerRoll(Player p){
        int i = 0;
        boolean[] choice = new boolean[p.getHand().getRolls().length];
        for (Node button: handChoice.getChildren()) {
            ToggleButton playerButton = (ToggleButton) button;
            if(playerButton.isSelected()){
                choice[i] = true;
            }
            else{
                choice[i] = false;
            }
            i++;
        }
        System.out.println(choice.length);
        p.rollOnce(choice);
    }
}
