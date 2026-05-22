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
import ru.iteco.fmhandroid.ui.page.ControlPanelPage;
import ru.iteco.fmhandroid.ui.page.LoginPage;
import ru.iteco.fmhandroid.ui.page.NavigationPage;
import ru.iteco.fmhandroid.ui.page.NewsEditorPage;
import ru.iteco.fmhandroid.ui.page.NewsSectionPage;
import ru.iteco.fmhandroid.ui.page.TimePage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Раздел Новости")
@Feature("Создание новостей")

public class CreateNewsTest {

    @Rule
    public ActivityScenarioRule<AppActivity> activityRule = new ActivityScenarioRule<>(AppActivity.class);

    private final LoginPage loginPage = new LoginPage();
    private final NavigationPage navigationPage = new NavigationPage();
    private final NewsSectionPage newsSectionPage = new NewsSectionPage();
    private final ControlPanelPage controlPanelPage = new ControlPanelPage();
    private final NewsEditorPage newsEditorPage = new NewsEditorPage();
    private final CalendarPage calendarPage = new CalendarPage();
    private final TimePage timePage = new TimePage();

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
        newsSectionPage.clickEdit();
        controlPanelPage.checkControlPanelLoaded();
    }

    @Test
    @DisplayName("Успешное создание новости")
    @Description("Заполнение всех полей валидными данными и проверка появления новости в списке")
    public void newsCreation_98() throws Exception {
        String title = DataHelper.generateTitle();
        LocalDate targetDate = LocalDate.now().plusDays(1);
        String hour = DataHelper.getRandomHour();
        String minute = DataHelper.getRandomMinuteForPicker();
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.selectCategory(DataHelper.getRandomCategory());
        newsEditorPage.enterTitle(title);
        newsEditorPage.selectDateFromCalendar(targetDate);
        newsEditorPage.selectTimeFromPicker(hour, minute);
        newsEditorPage.enterDescription("Description for " + title);
        newsEditorPage.checkActiveSwitcherStatus();
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.checkNewsWithTitleExists(title);
    }

    @Test
    @DisplayName("Отмена создания новости")
    @Description("Заполнение категории и отмена создания через диалоговое окно")
    public void cancelingNewsCreation_99() {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.selectCategory(DataHelper.getRandomCategory());
        newsEditorPage.clickCancel();
        newsEditorPage.checkCancelDialogText();
        newsEditorPage.confirmCancelDialog();
        controlPanelPage.checkControlPanelLoaded();
    }

    @Test
    @DisplayName("Создание новости с пустыми полями")
    @Description("Проверка появления ошибок валидации при попытке сохранить пустую форму")
    public void creatingNewsItemWithCategoryOnly_100() {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.selectCategory(DataHelper.getRandomCategory());
        newsEditorPage.enterTitle("");
        newsEditorPage.enterDate("");
        newsEditorPage.enterTime("");
        newsEditorPage.enterDescription("");
        newsEditorPage.clickSave();
        newsEditorPage.checkSaveButtonIsDisplayed();
        newsEditorPage.checkEmptyFieldsToast();
        newsEditorPage.checkTitleFieldEmptyError();
        newsEditorPage.checkDescriptionFieldEmptyError();
    }

    @Test
    @DisplayName("Ввод произвольной категории")
    @Description("Проверка ошибки при вводе категории, которой нет в списке")
    public void anotherValueInTheCategoryField_101() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.enterCustomCategory("Заявление");
        newsEditorPage.enterTitle(DataHelper.generateTitle());
        newsEditorPage.selectDateFromCalendar(LocalDate.now().plusDays(1));
        newsEditorPage.selectTimeFromPicker(DataHelper.getRandomHour(), DataHelper.getRandomMinuteForPicker());
        newsEditorPage.enterDescription(DataHelper.getRandomString(20));
        newsEditorPage.clickSave();
        newsEditorPage.checkSavingFailedToast();
    }

    @Test
    @DisplayName("Выбор прошедшей даты")
    @Description("Проверка, что при выборе вчерашнего дня в календаре устанавливается текущая дата")
    public void lastDateInTheCreation_109() throws Exception {
        String yesterday = DataHelper.getYesterdayDay();
        String today = DataHelper.getDate(0);
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openCalendar();
        calendarPage.selectDay(yesterday);
        calendarPage.clickOk();
        newsEditorPage.checkDateText(today);
    }

    @Test
    @DisplayName("Отмена выбора даты в календаре")
    @Description("Проверка, что дата не устанавливается, если в календаре нажата кнопка Cancel")
    public void cancelingTheDateSelection_110() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openCalendar();
        calendarPage.cancelDate(LocalDate.now().plusDays(2));
        newsEditorPage.checkDateText("");
    }

    @Test
    @DisplayName("Отмена выбора времени")
    @Description("Проверка, что время не устанавливается, если в селекторе нажата кнопка Cancel")
    public void cancelingTheTimeSelection_113() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openTimePicker();
        timePage.selectTime("10", "45");
        timePage.clickCancel();
        newsEditorPage.checkTimeText("");
    }

    @Test
    @DisplayName("Ввод времени через клавиатуру")
    @Description("Проверка корректности установки времени при ручном вводе через режим клавиатуры")
    public void creatingTheTimeFieldViaTheKeyboard_114() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("15", "10");
        timePage.clickOk();
        newsEditorPage.checkTimeText("15:10");
    }

    @Test
    @DisplayName("Отмена ввода времени через клавиатуру")
    @Description("Проверка, что введенное вручную время не сохраняется при нажатии Cancel")
    public void cancelingTheTimeSelectionViaTheKeyboard_115() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("23", "55");
        timePage.clickCancel();
        newsEditorPage.checkTimeText("");
    }

    @Test
    @DisplayName("Пустые поля времени в режиме клавиатуры")
    @Description("Проверка валидации: нельзя подтвердить пустые поля ввода часов и минут")
    public void emptyTimeFields_116() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("", "");
        timePage.clickOk();
        timePage.checkValidTimeError();
        timePage.checkTimePickerIsStillOpen();
    }

    @Test
    @DisplayName("Граничное значение часов (24:15)")
    @Description("Проверка ошибки валидации при вводе недопустимого часа (24)")
    public void boundaryValueInClockField_119() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("24", "15");
        timePage.clickOk();
        timePage.checkValidTimeError();
        timePage.checkTimePickerIsStillOpen();
    }

    @Test
    @DisplayName("Переключение режимов ввода времени")
    @Description("Проверка работы кнопок переключения между режимом 'Циферблат' и 'Клавиатура'")
    public void switchingTheTimeInputMethod_122() throws Exception {
        controlPanelPage.clickAddNews();
        newsEditorPage.checkAddNewsFormIsLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.switchToClockMode();
        timePage.checkTimePickerIsStillOpen();
    }
}
