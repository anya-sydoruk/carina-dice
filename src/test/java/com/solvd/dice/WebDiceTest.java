package com.solvd.dice;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.solvd.dice.web.component.*;
import com.solvd.dice.web.page.DiceAbstractPage;
import com.solvd.dice.web.page.SearchPage;
import com.solvd.dice.web.page.ShoppingCartPage;
import com.solvd.dice.web.page.StorePage;
import com.zebrunner.agent.core.annotation.TestCaseKey;
import com.zebrunner.agent.core.registrar.TestCase;
import com.zebrunner.carina.utils.R;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class WebDiceTest implements IAbstractTest {

    @BeforeSuite
    public void beforeTest() {
        TestCase.setTestRunId("143");
        TestCase.enableRealTimeSync();
    }


    @Test()
    @TestCaseKey({"TOHA-161"})
    public void redirectToShoppingCartPageTest() {
        DiceAbstractPage diceAbstractPage = new DiceAbstractPage(getDriver());
        diceAbstractPage.open();
        PopupSignUpForm popupSignUpForm = diceAbstractPage.getPopupSignUpForm();
        Assert.assertTrue(popupSignUpForm.isUIObjectPresent(), "PopupSignupForm is not present");
        popupSignUpForm.closePopupSignupForm();
        Header header = diceAbstractPage.getHeader();
        Assert.assertTrue(header.isUIObjectPresent(), "Header is not present");
        ShoppingCartPage shoppingCartPage = header.openShoppingCart();
        Assert.assertTrue(shoppingCartPage.isPageOpened(), "ShoppingCartPage is not opened");
    }

    @Test()
    @TestCaseKey({"TOHA-162"})
    public void checkItemsInStoreTest() {
        DiceAbstractPage diceAbstractPage = new DiceAbstractPage(getDriver());
        diceAbstractPage.open();
        PopupSignUpForm popupSignUpForm = diceAbstractPage.getPopupSignUpForm();
        Assert.assertTrue(popupSignUpForm.isUIObjectPresent(), "PopupSignupForm is not present");
        popupSignUpForm.closePopupSignupForm();
        Header header = diceAbstractPage.getHeader();
        Assert.assertTrue(header.isUIObjectPresent(), "Header is not present");
        StorePage storePage = header.goToStore();
        Assert.assertTrue(storePage.isPageOpened(), "StorePage is not opened");
        List<ProductBlock> productBlocks = storePage.getProductBlocks();
        Assert.assertNotNull(productBlocks, "ProductBlock is empty");
    }

    @Test()
    @TestCaseKey({"TOHA-163"})
    public void checkSearchItemsTest() {
        DiceAbstractPage diceAbstractPage = new DiceAbstractPage(getDriver());
        diceAbstractPage.open();
        PopupSignUpForm popupSignUpForm = diceAbstractPage.getPopupSignUpForm();
        Assert.assertTrue(popupSignUpForm.isUIObjectPresent(), "PopupSignupForm is not present");
        popupSignUpForm.closePopupSignupForm();
        Header header = diceAbstractPage.getHeader();
        Assert.assertTrue(header.isUIObjectPresent(), "Header is not present");
        SearchPage searchPage = header.goToSearch();
        Assert.assertTrue(searchPage.isPageOpened(), "SearchPage is not opened");
        searchPage.inputSearch(R.TESTDATA.get("search_key_word"));
        List<SearchBlock> searchBlocks = searchPage.getSearchBlocks();
        SoftAssert searchAssert = new SoftAssert();
        searchBlocks.forEach(searchBlock ->
                searchAssert.assertTrue(searchBlock.getSearchKeyWord().toLowerCase().contains(R.TESTDATA.get("search_key_word"))));
        searchAssert.assertAll();
    }

    @Test()
    @TestCaseKey({"TOHA-164"})
    public void redirectToSocialNetworksButtonsIsClickable() {
        DiceAbstractPage diceAbstractPage = new DiceAbstractPage(getDriver());
        diceAbstractPage.open();
        PopupSignUpForm popupSignUpForm = diceAbstractPage.getPopupSignUpForm();
        Assert.assertTrue(popupSignUpForm.isUIObjectPresent(), "PopupSignupForm is not present");
        popupSignUpForm.closePopupSignupForm();
        Footer footer = diceAbstractPage.getFooter();
        Assert.assertTrue(footer.isUIObjectPresent(), "Footer is not present");
        Assert.assertTrue(footer.applePodcastsButtonIsClickable(), "applePodcastsButton is not clickable");
        Assert.assertTrue(footer.facebookButtonIsClickable(), "facebookButton is not clickable");
        Assert.assertTrue(footer.youTubeButtonIsClickable(), "youTubeButton is not clickable");
        Assert.assertTrue(footer.instagramButtonIsClickable(), "instagramButton is not clickable");
    }
}
