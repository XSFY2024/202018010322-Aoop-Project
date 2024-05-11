import java.util.Scanner;

/**
 * Represents a command-line interface (CLI) application for playing the Numberle game.
 * The user interacts with the game through the command line.
 * This class provides the main entry point for running the game.
 */
public class CLIApp {
    private INumberleModel model;
    private Scanner scanner;

    /**
     * Constructs a CLIApp object with a new instance of NumberleModel and a Scanner for user input.
     */
    public CLIApp() {
        model = new NumberleModel();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the Numberle game.
     * This method displays welcome messages, handles user input, and controls the game flow.
     */
    public void startGame() {
        System.out.println("Welcome to Numberle Game!");
        model.startNewGame();

        while (!model.isGameOver()) {
            System.out.println("Remaining attempts: " + model.getRemainingAttempts());
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine();

            boolean valid = model.processInput(guess);
            if (valid) {
                System.out.println("CurrentGuess: " + model.getCurrentGuess().toString());
            } else {
                System.out.println("Invalid equation.");
            }

            System.out.println("----------------------------------------"); // Print a separator line
        }

        if (model.isGameWon()) {
            System.out.println("Congratulations! You won the game!"); // Print victory message
        } else {
            System.out.println("Game over! You ran out of attempts. The target word was: " + model.getTargetNumber());
        }

        System.out.println("Do you want to play again? (yes/no)");
        String playAgain = scanner.nextLine();
        if (playAgain.equalsIgnoreCase("yes")) {
            startGame();
        } else {
            System.out.println("Thank you for playing Numberle Game!");
        }
    }

    /**
     * The main entry point of the application.
     * Creates an instance of CLIApp and starts the game.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        CLIApp game = new CLIApp();
        game.startGame();
    }
}
