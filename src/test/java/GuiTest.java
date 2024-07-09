import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static steps.asserts.Gui.*;
import static utils.Locators.*;

public class GuiTest {

    @BeforeAll
    public static void setUp() {

        WebDriverManager.chromedriver().setup();
        Configuration.baseUrl = "https://the-internet.herokuapp.com/";
    }

    @Test
    @DisplayName("Проверка новой вкладки")
    public void checkNewTab() {

        open("windows");

        String titleFirstTab = Selenide.title();

        SelenideElement clickHere = $(CSS_LINK_FOR_OPEN_NEW_PAGE);
        clickHere.click();

        Selenide.switchTo().window(1);

        String titleSecondTab = Selenide.title();

        shouldNotEquals(titleFirstTab, titleSecondTab);

        $("h3").shouldHave(text("New Page"));

        Selenide.closeWindow();

        Selenide.switchTo().window(0);

        clickHere.shouldBe(visible.because("Ожидался переход на предыдущую вкладку, но этого не произошло"));

    }

    @Test
    @DisplayName("Проверка уведомлений")
    public void checkAlerts() {

        open("javascript_alerts");

        $(CSS_BUTTON_JS_ALERT).click();

        String jsAlertText = Selenide.confirm();
        String jsAlertExpText = "I am a JS Alert";

        shouldBeEquals(jsAlertExpText, jsAlertText);
        shouldAbsent("Ожидалось закрытие jsAlert, но этого не произошло");

        $(CSS_BUTTON_JS_CONFIRM).click();

        Selenide.dismiss();

        shouldAbsent("Ожидалось закрытие jsConfirm, но этого не произошло");

        $(CSS_BUTTON_JS_PROMPT).click();

        String inputText = "Hello World";

        Selenide.prompt(inputText);

        $(ID_RESULT).shouldHave(text("You entered: " + inputText));

    }
}
