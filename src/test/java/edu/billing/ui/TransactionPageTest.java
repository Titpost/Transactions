package edu.billing.ui;

import edu.testing.base.BaseSelenideTest;
import edu.testing.listeners.AllureAttachmentListener;

import edu.testing.pageobjects.TransactionPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

@Title("Transaction test class")
@Description("Test to ensure that account tables look properly after transactions")
@Listeners(AllureAttachmentListener.class)
public class TransactionPageTest extends BaseSelenideTest {

    private final static String URL = "http://localhost:8080/index.html";

    @BeforeClass
    public void setUp() {
        super.setUp(URL);
    }

    @AfterClass
    public void closeDown() {
        super.closeDown();
    }

    /**
     * Elements page test
     */
    @Test
    public void someTest() {
        transactionPage.checkCheckboxSelection();
    }

}
