package ru.iteco.fmhandroid.ui.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.AllureJunit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.page.LoginPage;

@LargeTest
@RunWith(AndroidJUnit4.class)
@Epic("Авторизация")
@Feature("Форма входа в систему")

public class LoginTest  {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);


    private final LoginPage loginPage = new LoginPage();

    @Before
    public void setUp() {
        if (loginPage.isLoggedIn()) {
            loginPage.logout();
        }
    }

    @Test
    @DisplayName("Успешная авторизация")
    @Description("Вход в систему с валидными данными: login2/password2")
    public void successfulAuthorization_1() {
        loginPage.logInToTheSystem("login2", "password2");
        loginPage.verifyTextIsVisible(LoginPage.MAIN_SCREEN_TITLE);
    }

    @Test
    @DisplayName("Ошибка при невалидном логине")
    @Description("Проверка появления ошибки при вводе несуществующего логина")
    public void invalidLogin_2() {
        loginPage.logInToTheSystem("test", "password2");
        loginPage.checkErrorToast(LoginPage.ERROR_INVALID_DATA);
    }

    @Test
    @DisplayName("Спецсимволы в логине")
    @Description("Проверка системы при вводе символов !@#$%^")
    public void specialCharactersInLogin_4() {
        loginPage.logInToTheSystem("!@#$%^", "password2");
        loginPage.checkErrorToast(LoginPage.ERROR_INVALID_DATA);
    }

    @Test
    @DisplayName("Пустой логин")
    @Description("Проверка валидации при пустом поле логина")
    public void emptyLogin_6() {
        loginPage.logInToTheSystem("", "password2");
        loginPage.checkErrorToast(LoginPage.ERROR_EMPTY_FIELDS);
    }

    @Test
    @DisplayName("Регистр в логине")
    @Description("Проверка чувствительности системы к регистру в логине")
    public void loginRegister_9() {
        loginPage.logInToTheSystem("Login2", "password2");
        loginPage.checkErrorToast(LoginPage.ERROR_INVALID_DATA);
    }

    @Test
    @DisplayName("Невалидный пароль")
    @Description("Вход с верным логином, но неверным паролем")
    public void invalidPassword_10() {
        loginPage.logInToTheSystem("login2", "test");
        loginPage.checkErrorToast(LoginPage.ERROR_INVALID_DATA);
    }

    @Test
    @DisplayName("Спецсимволы в пароле")
    @Description("Проверка реакции системы на ввод специальных символов (&$#*@) в поле пароля")
    public void specialCharactersInPassword_13() {
        loginPage.logInToTheSystem("login2", "(&$#*@)");
        loginPage.checkErrorToast(LoginPage.ERROR_INVALID_DATA);
    }

    @Test
    @DisplayName("Пустой пароль")
    @Description("Проверка валидации при заполнении логина и пустом поле пароля")
    public void emptyPassword_15() {
        loginPage.logInToTheSystem("login2", "");
        loginPage.checkErrorToast(LoginPage.ERROR_EMPTY_FIELDS);
    }

    @Test
    @DisplayName("Пустые поля логина и пароля")
    @Description("Проверка невозможности авторизации, если оба поля не заполнены")
    public void emptyLoginAndPassword_17() {
        loginPage.logInToTheSystem("", "");
        loginPage.checkErrorToast(LoginPage.ERROR_EMPTY_FIELDS);
    }

    @Test
    @DisplayName("Успешный выход из системы")
    @Description("Авторизация и последующий логаут с проверкой возврата на экран логина")
    public void successfulLogout_18() {
        loginPage.ensureLoggedIn("login2", "password2");
        loginPage.logout();
        loginPage.verifyLoginPageIsDisplayed();
    }
}
