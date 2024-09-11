package com.example.bgame;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

class GameBoardPane extends Pane {
        private Rectangle lane;
        private Circle[] pins = new Circle[6];
        private Circle ball;

        public GameBoardPane(){
            // Lane
            lane = new Rectangle(100, 50, 500, 100);
            lane.setFill(Color.GREEN);
            getChildren().add(lane);

            // Pins
            double[][] pinPositions = {
                    {200, 100}, // Pin 1
                    {230, 120}, {230, 80}, // Pins 2, 3
                    {260, 140}, {260, 60}, {260, 100}, // Pins 4, 5, 6
            };

            for (int i = 0; i < 6; i++) {
                pins[i] = new Circle(pinPositions[i][0], pinPositions[i][1], 10);
                pins[i].setFill(Color.WHITE);
                pins[i].setStroke(Color.BLACK);
                getChildren().add(pins[i]);
            }

            // Bowling Ball
            ball = new Circle(50, 100, 100);
            ball.setFill(Color.BLUE);
            getChildren().add(ball);
        }
    }


public class BowlingGame extends Application {
Game game;
    public void start(Stage primaryStage) {
        Game game = new Game();
        // Create the primary stage and scene
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // Create the game board pane
        GameBoardPane gameBoardPane = new GameBoardPane();
        root.setCenter(gameBoardPane);

        // Create the score label
        Label scoreLabel = new Label();
        scoreLabel.setText("Score: 0");
        scoreLabel.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 24));
        scoreLabel.setAlignment(Pos.CENTER_RIGHT);
        root.setTop(scoreLabel);

        // Create the roll button
        Button rollButton = new Button();
        rollButton.setText("Roll");
        rollButton.setFont(javafx.scene.text.Font.font("Arial", 24));
        root.setBottom(rollButton);

        // Create the "New Game" button
        Button createButton = new Button();
        createButton.setText("New Game");
        createButton.setFont(javafx.scene.text.Font.font("Arial", 24));
        createButton.setAlignment(Pos.BOTTOM_RIGHT);
        root.setRight(createButton);

        // Create the input from user
        TextField input = new TextField();
        root.setLeft(input);


        final int[] count = {0};
        final int[] roll = {1}; // Start with the first roll
        final int[] firstRoll = {0};

// Connect the create button event handler
        createButton.setOnAction(actionEvent -> {
            scoreLabel.setText("Score: 0");
            input.setText("");
            game.reset();
            count[0] = 0;
            roll[0] = 1;
            firstRoll[0] = 0;
        });

// Connect the roll button event handler
        rollButton.setOnAction(event -> {
            int value = Integer.parseInt(input.getText());

            if (count[0] < 12) {
                if (roll[0] == 1) { // First Roll
                        firstRoll[0] = value;
                        // Check to make sure next input is between 0 and value that makes it a spare (half of roll 1)
                    if (value <= 10 && value > 0) {
                        game.add(value);
                        scoreLabel.setText("Score: " + game.score());
                        if (value == 10) { // Strike
                            count[0]++;
                        } else {
                            roll[0] = 2;
                        }
                    }
                    else{
                        scoreLabel.setText("Invalid input. Please enter a number between 0 and 10!!!");
                        game.reset();
                    }
                } else if (roll[0] == 2) {
                    if(firstRoll[0] + value <= 10) { // Valid second roll
                        game.add(value);
                        scoreLabel.setText("Score: " + game.score());
                        count[0]++;
                        roll[0] = 1;
                    } else {
                        scoreLabel.setText("Invalid input. Please enter a number between 0 and " + (10 - firstRoll[0]) + "!!!");
                    }
                }
            } else {
                scoreLabel.setText("GAME OVER, No more rolls allowed!!!");
            }
        });


        // Show the stage
        primaryStage.setTitle("Bowling Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}