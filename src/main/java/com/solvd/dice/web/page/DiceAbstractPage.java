package com.solvd.dice.web.page;

import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.dice.web.component.Footer;
import com.solvd.dice.web.component.Header;
import com.solvd.dice.web.component.PopupSignUpForm;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DiceAbstractPage extends AbstractPage {

    @FindBy(xpath = "//*[contains(@class, 'Header--top')]")
    private Header header;

    @FindBy(xpath = "//*[@class = 'Footer']")
    private Footer footer;

    @FindBy(xpath = "//*[@id = 'PopupSignupForm_0']")
    private PopupSignUpForm popupSignUpForm;

    public DiceAbstractPage(WebDriver driver) {
        super(driver);
    }

    public Header getHeader() {
        return header;
    }

    public Footer getFooter() {
        return footer;
    }

    public PopupSignUpForm getPopupSignUpForm() {
        return popupSignUpForm;
    }
}

