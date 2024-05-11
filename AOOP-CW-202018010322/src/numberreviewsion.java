import javax.swing.*;import java.awt.*;import java.awt.event.KeyAdapter;import java.awt.event.KeyEvent;import java.util.Observer;import java.util.Set;
public class numberreviewsion implements Observer {
    private final INumberleModel model;
    private final NumberleController controller;
    private final JFrame frame = new JFrame("Numberle");
    private JLabel attemptsLabel;
    private JPanel inputPanel;
    private JLabel[] letterLabels;
    private JPanel letterPanel;
    private JButton newGameButton;
    private KeyAdapter inputPanelKeyListener;
    public numberreviewsion(INumberleModel model, NumberleController controller) {this.controller = controller;this.model = model;this.controller.startNewGame();
        ((NumberleModel)this.model).addObserver(this);initializeframe();this.controller.setView(this);update((NumberleModel)this.model, null);}
    public void initializeframe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);frame.setSize(600, 500);frame.setLayout(new BorderLayout());
        setattemptlabel();startinputpanel();keyboard();initializenewgamefunctionbutton();setinputpanelkeylistener();frame.setVisible(true);}
    private void setattemptlabel() {
        JPanel attemptsPanel = new JPanel();attemptsLabel = new JLabel("Attempts remaining: " + controller.getRemainingAttempts());attemptsPanel.add(attemptsLabel);frame.add(attemptsPanel, BorderLayout.EAST);}
    private void startinputpanel() {inputPanel = new JPanel();inputPanel.setLayout(new GridLayout(6, 7));letterLabels = new JLabel[42];
        for (int i = 0; i < 42; i++) {JLabel label = new JLabel();label.setOpaque(true);label.setBackground(Color.WHITE);label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            inputPanel.add(label);letterLabels[i] = label;}frame.add(inputPanel, BorderLayout.CENTER);}
    private void keyboard() {
        letterPanel = new JPanel();letterPanel.setLayout(new GridLayout(2, 1));
        JPanel row1Panel = new JPanel();row1Panel.setLayout(new GridLayout(1, 10));String[] row1Letters = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
        for (String letter : row1Letters) {JButton button = new JButton(letter);button.setEnabled(true);button.addActionListener(e -> {updateinputpanel(getinput() + button.getText());});
            row1Panel.add(button);}letterPanel.add(row1Panel);JPanel row2Panel = new JPanel();row2Panel.setLayout(new GridLayout(1, 7));JButton backspaceButton = new JButton("×");
        backspaceButton.setEnabled(true);backspaceButton.addActionListener(e -> {String currentText = getinput();
            if (!currentText.isEmpty()) {updateinputpanel(currentText.substring(0, currentText.length() - 1));}});
        row2Panel.add(backspaceButton);String[] row2Letters = { "+", "-", "*", "/", "=" };
        for (String letter : row2Letters) {JButton button = new JButton(letter);button.setEnabled(true);button.addActionListener(e -> {
            updateinputpanel(getinput() + button.getText());});row2Panel.add(button);}
        JButton enterButton = new JButton("Enter");enterButton.setEnabled(true);enterButton.addActionListener(e -> {
            if (!controller.processInput(getinput())) {JOptionPane.showMessageDialog(null, "Invalid input!");}});
        row2Panel.add(enterButton);letterPanel.add(row2Panel);frame.add(letterPanel, BorderLayout.SOUTH);}
    private String getinput() {
        int currentGuess = INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts();int startRow = currentGuess % 6;int startIndex = startRow * 7;int endIndex = startIndex + 7;
        StringBuilder sb = new StringBuilder();for (int i = startIndex; i < endIndex; i++) {String text = letterLabels[i].getText();sb.append(text);}return sb.toString();}
    private void updateinputpanelstatement() {
        int lastGuess = INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts() - 1;
        if (lastGuess >= 0) {int startRow = lastGuess % 6;int startIndex = startRow * 7;int endIndex = startIndex + 7;String currentGuess = controller.getCurrentGuess().toString();
            for (int i = startIndex; i < endIndex; i++) {switch (currentGuess.charAt(i % 7)) {
                    case '*': letterLabels[i].setBackground(Color.GREEN);break; case '.': letterLabels[i].setBackground(Color.YELLOW);break; case '_': letterLabels[i].setBackground(Color.LIGHT_GRAY);break;
                    default: letterLabels[i].setBackground(Color.WHITE);break;}}}}
    private void updateinputpanel(String text) {
        int currentGuess = INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts();int startRow = currentGuess % 6;int startIndex = startRow * 7;int endIndex = startIndex + 7;
        for (int i = startIndex; i < endIndex; i++) {if (i - startIndex < text.length()) {letterLabels[i].setText(String.valueOf(text.charAt(i - startIndex)));} else {letterLabels[i].setText("");}}}
    private void initializenewgamefunctionbutton() {
        newGameButton = new JButton("Start New Game");newGameButton.setEnabled(false);newGameButton.addActionListener(e -> {controller.startNewGame();
            startanewgame();updatenewgamefunctionbutton();});JPanel buttonPanel = new JPanel();buttonPanel.add(newGameButton);frame.add(buttonPanel, BorderLayout.WEST);}
    public void startanewgame() {frame.getContentPane().removeAll();
        setattemptlabel();startinputpanel();keyboard();initializenewgamefunctionbutton();setinputpanelkeylistener();frame.revalidate();frame.repaint();}
    private void checkgameover() {
        if (controller.isGameOver()) {setcomponentsenabled(letterPanel, false);inputPanel.removeKeyListener(inputPanelKeyListener);
            if (controller.isGameWon()) {JOptionPane.showMessageDialog(null, "Congratulations! You won!");} else {JOptionPane.showMessageDialog(null, "Sorry, you lost. The word was " + controller.getTargetWord());}}}
    private void setcomponentsenabled(Component component, boolean enabled) {
        component.setEnabled(enabled);if (component instanceof Container) {Component[] components = ((Container) component).getComponents();
            for (Component childComponent : components) {setcomponentsenabled(childComponent, enabled);}}}
    private void updatenewgamefunctionbutton() {
        if (INumberleModel.MAX_ATTEMPTS - controller.getRemainingAttempts() >= 1) {newGameButton.setEnabled(true);} else {newGameButton.setEnabled(false);}}
    private void setinputpanelkeylistener() {
        inputPanelKeyListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {char c = e.getKeyChar();if (!isvalidinput(c)) {e.consume();} else if (c == KeyEvent.VK_ENTER) {
                    if (!controller.processInput(getinput())) {JOptionPane.showMessageDialog(null, "Invalid input!");}} else if (c == KeyEvent.VK_BACK_SPACE) {String currentText = getinput();
                    if (!currentText.isEmpty()) {updateinputpanel(currentText.substring(0, currentText.length() - 1));}} else {updateinputpanel(getinput() + c);}}
            private boolean isvalidinput(char c) {
                return (c >= '0' && c <= '9') || (c == '+' || c == '-' || c == '*' || c == '/' || c == '=')
                        || c == KeyEvent.VK_BACK_SPACE
                        || c == KeyEvent.VK_ENTER;}};inputPanel.addKeyListener(inputPanelKeyListener);inputPanel.setFocusable(true);inputPanel.requestFocusInWindow();}
    private void uploadattemptslabel() {attemptsLabel.setText("Attempts remaining: " + controller.getRemainingAttempts());}
    private void uploadkeyboard() {Set<String> greyLetters = model.getGreyLetters();Set<String> goldLetters = model.getGoldLetters();Set<String> greenLetters = model.getGreenLetters();
        for (Component component : letterPanel.getComponents()) {if (component instanceof JPanel) {JPanel rowPanel = (JPanel) component;
                for (Component buttonComponent : rowPanel.getComponents()) {if (buttonComponent instanceof JButton) {JButton button = (JButton) buttonComponent;String letter = button.getText().toLowerCase();
                        if (greenLetters.contains(letter)) {button.setBackground(Color.GREEN);} else if (goldLetters.contains(letter)) {button.setBackground(Color.YELLOW);} else if (greyLetters.contains(letter)) {button.setBackground(Color.GRAY);
                        } else {button.setBackground(null);}}}}}}
    @Override
    public void update(java.util.Observable o, Object arg) {uploadattemptslabel();updateinputpanelstatement();uploadkeyboard();updatenewgamefunctionbutton();checkgameover();}}