package ru.iteco.fmhandroid.ui.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataHelper {

    public static final String[] CATEGORIES = {
            "Объявление", "День рождения", "Зарплата", "Профсоюз", "Праздник", "Массаж", "Благодарность", "Нужна помощь"
    };
    private static final String VALID_LOGIN = "login2";
    private static final String VALID_PASSWORD = "password2";

    public static String getValidLogin() {
        return VALID_LOGIN;
    }

    public static String getValidPassword() {
        return VALID_PASSWORD;
    }

    public static String getRandomCategory() {
        return CATEGORIES[new Random().nextInt(CATEGORIES.length)];
    }

    public static String getDate(int daysOffset) {
        return LocalDate.now()
                .plusDays(daysOffset)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getYesterdayDay() {
        return String.valueOf(LocalDate.now().minusDays(1).getDayOfMonth());
    }

    public static String getFutureDay(int daysOffset) {
        return String.valueOf(LocalDate.now().plusDays(daysOffset).getDayOfMonth());
    }

    public static String getCurrentTime() {
        return java.time.LocalTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static String getRandomHour() {
        int hour = new Random().nextInt(24);
        return String.valueOf(hour);
    }

    public static String getRandomMinuteForPicker() {
        int[] minutes = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
        int randomMin = minutes[new Random().nextInt(minutes.length)];
        return String.valueOf(randomMin);
    }

    public static String formatTime(String hour, String minute) {
        int h = Integer.parseInt(hour);
        int m = Integer.parseInt(minute);
        return String.format("%02d:%02d", h, m);
    }

    public static String generateTitle() {
        return "Auto-Title " + System.currentTimeMillis();
    }

    public static String getRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static String generateSingleCharTitle(String c) {
        return c + " " + System.currentTimeMillis();
    }
}
