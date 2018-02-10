package edu.testing.base;

import edu.testing.pageobjects.TransactionPage;

import static com.codeborne.selenide.Selenide.close;

public class BaseSelenideTest {

    protected TransactionPage transactionPage;

    /**
     * Gets Object-Page instance
     */
    protected void setUp(String url) {
        // Open test site by URL
        transactionPage = TransactionPage.getInstance(url);
    }

    /**
     * Closes session
     */
    public void closeDown() {
        close();
    }

}
