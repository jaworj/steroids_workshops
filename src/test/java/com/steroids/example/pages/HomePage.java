package com.steroids.example.pages;


import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.google.gson.Gson;
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

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;


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
    WebElement
        elSearchButton =
        driverWait(10).until(ExpectedConditions.elementToBeClickable(BY_SEARCH_BUTTON));
    elSearchButton.click();
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
    String timings =  js.executeScript("return (window.performance.timing)").toString();
    Gson gson = new Gson();
    Timings timingsJson = gson.fromJson(timings, Timings.class);
    long pageLoadTime = timingsJson.loadEventStart - timingsJson.navigationStart;
    long backendTime = timingsJson.responseStart - timingsJson.navigationStart;
    logInfo("pageLoadTime: " + pageLoadTime);
    logInfo("backendTime: " + backendTime);
  }

  class Timings {
    private long loadEventStart;
    private long navigationStart;
    private long backEndTime;
    private long responseStart;

  }
}
