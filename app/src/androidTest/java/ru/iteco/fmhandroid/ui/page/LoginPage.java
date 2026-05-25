package ru.iteco.fmhandroid.ui.page;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class LoginPage extends BasePage {
    public static final String MAIN_SCREEN_TITLE = "News";
    public static final String ERROR_INVALID_DATA = "Something went wrong. Try again later.";
    public static final String ERROR_EMPTY_FIELDS = "Login and password cannot be empty";
    public static final String LOGOUT_ACTION_TEXT = "Log out";
    private static final String EDIT_TEXT_CLASS_NAME = "EditText";
    private final int loginLayout = R.id.login_text_input_layout;
    private final int passwordLayout = R.id.password_text_input_layout;
    private final int enterButton = R.id.enter_button;
    private final int authButton = R.id.authorization_image_button;


    public void logInToTheSystem(String login, String password) {
        Allure.step("Вход в систему с логином: " + login);
        onView(isRoot()).perform(waitDisplayed(loginLayout, DEFAULT_TIMEOUT));
        Allure.step("Ввод логина");
        onView(allOf(isDescendantOfA(withId(loginLayout)), withClassName(containsString(EDIT_TEXT_CLASS_NAME))))
                .perform(replaceText(login));
        Allure.step("Ввод пароля");
        onView(allOf(isDescendantOfA(withId(passwordLayout)), withClassName(containsString(EDIT_TEXT_CLASS_NAME))))
                .perform(replaceText(password), closeSoftKeyboard()); // Закрыли ДО нажатия кнопки
        Allure.step("Нажатие кнопки входа");
        onView(withId(enterButton)).perform(click());
    }

    public void logout() {
        Allure.step("Выход из системы");
        onView(isRoot()).perform(waitDisplayed(authButton, DEFAULT_TIMEOUT));
        onView(withId(authButton)).perform(click());
        onView(allOf(withId(android.R.id.title), withText(LOGOUT_ACTION_TEXT))).perform(click());
    }

    public void verifyTextIsVisible(String text) {
        Allure.step("Проверка видимости текста: " + text);
        onView(isRoot()).perform(waitTextDisplayed(text, DEFAULT_TIMEOUT));
        onView(withText(text)).check(matches(isDisplayed()));
    }

    public void verifyLoginPageIsDisplayed() {
        Allure.step("Проверка, что отображается страница логина");
        onView(isRoot()).perform(waitDisplayed(enterButton, DEFAULT_TIMEOUT));
        onView(withId(enterButton)).check(matches(isDisplayed()));
    }

    public void checkErrorToast(String expectedText) {
        Allure.step("Проверка появления всплывающего сообщения об ошибке: " + expectedText);
        onView(withText(containsString(expectedText)))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    public boolean isLoggedIn() {
        try {
            onView(isRoot()).perform(waitDisplayed(authButton, DEFAULT_TIMEOUT));
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public void ensureLoggedIn(String login, String password) {
        Allure.step("Гарантированная авторизация");
        if (!isLoggedIn()) {
            logInToTheSystem(login, password);
        }
    }
}
