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

    private final int LOGIN_LAYOUT = R.id.login_text_input_layout;
    private final int PASSWORD_LAYOUT = R.id.password_text_input_layout;
    private final int ENTER_BUTTON = R.id.enter_button;
    private final int AUTH_BUTTON = R.id.authorization_image_button;


    public void logInToTheSystem(String login, String password) {
        Allure.step("Вход в систему с логином: " + login);
        onView(isRoot()).perform(waitDisplayed(LOGIN_LAYOUT, DEFAULT_TIMEOUT));
        Allure.step("Ввод логина");
        onView(allOf(isDescendantOfA(withId(LOGIN_LAYOUT)), withClassName(containsString("EditText"))))
                .perform(replaceText(login));
        Allure.step("Ввод пароля");
        onView(allOf(isDescendantOfA(withId(PASSWORD_LAYOUT)), withClassName(containsString("EditText"))))
                .perform(replaceText(password), closeSoftKeyboard()); // Закрыли ДО нажатия кнопки
        Allure.step("Нажатие кнопки входа");
        onView(withId(ENTER_BUTTON)).perform(click());
    }

    public void logout() {
        Allure.step("Выход из системы");
        onView(isRoot()).perform(waitDisplayed(AUTH_BUTTON, DEFAULT_TIMEOUT));
        onView(withId(AUTH_BUTTON)).perform(click());
        onView(allOf(withId(android.R.id.title), withText("Log out"))).perform(click());
    }

    public void verifyTextIsVisible(String text) {
        Allure.step("Проверка видимости текста: " + text);
        onView(isRoot()).perform(waitTextDisplayed(text, DEFAULT_TIMEOUT));
        onView(withText(text)).check(matches(isDisplayed()));
    }

    public void verifyLoginPageIsDisplayed() {
        Allure.step("Проверка, что отображается страница логина");
        onView(isRoot()).perform(waitDisplayed(ENTER_BUTTON, DEFAULT_TIMEOUT));
        onView(withId(ENTER_BUTTON)).check(matches(isDisplayed()));
    }

    public void checkErrorToast(String expectedText) {
        Allure.step("Проверка появления всплывающего сообщения об ошибке: " + expectedText);
        onView(withText(containsString(expectedText)))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    public boolean isLoggedIn() {
        try {
            onView(isRoot()).perform(waitDisplayed(AUTH_BUTTON, DEFAULT_TIMEOUT));
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
