package com.steroids.example.pages;


import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.steroids.example.framework.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.galenframework.api.Galen;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class HomePage extends AbstractPage {

    private static final String STST_HOME_URL = "https://www.stepstone.de/";
    private static final By BY_SEARCH_FIELD = By.name("q");
    private static final By BY_SEARCH_BUTTON = By.cssSelector("button.btn-primary");
    private static final By BY_JAPU_MODAL = By.id("japubox-popover__modal");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        getDriver().navigate().to(STST_HOME_URL);
    }

    public void performSearch() {
        WebElement elSearchButton = driverWait(10).until(ExpectedConditions.elementToBeClickable(BY_SEARCH_BUTTON));
        elSearchButton.click();
    }

    public boolean japuIsDisplayed() {
        try {
            WebElement elJapuModal = driverWait(5).until(ExpectedConditions.elementToBeClickable(BY_JAPU_MODAL));
            return elJapuModal.isDisplayed();
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void checkPerformance(){
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        String outcome = js.executeScript("return (JSON.stringify(window.performance.timing))").toString();
        System.out.println(outcome);
    }

    public void homePageLayoutTest(WebDriver driver) throws IOException
    {
        //Create a layoutReport object
        //checkLayout function checks the layout and returns a LayoutReport object
        LayoutReport layoutReport = Galen.checkLayout(driver, "/Users/jaworj01/Git/cucumber-jvm-java-example/src/test/resources/com/steroids/example/specs/loginPage.gspec", Arrays.asList("tablet"));

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
        if (layoutReport.errors() > 0)
        {
            Assert.fail("Layout test failed");
        }
    }
}
