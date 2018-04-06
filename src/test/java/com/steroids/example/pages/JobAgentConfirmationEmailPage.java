package com.steroids.example.pages;

import com.steroids.example.framework.AbstractPage;
import com.steroids.example.framework.EmailChecker;
import com.steroids.example.framework.MailNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class JobAgentConfirmationEmailPage extends AbstractPage {


  public JobAgentConfirmationEmailPage(WebDriver driver) {
    super(driver);
  }


  public PasswordSetPage confirmEmailMessageFistep(String email, String subject) {
    EmailChecker emailChecker = new EmailChecker();
    String url = null;
    try {
      url = emailChecker.getMessageUrl(email, subject);
    } catch (MailNotFoundException e) {
      log.error("Cannot found confirmation email", e);
    }
    if (!Objects.equals(url, getDriver().getCurrentUrl())) {
      goToConfirmationEmailFistep(email, subject);
    }
    String confirmationUrl =
        getDriver().findElement(
            By.cssSelector("a[href*='EasyJobagent.register']")).getAttribute("href");

    getDriver().get(confirmationUrl);
    return new PasswordSetPage();
  }

  public void goToConfirmationEmailFistep(String email, String subject) {
    logInfo("Subject parameter: " + subject);
    logInfo("Subject from xml: " + subject);
    logInfo("Email: " + email);
    logInfo("Wait 10 seconds for message...");
    //Utils.wait(10);
    logInfo("Wait 10 seconds for message. Done");
    EmailChecker emailChecker = new EmailChecker();
    String url = null;
    try {
      url = emailChecker.getMessageUrl(email, subject);
    } catch (MailNotFoundException e) {
      log.error("Cannot found confirmation email", e);
    }
    getDriver().get(url);
  }
}
