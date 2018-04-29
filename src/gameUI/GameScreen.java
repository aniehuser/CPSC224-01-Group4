package gameUI;

import factions.Faction;
import gameRunner.Die;
import gameRunner.Game;
import gameRunner.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameScreen {

    //Display fields:
    private Text playerText; //Display's whose turn it currently is
    private Text factionBonus; //Display's the hand's current score
    private GridPane playerDiceButtons; //container for toggleButtons that allow user to select which dice they want to keep or reroll

    Button roll; //button to allow a player to roll

    private ListView<Button> scoreListView; //creates a listview for displaying the possible avenues of score
    private ObservableList<Button> scoreListButtons = //creates a button array to attach to the listview
            FXCollections.observableArrayList();

    private Player currentPlayer; //player whose turn it is
    private int currentPlayerTracker; //tracks what players have played and who is up next
    private int maxPlayers; //how many players are playing
    private ArrayList<Player> players; //array containing all players

    private StackPane root = new StackPane();
    private Stage primaryStage;
    private static final Game game = new Game(); //container for game logic

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

        //initialize game variables and logic
        game.start();
        //if problem starting game, ends program
        if (!game.isValidInstance()) {
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

        //creates our roll button which rolls the selected dice
        roll = new Button("Roll");
        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPlayer != null) {
                    playerRoll();
                }
            }
        });

        //TODO: Create CSS for all our buttons and textFields -> Nicole
        //add css file later for this stuff to clean up code
        roll.setTranslateX(350);
        playerText.setStyle("-fx-text-fill: black; -fx-font-size: 16;");
        factionBonus.setTranslateY(250);
        factionBonus.setTranslateX(-250);

        //grab the initial player's hand and instantiate our toggleButtons that control the die selection process
        Die[] playerHand = currentPlayer.getDie();
        for (int i = 0; i < game.getDieNum(); i++) {
            playerDiceButtons.add(new ToggleButton(playerHand[i].toString()), i, 0, 1, game.getDieNum());
        }

        //create our button listview
        VBox scoreVBoxContainer = new VBox();
        scoreVBoxContainer.setMaxWidth(200);
        scoreListView = new ListView<>();
        scoreListView.setItems(scoreListButtons);

        scoreVBoxContainer.getChildren().add(scoreListView);

        //create our UI
        root.getChildren().addAll(playerText, playerDiceButtons, roll, factionBonus, scoreVBoxContainer);
        primaryStage.setScene(new Scene(root, 1100, 1000, Color.BLACK));
        primaryStage.show();

        //start our game loop
        gameDisplayController();
    }

    /**
     * initailizes the views for a player
     */
    private void gameDisplayController() {
        scoreListButtons.clear();
        int i = 0;

        //display currentPlayer
        playerText.setText(currentPlayer.getName() + "'s turn");

        //TODO: Change to images instead of text - > Nicole you can do this if you'd like
        //set text on toggle buttons to reflect the current dice value
        Die[] playerHand = currentPlayer.getDie();
        for (Node node : playerDiceButtons.getChildren()) {
            ToggleButton toggleButton = (ToggleButton) node;
            toggleButton.setText(playerHand[i].toString());
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
        gameDisplayController();
        //roll the selected dice and generate new buttons to display the score
        currentPlayer.rollOnce(choice);
        generateScorecard();
    }

    /**
     * Clears buttons so users can make new choices
     * use this method in between rolls
     */
    private void resetButtons() {
        for (Node node : playerDiceButtons.getChildren()) {
            ToggleButton playerButton = (ToggleButton) node;
            if (playerButton.isSelected()) {
                playerButton.setSelected(false);
            }
        }
    }

    /**
     * adds buttons with current available lines for the player to select
     * button ids are assigned with our line labels
     */
    private void generateScorecard() {
        //score labels are the keys to access the proper lines in our score object
        String[] scoreLabels = {"1", "2", "3", "4", "5", "6", "7", "3 of a Kind", "4 of a Kind", "Full House", "The North",
                "The South", "Easteros", "The Dead", "The Crown", "The Others", "Dragons"};

        //iterate through each line and add a message to the button representing it how many points that line is worth
        //set id to be the string representing the line the user selected
        for (int i = 0; i < scoreLabels.length - 1; i++) {
            if (currentPlayer.isScoreSet((scoreLabels[i]))) {
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
        public void handle(ActionEvent event) {
            Button pressedLine = (Button) event.getSource();
            if (currentPlayer.isScoreSet(pressedLine.getId())) {
                // set the player's score by inputting the string key of what the user chose
                currentPlayer.setScore(pressedLine.getId());
                currentPlayerTracker++; //increment to next player

                //reset our toggle buttons
                resetButtons();

                //all players played a round increment game round
                if (currentPlayerTracker > maxPlayers) {
                    //start of new round
                    currentPlayerTracker = 0;
                    System.out.println("Game round: " + game.getCurrentRound());
                    System.out.println("Max game round: " + game.getMaxRounds());
                    game.incrementRound();
                    if (!game.isGameOver()) {
                        // close globals
                        game.end();
                        WinnerScreen winnerScreen = new WinnerScreen();
                        winnerScreen.start(primaryStage, players);
                    }
                }

                currentPlayer = players.get(currentPlayerTracker);
                currentPlayer.rollInit();
                generateScorecard();
                gameDisplayController();

                //TODO: delete this when done testing winner screen
//                WinnerScreen winnerScreen = new WinnerScreen();
//                winnerScreen.start(primaryStage, players);
            }
        }
    }
}
