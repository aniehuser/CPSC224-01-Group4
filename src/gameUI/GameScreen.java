package gameUI;

import factions.Faction;
import gameRunner.Die;
import gameRunner.Game;
import gameRunner.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import static gameUI.TitleScreen.game;
/**
 * Class to attach backend game logic to a ui in order to simulate a game
 *
 * CPSC 224-01, Spring 2018
 * Programming Project
 *
 * @author Carl Lundin
 *
 * @version v1 5/4/18
 */
public class GameScreen {

    //Display fields:
    private Text playerText; //Display's whose turn it currently is
    private Text factionBonus; //Display's the hand's current score
    private GridPane playerDiceButtons; //container for toggleButtons that allow user to select which dice they want to keep or reroll

    private Button roll; //button to allow a player to roll

    private ListView<Button> scoreListView; //creates a listview for displaying the possible avenues of score
    private ObservableList<Button> scoreListButtons = //creates a button array to attach to the listview
            FXCollections.observableArrayList();

    private Player currentPlayer; //player whose turn it is
    private int currentPlayerTracker; //tracks what players have played and who is up next
    private int maxPlayers; //how many players are playing
    private ArrayList<Player> players; //array containing all players
    private int maxRolls; //max amount of rolls
    private int currentRolls; //current amount of rolls

    private StackPane root = new StackPane();
    private Stage primaryStage;

    /**
     * @params stage currently being used and an array of player names
     * initializes and starts the logic of the actual game being played
     */

