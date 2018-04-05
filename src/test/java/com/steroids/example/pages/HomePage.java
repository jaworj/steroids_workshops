package com.steroids.example.pages;


import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.steroids.example.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class HomePage extends AbstractPage {

  private static final String STST_HOME_URL = "https://www.stepstone.de/";
  private static final By BY_SEARCH_WHAT_FIELD = By.name("ke");
  private static final By BY_SEARCH_BUTTON = By.cssSelector("div.search-box__submit button");
  private static final By BY_JAPO_MODAL = By.id("japubox-popover__modal");
  private static final By BY_JAPO_EMAIL_FIELD = By.cssSelector(".modal-content [name='email']");
  private static final By BY_JAPO_SAVE_BUTTON = By.cssSelector(".modal-content [type='submit']");


  public HomePage(WebDriver driver) {
    super(driver);
  }

  public void navigateToHomePage() {
    getDriver().navigate().to(STST_HOME_URL);
  }

  public void performSearch() {
    WebElement ek = driverWait(10).until(ExpectedConditions.elementToBeClickable(BY_SEARCH_BUTTON));
    ek.click();
  }

  public boolean japoIsDisplayed() {
    try {
      WebElement
          elJapuModal =
          driverWait(5).until(ExpectedConditions.elementToBeClickable(BY_JAPO_MODAL));
      return elJapuModal.isDisplayed();
    } catch (TimeoutException ex) {
      return false;
    }
  }

  public void typeEmailToJapo(String email) {
    getDriver().findElement(BY_JAPO_EMAIL_FIELD).sendKeys(email);
  }

  public void saveJobAgentFromJapo() {
    getDriver().findElement(BY_JAPO_SAVE_BUTTON).click();

  }

  public void checkPerformance() {
    JavascriptExecutor js = (JavascriptExecutor) getDriver();
    String
        outcome =
        js.executeScript("return (JSON.stringify(window.performance.timing))").toString();
    System.out.println(outcome);
  }

  public void homePageLayoutTest(WebDriver driver) throws IOException {
    //Create a layoutReport object
    //checkLayout function checks the layout and returns a LayoutReport object
    LayoutReport
        layoutReport =
        Galen.checkLayout(driver,
                          "C:/Git/steroids_workshops/src/test/resources/com/steroids/example/specs/loginPage.gspec",
                          Arrays.asList("tablet"));

    //Create a tests list
    List<GalenTestInfo> tests = new LinkedList<>();

    //Create a GalenTestInfo object
    GalenTestInfo test = GalenTestInfo.fromString("homepage layout");

    //Get layoutReport and assign to test object
    test.getReport().layout(layoutReport, "check homepage layout");

    //Add test object to the tests list
    tests.add(test);

    //Create a htmlReportBuilder object
    HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();

    //Create a report under /target folder based on tests list
    htmlReportBuilder.build(tests, "target");

    //If layoutReport has errors Assert Fail
    if (layoutReport.errors() > 0) {
      Assert.fail("Layout test failed");
    }
  }
}
