package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoMemory implements MealDao{

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MealDaoMemory() {
        List<Meal> meals = MealsUtil.meals;
        meals.forEach(meal -> storage.put(meal.getId(), meal));
    }

    @Override
    public int addMeal(Meal meal) {
        int id = MealsUtil.ID.incrementAndGet();
        meal.setId(id);
        storage.put(id, meal);
        return id;
    }

    @Override
    public void deleteMeal(int mealId) {
        storage.remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal getMealById(int mealId) {
        return storage.get(mealId);
    }
}
