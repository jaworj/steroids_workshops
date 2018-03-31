package com.steroids.example.pages;


import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.steroids.example.framework.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.galenframework.api.Galen;
import org.testng.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class HomePage extends AbstractPage {

    private static final String GOOGLE_HOME_URL = "https://www.google.com/";
    private static final By BY_SEARCH_FIELD = By.name("q");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHomePage() {
        getDriver().navigate().to(GOOGLE_HOME_URL);
    }

    public void enterSearchPhrase(String phrase) {
        WebElement searchField = driverWait(10).until(ExpectedConditions.elementToBeClickable(BY_SEARCH_FIELD));
        searchField.sendKeys(phrase);
        searchField.submit();
    }

    public boolean pageTitleContains(String phrase) {
        try {
            return driverWait(5).until(ExpectedConditions.titleContains(phrase));
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
        List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();

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
