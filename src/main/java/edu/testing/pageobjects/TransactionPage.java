package edu.testing.pageobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import edu.testing.base.SelenideBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static edu.testing.base.SelenideBase.SOURCE_ID;
import static edu.testing.base.SelenideBase.TARGET_ID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TransactionPage {

    @FindBy(css = "#myGridFrom .ag-body-container .ag-row")
    private List<SelenideElement> gridFrom;

    @FindBy(css = "#from")
    private SelenideElement fromElement;

    @FindBy(css = "#to")
    private SelenideElement toElement;

    @FindBy(css = "#transact")
    private SelenideElement transactElement;

    @FindBy(css = "#amount")
    private SelenideElement amountElement;

    @FindBy(css = "#myGridTo .ag-body-container .ag-row")
    private List<SelenideElement> gridTo;

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
     * Select accounts and make a transfer of random amount
     */
    @Step
    public void checkSelectedRowsUpdatedAfterTransaction() {

        final String idLocator = "div[col-id='id']";
        final String amountLocator = "div[col-id='amount']";

        final int sourceAmount = Integer.getInteger(gridFrom.stream()
                .filter(r -> r.$(idLocator).getText().equals(SOURCE_ID))
                .findFirst().get()
                .$(amountLocator)
                .getText()
        );

        final int targetAmount = Integer.getInteger(gridTo.stream()
                .filter(r -> r.$(idLocator).getText().equals(TARGET_ID))
                .findFirst().get()
                .$(amountLocator)
                .getText()
        );

        final Random random = new Random();
        final Integer delta = random.nextInt(sourceAmount);
        amountElement.setValue(delta.toString());

        fromElement.setValue(SOURCE_ID);
        toElement.setValue(TARGET_ID);

        transactElement.click();
    }
}