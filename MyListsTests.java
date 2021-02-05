package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Mobil device company";

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Nokia");
        SearchPageObject.clickByArticleWithSubstring("Finnish");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else { ArticlePageObject.addArticlesToMySaved(); }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid())
        { MyListPageObject.openFolderByName(name_of_folder); }

        MyListPageObject.swipeByArticleToDelete(article_title);

    }


    //Refactoring Ex11
    // Для Android статьи добовляются не просто в папку "Save", а в специально создаваемую папку.
    //Для другого способа верификации статьи я использую встроенный поиск, который реализован как в iOS, так и на Android,
    //он позволяет найти статью среди добавленых в закладки, а потом мы считаем количество оставшихся закладок, после свайпа одной.

    @Test
    public void testSaveSecondArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Nokia");

        if (Platform.getInstance().isAndroid()){
            SearchPageObject.clickByArticleWithSubstring("Finnish technology and telecommunications company");
        } else { SearchPageObject.clickByArticleWithSubstring("Finnish"); }

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = "HTC";
        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else { ArticlePageObject.addArticlesToMySaved(); }

        ArticlePageObject.closeArticleAndClearSearchLine();

        SearchPageObject.typeSearchLine("HTC");
        if(Platform.getInstance().isAndroid()){
            SearchPageObject.clickByArticleWithSubstring("Taiwanese electronics company");
        } else { SearchPageObject.clickByArticleWithSubstring("Taiwanese"); }

        ArticlePageObject.waitForTitleElement1();
        if(Platform.getInstance().isAndroid()) {
        ArticlePageObject.addArticleMyList();
        ArticlePageObject.savedArticleToMyList(name_of_folder);
        } else { ArticlePageObject.addArticlesToMySavedNotInformationWindow(); }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid())
        { MyListPageObject.openFolderByName(name_of_folder); }
        MyListPageObject.swipeByArticleToDelete(article_title);
        //другой способ верификации оставшейся статьи
        MyListPageObject.waitForArticleNotUsedTitle("Nokia");
    }

}
