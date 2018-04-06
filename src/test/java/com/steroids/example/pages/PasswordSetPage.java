package com.steroids.example.pages;


import com.steroids.example.framework.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class PasswordSetPage extends AbstractPage{

  @FindBy(css = "body.candidateLogin, body.candidatelogin")
  private List<WebElement> elConfirmationAccountPage;

  @FindBy(id = "login_passw1")
  private WebElement elPasswordTextBox;

  @FindBy(id = "login_passw2")
  private WebElement elPasswordRepeatTextBox;

  @FindBy(id = "rememberMe")
  private WebElement elRememberMeCheckbox;

  @FindBy(id = "confimrationSetPassword")
  private WebElement elPasswordSetSection;

  @FindBy(xpath = "//button[@type='submit']")
  private WebElement elSubmitButton;

  @FindBy(css = "#setPassword>strong")
  private WebElement elSetPasswordSuccessMessage;

  public boolean checkIfThisIsPasswordSetPage(){
    driverWait(10).until(ExpectedConditions.elementToBeClickable(By.id("password-strength-info")));
    return elPasswordSetSection.isEnabled() ;
  }

  public void typePasswordRepeat(String value) {
    elPasswordRepeatTextBox.clear();
    elPasswordRepeatTextBox.sendKeys(value);
  }


  public void typePassword(String password) {
    elPasswordTextBox.clear();
    elPasswordTextBox.sendKeys(password);
  }

}
