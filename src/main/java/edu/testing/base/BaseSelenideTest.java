package edu.testing.base;

import workshop.day04_05.pageObjects.LoginPage;

import static com.codeborne.selenide.Selenide.close;

public class BaseSelenideTest {

    protected LoginPage loginPage;

    /**
     * Gets Object-Page instance
     */
    protected void setUp() {
        // Open test site by URL
        loginPage = LoginPage.getInstance("localhost");
    }

    /**
     * Closes session
     */
    public void closeDown() {
        close();
    }

}
