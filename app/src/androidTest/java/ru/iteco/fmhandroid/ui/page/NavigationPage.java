package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class NavigationPage extends BasePage {

    public static final String MENU_MAIN_TEXT = "Main";
    public static final String MENU_NEWS_TEXT = "News";
    public static final String MENU_ABOUT_TEXT = "About";
    private final int mainMenuBtnId = R.id.main_menu_image_button;
    private final int newsContainerId = R.id.all_news_cards_block_constraint_layout;
    private final int aboutVersionId = R.id.about_version_value_text_view;
    private final int aboutPrivacyPolicyId = R.id.about_privacy_policy_value_text_view;
    private final int aboutTermsOfUseId = R.id.about_terms_of_use_value_text_view;
    private final int aboutBackBtnId = R.id.about_back_image_button;
    private final int newsListOnMainId = R.id.container_list_news_include_on_fragment_main;
    private final int allNewsLinkId = R.id.all_news_text_view;
    private final int butterflyBtnId = R.id.our_mission_image_button;
    private final int quotesTitleId = R.id.our_mission_title_text_view;
    private final int quotesListId = R.id.our_mission_item_list_recycler_view;

    public void openNavigationMenu() {
        Allure.step("Открыть главное навигационное меню");
        onView(isRoot()).perform(waitDisplayed(mainMenuBtnId, SHORT_TIMEOUT));
        onView(withId(mainMenuBtnId)).perform(click());
    }

    public void clickNews() {
        Allure.step("Выбрать в меню раздел 'News'");
        onView(withText(MENU_NEWS_TEXT)).perform(click());
    }

    public void clickAbout() {
        Allure.step("Выбрать в меню раздел 'About'");
        onView(withText(MENU_ABOUT_TEXT)).perform(click());
    }

    public void clickMain() {
        Allure.step("Выбрать в меню раздел 'Main'");
        onView(withText(MENU_MAIN_TEXT)).perform(click());
    }

    public void checkIsNewsPage() {
        Allure.step("Проверка: экран раздела News отображается");
        onView(isRoot()).perform(waitDisplayed(newsContainerId, SHORT_TIMEOUT));
        onView(withId(newsContainerId)).check(matches(isDisplayed()));
    }

    public void checkIsAboutPage() {
        Allure.step("Проверка: экран раздела About отображается");
        onView(isRoot()).perform(waitDisplayed(aboutVersionId, SHORT_TIMEOUT));
        onView(withId(aboutVersionId)).check(matches(isDisplayed()));
        onView(withId(aboutPrivacyPolicyId)).check(matches(isDisplayed()));
        onView(withId(aboutTermsOfUseId)).check(matches(isDisplayed()));
    }

    public void checkMenuElementsDisplayed() {
        Allure.step("Проверка отображения элементов меню (Main, News, About)");
        onView(isRoot()).perform(waitTextDisplayed(MENU_NEWS_TEXT, SHORT_TIMEOUT));
        onView(withText(MENU_MAIN_TEXT)).check(matches(isDisplayed()));
        onView(withText(MENU_NEWS_TEXT)).check(matches(isDisplayed()));
        onView(withText(MENU_ABOUT_TEXT)).check(matches(isDisplayed()));
    }

    public void clickBack() {
        Allure.step("Нажать кнопку 'Назад' на экране About");
        onView(isRoot()).perform(waitDisplayed(aboutBackBtnId, SHORT_TIMEOUT));
        onView(withId(aboutBackBtnId)).perform(click());
    }

    public void checkIsMainPage() {
        Allure.step("Проверка: экран раздела Main отображается");
        onView(isRoot()).perform(waitDisplayed(newsListOnMainId, SHORT_TIMEOUT));
        onView(withId(newsListOnMainId)).check(matches(isDisplayed()));
        onView(withId(allNewsLinkId)).check(matches(isDisplayed()));
    }

    public void clickButterfly() {
        Allure.step("Нажать на иконку бабочки (Тематические цитаты)");
        onView(isRoot()).perform(waitDisplayed(butterflyBtnId, SHORT_TIMEOUT));
        onView(withId(butterflyBtnId)).perform(click());
    }

    public void checkIsQuotesPage() {
        Allure.step("Проверка: экран тематических цитат отображается");
        onView(isRoot()).perform(waitDisplayed(quotesTitleId, SHORT_TIMEOUT));
        onView(withId(quotesListId)).check(matches(isDisplayed()));
    }

    public void clickAllNewsLink() {
        Allure.step("Нажать на ссылку 'All News' на главном экране");
        onView(isRoot()).perform(waitDisplayed(allNewsLinkId, SHORT_TIMEOUT));
        onView(withId(allNewsLinkId)).perform(click());
    }
}

