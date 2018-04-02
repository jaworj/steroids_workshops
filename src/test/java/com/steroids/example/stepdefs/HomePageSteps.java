package com.steroids.example.stepdefs;

import com.steroids.example.framework.EmailChecker;
import com.steroids.example.pages.HomePage;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageSteps {


    private WebDriver driver;
    private HomePage homePage;
    private EmailChecker emailChecker;

    @Before(value = "@automated", order = 1)
    public void initWebDriver() throws Throwable {
        System.setProperty("webdriver.chrome.driver","/Users/jaworj01/Desktop/chromedriver");
        driver = new ChromeDriver();
    }

    @Before(value = "@automated", order = 10)
    public void initHomePage() {
        homePage = new HomePage(driver);
    }

    @Given("^I am on the home page$")
    public void iAmOnTheHomePage() {
        homePage.navigateToHomePage();
    }

    @When("^I perform search$")
    public void theSearchPhraseIsEntered() {
        homePage.performSearch();
    }

    @Then("^results are shown$")
    public void resultsForAreShown() {
        assertThat(homePage.japuIsDisplayed()).isTrue();
    }

    @Then("^check email \"([^\"]*)\"$")
    public void checkMailbox(String mailbox) throws IOException {
        emailChecker.getMessages();
    }

    @Then("^I check layout on \"([^\"]*)\"$")
    public void checkLooksGood(String device) throws IOException {
    homePage.homePageLayoutTest(driver);
    }

    @Then("^I check performance$")
    public void checkPerf() throws IOException {
    homePage.checkPerformance();
    }

    @After(value = "@automated")
    public void disposeWebDriver() {
        driver.quit();
    }
}
