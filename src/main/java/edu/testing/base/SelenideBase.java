package edu.testing.base;

import edu.testing.pageobjects.TransactionPage;

import static com.codeborne.selenide.Selenide.close;

public class SelenideBase {

    public static final String SOURCE_ID = "4444 4444 4444 4444";
    public static final String TARGET_ID = "6666 6666 6666 6666";

    protected final static String URL = "http://localhost:8080/index.html";

    protected TransactionPage transactionPage;

    /**
     * Gets Object-Page instance
     */
    protected void setUp() {
        transactionPage = TransactionPage.getInstance(URL);
    }

    /**
     * Closes session
     */
    public void closeDown() {
        close();
    }

}
