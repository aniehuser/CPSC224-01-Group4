package gameUI;

import factions.Faction;
import gameRunner.Die;
import gameRunner.Game;
import gameRunner.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
    private Text currentHandScore; //Display's the hand's current score
    private GridPane playerDiceButtons; //container for toggleButtons that allow user to select which dice they want to keep or reroll

    Button roll;
    Button keepHand;

    private ListView<Button> scoreListView; //creates a listview for displaying the possible avenues of score
    private ObservableList<Button> scoreListButtons = //creates a button array to attach to the listview
            FXCollections.observableArrayList();

    private Player currentPlayer; //player whose turn it is
    private int currentPlayerTracker; //tracks what players have played and who is up next
    private int maxPlayers; //how many players are playing
    private ArrayList<Player> players; //array containing all players

    private StackPane root = new StackPane();
    private static final Game game = new Game(); //container for game logic

    /**
     * @params stage currently being used and an array of player names
     * initializes and starts the logic of the actual game being played
     */

    public void start(final Stage primaryStage, ArrayList<Player> players) {
        primaryStage.setTitle("Game Of Yahtzee");

        //initialize fields
        currentHandScore = new Text();
        playerText = new Text(players.get(0).getName() + "'s turn");
        playerDiceButtons = new GridPane();

        //initialize player variables and logic
        game.start();
        //if problem starting game, ends program
        if (!game.isValidInstance()) {
            System.exit(0);
        }
        currentPlayerTracker = 0;
        maxPlayers = players.size() - 1;

        //create our player's container
        this.players = players;
        //intialize the current player
        currentPlayer = players.get(currentPlayerTracker);
        currentPlayer.rollInit();

        //TODO: same problem as titlescreen
        //creates our quit button which exits the game
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        //TODO: Titlescreen appears to lose focus and is not clickable -> Carl need to fix this behavior
        //creates our titlescreen button which returns to the title screen
        Button titleScreen = new Button("Title Screen");
//        System.out.println(titleScreen.isFocused());
//        titleScreen.setFocusTraversable(false);
//        titleScreen.requestFocus();
        titleScreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TitleScreen titleScreen = new TitleScreen();
                titleScreen.start(primaryStage);
            }
        });

        //creates our roll button which rolls the selected dice
        roll = new Button("Roll");
        roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPlayer != null) {
                    playerRoll();
                    if (currentPlayer.isRoundOver()) {
                        turnOver();
                    }
                }
            }
        });

        //create our keephand button which keeps the whole hand
        keepHand = new Button("Keep");
        keepHand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentPlayer != null) {
                    boolean[] choice = new boolean[game.getDieNum()];
                    for (int i = 0; i < choice.length; i++) {
                        choice[i] = true;
                    }
                    currentPlayer.rollOnce(choice);
//                    roll.setDisable(true);
//                    keepHand.setDisable(true);
                    if (currentPlayer.isRoundOver()) {
                        turnOver();
                    }
                }
            }
        });

        //TODO: DELETE THIS -> Carl
        //debugging stuff
        System.out.println(players);
        System.out.println(players.size());
        System.out.println(playerText.getText());

        //TODO: Create CSS for all our buttons and textFields -> Nicole
        //add css file later for this stuff to clean up code
        quit.setTranslateX(500);
        titleScreen.setTranslateX(-500);
        roll.setTranslateX(350);
        keepHand.setTranslateX(-350);
        playerText.setStyle("-fx-text-fill: black; -fx-font-size: 16;");

        //grab the initial player's hand and instantiate our toggleButtons that control the die selection process
        Die[] playerHand = currentPlayer.getDie();
        for (int i = 0; i < game.getDieNum(); i++) {
            System.out.println("TOGGLE BUTTONS:" + i);
            playerDiceButtons.add(new ToggleButton(playerHand[i].toString()), i, 0, 1, game.getDieNum());
        }

        //create our button listview
        VBox scoreVBoxContainer = new VBox();
        scoreVBoxContainer.setMaxWidth(200);
        scoreListView = new ListView<>();
        scoreListView.setItems(scoreListButtons);

        scoreVBoxContainer.getChildren().add(scoreListView);
        StackPane.setAlignment(scoreVBoxContainer, Pos.TOP_RIGHT);

        StackPane.setAlignment(playerDiceButtons, Pos.TOP_LEFT);

        //create our UI
        root.getChildren().addAll(playerText, quit, titleScreen, playerDiceButtons, roll, keepHand, currentHandScore, scoreVBoxContainer);
        primaryStage.setScene(new Scene(root, 800, 700, Color.BLACK));
        primaryStage.show();

        //start our game loop
        gameDisplayController();
    }

    /**
     * initailizes the views for a player
     */
    private void gameDisplayController() {
        scoreListButtons.clear();
        resetButtons();
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
        currentPlayer.rollOnce(choice);
        resetButtons();
        generateScorecard();
        if (currentPlayer.isRoundOver()) {
            turnOver();
        }
    }

    /**
     * when a player's turn is over the preceding logic is handled in this method
     * there are multiple avenues of logic that could occur here see comments
     */
    private void turnOver() {
        //TODO: haven't started work on this method avoid coding in this -> Carl
        // to print out players current score, call the player objects toString() method
        System.out.println(currentPlayer.toString());
        currentHandScore.setText(currentPlayer.getScorer().toString());
        // first check to make sure the player hasnt already assigned the score to their scorecard.
        // this step is important because due to our special rules, i will not be doing checks in
//         setScore(key) to make sure that an already assigned score
//                if(p.isScoreSet(keepScore)){
//                    // set the player's score by inputting the string key of what the user chose
//                    p.setScore(keepScore);
//                }
        generateScorecard();
        System.out.println("Game round max :" + game.getMaxRounds());
        System.out.println("Game round current:" + game.getCurrentRound());
    }

    /**
     * Clears buttons so users can make new choices
     * use this method in between rolls
     */
    private void resetButtons() {
//        roll.setDisable(false);
//        keepHand.setDisable(false);
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
        String[] scoreLabels = {"1", "2", "3", "4", "5", "6", "7", "Upper", "3 of a Kind", "4 of a Kind", "Full House", "The North",
                "The South", "Easteros", "The Dead", "The Crown", "The Others", "Dragons", "Yahtzee", "Lower", "Grand"};
        for (int i = 0; i < scoreLabels.length - 1; i++) {
            if (currentPlayer.isScoreSet(currentPlayer.getScorer().generateScoreMessage(scoreLabels[i]))) {
                Button button = new Button(currentPlayer.getScorer().generateScoreMessage(scoreLabels[i]));
                button.setOnAction(new ScoreActionListener());
                button.setId(currentPlayer.getScorer().generateScoreMessage(scoreLabels[i]));
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
            System.out.println(currentPlayer.isScoreSet(pressedLine.getId()));

            if (currentPlayer.isScoreSet(pressedLine.getId())) {

                // set the player's score by inputting the string key of what the user chose
                currentPlayer.setScore(pressedLine.getId());
                System.out.println("is round over in listener: " + currentPlayer.isRoundOver());
                currentPlayerTracker++;
                if (currentPlayerTracker > maxPlayers) {
                    //start of new round
                    currentPlayerTracker = 0;
                    game.incrementRound();
                    if (!game.isGameOver()) {
                        //TODO: move to victory screen instead of ending game -> Carl
                        // close globals
                        game.end();
                        System.exit(0);
                    }
                }
                currentPlayer = players.get(currentPlayerTracker);
                currentPlayer.rollInit();
                gameDisplayController();

            }
        }
    }

}
