import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardGameApp extends Application {

    // =========================================================
    // PART 1 VARIABLES
    // =========================================================

    private final List<Card> displayDeck = new ArrayList<>();
    private final GridPane deckGrid = new GridPane();

    // =========================================================
    // PART 4 VARIABLES
    // =========================================================

    private final List<Card> gameDeck = new ArrayList<>();

    private int playerScore = 0;
    private int computerScore = 0;

    private final Label playerScoreLabel = new Label("Player: 0");
    private final Label computerScoreLabel = new Label("Computer: 0");

    private final Label gameMessage =
            new Label("Press DRAW CARD to begin!");

    private final StackPane playerCardArea = new StackPane();
    private final StackPane computerCardArea = new StackPane();

    private final Button drawButton = new Button("DRAW CARD");
    private final Label cardsRemainingLabel =
            new Label("Cards Remaining: 52");


    // =========================================================
    // START APPLICATION
    // =========================================================

    @Override
    public void start(Stage stage) {

        // Create the Part 1 deck.
        displayDeck.addAll(createDeck());

        // Create the Part 4 game.
        startNewGame();

        TabPane tabPane = new TabPane();

        // Part 1 Tab
        Tab part1Tab = new Tab(
                "Part 1 - Card Display",
                createPart1View()
        );

        // Part 4 Tab
        Tab part4Tab = new Tab(
                "Part 4 - High Card Game",
                createPart4View()
        );

        // Prevent tabs from being closed.
        part1Tab.setClosable(false);
        part4Tab.setClosable(false);

        tabPane.getTabs().addAll(
                part1Tab,
                part4Tab
        );

        Scene scene = new Scene(tabPane, 1200, 800);

        stage.setTitle(
                "CS 370 Lab 5 - JavaFX Card Application"
        );

        stage.setScene(scene);
        stage.show();
    }


    // =========================================================
    // PART 1
    // DISPLAY ALL 52 CARDS
    // =========================================================

    private VBox createPart1View() {

        Label title = new Label(
                "Part 1 - Standard 52 Card Deck"
        );

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        26
                )
        );

        Label instructions = new Label(
                "All 52 cards are displayed below. " +
                "Press Shuffle Deck to randomize their order."
        );

        Button shuffleButton =
                new Button("Shuffle Deck");

        shuffleButton.setPrefWidth(180);
        shuffleButton.setPrefHeight(40);

        // Shuffle Part 1 deck.
        shuffleButton.setOnAction(event -> {

            Collections.shuffle(displayDeck);

            displayAllCards();
        });

        deckGrid.setHgap(8);
        deckGrid.setVgap(8);

        deckGrid.setAlignment(Pos.CENTER);

        // Display cards initially.
        displayAllCards();

        ScrollPane scrollPane =
                new ScrollPane(deckGrid);

        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        VBox root = new VBox(
                15,
                title,
                instructions,
                shuffleButton,
                scrollPane
        );

        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        VBox.setVgrow(
                scrollPane,
                Priority.ALWAYS
        );

        return root;
    }


    // =========================================================
    // DISPLAY ALL CARDS
    // =========================================================

    private void displayAllCards() {

        // Remove previously displayed cards.
        deckGrid.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Card card : displayDeck) {

            VBox cardView =
                    createCardView(
                            card,
                            75,
                            105
                    );

            deckGrid.add(
                    cardView,
                    column,
                    row
            );

            column++;

            // 13 cards per row.
            if (column == 13) {

                column = 0;
                row++;
            }
        }
    }


    // =========================================================
    // PART 4
    // HIGH CARD GAME
    // =========================================================

    private VBox createPart4View() {

        Label title =
                new Label("HIGH CARD BATTLE");

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        30
                )
        );


        // -----------------------------------------------------
        // SCOREBOARD
        // -----------------------------------------------------

        playerScoreLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        20
                )
        );

        computerScoreLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        20
                )
        );

        HBox scoreboard =
                new HBox(
                        100,
                        playerScoreLabel,
                        computerScoreLabel
                );

        scoreboard.setAlignment(Pos.CENTER);


        // -----------------------------------------------------
        // CARD AREAS
        // -----------------------------------------------------

        Label playerTitle =
                new Label("PLAYER");

        Label computerTitle =
                new Label("COMPUTER");

        playerTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        computerTitle.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );


        // Give card areas a fixed size.
        playerCardArea.setPrefSize(160, 220);

        computerCardArea.setPrefSize(160, 220);


        VBox playerSide =
                new VBox(
                        10,
                        playerTitle,
                        playerCardArea
                );

        playerSide.setAlignment(Pos.CENTER);


        VBox computerSide =
                new VBox(
                        10,
                        computerTitle,
                        computerCardArea
                );

        computerSide.setAlignment(Pos.CENTER);


        HBox cardsBox =
                new HBox(
                        100,
                        playerSide,
                        computerSide
                );

        cardsBox.setAlignment(Pos.CENTER);


        // -----------------------------------------------------
        // GAME MESSAGE
        // -----------------------------------------------------

        gameMessage.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        20
                )
        );

        gameMessage.setWrapText(true);

        gameMessage.setAlignment(
                Pos.CENTER
        );


        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        drawButton.setPrefSize(
                160,
                45
        );

        Button newGameButton =
                new Button("NEW GAME");

        newGameButton.setPrefSize(
                160,
                45
        );


        // Draw card.
        drawButton.setOnAction(event -> {

            playRound();
        });


        // Start new game.
        newGameButton.setOnAction(event -> {

            startNewGame();
        });


        HBox buttons =
                new HBox(
                        20,
                        drawButton,
                        newGameButton
                );

        buttons.setAlignment(Pos.CENTER);


        cardsRemainingLabel.setFont(
                Font.font("Arial", 16)
        );


        // -----------------------------------------------------
        // RULES
        // -----------------------------------------------------

        Label rules = new Label(
                """
                Rules:
                • Player and computer each draw one card.
                • Higher ranked card wins the round.
                • Ace is high.
                • Suits do not change card value.
                • A tie awards no points.
                • First player to reach 10 points wins.
                """
        );

        rules.setFont(
                Font.font("Arial", 15)
        );


        // -----------------------------------------------------
        // MAIN GAME LAYOUT
        // -----------------------------------------------------

        VBox root =
                new VBox(
                        18,
                        title,
                        scoreboard,
                        cardsBox,
                        gameMessage,
                        cardsRemainingLabel,
                        buttons,
                        rules
                );

        root.setPadding(new Insets(25));

        root.setAlignment(
                Pos.TOP_CENTER
        );

        return root;
    }


    // =========================================================
    // START / RESET GAME
    // =========================================================

    private void startNewGame() {

        // Reset scores.
        playerScore = 0;
        computerScore = 0;

        // Reset scoreboard.
        playerScoreLabel.setText(
                "Player: 0"
        );

        computerScoreLabel.setText(
                "Computer: 0"
        );

        // Create fresh deck.
        gameDeck.clear();

        gameDeck.addAll(
                createDeck()
        );

        // Shuffle game deck.
        Collections.shuffle(gameDeck);

        // Reset message.
        gameMessage.setText(
                "Press DRAW CARD to begin!"
        );

        cardsRemainingLabel.setText(
                "Cards Remaining: " +
                gameDeck.size()
        );

        // Re-enable draw button.
        drawButton.setDisable(false);

        // Show card backs.
        showCardBack(playerCardArea);

        showCardBack(computerCardArea);
    }


    // =========================================================
    // PLAY ONE ROUND
    // =========================================================

    private void playRound() {

        // Need two cards for one round.
        if (gameDeck.size() < 2) {

            gameDeck.clear();

            gameDeck.addAll(
                    createDeck()
            );

            Collections.shuffle(
                    gameDeck
            );

            gameMessage.setText(
                    "Deck was empty, so a new deck was shuffled!"
            );
        }


        // Draw player card.
        Card playerCard =
                gameDeck.remove(
                        gameDeck.size() - 1
                );

        // Draw computer card.
        Card computerCard =
                gameDeck.remove(
                        gameDeck.size() - 1
                );


        // Show both cards.
        showGameCard(
                playerCardArea,
                playerCard
        );

        showGameCard(
                computerCardArea,
                computerCard
        );


        // -----------------------------------------------------
        // DETERMINE ROUND WINNER
        // -----------------------------------------------------

        if (playerCard.getValue()
                > computerCard.getValue()) {

            playerScore++;

            gameMessage.setText(
                    "You win the round! " +
                    playerCard +
                    " beats " +
                    computerCard
            );

        }

        else if (computerCard.getValue()
                > playerCard.getValue()) {

            computerScore++;

            gameMessage.setText(
                    "Computer wins the round! " +
                    computerCard +
                    " beats " +
                    playerCard
            );

        }

        else {

            gameMessage.setText(
                    "Tie! Both cards are " +
                    playerCard.getRank() +
                    ". No points awarded."
            );
        }


        // -----------------------------------------------------
        // UPDATE SCORES
        // -----------------------------------------------------

        playerScoreLabel.setText(
                "Player: " +
                playerScore
        );

        computerScoreLabel.setText(
                "Computer: " +
                computerScore
        );

        cardsRemainingLabel.setText(
                "Cards Remaining: " +
                gameDeck.size()
        );


        // -----------------------------------------------------
        // CHECK WIN / LOSS STATE
        // -----------------------------------------------------

        checkGameWinner();
    }


    // =========================================================
    // CHECK WINNER
    // =========================================================

    private void checkGameWinner() {

        // Player success state.
        if (playerScore >= 10) {

            gameMessage.setText(
                    "YOU WIN!\n\n" +
                    "Final Score: Player " +
                    playerScore +
                    " - Computer " +
                    computerScore
            );

            drawButton.setDisable(true);
        }


        // Player failure state.
        else if (computerScore >= 10) {

            gameMessage.setText(
                    "GAME OVER - COMPUTER WINS\n\n" +
                    "Final Score: Player " +
                    playerScore +
                    " - Computer " +
                    computerScore
            );

            drawButton.setDisable(true);
        }
    }


    // =========================================================
    // CREATE STANDARD 52 CARD DECK
    // =========================================================

    private List<Card> createDeck() {

        List<Card> deck =
                new ArrayList<>();


        String[] suits = {
                "Hearts",
                "Diamonds",
                "Clubs",
                "Spades"
        };


        String[] ranks = {
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
                "Jack",
                "Queen",
                "King",
                "Ace"
        };


        // Values match ranks.
        int[] values = {
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14
        };


        // Four suits.
        for (String suit : suits) {

            // Thirteen cards per suit.
            for (int i = 0;
                 i < ranks.length;
                 i++) {

                Card card =
                        new Card(
                                ranks[i],
                                suit,
                                values[i]
                        );

                deck.add(card);
            }
        }

        return deck;
    }


    // =========================================================
    // CREATE VISUAL CARD
    // =========================================================

    private VBox createCardView(
            Card card,
            double width,
            double height) {

        String symbol =
                getSuitSymbol(
                        card.getSuit()
                );

        Label rankLabel =
                new Label(
                        card.getRank()
                );

        Label suitLabel =
                new Label(symbol);


        rankLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        15
                )
        );


        suitLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        28
                )
        );


        // Hearts / diamonds are red.
        if (card.getSuit().equals("Hearts")
                ||
                card.getSuit().equals("Diamonds")) {

            rankLabel.setStyle(
                    "-fx-text-fill: red;"
            );

            suitLabel.setStyle(
                    "-fx-text-fill: red;"
            );
        }

        else {

            rankLabel.setStyle(
                    "-fx-text-fill: black;"
            );

            suitLabel.setStyle(
                    "-fx-text-fill: black;"
            );
        }


        VBox cardBox =
                new VBox(
                        3,
                        rankLabel,
                        suitLabel
                );


        cardBox.setAlignment(
                Pos.CENTER
        );


        cardBox.setPrefSize(
                width,
                height
        );

        cardBox.setMinSize(
                width,
                height
        );


        cardBox.setStyle(
                """
                -fx-background-color: white;
                -fx-border-color: black;
                -fx-border-width: 2;
                -fx-border-radius: 5;
                -fx-background-radius: 5;
                """
        );

        return cardBox;
    }


    // =========================================================
    // DISPLAY CARD IN HIGH CARD GAME
    // =========================================================

    private void showGameCard(
            StackPane cardArea,
            Card card) {

        VBox cardView =
                createCardView(
                        card,
                        150,
                        210
                );

        cardArea.getChildren().clear();

        cardArea.getChildren().add(
                cardView
        );
    }


    // =========================================================
    // DISPLAY CARD BACK
    // =========================================================

    private void showCardBack(
            StackPane cardArea) {

        Label backLabel =
                new Label("★");

        backLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        60
                )
        );


        VBox cardBack =
                new VBox(backLabel);

        cardBack.setAlignment(
                Pos.CENTER
        );

        cardBack.setPrefSize(
                150,
                210
        );

        cardBack.setMaxSize(
                150,
                210
        );


        cardBack.setStyle(
                """
                -fx-background-color: darkblue;
                -fx-border-color: black;
                -fx-border-width: 3;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                """
        );


        backLabel.setStyle(
                "-fx-text-fill: white;"
        );


        cardArea.getChildren().clear();

        cardArea.getChildren().add(
                cardBack
        );
    }


    // =========================================================
    // GET SUIT SYMBOL
    // =========================================================

    private String getSuitSymbol(
            String suit) {

        return switch (suit) {

            case "Hearts" -> "♥";

            case "Diamonds" -> "♦";

            case "Clubs" -> "♣";

            case "Spades" -> "♠";

            default -> "?";
        };
    }


    // =========================================================
    // CARD CLASS
    // =========================================================

    private static class Card {

        private final String rank;
        private final String suit;
        private final int value;


        public Card(
                String rank,
                String suit,
                int value) {

            this.rank = rank;
            this.suit = suit;
            this.value = value;
        }


        public String getRank() {

            return rank;
        }


        public String getSuit() {

            return suit;
        }


        public int getValue() {

            return value;
        }


        @Override
        public String toString() {

            return rank +
                    " of " +
                    suit;
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        launch(args);
    }
}