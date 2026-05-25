package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.not;
import androidx.test.espresso.contrib.RecyclerViewActions;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class ControlPanelPage extends BasePage {

    public static final String CONTROL_PANEL_TITLE = "Control panel";
    private final String DELETE_DIALOG_TEXT = "Are you sure you want to permanently delete the document? These changes cannot be reversed in the future.";
    private final int sortBtnId = R.id.sort_news_material_button;
    private final int filterBtnId = R.id.filter_news_material_button;
    private final int addNewsBtnId = R.id.add_news_image_view;
    private final int editNewsItemBtnId = R.id.edit_news_item_image_view;
    private final int deleteBtnId = R.id.delete_news_item_image_view;
    private final int newsDateId = R.id.news_item_publication_date_text_view;
    private final int newsTitleId = R.id.news_item_title_text_view;
    private final int expandNewsBtnId = R.id.view_news_item_image_view;
    private final int newsDescriptionId = R.id.news_item_description_text_view;
    private final int controlPanelRefreshBtnId = R.id.control_panel_news_retry_material_button;
    private final int controlPanelContainerId = R.id.container_custom_app_bar_include_on_fragment_news_control_panel;
    private final int newsListId = R.id.news_list_recycler_view;
    private final int newsStatusId = R.id.news_item_published_text_view;

    public void checkControlPanelLoaded() {
        Allure.step("Проверка загрузки панели управления (Control Panel)");
        onView(isRoot()).perform(waitTextDisplayed(CONTROL_PANEL_TITLE, DEFAULT_TIMEOUT));
        onView(withText(CONTROL_PANEL_TITLE)).check(matches(isDisplayed()));
    }

    public void clickSort() {
        Allure.step("Нажать кнопку сортировки");
        onView(withId(sortBtnId)).perform(click());
    }

    public void clickFilter() {
        Allure.step("Открыть фильтр в панели управления");
        onView(withId(filterBtnId)).perform(click());
    }

    public void clickAddNews() {
        Allure.step("Нажать кнопку добавления новости (+)");
        onView(isRoot()).perform(waitDisplayed(addNewsBtnId, SHORT_TIMEOUT));
        onView(withId(addNewsBtnId)).perform(click());
    }

    public void clickEditNews(int index) {
        Allure.step("Нажать кнопку редактирования новости с индексом: " + index);
        onView(withIndex(withId(editNewsItemBtnId), index)).perform(click());
    }

    public void clickExpandNews(int index) {
        Allure.step("Развернуть новость с индексом: " + index);
        onView(withIndex(withId(expandNewsBtnId), index)).perform(click());
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

    public void checkNewsWithTitleExists(String title) {
        Allure.step("Поиск новости с заголовком: " + title);
        onView(withId(newsListId))
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(title))
                ));
        onView(withText(title)).check(matches(isDisplayed()));
    }

    public void clickDeleteNews(int index) {
        Allure.step("Нажать кнопку удаления новости с индексом: " + index);
        onView(withIndex(withId(deleteBtnId), index)).perform(click());
    }

    public void checkDeleteDialogDisplayed() {
        Allure.step("Проверка отображения диалога подтверждения удаления");
        onView(withText(DELETE_DIALOG_TEXT)).check(matches(isDisplayed()));
    }

    public void confirmDeletion() {
        Allure.step("Подтвердить удаление (кнопка OK)");
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void cancelDeletion() {
        Allure.step("Отменить удаление (кнопка Cancel)");
        onView(withId(android.R.id.button2)).perform(click());
    }

    public String getFirstNewsDate() {
        Allure.step("Получить дату публикации первой новости");
        return getTextFromView(withId(newsDateId), 0);
    }

    public void checkFirstNewsDateChanged(String oldDate) {
        Allure.step("Проверка: дата первой новости изменилась и не равна: " + oldDate);
        onView(withIndex(withId(newsDateId), 0))
                .check(matches(not(withText(oldDate))));
    }

    public void checkFirstNewsDate(String expectedDate) {
        Allure.step("Проверка: дата первой новости соответствует ожидаемой: " + expectedDate);
        onView(withIndex(withId(newsDateId), 0))
                .check(matches(withText(expectedDate)));
    }

    public void checkDescriptionVisible(int index) {
        Allure.step("Проверка видимости описания новости с индексом: " + index);
        onView(withIndex(withId(newsDescriptionId), index)).check(matches(isDisplayed()));
    }

    public void checkDescriptionText(int index, String expectedText) {
        Allure.step("Проверка текста описания новости: " + expectedText);
        onView(withIndex(withId(newsDescriptionId), index))
                .check(matches(withText(expectedText)));
    }

    public void checkFirstNewsStatus(String expectedStatus) {
        Allure.step("Проверка статуса первой новости (Published/Not Published): " + expectedStatus);
        onView(withIndex(withId(newsStatusId), 0))
                .check(matches(withText(expectedStatus)));
    }

    public void checkEmptyListAndRefreshButtonVisible() {
        Allure.step("Проверка: список пуст, отображается кнопка Refresh");
        onView(isRoot()).perform(waitDisplayed(controlPanelRefreshBtnId, DEFAULT_TIMEOUT));
        onView(withId(controlPanelRefreshBtnId)).check(matches(isDisplayed()));
    }

    public void checkFilterResultVisible() {
        Allure.step("Проверка отображения списка новостей в панели управления");
        onView(isRoot()).perform(waitDisplayed(controlPanelContainerId, SHORT_TIMEOUT));
        onView(anyOf(
                allOf(withId(newsListId), isDisplayed()),
                allOf(withId(controlPanelRefreshBtnId), isDisplayed())
        )).check(matches(isDisplayed()));
    }

    public void checkFirstNewsStatusIfPresent(String expectedStatus) {
        Allure.step("Проверка статуса первой новости, если список не пуст");
        try {
            onView(withIndex(withId(newsStatusId), 0))
                    .check(matches(isDisplayed()));
            checkFirstNewsStatus(expectedStatus);
        } catch (AssertionError | Exception e) {
            checkEmptyListAndRefreshButtonVisible();
        }
    }

    public void checkNewsCountDecreased(String title, int initialCount) {
        Allure.step("Проверка уменьшения количества новостей с заголовком: " + title);
        waitForIdle(SHORT_TIMEOUT);
        int currentCount = getNewsCount(title);
        if (currentCount != initialCount - 1) {
            throw new AssertionError("Количество новостей не уменьшилось! Было: " + initialCount + ", стало: " + currentCount);
        }
    }

    public int getNewsCount(String title) {
        Allure.step("Подсчет количества новостей с заголовком: " + title);
        return getCount(allOf(withId(newsTitleId), withText(title)));
    }
}
