import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPhoneNumber {
    private PhoneNumber phoneNumber;

    private final int[] number1 = {30, 2, 5, 58};
    private final int[] number2 = {2, 10, 6, 9, 30, 6, 6 ,4};

    private final int[] number3 = {0, 0, 30, 69, 700, 24, 1, 3, 50, 2};

    @BeforeEach
    void setUp() {
        phoneNumber = new PhoneNumber(number2);
    }

    @Test
    @DisplayName("number validation")
    void testValidateGreekNumber() {
        assertFalse(phoneNumber.validateGreekNumber(number1).isValid());
        assertTrue(phoneNumber.validateGreekNumber(number2).isValid());
        assertFalse(phoneNumber.validateGreekNumber(number3).isValid());
    }

    @Test
    @DisplayName("ambiguity")
    void testHandleNumberAmbiguities() {
        phoneNumber.validateGreekNumber(number2);
        assertTrue(phoneNumber.handleNumberAmbiguities(phoneNumber));
    }
}
