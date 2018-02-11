package edu.testing.pageobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TransactionPage {

    @FindBy(css = "#myGridFrom .ag-body-container .ag-row")
    private List<SelenideElement> gridFrom;


    /**
     * Factory method
     *
     * @return new page object instance
     */
    public static TransactionPage getInstance(String pageUrl) {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        Configuration.browser = "CHROME";

        return open(pageUrl, TransactionPage.class);
    }

    /**
     * Click 2 checkboxes and assert they got checked
     */
    @Step
    public void checkCheckboxSelection() {
        assertEquals(2, gridFrom.size());
    }
}