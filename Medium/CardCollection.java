import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CardCollectionSystem {
    private HashMap<String, ArrayList<String>> cardCollection;
    private Scanner scanner;

    // Constructor
    public CardCollectionSystem() {
        cardCollection = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeSymbols(); // Initialize common card symbols
    }

    // Initialize common card symbols (hearts, spades, diamonds, clubs)
    private void initializeSymbols() {
        cardCollection.put("Hearts", new ArrayList<>());
        cardCollection.put("Spades", new ArrayList<>());
        cardCollection.put("Diamonds", new ArrayList<>());
        cardCollection.put("Clubs", new ArrayList<>());
    }

    // Main method to run the program
    public static void main(String[] args) {
        CardCollectionSystem ccs = new CardCollectionSystem();
        ccs.run();
    }

    // Method to run the card collection system
    public void run() {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCard();
                    break;
                case 2:
                    searchCardsBySymbol();
                    break;
                case 3:
                    displayAllCards();
                    break;
                case 4:
                    System.out.println("Exiting the Card Collection System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Display the menu options
    private void displayMenu() {
        System.out.println("\n=== Card Collection System ===");
        System.out.println("1. Add Card");
        System.out.println("2. Search Cards by Symbol");
        System.out.println("3. Display All Cards");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to add a card
    private void addCard() {
        System.out.print("Enter Card Name (e.g., Ace of Hearts): ");
        String cardName = scanner.nextLine().trim();

        // Extract symbol from card name (e.g., "Hearts" from "Ace of Hearts")
        String symbol = extractSymbol(cardName);
        if (!cardCollection.containsKey(symbol)) {
            System.out.println("Invalid card symbol. Use Hearts, Spades, Diamonds, or Clubs.");
            return;
        }

        cardCollection.get(symbol).add(cardName);
        System.out.println("Card '" + cardName + "' added successfully!");
    }

    // Extract symbol from card name (e.g., "Hearts" from "Ace of Hearts")
    private String extractSymbol(String cardName) {
        String[] parts = cardName.split(" of ");
        if (parts.length == 2) {
            String symbol = parts[1].trim();
            if (cardCollection.containsKey(symbol)) {
                return symbol;
            }
        }
        return ""; // Invalid if no valid symbol found
    }

    // Method to search cards by symbol
    private void searchCardsBySymbol() {
        System.out.print("Enter Symbol to search (e.g., Hearts): ");
        String symbol = scanner.nextLine().trim();

        if (!cardCollection.containsKey(symbol)) {
            System.out.println("No cards found for symbol '" + symbol + "'. Use Hearts, Spades, Diamonds, or Clubs.");
            return;
        }

        ArrayList<String> cards = cardCollection.get(symbol);
        if (cards.isEmpty()) {
            System.out.println("No cards found with symbol '" + symbol + "'.");
        } else {
            System.out.println("Searching for cards with symbol '" + symbol + "'...");
            System.out.println("Found cards: " + cards);
        }
    }

    // Method to display all cards
    private void displayAllCards() {
        boolean hasCards = false;
        System.out.println("\n=== All Cards ===");
        for (String symbol : cardCollection.keySet()) {
            ArrayList<String> cards = cardCollection.get(symbol);
            if (!cards.isEmpty()) {
                System.out.println("Symbol: " + symbol + " -> " + cards);
                hasCards = true;
            }
        }
        if (!hasCards) {
            System.out.println("No cards in the collection!");
        }
    }
}