    public void start(final Stage primaryStage, ArrayList<Player> players) {
        primaryStage.setTitle("Game Of Yahtzee");

        //initialize fields
        factionBonus = new Text(players.get(0).getFaction().specialInstructions());
        playerText = new Text(players.get(0).getName() + "'s turn");
        playerDiceButtons = new GridPane();

        //if problem starting game, ends program
        if (!TitleScreen.game.isValidInstance()) {
            System.exit(0);
        }
        currentPlayerTracker = 0;
        maxPlayers = players.size() - 1;

        //create our player's container
        this.players = players;
        this.primaryStage = primaryStage;

        //intialize the current player
        currentPlayer = players.get(currentPlayerTracker);
        currentPlayer.rollInit();
        maxRolls = game.getRollsPerRound()-1;
        currentRolls = 1;

        //creates our roll button which rolls the selected dice
        roll = new Button("Roll");
        roll.getStyleClass().add("rich-blue");
        StackPane.setAlignment(roll, Pos.CENTER);
        roll.setMinSize(150,75);
        roll.setMaxSize(200, 100);
        roll.setTranslateY(275);
        roll.setTranslateX(150);
        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPlayer != null) {
                    if(!currentPlayer.isRoundOver()) {
                        playerRoll();
                    }
                }
            }
        });

        //set the player's Turn and display their special feature
        playerText.setStyle("-fx-text-fill: white; -fx-font-size: 40;");
        playerText.setFill(Color.WHITESMOKE);
        factionBonus.setFill(Color.WHITESMOKE);
        playerText.setStyle("-fx-font-size: 30;");
        StackPane.setAlignment(factionBonus, Pos.CENTER_LEFT);
        StackPane.setAlignment(playerText, Pos.CENTER_LEFT);
        playerText.setTranslateY(40);
        factionBonus.setTranslateY(280);
        playerText.setTranslateY(250);

        //grab the initial player's hand and instantiate our toggleButtons that control the die selection process
        Die[] playerHand = currentPlayer.getDie();
        for (int i = 0; i < game.getDieNum(); i++) {
            playerDiceButtons.add(new ToggleButton(playerHand[i].toString()), i, 0, 1, game.getDieNum());
        }

        //Create a Scorecard title so they know what scoreListView is for
        ImageView imageView = new ImageView();
        Image title = new Image(getClass().getClassLoader().getResourceAsStream("res/scorecard_faction.png"));
        imageView.setImage(title);
        StackPane.setAlignment(imageView, Pos.TOP_RIGHT);
        imageView.setTranslateY(170);

        //create our button listview
        VBox scoreVBoxContainer = new VBox();
        scoreVBoxContainer.setMaxWidth(200);
        scoreListView = new ListView<>();
        scoreListView.setItems(scoreListButtons);
        scoreVBoxContainer.setTranslateY(195);
        scoreVBoxContainer.getChildren().add(scoreListView);
        StackPane.setAlignment(scoreVBoxContainer, Pos.TOP_RIGHT);
        StackPane.setAlignment(playerDiceButtons, Pos.TOP_CENTER);
        playerDiceButtons.setHgap(10);
        playerDiceButtons.setPadding(new Insets(0, 0, 0, 10));

        //create our UI
        root.setPadding(new Insets(25, 25, 50, 25));
        root.getChildren().addAll(playerText, playerDiceButtons, roll, factionBonus, scoreVBoxContainer, imageView);
        root.getStylesheets().add("title.css");
        root.setId("pane2");

        //start our game loop
        generateScorecard();
        gameDisplayController();

        primaryStage.setScene(new Scene(root, 1150, 700, Color.BLACK));
        primaryStage.show();


    }

    /**
     * initailizes the views for a player
     */
    private void gameDisplayController() {
        //clear the controller for our listView of scoreline buttons
        scoreListButtons.clear();
        //set the faction bonus instructions to the current faction
        factionBonus.setText(currentPlayer.getFaction().specialInstructions());
        int i = 0;

        //display currentPlayer
        playerText.setText(currentPlayer.getName() + "'s turn");

        //set text on toggle buttons to reflect the current dice value
        Die[] playerHand = currentPlayer.getDie();
        for (Node node : playerDiceButtons.getChildren()) {
            ToggleButton toggleButton = (ToggleButton) node;

            //Initialize toggle button to match the specs of css file
            toggleButton.setText(null);
            toggleButton.setBackground(Background.EMPTY);
            toggleButton.getStyleClass().clear();
            toggleButton.setText(null);
            playerDiceButtons.setHgap(50);


            //Get the Type of each die roll and set it to that image
            switch (playerHand[i].getType()) {
                case 1: //Nightswatch Ranger -- Jon Snow
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button1s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button1");
                    }
                    break;
                case 2: //Knightsguard Knight -- Jamie Lannister
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button2s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button2");
                    }
                    break;
                case 3:  //Faceless men -- Arya Stark
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button3s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button3");
                    }
                    break;
                case 4:  //Undead -- Undead Dragon
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button4s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button4");
                    }
                    break;
                case 5: //Wildfire -- Cersei
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button5s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button5");
                    }
                    break;
                case 6: //White Walker -- the Night King
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button6s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button6");
                    }
                    break;
                case 7:  //Dragon -- Drogon
                    if (playerHand[i].isSpecial()) {
                        toggleButton.getStyleClass().add("toggle-button7s");
                    } else {
                        toggleButton.getStyleClass().add("toggle-button7");
                    }
                    break;
            }
            i++;
        }
    }


    /**
     * Simulates a player rolling their hand
     */
    private void playerRoll() {
        //tracks the die we are currently at
        int i = 0;

        //checks to see what toggelbuttons are selected
        //selected togglebuttons are die we do not want to keep so that indice is set to false
        boolean[] choice = new boolean[game.getDieNum()];
        for (Node node : playerDiceButtons.getChildren()) {
            ToggleButton playerButton = (ToggleButton) node;
            if (playerButton.isSelected()) {
                choice[i] = false;
            } else {
                choice[i] = true;
            }
            i++;
        }

        //clears the toggles on our toggle buttons
        resetButtons();
        //roll the selected dice and generate new buttons to display the score
        currentPlayer.rollOnce(choice);
        gameDisplayController();
        generateScorecard();
    }

    /**
     * Clears buttons so users can make new choices
     * use this method in between rolls
     */
    private void resetButtons() {
        for (Node node : playerDiceButtons.getChildren()) {
            ToggleButton playerButton = (ToggleButton) node;
            //if a button is selected set it to be unselected
            if (playerButton.isSelected()) {
                playerButton.setSelected(false);
            }

            //clear the current styling of the buttons
            playerButton.getStyleClass().clear();
            playerButton.setText(null);
        }
    }

    /**
     * adds buttons with current available lines for the player to select
     * button ids are assigned with our line labels
     */
    private void generateScorecard() {
        //score labels are the keys to access the proper lines in our score object
        String[] scoreLabels = {"1", "2", "3", "4", "5", "6", "7", "3 of a Kind", "4 of a Kind", "Full House", "The North",
                "The South", "Easteros", "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee"};

        //iterate through each line and add a message to the button representing it how many points that line is worth
        //set id to be the string representing the line the user selected
        for (int i = 0; i < scoreLabels.length; i++) {
            if (!currentPlayer.isScoreSet((scoreLabels[i]))) {
                Button button = new Button(currentPlayer.getScorer().generateScoreMessage(scoreLabels[i]));
                button.setOnAction(new ScoreActionListener());
                button.setId(scoreLabels[i]);
                scoreListButtons.add(button);
            }
        }
    }

    /**
     * custom listener to handle our scoreline events
     */
    private class ScoreActionListener implements EventHandler<ActionEvent> {
        /**
         * handle
         * @params  event
         *
         * */
        public void handle(ActionEvent event) {
            //grab the button that was pressed from the event object
            Button pressedLine = (Button) event.getSource();

            //make sure the player's turn is over
            if(!currentPlayer.isPlayerTurnsOver()) {
                boolean[] choice = new boolean[game.getDieNum()];
                for (int i = 0; i < choice.length - 1; i++) {
                    choice[i] = true;
                }
                currentPlayer.rollOnce(choice);
            }

            //check to see if the score has already been set
            if (!currentPlayer.isScoreSet(pressedLine.getId())) {
                // set the player's score by inputting the string key of what the user chose
                currentPlayer.setScore(pressedLine.getId());
                currentPlayerTracker++; //increment to next player

                //all players played a round increment game round
                if (currentPlayerTracker > maxPlayers) {
                    //start of new round
                    currentPlayerTracker = 0;
                    game.incrementRound();
                    if (!game.isGameOver()) {
                        WinnerScreen winnerScreen = new WinnerScreen();
                        winnerScreen.start(primaryStage, players);
                    }
                }

                //reset the toggle buttons
                resetButtons();
                currentPlayer = players.get(currentPlayerTracker);
                currentPlayer.rollInit();
            }

            //create the new player's ui
            generateScorecard();
            gameDisplayController();
        }
    }
}
