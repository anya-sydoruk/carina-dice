package com.solvd.dice.web.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Footer extends AbstractUIObject {

    @FindBy(xpath = ".//*[@aria-label = 'Instagram']")
    private ExtendedWebElement instagramButton;

    @FindBy(xpath = ".//*[@aria-label = 'Facebook']")
    private ExtendedWebElement facebookButton;

    @FindBy(xpath = ".//*[@aria-label = 'Apple Podcasts']")
    private ExtendedWebElement applePodcastsButton;

    @FindBy(xpath = ".//*[@aria-label = 'YouTube']")
    private ExtendedWebElement youTubeButton;

    public Footer(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean instagramButtonIsClickable() {
        return instagramButton.isClickable();
    }

    public boolean facebookButtonIsClickable() {
        return facebookButton.isClickable();
    }

    public boolean applePodcastsButtonIsClickable() {
        return applePodcastsButton.isClickable();
    }

    public boolean youTubeButtonIsClickable() {
        return youTubeButton.isClickable();
    }
}
