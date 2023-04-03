package com.solvd.dice.web.component;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductBlock extends AbstractUIObject {

    @FindBy(xpath = ".//*[@class = 'ProductList-item-link']")
    private ExtendedWebElement itemLink;

    public ProductBlock(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public ExtendedWebElement getItemLink() {
        return itemLink;
    }
}
