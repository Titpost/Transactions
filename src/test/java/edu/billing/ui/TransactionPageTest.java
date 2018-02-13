package edu.billing.ui;

import edu.testing.base.SelenideBase;
import edu.testing.listeners.AllureAttachmentListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

@Title("Transaction test class")
@Description("Test to ensure that account tables look properly after transactions")
@Listeners(AllureAttachmentListener.class)
public class TransactionPageTest extends SelenideBase {

    @BeforeClass
    public void setUp() {
        super.setUp();
    }

    @AfterClass
    public void closeDown() {
        super.closeDown();
    }

    @Test
    public void transactionItself() {
        transactionPage.checkSelectedRowsUpdatedAfterTransaction();
    }

    @Test
    public void transactionByClicksOnSameAccount() {
        transactionPage.checkClickedRowsNotModifiedWhenNotNeeded();
    }
}
