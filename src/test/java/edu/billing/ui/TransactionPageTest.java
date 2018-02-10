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

@Title("Page 'Menu & Elements' test class")
@Description("Test class for slider's DnD")
@Listeners(AllureAttachmentListener.class)
public class TransactionPageTest extends BaseSelenideTest {

    private TransactionPage transactionPage;

    @BeforeClass
    public void setUp() {
        super.setUp();
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
    }

}
