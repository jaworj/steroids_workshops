package com.steroids.example.stepdefs;

import com.steroids.example.pages.HomePage;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class HomePageSteps {

  private WebDriver driver;
  private HomePage homePage;


  @Before(value = "@automated", order = 1)
  public void initWebDriver() {
    ChromeOptions options = new ChromeOptions();
    String path;
    options.addArguments("--start-maximized");
    String os = System.getProperty("os.name").toLowerCase();
    ClassLoader classLoader = getClass().getClassLoader();

    if (os.contains("win")) {
      path = classLoader.getResource("chromedriverwin.exe").getPath();
      System.setProperty("webdriver.chrome.driver", path);
    } else if (os.contains("mac")) {
      path = classLoader.getResource("chromedriver").getPath();
      System.setProperty("webdriver.chrome.driver", path);
    } else {
      path = classLoader.getResource("chromedriverlin").getPath();
    }
    System.setProperty("webdriver.chrome.driver", path);
    driver = new ChromeDriver(options);
  }

  @Before(value = "@automated", order = 10)
  public void initHomePage() {
    homePage = new HomePage(driver);
  }

  @Given("^I am on the home page$")
  public void iAmOnTheHomePage() {
    homePage.navigateToHomePage();
  }

  @After(value = "@automated")
  public void disposeWebDriver() {
    driver.quit();
  }
}
