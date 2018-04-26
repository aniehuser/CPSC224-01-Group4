package gameRunner;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FactionScreen {

    private final Button stark = new Button();
    private final Button targaryen = new Button();
    private final Button lannister = new Button();
    private final Button greyjoy = new Button();
    private final Button baratheon = new Button();
    private final Button forrester = new Button();
    private final Button whiteWalker = new Button();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Of Yahtzee - Choose Faction");

        //root container, add the background, get style sheets, set padding
        GridPane root = new GridPane();
        root.getStylesheets().add("title.css");
        root.setId("pane");
        root.setPadding(new Insets(5, 0, 25,20));
        root.setHgap(10);
        root.setVgap(20);

        //intitiale effect
        DropShadow ds = new DropShadow();
        ds.setOffsetY(5.5f);
        ds.setColor(Color.color(0.0f, 0.0f, 0.0f));

        //imageview of choose faction
        //set the games of thrones font "Choose number of players"
        ImageView imageView = new ImageView();
        Image title = new Image(getClass().getClassLoader().getResourceAsStream("res/choose_faction.png"));
        imageView.setImage(title);
        root.add(imageView, 0, 1);

        //Text area to state whose turn it is to pick their faction
        //TODO: get current player's name and add it to text
        Text text = new Text("Player: UnKNOWN's turn to pick faction");
        text.setFill(Color.GRAY);
        text.setFont(new Font(30));
        GridPane.setHalignment(text, HPos.CENTER);
        root.add(text, 1, 2);


        root.getColumnConstraints().add(new ColumnConstraints(240));


        stark.setId("stark");
        stark.setOnAction(new ButtonHandler());
        Image starkLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/stark_faction.png"));
        stark.setGraphic(new ImageView(starkLogo));
        stark.setBackground(Background.EMPTY);
        stark.setEffect(ds);
        root.add(stark, 0, 4);

        root.getColumnConstraints().add(new ColumnConstraints(240));

        targaryen.setId("targaryen");
        targaryen.setOnAction(new ButtonHandler());
        Image targaryenLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/targaryen_faction.png"));
        targaryen.setGraphic(new ImageView(targaryenLogo));
        targaryen.setBackground(Background.EMPTY);
        targaryen.setEffect(ds);
        root.add(targaryen, 1, 4);

        root.getColumnConstraints().add(new ColumnConstraints(240));

        Image lannistersLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/lannister_faction.png"));
        lannister.setId("lannister");
        lannister.setOnAction(new ButtonHandler());
        lannister.setGraphic(new ImageView(lannistersLogo));
        //lannister.setStyle("-fx-border-color: white;");
        lannister.setBackground(Background.EMPTY);
        lannister.setEffect(ds);
        root.add(lannister, 2, 4);

        Image greyjoyLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/greyjoy_faction.png"));
        greyjoy.setId("greyjoy");
        greyjoy.setOnAction(new ButtonHandler());
        greyjoy.setGraphic(new ImageView(greyjoyLogo));
        greyjoy.setBackground(Background.EMPTY);
        greyjoy.setEffect(ds);
        root.add(greyjoy, 3, 4);

        Image baratheonLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/baratheon.png"));
        baratheon.setId("baratheon");
        baratheon.setOnAction(new ButtonHandler());
        baratheon.setGraphic(new ImageView(baratheonLogo));
        baratheon.setBackground(Background.EMPTY);
        baratheon.setEffect(ds);
        root.add(baratheon, 0, 5);

        Image forresterLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/forrester_faction.png"));
        forrester.setId("forrester");
        forrester.setOnAction(new ButtonHandler());
        forrester.setGraphic(new ImageView(forresterLogo));
        forrester.setBackground(Background.EMPTY);
        forrester.setEffect(ds);
        root.add(forrester, 1, 5);

        Image whiteWalkerLogo = new Image(getClass().getClassLoader().getResourceAsStream("res/white_walker_faction.png"));
        whiteWalker.setId("whitewalker");
        whiteWalker.setOnAction(new ButtonHandler());
        whiteWalker.setGraphic(new ImageView(whiteWalkerLogo));
        whiteWalker.setBackground(Background.EMPTY);
        whiteWalker.setEffect(ds);
        root.add(whiteWalker, 2, 5);

        //set the scene with root, show the stage
        primaryStage.setScene(new Scene(root, 1100, 1000, Color.BLACK));
        primaryStage.show();

    }

    //TODO: change the which player's choice it is
    public class ButtonHandler implements EventHandler {

        DropShadow dsWhite;

        public ButtonHandler() {
            dsWhite = new DropShadow();
            dsWhite.setOffsetY(2.5f);
            dsWhite.setColor(Color.color(1f, 1f, 1f));
        }

        @Override
        public void handle(Event event) {
            Button button = (Button) event.getSource();
            String faction = button.getId();
            System.out.println(faction);

            /*

            For if one is clicked we can decide if we want the faction to stay on the screen but
            not be clickable or dissapear.
             */

            switch (faction) {
                case "stark":
                    stark.setEffect(dsWhite);
                    //stark.setVisible(false);
                    stark.setMouseTransparent(true);
                    break;
                case "targaryen":
                    targaryen.setEffect(dsWhite);
                    targaryen.setVisible(false);
                    break;
                case "lannister":
                    lannister.setEffect(dsWhite);
                    lannister.setVisible(false);
                    break;
                case "greyjoy":
                    greyjoy.setEffect(dsWhite);
                    greyjoy.setVisible(false);
                    break;
                case "baratheon":
                    baratheon.setEffect(dsWhite);
                    baratheon.setVisible(false);
                    break;
                case "forrester":
                    forrester.setEffect(dsWhite);
                    forrester.setVisible(false);
                    break;
                case "whitewalker":
                    whiteWalker.setEffect(dsWhite);
                    whiteWalker.setVisible(false);
                    break;
            }

        }
    }

}