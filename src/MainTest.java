import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void check_data_is_valid() {
        Main main = new Main();

        String word1 = "friend";
        String word2 = "redfin";
        String test_logs = "text-files/test_logs.txt";
        assertEquals(true, main.order_word_alphabetically(word1, word2, test_logs));

        word1 = "friend ";
        word2 = "redfin1";
        test_logs = "text-files/test_logs.txt";
        assertEquals(false, main.order_word_alphabetically(word1, word2, test_logs));
    }

    @Test
    void order_word_alphabetically() {
        Main main = new Main();

        String word1 = "friend";
        String word2 = "redfin";
        String test_logs = "text-files/test_logs.txt";
        assertEquals(true, main.order_word_alphabetically(word1, word2, test_logs));

    }

}