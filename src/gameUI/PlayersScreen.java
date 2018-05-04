package gameUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.ArrayList;

/**
 * Initializes the screen in which they enter the number of players
 * and enter the names of the players
 * @return nothing
 */
public class PlayersScreen {

    private ImageView imageView = new ImageView();
    private HBox hbox = new HBox(100.0);
    private Button btn1 = new Button("1");
    private Button btn2 = new Button("2");
    private Button btn3 = new Button("3");
    private Button btn4 = new Button("4");
    private StackPane root = new StackPane();
    private DropShadow ds = new DropShadow();

    /**
     * Initializes the screen in which they enter the number of players
     * and enter the names of the players
     * @return nothing
     * @param primaryStage the stage where we set our scene and pane
     */
    public void start(Stage primaryStage){
        primaryStage.setTitle("Game Of Yahtzee - Choose Players");

        //root container, add the background, get style sheets, set padding
        root.getStylesheets().add("title.css");
        root.setId("pane");
        root.setPadding(new Insets(150, 0, 25,0));

        //set the games of thrones font "Choose number of players"
        Image title = new Image(getClass().getClassLoader().getResourceAsStream("res/choose_players_smaller.png"));
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);
        imageView.setImage(title);

        //set properties of the drop shadow effect
        ds.setOffsetY(4.5f);
        ds.setColor(Color.color(0.0f, 0.0f, 0.0f));

        //create hbox for the children buttons
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn1.setMinHeight(50.0);
        btn1.setEffect(ds);
        HBox.setHgrow(btn1, Priority.ALWAYS);

        btn2.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btn2, Priority.ALWAYS);
        btn2.setEffect(ds);
        btn2.setMinHeight(50.0);

        btn3.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btn3, Priority.ALWAYS);
        btn3.setEffect(ds);
        btn3.setMinHeight(50.0);

        btn4.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btn4, Priority.ALWAYS);
        btn4.setEffect(ds);
        btn4.setMinHeight(50.0);

        hbox.getChildren().addAll(btn1, btn2, btn3, btn4);
        StackPane.setAlignment(hbox, Pos.BOTTOM_CENTER);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20, 20, 20,20));
        root.getChildren().addAll(hbox, imageView);

        //set the scene with root, show the stage
        primaryStage.setScene(new Scene(root, 1150, 700, Color.BLACK));
        primaryStage.show();

        //Button Listeners for the number of players buttons
        btn1.setOnAction(new ButtonHandler(1, primaryStage));
        btn2.setOnAction(new  ButtonHandler(2, primaryStage));
        btn3.setOnAction(new ButtonHandler(3, primaryStage));
        btn4.setOnAction(new ButtonHandler(4, primaryStage));
    }

    /*
     inner class for Button Handling, this class changes the instructions
     to say "Enter Names" and has textFields for each name required.
     When they are done entering names they can press the done button.
     */
    private class ButtonHandler implements EventHandler<ActionEvent> {

        private int numOfNames;
        private Button enterButton;
        private Stage primaryStage;

        public ButtonHandler(int numOfNames, Stage primaryStage) {
            this.numOfNames = numOfNames;
            enterButton = new Button("Enter");
            this.primaryStage = primaryStage;
        }

        /**
         * Handles the action of a button that was pressed, this
         * changes the pane to enter the names of each play
         * @return nothing
         * @param event, the event the button press was located
         */
        @Override
        public void handle(ActionEvent event) {

            //Change the Title to "Enter Names"
            Image name = new Image(getClass().getClassLoader().getResourceAsStream("res/names_promt.png"));
            imageView.setImage(name);

            //replace the number of players buttons with text fields for names
            //Create an ArrayList of TextFields, and loop and add to hbox
            hbox.getChildren().removeAll(btn1, btn2, btn3, btn4);
            final ArrayList<TextField> textFields = new ArrayList<TextField>(numOfNames);
            for (int i = 0; i < numOfNames; i++) {
                textFields.add(new TextField());
                textFields.get(i).setPromptText("Enter name " + (i+1));
                textFields.get(i).setFocusTraversable(false);
                textFields.get(i).setEffect(ds);
            }
            hbox.getChildren().addAll(textFields);

            //Add the done button
            StackPane.setAlignment(enterButton, Pos.BOTTOM_CENTER);
            enterButton.setText("Enter");
            enterButton.setMaxWidth(150.00);
            enterButton.setMinHeight(50.0);
            enterButton.setEffect(ds);


            //Action listener for the enter button, this takes you
            //to the screen where players will choose their faction
            enterButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ArrayList<String> names = new ArrayList<>();
                    for (TextField text: textFields) {
                        names.add(text.getText());
                    }
                    FactionScreen factionScreen = new FactionScreen();
                    factionScreen.start(primaryStage,names);
                }
            });
            root.getChildren().add(enterButton);
        }
    }
}
