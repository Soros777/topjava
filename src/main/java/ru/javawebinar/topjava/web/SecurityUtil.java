package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int userId = 1;

    public static int authUserId() {
        return userId;
    }

    public static void seAuthUserId(String userIdStr) {
        if(!userIdStr.isEmpty()) {
            userId = Integer.parseInt(userIdStr);
        }
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}