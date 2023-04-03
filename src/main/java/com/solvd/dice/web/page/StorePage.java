package com.solvd.dice.web.page;

import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.dice.web.component.ProductBlock;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StorePage extends AbstractPage {

    @FindBy(xpath = "//*[@class = 'ProductList-grid clear']")
    private List<ProductBlock> productBlocks;

    public StorePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url")+"store");
    }

    public List<ProductBlock> getProductBlocks(){
        return productBlocks;
    }
}
