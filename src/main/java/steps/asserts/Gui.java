package steps.asserts;

import com.codeborne.selenide.ModalOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.AlertNotFoundError;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class Gui {

    public static void shouldNotEquals(String expected, String actual) {

        assertNotEquals(expected, actual);
    }

    public static void shouldBeEquals(String expected, String actual) {

        assertEquals(expected, actual);
    }

    public static void shouldAbsent(String errorMessage) {

        boolean absent;

        try {
            Selenide.switchTo().alert(Duration.ofSeconds(0));
            absent = false;
        } catch (AlertNotFoundError e) {
            absent = true;
        }

        assertTrue(absent, errorMessage);
    }
}
