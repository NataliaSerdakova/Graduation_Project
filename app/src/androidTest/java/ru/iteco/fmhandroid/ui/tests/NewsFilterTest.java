package ru.iteco.fmhandroid.ui.tests;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.time.LocalDate;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Feature;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.DataHelper;
import ru.iteco.fmhandroid.ui.page.CalendarPage;
import ru.iteco.fmhandroid.ui.page.LoginPage;
import ru.iteco.fmhandroid.ui.page.NavigationPage;
import ru.iteco.fmhandroid.ui.page.NewsFilterPage;
import ru.iteco.fmhandroid.ui.page.NewsSectionPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Раздел Новости")
@Feature("Фильтрация новостей")

public class NewsFilterTest {

    private final LoginPage loginPage = new LoginPage();
    private final NavigationPage navigationPage = new NavigationPage();
    private final NewsSectionPage newsSectionPage = new NewsSectionPage();
    private final NewsFilterPage newsFilterPage = new NewsFilterPage();
    private final CalendarPage calendarPage = new CalendarPage();
    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        if (!loginPage.isLoggedIn()) {
            loginPage.logInToTheSystem(DataHelper.getValidLogin(), DataHelper.getValidPassword());
        } else {
            loginPage.logout();
            loginPage.logInToTheSystem(DataHelper.getValidLogin(), DataHelper.getValidPassword());
        }
        navigationPage.openNavigationMenu();
        navigationPage.clickNews();
        navigationPage.checkIsNewsPage();
    }

    @Test
    @DisplayName("Фильтрация по всем полям")
    @Description("Установка категории и корректного временного интервала")
    public void filteringNewsByAllFieldsInNews_35() {
        String startDate = DataHelper.getDate(-1);
        String endDate = DataHelper.getDate(0);
        String category = DataHelper.getRandomCategory();
        newsSectionPage.clickFilter();
        newsFilterPage.checkFilterFormIsLoaded();
        newsFilterPage.selectCategory(category);
        newsFilterPage.enterStartDate(startDate);
        newsFilterPage.enterEndDate(endDate);
        newsFilterPage.clickFilterSubmit();
        newsSectionPage.checkFilterResultVisible();
    }

    @Test
    @DisplayName("Отмена фильтрации")
    @Description("Заполнение данных фильтра и нажатие кнопки Cancel")
    public void cancelingNewsFilteringByAllFieldsInNews_36() {
        String category = DataHelper.getRandomCategory();
        newsSectionPage.clickFilter();
        newsFilterPage.checkFilterFormIsLoaded();
        newsFilterPage.selectCategory(category);
        newsFilterPage.clickCancel();
        newsSectionPage.checkFilterResultVisible();
    }

    @Test
    @DisplayName("Фильтрация только по категории")
    @Description("Выбор категории без указания дат")
    public void filteringNewsByCategoryInNews_37() {
        String category = DataHelper.getRandomCategory();
        newsSectionPage.clickFilter();
        newsFilterPage.checkFilterFormIsLoaded();
        newsFilterPage.selectCategory(category);
        newsFilterPage.clickFilterSubmit();
        newsSectionPage.checkFilterResultVisible();
    }

    @Test
    @DisplayName("Фильтрация по кастомной категории")
    @Description("Ввод названия категории вручную")
    public void filteringNewsByAnotherCategoryInNews_38() {
        newsSectionPage.clickFilter();
        newsFilterPage.enterCustomCategory("Заявление");
        newsFilterPage.clickFilterSubmit();
        newsSectionPage.checkFilterResultVisible();
    }

    @Test
    @DisplayName("Фильтрация по валидному периоду")
    @Description("Выбор дат за последнюю неделю")
    public void filteringNewsByValidPeriodInNews_39() {
        String startDate = DataHelper.getDate(-7);
        String endDate = DataHelper.getDate(0);
        newsSectionPage.clickFilter();
        newsFilterPage.checkFilterFormIsLoaded();
        newsFilterPage.enterStartDate(startDate);
        newsFilterPage.enterEndDate(endDate);
        newsFilterPage.clickFilterSubmit();
        newsSectionPage.checkFilterResultVisible();
    }

    @Test
    @DisplayName("Ошибка при пустой дате окончания")
    @Description("Ввод только даты начала периода и проверка уведомления об ошибке")
    public void filteringNewsByTheBeginningOfThePeriodInNews_40() {
        String startDate = DataHelper.getDate(0);
        newsSectionPage.clickFilter();
        newsFilterPage.checkFilterFormIsLoaded();
        newsFilterPage.enterStartDate(startDate);
        newsFilterPage.clickFilterSubmit();
        newsFilterPage.checkWrongPeriodError();
    }

    @Test
    @DisplayName("Инвертированный период дат")
    @Description("Дата начала позже даты окончания")
    public void filteringNewsByInvalidPeriodInNews_42() {
        String startDate = "06.03.2026";
        String endDate = "01.03.2026";
        newsSectionPage.clickFilter();
        newsFilterPage.checkFilterFormIsLoaded();
        newsFilterPage.enterStartDate(startDate);
        newsFilterPage.enterEndDate(endDate);
        newsFilterPage.clickFilterSubmit();
        newsSectionPage.checkFilterResultVisible();
    }

    @Test
    @DisplayName("Выбор даты через календарь")
    @Description("Проверка выбора завтрашнего дня в системном календаре")
    public void choiceStartDateSelectionInCalendar_43() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        newsSectionPage.clickFilter();
        newsFilterPage.openStartDateCalendar();
        calendarPage.setDate(tomorrow);
        newsFilterPage.checkStartDateFieldHasText(String.valueOf(tomorrow.getDayOfMonth()));
    }

    @Test
    @DisplayName("Отмена выбора в календаре")
    @Description("Открытие календаря и нажатие кнопки отмены")
    public void cancelStartDateSelectionInCalendar_44() throws Exception {
        LocalDate futureDate = LocalDate.now().plusDays(2);
        newsSectionPage.clickFilter();
        newsFilterPage.openStartDateCalendar();
        calendarPage.cancelDate(futureDate);
        newsFilterPage.checkStartDateFieldIsEmpty();
    }
}
