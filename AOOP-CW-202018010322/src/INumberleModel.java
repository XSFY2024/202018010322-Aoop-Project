import java.util.Set;

public interface INumberleModel {
    int MAX_ATTEMPTS = 6;
    int EQUATION_LENGTH = 7; // Length of the equation
    String GUESS_EQUATIONS_FILE = "equations.txt";
    boolean FLAG_SHOW_ERROR_EQUATION = true;
    boolean FLAG_DISPLAY_EQUATION = true;
    boolean FLAG_RANDOM_SELECT = true;

    void initialize();
    boolean processInput(String input);
    boolean isGameOver();
    boolean isGameWon();
    String getTargetNumber();
    StringBuilder getCurrentGuess();
    int getRemainingAttempts();
    void startNewGame();
    Set<String> getGreyLetters();
    Set<String> getGoldLetters();
    Set<String> getGreenLetters();
}