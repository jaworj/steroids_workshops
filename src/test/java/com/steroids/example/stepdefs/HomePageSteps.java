package com.steroids.example.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;

import com.steroids.example.framework.EmailChecker;
import com.steroids.example.pages.HomePage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.UUID;

public class HomePageSteps {


  private WebDriver driver;
  private HomePage homePage;
  private EmailChecker emailChecker;
  private String email;

  @Before(value = "@automated", order = 1)
  public void initWebDriver() throws Throwable {
    System.setProperty("webdriver.chrome.driver", "/Users/jaworj01/Desktop/chromedriver");
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

  @Then("^Japu is shown$")
  public void resultsForAreShown() {
    assertThat(homePage.japoIsDisplayed()).isTrue();
  }

  @Then("^I type random email into Japo$")
  public void typeRandomEmailIntoJapo() {
    email = "test" + UUID.randomUUID().toString().substring(0, 10) + "@in.fistep.com";
    homePage.typeEmailToJapo(email);
  }

  @And("^I save JobAgent from Japo$")
  public void saveJobAgentFromJapo() {
    homePage.saveJobAgentFromJapo();
  }

  @Then("^check email \"([^\"]*)\"$$")
  public void checkMailbox(String mailboxName) throws IOException {
    emailChecker = new EmailChecker();
    emailChecker.getMessages(mailboxName);
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
