package com.solvd.dice.web.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import javax.naming.ldap.ExtendedRequest;

public class SearchBlock extends AbstractUIObject {

    @FindBy(xpath = ".//*[@class = 'sqs-content']//em")
    private ExtendedWebElement searchKeyWord;

    public SearchBlock(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getSearchKeyWord() {
        return searchKeyWord.getText();
    }
}
