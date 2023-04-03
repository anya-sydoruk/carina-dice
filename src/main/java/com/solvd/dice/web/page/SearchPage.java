package com.solvd.dice.web.page;

import com.qaprosoft.carina.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.qaprosoft.carina.core.gui.AbstractPage;
import com.solvd.dice.web.component.SearchBlock;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends AbstractPage {

    @FindBy(xpath = "//input[@placeholder = 'Type to search…']")
    private ExtendedWebElement searchField;

    @FindBy(xpath = "//*[contains(@class, 'search-page-output')]")
    private List<SearchBlock> searchBlocks;

    public SearchPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL(R.CONFIG.get("url")+"search?q=");
    }

    public void inputSearch(String searchItem){
        searchField.type(searchItem);
    }

    public List<SearchBlock> getSearchBlocks(){
        return searchBlocks;
    }
}
