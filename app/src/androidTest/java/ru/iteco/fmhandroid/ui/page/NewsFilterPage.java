package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class NewsFilterPage extends BasePage {

    public static final String FILTER_SCREEN_TITLE = "Filter news";
    public static final String ERROR_WRONG_PERIOD = "Wrong period";
    private final int filterTitleId = R.id.filter_news_title_text_view;
    private final int categoryFieldId = R.id.news_item_category_text_auto_complete_text_view;
    private final int dateStartId = R.id.news_item_publish_date_start_text_input_edit_text;
    private final int dateEndId = R.id.news_item_publish_date_end_text_input_edit_text;
    private final int filterActionBtnId = R.id.filter_button;
    private final int cancelBtnId = R.id.cancel_button;

    public void checkFilterFormIsLoaded() {
        Allure.step("Проверка: форма фильтрации новостей загружена");
        onView(isRoot()).perform(waitDisplayed(filterTitleId, SHORT_TIMEOUT));
        onView(withId(filterTitleId)).check(matches(withText(FILTER_SCREEN_TITLE)));
        onView(withId(categoryFieldId)).check(matches(isDisplayed()));
        onView(withId(dateStartId)).check(matches(isDisplayed()));
        onView(withId(dateEndId)).check(matches(isDisplayed()));
        onView(withId(filterActionBtnId)).check(matches(isDisplayed()));
        onView(withId(cancelBtnId)).check(matches(isDisplayed()));
    }

    public void selectCategory(String category) {
        Allure.step("Выбрать категорию из списка: " + category);
        onView(withId(categoryFieldId)).perform(click());
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withText(category)).inRoot(isPlatformPopup()).perform(click());
    }

    public void enterStartDate(String date) {
        Allure.step("Ввести дату начала периода: " + date);
        onView(withId(dateStartId)).perform(replaceText(date), closeSoftKeyboard());
        onView(withId(dateStartId)).check(matches(withText(date)));
    }

    public void enterEndDate(String date) {
        Allure.step("Ввести дату окончания периода: " + date);
        onView(withId(dateEndId)).perform(replaceText(date), closeSoftKeyboard());
        onView(withId(dateEndId)).check(matches(withText(date)));
    }

    public void clickFilterSubmit() {
        Allure.step("Нажать кнопку 'Filter' (применить фильтр)");
        onView(withId(filterActionBtnId)).perform(click());
    }

    public void clickCancel() {
        Allure.step("Нажать кнопку 'Cancel' (отмена фильтрации)");
        onView(withId(cancelBtnId)).perform(click());
    }

    public void checkWrongPeriodError() {
        Allure.step("Проверка появления ошибки 'Wrong period'");
        onView(withText(ERROR_WRONG_PERIOD)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void openStartDateCalendar() {
        Allure.step("Открыть календарь для выбора даты начала");
        onView(isRoot()).perform(waitDisplayed(dateStartId, SHORT_TIMEOUT));
        onView(withId(dateStartId)).perform(click());
    }

    public void checkStartDateFieldIsEmpty() {
        Allure.step("Проверка: поле даты начала пустое");
        onView(withId(dateStartId)).check(matches(withText("")));
    }

    public void checkStartDateFieldHasText(String expectedText) {
        Allure.step("Проверка: поле даты содержит текст: " + expectedText);
        onView(withId(dateStartId)).check(matches(withText(containsString(expectedText))));
    }

    public void enterCustomCategory(String text) {
        Allure.step("Ввести произвольную категорию: " + text);
        onView(withId(categoryFieldId)).perform(replaceText(text), closeSoftKeyboard());
    }
}
