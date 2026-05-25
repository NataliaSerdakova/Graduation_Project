package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import java.time.LocalDate;
import io.qameta.allure.kotlin.Allure;

public class CalendarPage extends BasePage {
    private final UiDevice device;

    public CalendarPage() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public void openCalendarForField(int fieldId) {
        Allure.step("Открыть календарь для поля с ID: " + fieldId);
        onView(withId(fieldId)).perform(click());
    }

    public void changeYear(String year) throws Exception {
        Allure.step("Смена года в календаре на: " + year);
        UiObject yearHeader = device.findObject(new UiSelector().textMatches("^\\d{4}$"));
        if (yearHeader.exists()) {
            yearHeader.click();
            UiScrollable yearList = new UiScrollable(new UiSelector().scrollable(true));
            yearList.scrollTextIntoView(year);
            device.findObject(new UiSelector().text(year)).click();
        }
    }

    public void clickNextMonth() throws Exception {
        Allure.step("Перейти к следующему месяцу");
        UiObject next = device.findObject(new UiSelector().descriptionMatches("(?i)Next month|Следующий месяц"));
        if (next.exists()) next.click();
    }

    public void clickPrevMonth() throws Exception {
        Allure.step("Перейти к предыдущему месяцу");
        UiObject prev = device.findObject(new UiSelector().descriptionMatches("(?i)Previous month|Предыдущий месяц"));
        if (prev.exists()) prev.click();
    }

    public void selectDay(String day) throws Exception {
        Allure.step("Выбрать день в календаре: " + day);
        UiObject dayBtn = device.findObject(new UiSelector().text(day).className("android.view.View"));
        if (dayBtn.exists()) dayBtn.click();
    }

    public void clickOk() throws Exception {
        Allure.step("Нажать кнопку подтверждения даты (OK)");
        UiObject ok = device.findObject(new UiSelector().textMatches("(?i)OK|ОК|ГОТОВО"));
        if (ok.exists()) ok.click();
    }

    public void clickCancel() throws Exception {
        Allure.step("Нажать кнопку отмены (CANCEL)");
        UiObject cancel = device.findObject(new UiSelector().textMatches("(?i)CANCEL|ОТМЕНА"));
        if (cancel.exists()) cancel.click();
    }

    public void checkDateInField(int fieldId, String expectedPart) {
        Allure.step("Проверка: поле содержит ожидаемую дату: " + expectedPart);
        onView(withId(fieldId)).check(matches(withText(containsString(expectedPart))));
    }

    public void checkFieldIsEmpty(int fieldId) {
        Allure.step("Проверка: поле ввода даты пустое");
        onView(withId(fieldId)).check(matches(withText("")));
    }

    private void pickDay(LocalDate targetDate) throws Exception {
        LocalDate now = LocalDate.now();
        if (targetDate.isAfter(now) && targetDate.getMonthValue() != now.getMonthValue()) {
            clickNextMonth();
        }
        if (targetDate.isBefore(now) && targetDate.getMonthValue() != now.getMonthValue()) {
            clickPrevMonth();
        }
        selectDay(String.valueOf(targetDate.getDayOfMonth()));
    }

    public void setDate(LocalDate targetDate) throws Exception {
        Allure.step("Установка полной даты: " + targetDate.toString());
        pickDay(targetDate);
        clickOk();
    }

    public void cancelDate(LocalDate targetDate) throws Exception {
        Allure.step("Выбор даты " + targetDate.toString() + " с последующей отменой");
        pickDay(targetDate);
        clickCancel();
    }
}
