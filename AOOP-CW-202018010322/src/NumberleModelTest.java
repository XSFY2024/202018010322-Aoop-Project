import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NumberleModelTest {
    @Test
    void processInput_ValidInput_Test() {
        NumberleModel model = new NumberleModel();
        model.initialize();
        String targetNumber = model.getTargetNumber();
        assertTrue(model.processInput(targetNumber), "Valid input should return true");
        assertTrue(model.isGameWon(), "Game should be won after correct input");
    }

    @Test
    void processInput_InvalidInput_Test() {
        NumberleModel model = new NumberleModel();
        model.initialize();
        String targetNumber = model.getTargetNumber();
        String invalidInput = targetNumber.replace('+', '-');
        assertFalse(model.processInput(invalidInput), "Invalid input should return false");
        assertFalse(model.isGameWon(), "Game should not be won after invalid input");
    }

    @Test
    void isGameOver_GameNotOver_Test() {
        NumberleModel model = new NumberleModel();
        model.initialize();
        assertFalse(model.isGameOver(), "Game should not be over at the start");
    }

    @Test
    void getGreyLetters_Test() {
        NumberleModel model = new NumberleModel();
        model.initialize();
        Set<String> greyLetters = model.getGreyLetters();
        assertTrue(greyLetters.isEmpty(), "Grey letters should be empty initially");
    }

    @Test
    void getGoldLetters_Test() {
        NumberleModel model = new NumberleModel();
        model.initialize();
        Set<String> goldLetters = model.getGoldLetters();
        assertTrue(goldLetters.isEmpty(), "Gold letters should be empty initially");
    }

    @Test
    void getGreenLetters_Test() {
        NumberleModel model = new NumberleModel();
        model.initialize();
        Set<String> greenLetters = model.getGreenLetters();
        assertTrue(greenLetters.isEmpty(), "Green letters should be empty initially");
    }
}
