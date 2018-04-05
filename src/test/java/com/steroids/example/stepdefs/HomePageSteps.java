package com.steroids.example.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;

import com.steroids.example.framework.AbstractPage;
import com.steroids.example.framework.EmailChecker;
import com.steroids.example.framework.MailNotFoundException;
import com.steroids.example.pages.HomePage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class HomePageSteps {


  public static final Logger log = LogManager.getLogger(AbstractPage.class);
  private WebDriver driver;
  private HomePage homePage;
  private EmailChecker emailChecker;
  private String email;

  @Before(value = "@automated", order = 1)
  public void initWebDriver() throws Throwable {
    System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
    driver = new ChromeDriver();
  }

  @Before(value = "@automated", order = 10)
  public void initHomePage() {
    homePage = new HomePage(driver);
  }

  public void logInfo(String message) {
    Scanner scanner = new Scanner(message);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      log.info(line);
    }
    scanner.close();
  }

  @Given("^I am on the home page$")
  public void iAmOnTheHomePage() {
    homePage.navigateToHomePage();
  }

  @When("^I perform search$")
  public void theSearchPhraseIsEntered() {
    homePage.performSearch();
  }

  @Then("^Japo is shown$")
  public void japoIsShown() {
    assertThat(homePage.japoIsDisplayed()).isTrue();
  }

  @Then("^I type random email into Japo$")
  public void typeRandomEmailIntoJapo() {
    email =
        "test" + UUID.randomUUID().toString().substring(0, 10)
        + "@ec2-34-244-6-12.eu-west-1.compute.amazonaws.com";
    homePage.typeEmailToJapo(email);
    logInfo(email);
  }

  @And("^I save JobAgent from Japo$")
  public void saveJobAgentFromJapo() {
    homePage.saveJobAgentFromJapo();
  }

  @Then("^Check JobAgent confirmation email$")
  public void checkJobAgentConfirmationEmail() throws MailNotFoundException {
    emailChecker = new EmailChecker();
    emailChecker.getMessageUrl(email,"Bitte bestätigen Sie Ihren Job Agent - StepStone");
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
