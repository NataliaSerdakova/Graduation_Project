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
@Feature("Редактирование новостей")

public class EditNewsTest {

    private final LoginPage loginPage = new LoginPage();
    private final NavigationPage navigationPage = new NavigationPage();
    private final NewsSectionPage newsSectionPage = new NewsSectionPage();
    private final ControlPanelPage controlPanelPage = new ControlPanelPage();
    private final CalendarPage calendarPage = new CalendarPage();
    private final TimePage timePage = new TimePage();
    private final NewsEditorPage newsEditorPage = new NewsEditorPage();
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
        newsSectionPage.clickEdit();
        newsSectionPage.checkControlPanelIsLoaded();
    }


    @Test
    @DisplayName("Редактирование всех полей новости")
    @Description("Изменение категории, заголовка, даты, времени и описания с последующим сохранением")
    public void editingAllNewsFields_54() {
        String category = DataHelper.getRandomCategory();
        String newTitle = DataHelper.generateTitle();
        String newDate = DataHelper.getDate(1);
        String newTime = DataHelper.getCurrentTime();
        String newDescription = "Auto-Description: " + DataHelper.getRandomString(20);
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.selectCategory(category);
        newsEditorPage.enterTitle(newTitle);
        newsEditorPage.enterDate(newDate);
        newsEditorPage.enterTime(newTime);
        newsEditorPage.enterDescription(newDescription);
        newsEditorPage.toggleActiveStatus(true);
        newsEditorPage.checkActiveSwitcherStatus();
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.checkNewsWithTitleExists(newTitle);
    }

    @Test
    @DisplayName("Редактирование только поля Категория")
    @Description("Смена категории новости и проверка сохранения изменений")
    public void editingTheCategoryField_55() {
        String newCategory = DataHelper.getRandomCategory();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.selectCategory(newCategory);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.checkCategoryText(newCategory);
    }

    @Test
    @DisplayName("Ввод недопустимой категории")
    @Description("Попытка сохранить новость с категорией, введенной вручную")
    public void anotherValueInTheCategoryField_56() {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.enterCustomCategory("Заявленние");
        newsEditorPage.clickSave();
        newsEditorPage.checkSavingFailedToast();
    }

    @Test
    @DisplayName("Редактирование заголовка")
    @Description("Изменение текста заголовка и проверка его отображения в списке")
    public void editingTheTitleField_57() {
        String newTitle = DataHelper.generateTitle();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.enterTitle(newTitle);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.checkNewsWithTitleExists(newTitle);
    }

    @Test
    @DisplayName("Удаление заголовка")
    @Description("Очистка поля заголовка и проверка ошибки валидации")
    public void missingTitle_58() {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.enterTitle("");
        newsEditorPage.clickSave();
        newsEditorPage.checkEmptyFieldsToast();
        newsEditorPage.checkTitleFieldEmptyError();
        newsEditorPage.checkSaveButtonIsDisplayed();
    }

    @Test
    @DisplayName("Заголовок из одного символа")
    @Description("Граничное значение: сохранение новости с заголовком 'A'")
    public void OneCharacterInTitleField_60() {
        String singleCharTitle = "A";
        String uniqueDescription = DataHelper.getRandomString(15);
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.enterTitle(singleCharTitle);
        newsEditorPage.enterDescription(uniqueDescription);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.checkNewsWithTitleExists(singleCharTitle);
        controlPanelPage.clickExpandNews(0);
        controlPanelPage.checkDescriptionText(0, uniqueDescription);
    }

    @Test
    @DisplayName("Редактирование даты через календарь")
    @Description("Выбор будущей даты в календаре и сохранение")
    public void editingThePublicationDateField_62() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.openCalendar();
        calendarPage.setDate(LocalDate.now().plusDays(25));
        newsEditorPage.clickSave();
    }

    @Test
    @DisplayName("Выбор вчерашней даты при редактировании")
    @Description("Проверка, что дата не меняется на вчерашнюю через календарь")
    public void lastDateInTheEdit_63() throws Exception {
        String today = DataHelper.getDate(0);
        String yesterday = DataHelper.getYesterdayDay();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.openCalendar();
        calendarPage.selectDay(yesterday);
        calendarPage.clickOk();
        newsEditorPage.checkDateText(today);
    }

    @Test
    @DisplayName("Отмена выбора даты")
    @Description("Проверка сохранения исходной даты при отмене выбора в календаре")
    public void cancelingTheDateSelection_64() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        String initialDate = newsEditorPage.getDateText();
        newsEditorPage.openCalendar();
        calendarPage.cancelDate(LocalDate.now().plusDays(2));
        newsEditorPage.checkDateText(initialDate);
    }

    @Test
    @DisplayName("Редактирование времени через селектор")
    @Description("Установка нового времени через графический интерфейс и проверка сохранения")
    public void editingTheTimeField_65() throws Exception {
        String hour = DataHelper.getRandomHour();
        String minute = DataHelper.getRandomMinuteForPicker();
        String expectedTime = DataHelper.formatTime(hour, minute);
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.selectTimeFromPicker(hour, minute);
        newsEditorPage.checkTimeText(expectedTime);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.checkTimeText(expectedTime);
    }

    @Test
    @DisplayName("Отмена выбора времени")
    @Description("Проверка сохранения исходного времени при нажатии кнопки Cancel в селекторе")
    public void cancelingTheTimeSelection_66() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        String initialTime = newsEditorPage.getTimeText();
        newsEditorPage.openTimePicker();
        timePage.selectTime("10", "45");
        timePage.clickCancel();
        newsEditorPage.checkTimeText(initialTime);
    }

    @Test
    @DisplayName("Редактирование времени через клавиатуру")
    @Description("Ввод времени цифрами и проверка его корректного отображения в поле")
    public void editingTheTimeFieldViaTheKeyboard_67() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("15", "10");
        timePage.clickOk();
        newsEditorPage.checkTimeText("15:10");
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkTimeText("15:10");
    }

    @Test
    @DisplayName("Отмена ввода времени через клавиатуру")
    @Description("Проверка, что ручной ввод времени сбрасывается при отмене")
    public void cancelingTheTimeSelectionViaTheKeyboard_68() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        String initialTime = newsEditorPage.getTimeText();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("23", "55");
        timePage.clickCancel();
        newsEditorPage.checkTimeText(initialTime);
    }

    @Test
    @DisplayName("Пустое поле часов")
    @Description("Валидация при частичном заполнении времени в режиме клавиатуры")
    public void emptyClockField_70() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("", "26");
        timePage.clickOk();
        timePage.checkValidTimeError();
        timePage.checkTimePickerIsStillOpen();
    }

    @Test
    @DisplayName("Граничное значение времени (24 часа)")
    @Description("Проверка появления ошибки при вводе недопустимого часа (24)")
    public void boundaryValueInClockField_72() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.typeTime("24", "15");
        timePage.clickOk();
        timePage.checkValidTimeError();
        timePage.checkTimePickerIsStillOpen();
    }

    @Test
    @DisplayName("Переключение режимов ввода времени")
    @Description("Проверка перехода между режимами 'Клавиатура' и 'Часы' в окне выбора времени")
    public void switchingTheTimeInputMethod_75() throws Exception {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditFormLoaded();
        newsEditorPage.openTimePicker();
        timePage.switchToKeyboardMode();
        timePage.switchToClockMode();
        timePage.checkTimePickerIsStillOpen();
    }

    @Test
    @DisplayName("Редактирование поля Описание")
    @Description("Изменение текста описания и проверка его отображения после сохранения")
    public void editingTheDescriptionField_76() {
        String newDescription = "Unique Desc " + DataHelper.getRandomString(10);
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.enterDescription(newDescription);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.clickExpandNews(0);
        controlPanelPage.checkDescriptionVisible(0);
        controlPanelPage.checkDescriptionText(0, newDescription);
    }

    @Test
    @DisplayName("Удаление описания")
    @Description("Очистка поля описания и проверка появления ошибки валидации")
    public void missingDescription_77() {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.enterDescription("");
        newsEditorPage.clickSave();
        newsEditorPage.checkEmptyFieldsToast();
        newsEditorPage.checkDescriptionFieldEmptyError();
        newsEditorPage.checkSaveButtonIsDisplayed();
    }

    @Test
    @DisplayName("Описание из одного символа")
    @Description("Граничное значение: сохранение новости с описанием из одного символа 'Р'")
    public void OneCharacterInDescriptionField_79() {
        String uniqueTitle = DataHelper.generateTitle();
        String singleCharDesc = "Р";
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.enterTitle(uniqueTitle);
        newsEditorPage.enterDescription(singleCharDesc);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.checkNewsWithTitleExists(uniqueTitle);
        controlPanelPage.clickExpandNews(0);
        controlPanelPage.checkDescriptionText(0, singleCharDesc);
    }

    @Test
    @DisplayName("Смена статуса на Active")
    @Description("Переключение статуса новости в положение 'Активна' и проверка в списке")
    public void changingTheNewsStatusToActive_81() {
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.toggleActiveStatus(false);
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.toggleActiveStatus(true);
        newsEditorPage.checkActiveSwitcherStatus();
        newsEditorPage.clickSave();
        controlPanelPage.checkControlPanelLoaded();
        controlPanelPage.checkFirstNewsStatus("Active");
    }

    @Test
    @DisplayName("Смена статуса на Not Active")
    @Description("Переключение статуса новости в положение 'Не активна'")
    public void changingTheNewsStatusToNotActive_82() {
        String newCategory = DataHelper.getRandomCategory();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.selectCategory(newCategory);
        newsEditorPage.clickCancel();
        newsEditorPage.checkCancelDialogText();
        newsEditorPage.confirmCancelDialog();
        controlPanelPage.checkControlPanelLoaded();
    }

    @Test
    @DisplayName("Отмена редактирования новости")
    @Description("Изменение категории и отмена через диалоговое окно подтверждения")
    public void cancelEditingNews_83() {
        String newCategory = DataHelper.getRandomCategory();
        controlPanelPage.clickEditNews(0);
        newsEditorPage.checkEditNewsFormIsLoaded();
        newsEditorPage.selectCategory(newCategory);
        newsEditorPage.clickCancel();
        newsEditorPage.checkCancelDialogText();
        newsEditorPage.confirmCancelDialog();
        controlPanelPage.checkControlPanelLoaded();
    }
}
