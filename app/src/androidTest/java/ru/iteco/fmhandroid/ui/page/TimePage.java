package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import io.qameta.allure.kotlin.Allure;

public class TimePage extends BasePage {
    private UiDevice device;
    public static final String VALID_TIME_ERROR = "Enter a valid time";

    public TimePage() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    public void selectTime(String hours, String minutes) throws Exception {
        Allure.step("Выбор времени в режиме часов: " + hours + ":" + minutes);
        UiObject hoursObj = device.findObject(new UiSelector().description(hours));
        if (!hoursObj.exists()) {
            hoursObj = device.findObject(new UiSelector().text(hours));
        }

        if (hoursObj.exists()) {
            hoursObj.click();
        } else {
            throw new RuntimeException("Не удалось найти цифру часов: " + hours);
        }

        UiObject minutesObj = device.findObject(new UiSelector().description(minutes));
        if (!minutesObj.exists()) {
            minutesObj = device.findObject(new UiSelector().text(minutes));
        }

        if (minutesObj.waitForExists(SHORT_TIMEOUT)) {
            minutesObj.click();
        } else {
            throw new RuntimeException("Не удалось дождаться появления минут: " + minutes);
        }
    }

    public void clickOk() throws Exception {
        Allure.step("Нажать кнопку подтверждения времени (OK)");
        UiObject okBtn = device.findObject(new UiSelector().textMatches("(?i)OK|ОК|ГОТОВО"));
        if (okBtn.exists()) {
            okBtn.click();
        }
    }

    public void clickCancel() throws Exception {
        Allure.step("Нажать кнопку отмены выбора времени (CANCEL)");
        UiObject cancelBtn = device.findObject(new UiSelector().textMatches("(?i)CANCEL|ОТМЕНА"));
        if (cancelBtn.exists()) {
            cancelBtn.click();
        }
    }

    public void switchToKeyboardMode() throws Exception {
        Allure.step("Переключиться на ручной ввод времени (клавиатура)");
        UiObject toKeyboard = device.findObject(new UiSelector()
                .description("Switch to text input mode for the time input."));

        if (toKeyboard.waitForExists(SHORT_TIMEOUT)) {
            toKeyboard.click();
        } else {
            throw new RuntimeException("Кнопка переключения на клавиатуру не найдена");
        }
    }

    public void switchToClockMode() throws Exception {
        Allure.step("Переключиться на режим выбора времени по часам");
        UiObject toClock = device.findObject(new UiSelector()
                .descriptionContains("clock mode"));

        if (toClock.waitForExists(SHORT_TIMEOUT)) {
            toClock.click();
        } else {
            throw new RuntimeException("Кнопка переключения на режим часов не найдена");
        }
    }

    public void typeTime(String hours, String minutes) throws Exception {
        Allure.step("Ввести время вручную: " + hours + ":" + minutes);
        UiObject hoursField = device.findObject(new UiSelector()
                .className("android.widget.EditText").instance(0));
        hoursField.setText(hours);

        UiObject minutesField = device.findObject(new UiSelector()
                .className("android.widget.EditText").instance(1));
        minutesField.setText(minutes);
    }

    public void checkValidTimeError() {
        Allure.step("Проверка наличия ошибки: " + VALID_TIME_ERROR);
        onView(withText(VALID_TIME_ERROR)).check(matches(isDisplayed()));
    }

    public void checkTimePickerIsStillOpen() throws Exception {
        Allure.step("Проверка, что окно выбора времени всё еще открыто");
        UiObject okBtn = device.findObject(new UiSelector().resourceId("android:id/button1"));
        if (!okBtn.exists()) {
            throw new RuntimeException("Окно часов закрылось, хотя должна была появиться ошибка");
        }
    }
}
