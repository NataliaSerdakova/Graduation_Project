package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import android.view.View;
import android.widget.CompoundButton;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import org.hamcrest.Matcher;
import java.time.LocalDate;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class NewsEditorPage extends BasePage {

    public static final String CREATING_SCREEN_TITLE = "Creating";
    public static final String EDITING_SCREEN_TITLE = "Editing";
    public static final String EMPTY_FIELDS_ERROR = "Fill empty fields";
    public static final String CANCEL_DIALOG_TEXT = "The changes won't be saved, do you really want to log out?";
    public static final String ERROR_SAVING_FAILED = "Saving failed. Try again later.";
    private final CalendarPage calendarPage = new CalendarPage();
    private final TimePage timePage = new TimePage();
    private final int categoryFieldId = R.id.news_item_category_text_auto_complete_text_view;
    private final int titleFieldId = R.id.news_item_title_text_input_edit_text;
    private final int dateFieldId = R.id.news_item_publish_date_text_input_edit_text;
    private final int timeFieldId = R.id.news_item_publish_time_text_input_edit_text;
    private final int descriptionFieldId = R.id.news_item_description_text_input_edit_text;
    private final int saveBtnId = R.id.save_button;
    private final int cancelBtnId = R.id.cancel_button;
    private final int switcherId = R.id.switcher;
    private final int titleInputLayoutId = R.id.news_item_title_text_input_layout;
    private final int descriptionLayoutId = R.id.news_item_description_text_input_layout;



    public void checkAddNewsFormIsLoaded() {
        Allure.step("Проверка: форма создания новости загружена");
        onView(isRoot()).perform(waitDisplayed(titleFieldId, SHORT_TIMEOUT));
        onView(withText(CREATING_SCREEN_TITLE)).check(matches(isDisplayed()));
        onView(withId(categoryFieldId)).check(matches(isDisplayed()));
        onView(withId(titleFieldId)).check(matches(isDisplayed()));
        onView(withId(dateFieldId)).check(matches(isDisplayed()));
        onView(withId(timeFieldId)).check(matches(isDisplayed()));
        onView(withId(descriptionFieldId)).check(matches(isDisplayed()));
        onView(withId(switcherId)).check(matches(allOf(isDisplayed(), isChecked(), not(isEnabled()))));
        onView(withId(saveBtnId)).check(matches(isDisplayed()));
        onView(withId(cancelBtnId)).check(matches(isDisplayed()));
    }

    public void checkEditNewsFormIsLoaded() {
        Allure.step("Проверка: форма редактирования новости загружена");
        onView(isRoot()).perform(waitDisplayed(titleFieldId, SHORT_TIMEOUT));
        onView(withText(EDITING_SCREEN_TITLE)).check(matches(isDisplayed()));
        onView(withId(categoryFieldId)).check(matches(isDisplayed()));
        onView(withId(titleFieldId)).check(matches(isDisplayed()));
        onView(withId(dateFieldId)).check(matches(isDisplayed()));
        onView(withId(timeFieldId)).check(matches(isDisplayed()));
        onView(withId(descriptionFieldId)).check(matches(isDisplayed()));
        onView(withId(switcherId)).check(matches(isDisplayed()));
        onView(withId(saveBtnId)).check(matches(isDisplayed()));
        onView(withId(cancelBtnId)).check(matches(isDisplayed()));
    }

    public void checkEditFormLoaded() {
        Allure.step("Проверка: экран редактирования новости загружен");
        onView(isRoot()).perform(waitDisplayed(titleFieldId, SHORT_TIMEOUT));
    }

    public void selectCategory(String category) {
        Allure.step("Выбор категории: " + category);
        onView(withId(categoryFieldId)).perform(replaceText(""));
        onView(withId(categoryFieldId)).perform(click(), closeSoftKeyboard());
        onView(withText(category))
                .inRoot(isPlatformPopup())
                .perform(click());
    }

    public void enterTitle(String title) {
        Allure.step("Ввод заголовка: " + title);
        onView(withId(titleFieldId)).perform(replaceText(title), closeSoftKeyboard());
    }

    public void enterDate(String date) {
        Allure.step("Ввод даты публикации: " + date);
        onView(withId(dateFieldId)).perform(replaceText(date), closeSoftKeyboard());
    }

    public void enterTime(String time) {
        Allure.step("Ввод времени публикации: " + time);
        onView(withId(timeFieldId)).perform(replaceText(time), closeSoftKeyboard());
    }

    public void enterDescription(String description) {
        Allure.step("Ввод описания новости");
        onView(withId(descriptionFieldId)).perform(scrollTo(), replaceText(description), closeSoftKeyboard());
    }

    public void checkActiveSwitcherStatus() {
        Allure.step("Проверка: переключатель 'Active' включен");
        onView(withId(switcherId)).check(matches(isChecked()));
    }

    public void clickSave() {
        Allure.step("Нажать кнопку 'Save'");
        onView(withId(saveBtnId)).perform(click());
    }

    public void openTimePicker() {
        Allure.step("Открыть окно выбора времени кликом по полю");
        onView(withId(timeFieldId)).perform(click());
    }

    public String getDateText() {
        Allure.step("Считать текст из поля даты");
        return getTextFromView(withId(dateFieldId), 0);
    }

    public String getTimeText() {
        Allure.step("Считать текст из поля времени");
        return getTextFromView(withId(timeFieldId), 0);
    }

    public void checkCategoryText(String expectedText) {
        Allure.step("Проверка: в поле категории отображается текст: " + expectedText);
        onView(withId(categoryFieldId)).check(matches(withText(expectedText)));
    }

    public void checkDateText(String date) {
        Allure.step("Проверка: в поле даты отображается: " + date);
        onView(withId(dateFieldId)).check(matches(withText(date)));
    }
    public void checkTimeText(String expectedTime) {
        Allure.step("Проверка: в поле времени отображается: " + expectedTime);
        onView(withId(timeFieldId)).check(matches(withText(expectedTime)));
    }

    public void checkSaveButtonIsDisplayed() {
        Allure.step("Проверка видимости кнопки сохранения");
        onView(withId(saveBtnId)).check(matches(isDisplayed()));
    }

    public void checkEmptyFieldsToast() {
        Allure.step("Проверка появления ошибки 'Fill empty fields'");
        onView(withText(EMPTY_FIELDS_ERROR))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    public void checkTitleFieldEmptyError() {
        Allure.step("Проверка индикации ошибки в поле заголовка");
        onView(withId(titleInputLayoutId))
                .perform(scrollTo())
                .check(matches(hasDescendant(withId(com.google.android.material.R.id.text_input_end_icon))));
    }

    public void checkDescriptionFieldEmptyError() {
        Allure.step("Проверка индикации ошибки в поле описания");
        onView(withId(descriptionLayoutId))
                .perform(scrollTo())
                .check(matches(hasDescendant(withId(com.google.android.material.R.id.text_input_end_icon))));
    }

    public void selectDateFromCalendar(LocalDate date) throws Exception {
        Allure.step("Выбор даты через календарь: " + date.toString());
        calendarPage.openCalendarForField(dateFieldId);
        calendarPage.setDate(date);
    }

    public void selectTimeFromPicker(String hours, String minutes) throws Exception {
        Allure.step("Выбор времени через селектор: " + hours + ":" + minutes);
        onView(withId(timeFieldId)).perform(click());
        timePage.selectTime(hours, minutes);
        timePage.clickOk();
    }

    public void clickCancel() {
        Allure.step("Нажать кнопку 'Cancel'");
        onView(withId(cancelBtnId)).perform(scrollTo(), click());
    }

    public void checkCancelDialogText() {
        Allure.step("Проверка текста в диалоге отмены изменений");
        onView(withText(CANCEL_DIALOG_TEXT)).check(matches(isDisplayed()));
    }

    public void confirmCancelDialog() {
        Allure.step("Подтверждение отмены изменений в диалоге");
        onView(withId(android.R.id.button1)).perform(click());
    }

    public void enterCustomCategory(String text) {
        Allure.step("Ввести произвольную категорию вручную: " + text);
        onView(withId(categoryFieldId)).perform(replaceText(text), closeSoftKeyboard());
    }

    public void checkSavingFailedToast() {
        Allure.step("Проверка появления сообщения о сбое сохранения");
        onView(withText(ERROR_SAVING_FAILED))
                .inRoot(new BasePage.ToastMatcher())
                .check(matches(isDisplayed()));
    }

    public void openCalendar() {
        Allure.step("Открыть календарь кликом по полю даты");
        onView(withId(dateFieldId)).perform(click());
    }

    public void toggleActiveStatus(boolean shouldBeActive) {
        Allure.step("Установка переключателя 'Active' в состояние: " + shouldBeActive);
        onView(withId(switcherId)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(CompoundButton.class);
            }
            @Override
            public String getDescription() {
                return "установка статуса активен/неактивен";
            }
            @Override
            public void perform(UiController uiController, View view) {
                CompoundButton switchView = (CompoundButton) view;
                if (switchView.isChecked() != shouldBeActive) {
                    switchView.performClick();
                }
            }
        });
    }
}
