package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class ControlPanelFilterPage extends NewsFilterPage{

    private final int activeCheckboxId = R.id.filter_news_active_material_check_box;
    private final int notActiveCheckboxId = R.id.filter_news_inactive_material_check_box;

    @Override
    public void checkFilterFormIsLoaded() {
        super.checkFilterFormIsLoaded();
        Allure.step("Проверка наличия чекбоксов статуса (Active/Not Active)");
        onView(withId(activeCheckboxId)).check(matches(isDisplayed()));
        onView(withId(notActiveCheckboxId)).check(matches(isDisplayed()));
    }

    public void checkActiveCheckboxChecked() {
        Allure.step("Проверка: чекбокс 'Active' отмечен");
        onView(withId(activeCheckboxId)).check(matches(isChecked()));
    }

    public void checkNotActiveCheckboxChecked() {
        Allure.step("Проверка: чекбокс 'Not Active' отмечен");
        onView(withId(notActiveCheckboxId)).check(matches(isChecked()));
    }

    public void clickActiveCheckbox() {
        Allure.step("Нажать на чекбокс 'Active'");
        onView(withId(activeCheckboxId)).perform(click());
    }

    public void clickNotActiveCheckbox() {
        Allure.step("Нажать на чекбокс 'Not Active'");
        onView(withId(notActiveCheckboxId)).perform(click());
    }
}
