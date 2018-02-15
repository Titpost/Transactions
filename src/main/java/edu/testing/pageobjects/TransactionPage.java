package edu.testing.pageobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static edu.testing.base.SelenideBase.SOURCE_ID;
import static edu.testing.base.SelenideBase.TARGET_ID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;


public class TransactionPage {

    @FindBy(css = fromLocator)
    private List<SelenideElement> rowsSetFrom;

    @FindBy(css = "#from")
    private SelenideElement fromElement;

    @FindBy(css = "#to")
    private SelenideElement toElement;

    @FindBy(css = "#transact")
    private SelenideElement transactElement;

    @FindBy(css = "#amount")
    private SelenideElement amountElement;

    @FindBy(css = toLocator)
    private List<SelenideElement> rowsSetTo;

    private final static String fromLocator = "#myGridFrom .ag-body-container .ag-row";
    private final static String toLocator = "#myGridTo .ag-body-container .ag-row";
    private final static String idLocator = "div[col-id='id']";
    private final static Random random = new Random();


    /**
     * Factory method
     *
     * @return new page object instance
     */
    public static TransactionPage getInstance(String pageUrl) {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        Configuration.browser = "CHROME";
        Configuration.startMaximized = false;

        return open(pageUrl, TransactionPage.class);
    }

    /**
     * Make a transfer of random amount from one account to another
     */
    @Step
    public void checkSelectedRowsUpdatedAfterTransaction() {

        final int sourceAmount = getAmount(rowsSetFrom, SOURCE_ID);
        final int targetAmount = getAmount(rowsSetFrom, TARGET_ID);

        final Integer delta = random.nextInt(sourceAmount);
        amountElement.setValue(delta.toString());

        fromElement.setValue(SOURCE_ID);
        toElement.setValue(TARGET_ID);

        clickAndEnsureStatusReady();

        assertEquals(sourceAmount - delta, getAmount(rowsSetFrom, SOURCE_ID));
        assertEquals(targetAmount + delta, getAmount(rowsSetFrom, TARGET_ID));
    }

    /**
     * Select accounts and try to make a transfer from one account to itself
     */
    @Step
    public void checkClickedRowsNotModifiedWhenNotNeeded() {

        final int amount = getAmount(rowsSetFrom, SOURCE_ID);

        getRow(rowsSetFrom, SOURCE_ID).click();
        getRow(rowsSetTo, SOURCE_ID).click();

        clickAndEnsureStatusReady();

        assertEquals(amount, getAmount(rowsSetFrom, SOURCE_ID));
        assertEquals(amount, getAmount(rowsSetTo, SOURCE_ID));
    }


    /**
     * Gets balance of account with the specified id
     *
     * @param rows SelenideElement[] containing a table rows
     * @param id account id
     * @return account balance
     */
    private int getAmount(List<SelenideElement> rows, String id) {
        final String amountLocator = "div[col-id='amount']";
        return Integer.valueOf(getRow(rows, id)
                .$(amountLocator)
                .getText()
        );
    }

    /**
     * Gets a row by account id
     *
     * @param rows SelenideElement[] containing a table rows
     * @param id account id
     * @return a row or fails if not found
     */
    private SelenideElement getRow(List<SelenideElement> rows, String id) {
        return rows.stream()
                .filter(r -> r.$(idLocator).getText().equals(id))
                .findFirst().orElseGet(() -> {
                    fail("Element not found: " + idLocator);
                    return null;
                });
    }

    /**
     * Start transaction and make sure ajax-call is finished before proceeding
     */
    private void clickAndEnsureStatusReady() {
        transactElement.click();
        $("#status").shouldHave(text("ready"));

        while (0 == rowsSetFrom.size()) {
            rowsSetFrom = $$(fromLocator);
        }
        while (0 == rowsSetTo.size()) {
            rowsSetTo = $$(toLocator);
        }
    }
}