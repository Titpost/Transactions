package edu.testing.pageobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static edu.testing.base.SelenideBase.SOURCE_ID;
import static edu.testing.base.SelenideBase.TARGET_ID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;


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

        return open(pageUrl, TransactionPage.class);
    }

    /**
     * Make a transfer of random amount from one account to another
     */
    @Step
    public void checkSelectedRowsUpdatedAfterTransaction() {

        final int sourceAmount = getAmount(gridFrom, SOURCE_ID);
        final int targetAmount = getAmount(gridFrom, TARGET_ID);

        final Integer delta = random.nextInt(sourceAmount);
        amountElement.setValue(delta.toString());

        fromElement.setValue(SOURCE_ID);
        toElement.setValue(TARGET_ID);

        transactElement.click();
        refresh();

        assertEquals(sourceAmount - delta, getAmount(gridFrom, SOURCE_ID));
        assertEquals(targetAmount + delta, getAmount(gridFrom, TARGET_ID));
    }

    /**
     * Select accounts and try to make a transfer from one account to itself
     */
    @Step
    public void checkClickedRowsNotModifiedWhenNotNeeded() {

        final int amount = getAmount(gridFrom, SOURCE_ID);

        getRow(gridFrom, SOURCE_ID).click();
        getRow(gridTo, SOURCE_ID).click();

        transactElement.click();
        refresh();

        assertEquals(amount, getAmount(gridFrom, SOURCE_ID));
        assertEquals(amount, getAmount(gridTo, SOURCE_ID));
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
}