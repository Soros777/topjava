package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import ru.javawebinar.topjava.model.UserMealWithExcessBoolean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCyclesOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

//        List<UserMealWithExcessBoolean> mealsTo = filteredByCyclesOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);


//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        System.out.println(filteredByStreamsOnePass(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            int caloriesThisDay = dayCalories.getOrDefault(mealDate, 0);
            caloriesThisDay += userMeal.getCalories();
            dayCalories.put(mealDate, caloriesThisDay);
        }
        List<UserMealWithExcess> result = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if(TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean caloriesExcess = dayCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
                result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), caloriesExcess));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        meals.forEach(meal -> dayCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), dayCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    // Optional 2
    public static List<UserMealWithExcessBoolean> filteredByCyclesOnePass(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Object[]> dayCaloriesForExcess = new HashMap<>();
        List<UserMealWithExcessBoolean> result = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            Object[] dayCalories = dayCaloriesForExcess.getOrDefault(mealDate, new Object[]{0, new AtomicBoolean()});
            AtomicBoolean excess = (AtomicBoolean) dayCalories[1];
            dayCalories[0] = (int) dayCalories[0] + userMeal.getCalories();
            if((int) dayCalories[0] > caloriesPerDay) {
                excess.set(true);
            }
            dayCaloriesForExcess.put(mealDate, new Object[]{dayCalories[0], excess});
            if(TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExcessBoolean meal = new UserMealWithExcessBoolean(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
                result.add(meal);
            }
        }
        return result;
    }

    public static List<UserMealWithExcessBoolean> filteredByStreamsOnePass(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Object[]> dayCalories = new HashMap<>();
        return meals.stream()
                .peek(meal -> {
                    Object[] calories = dayCalories.getOrDefault(meal.getDateTime().toLocalDate(), new Object[]{0, new AtomicBoolean()});
                    calories[0] = (int) calories[0] + meal.getCalories();
                    AtomicBoolean excess = (AtomicBoolean) calories[1];
                    if((int) calories[0] > caloriesPerDay) {
                        excess.set(true);
                    }
                    dayCalories.put(meal.getDateTime().toLocalDate(), calories);
                })
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcessBoolean(meal.getDateTime(), meal.getDescription(), meal.getCalories(), (AtomicBoolean ) dayCalories.get(meal.getDateTime().toLocalDate())[1]))
                .collect(Collectors.toList());
    }
}
