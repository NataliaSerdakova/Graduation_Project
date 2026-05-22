package ru.iteco.fmhandroid.ui.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.LoginPage;
import ru.iteco.fmhandroid.ui.page.NavigationPage;
import ru.iteco.fmhandroid.ui.page.QuotesPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Тематические цитаты")
@Feature("Просмотр цитат")

public class QuotesTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    private final LoginPage loginPage = new LoginPage();
    private final NavigationPage navigationPage = new NavigationPage();
    private final QuotesPage quotesPage = new QuotesPage();

    @Before
    public void setUp() {
        if (!loginPage.isLoggedIn()) {
            loginPage.logInToTheSystem(DataHelper.getValidLogin(), DataHelper.getValidPassword());
        } else {
            loginPage.logout();
            loginPage.logInToTheSystem(DataHelper.getValidLogin(), DataHelper.getValidPassword());
        }
        navigationPage.clickButterfly();
        navigationPage.checkIsQuotesPage();
    }

    @Test
    @DisplayName("Развертывание текста цитаты")
    @Description("Проверка отображения подробного описания цитаты при нажатии на кнопку развертывания")
    public void disclosureOfTheQuoteDescription_25() {
        quotesPage.expandFirstQuote();
        quotesPage.checkDescriptionVisible();
    }
}
