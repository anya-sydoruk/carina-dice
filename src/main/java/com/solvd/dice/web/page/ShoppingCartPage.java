package com.solvd.dice.web.page;

import com.qaprosoft.carina.core.gui.AbstractPage;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

public class ShoppingCartPage extends AbstractPage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url")+"cart");
    }
}
