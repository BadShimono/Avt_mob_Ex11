package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.junit.Assert;

abstract public class MyListPageObject extends MainPageObject{

   protected static String
    FOLDER_BY_NAME_TPL,
    SEARCH_INPUT_FOR_MY_LIST,
    SAVED_ARTICLE,
    ARTICLE_BY_TITLE_TPL,
    BUTTON_FILTER_MY_LIST;

    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",article_title);
    }
    private static String getSavedArticleXpathByTitle1(String article_title1)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",article_title1);
    }

    public MyListPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);

        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                10
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title, 15);
    }

    public void waitForArticleToMyListAndClick(String article_title1)
    {
        String article_xpath = getSavedArticleXpathByTitle1(article_title1);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title1, 15);
        this.waitForElementAndClick(article_xpath, "Cannot find article and click", 10);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForArticleToAppearByTitle(article_title);

        this.swipeElementToLeft(article_xpath,"Cannot find saved article");
        if(Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    //Метод который осуществляет поиск в листе закладок и считает количество 'ариклов' после свайпа одного из них.
    public void waitForArticleNotUsedTitle(String name_of_search)
    {
        if(Platform.getInstance().isIOS()) {
            this.waitForElementAndSendKeys(SEARCH_INPUT_FOR_MY_LIST, name_of_search, "Cannot find and type into search input", 5);
            this.waitForElementPresent(SAVED_ARTICLE, "Cannot find anything by the request " + SAVED_ARTICLE, 15);
            int amount_of_saved_results = getAAmountOfElements(SAVED_ARTICLE);
            Assert.assertTrue("We found too few results", amount_of_saved_results == 1);
        } else {
            this.waitForElementAndClick(BUTTON_FILTER_MY_LIST, "Cannot find and click Filter my list",5);
            this.waitForElementAndSendKeys(SEARCH_INPUT_FOR_MY_LIST, name_of_search, "Cannot find and type into search input", 10);
            this.waitForElementPresent(SAVED_ARTICLE, "Cannot find anything by the request " + SAVED_ARTICLE, 15);
            int amount_of_saved_results = getAAmountOfElements(SAVED_ARTICLE);
            Assert.assertTrue("We found too few results", amount_of_saved_results == 1);
        }
    }

}
