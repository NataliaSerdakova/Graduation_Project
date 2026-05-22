package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class QuotesPage extends BasePage {
    private final int expandBtnId = R.id.our_mission_item_open_card_image_button;
    private final int descriptionId = R.id.our_mission_item_description_text_view;

    public void expandFirstQuote() {
        Allure.step("Развернуть первую цитату в списке");
        onView(isRoot()).perform(waitDisplayed(expandBtnId, SHORT_TIMEOUT));
        onView(withIndex(allOf(withId(expandBtnId), isDisplayed()), 0))
                .perform(click());
    }

    public void checkDescriptionVisible() {
        Allure.step("Проверка: текст цитаты отображается");
        onView(withIndex(withId(descriptionId), 0))
                .check(matches(isDisplayed()));
    }
}
