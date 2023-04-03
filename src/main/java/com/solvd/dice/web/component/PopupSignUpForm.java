package com.solvd.dice.web.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PopupSignUpForm extends AbstractUIObject {

    @FindBy(xpath = ".//*[@class = 'mc-closeModal']")
    private ExtendedWebElement closeButton;

    public PopupSignUpForm(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void closePopupSignupForm(){
        closeButton.click();
    }
}
