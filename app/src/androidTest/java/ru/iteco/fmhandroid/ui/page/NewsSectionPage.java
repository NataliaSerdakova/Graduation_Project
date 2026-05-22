package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.not;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class NewsSectionPage extends BasePage {

    private final int sortBtnId = R.id.sort_news_material_button;
    private final int filterBtnId = R.id.filter_news_material_button;
    private final int editBtnId = R.id.edit_news_material_button;
    private final int controlPanelNewsListId = R.id.news_list_recycler_view;
    private final int newsRefreshBtnId = R.id.news_retry_material_button;
    private final int newsTitleId = R.id.news_item_title_text_view;
    private final int expandBtnId = R.id.view_news_item_image_view;
    private final int descriptionId = R.id.news_item_description_text_view;
    private final int newsContainerId = R.id.all_news_cards_block_constraint_layout;
    private final int retryBtnId = R.id.news_retry_material_button;

    public void clickSort() {
        Allure.step("Нажать кнопку сортировки новостей");
        onView(withId(sortBtnId)).perform(click());
    }

    public String getFirstNewsTitle() {
        Allure.step("Получить заголовок первой новости");
        return getTextFromView(withId(newsTitleId), 0);
    }

    public void checkFirstNewsTitleChanged(String oldTitle) {
        Allure.step("Проверка: заголовок первой новости изменился и больше не равен '" + oldTitle + "'");
        onView(isRoot()).perform(waitTextChange(withIndex(withId(newsTitleId), 0), oldTitle, DEFAULT_TIMEOUT));
        onView(withIndex(withId(newsTitleId), 0)).check(matches(not(withText(oldTitle))));
    }

    public void checkFirstNewsTitle(String expectedTitle) {
        Allure.step("Проверка: заголовок первой новости равен '" + expectedTitle + "'");
        onView(withIndex(withId(newsTitleId), 0))
                .check(matches(withText(expectedTitle)));
    }

    public void clickFilter() {
        Allure.step("Открыть фильтр новостей");
        onView(isRoot()).perform(waitDisplayed(filterBtnId, SHORT_TIMEOUT));
        onView(withId(filterBtnId)).perform(click());
    }


    public void clickEdit() {
        Allure.step("Перейти в режим редактирования новостей (Control Panel)");
        onView(isRoot()).perform(waitDisplayed(editBtnId, SHORT_TIMEOUT));
        onView(withId(editBtnId)).perform(click());
    }

    public void checkControlPanelIsLoaded() {
        Allure.step("Проверка: панель управления новостями загружена");
        onView(isRoot()).perform(waitDisplayed(controlPanelNewsListId, SHORT_TIMEOUT));
        onView(withText("Control panel")).check(matches(isDisplayed()));
        onView(withId(controlPanelNewsListId)).check(matches(isDisplayed()));
    }

    public void checkEmptyListAndRefreshButtonVisible() {
        Allure.step("Проверка: список пуст, отображается кнопка обновления");
        onView(isRoot()).perform(waitDisplayed(newsRefreshBtnId, DEFAULT_TIMEOUT));
        onView(withId(newsRefreshBtnId)).check(matches(isDisplayed()));
    }

    public void clickRefresh() {
        Allure.step("Нажать кнопку обновления списка");
        onView(withId(newsRefreshBtnId)).perform(click());
    }

    public void expandFirstNews() {
        Allure.step("Развернуть/свернуть первую новость");
        onView(isRoot()).perform(waitDisplayed(expandBtnId, DEFAULT_TIMEOUT));
        onView(withIndex(allOf(withId(expandBtnId), isDisplayed()), 0))
                .perform(click());
    }

    public void checkDescriptionVisible() {
        Allure.step("Проверка: описание новости отображается");
        onView(withIndex(withId(descriptionId), 0))
                .check(matches(isDisplayed()));
    }

    public void checkFilterResultVisible() {
        Allure.step("Проверка видимости результата фильтрации (список или кнопка повтора)");
        onView(isRoot()).perform(waitDisplayed(newsContainerId, SHORT_TIMEOUT));
        onView(withIndex(anyOf(
                allOf(withId(newsContainerId), isDisplayed()),
                allOf(withId(retryBtnId), isDisplayed())
        ), 0)).check(matches(isDisplayed()));
    }
}
