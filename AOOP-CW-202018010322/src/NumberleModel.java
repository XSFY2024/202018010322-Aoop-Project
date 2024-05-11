import java.io.File;import java.io.FileNotFoundException;import java.util.*;
public class NumberleModel extends Observable implements INumberleModel {
    private String targetNumber;
    private StringBuilder currentGuess;
    private int remainingAttempts;
    private boolean gameWon;
    private Set<String> greyLetters = new HashSet<>();
    private Set<String> goldLetters = new HashSet<>();
    private Set<String> greenLetters = new HashSet<>();
    @Override
    public void initialize() {if (!FLAG_RANDOM_SELECT) {targetNumber = "6+4=2*5";
        } else {Set<String> equations = readFile();Random rand = new Random();int index = rand.nextInt(equations.size());targetNumber = new ArrayList<>(equations).get(index);}
        currentGuess = new StringBuilder("       ");remainingAttempts = MAX_ATTEMPTS;gameWon = false;setChanged();notifyObservers();}
    @Override
    public boolean processInput(String input) {if (FLAG_SHOW_ERROR_EQUATION) {if (!validEquation(input)) {return false;}}
        char[] targetArray = targetNumber.toCharArray();char[] inputArray = input.toCharArray();
        for (int i = 0; i < targetArray.length; i++) {if (targetArray[i] == inputArray[i]) {currentGuess.setCharAt(i, '*');greenLetters.add(String.valueOf(inputArray[i]));
            } else if (targetNumber.indexOf(String.valueOf(inputArray[i])) != -1) {currentGuess.setCharAt(i, '.');goldLetters.add(String.valueOf(inputArray[i]));
            } else {currentGuess.setCharAt(i, '_');greyLetters.add(String.valueOf(inputArray[i]));}}remainingAttempts--;
        if (currentGuess.toString().equals("*".repeat(EQUATION_LENGTH))) {gameWon = true;}setChanged();notifyObservers();return true;}
    @Override
    public boolean isGameOver() {
        return remainingAttempts <= 0 || gameWon;
    }
    @Override
    public boolean isGameWon() {
        return gameWon;
    }
    @Override
    public String getTargetNumber() {
        return targetNumber;
    }
    @Override
    public StringBuilder getCurrentGuess() {
        return currentGuess;
    }
    @Override
    public int getRemainingAttempts() {
        return remainingAttempts;
    }
    @Override
    public void startNewGame() {
        initialize();
    }
    @Override
    public Set<String> getGreyLetters() {
        return greyLetters;
    }
    @Override
    public Set<String> getGoldLetters() {
        return goldLetters;
    }
    @Override
    public Set<String> getGreenLetters() {
        return greenLetters;
    }
    private Set<String> readFile() {Set<String> result = new HashSet<>();
        try {Scanner sc = new Scanner(new File(GUESS_EQUATIONS_FILE));while (sc.hasNextLine()) {String line = sc.nextLine().strip();
                if (!line.isEmpty()) {result.add(line);}}} catch (FileNotFoundException e) {e.printStackTrace();}return result;}
    private boolean validEquation(String equation) {if (equation.length() != 7 || equation.indexOf("=") == -1) {return false;}
        if (equation.charAt(0) == '=' || equation.charAt(6) == '=') {return false;}
        String[] parts = equation.split("=");String leftExpression = parts[0];String rightExpression = parts[1];return evaluateExpression(leftExpression) == evaluateExpression(rightExpression);}
    private int evaluateExpression(String expression) {
        int result = 0;char operator = '+';int num = 0;int prevNum = 0;char prevOperator = '+';
        for (int i = 0; i < expression.length(); i++) {char c = expression.charAt(i);if (Character.isDigit(c)) {num = num * 10 + (c - '0');} else if (c == '+' || c == '-') {
                prevNum = applyOperator(prevNum, num, prevOperator);if (operator == '+') {result += prevNum;
                } else {result -= prevNum;}prevNum = 0;prevOperator = c;num = 0;} else if (c == '*' || c == '/') {
                if (prevOperator == '*' || prevOperator == '/') {prevNum = applyOperator(prevNum, num, prevOperator);
                } else {prevNum = num;}prevOperator = c;num = 0;}}
        prevNum = applyOperator(prevNum, num, prevOperator);if (operator == '+') {result += prevNum;} else {result -= prevNum;}return result;}
    private int applyOperator(int result, int num, char operator) {switch (operator) {
            case '+': return result + num; case '-': return result - num; case '*': return result * num; case '/': return result / num;
            default: throw new IllegalArgumentException("Invalid operator");}}}