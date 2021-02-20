package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int meal1Id = START_SEQ + 2;
    public static final int NOT_FOUND = 12;

    public static final Meal meal1 = new Meal(meal1Id, LocalDateTime.of(2020, 01, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(START_SEQ+3, LocalDateTime.of(2020, 01, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(START_SEQ+4, LocalDateTime.of(2020, 01, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(START_SEQ+5, LocalDateTime.of(2020, 01, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(START_SEQ+6, LocalDateTime.of(2020, 01, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(START_SEQ+7, LocalDateTime.of(2020, 01, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(START_SEQ+8, LocalDateTime.of(2020, 01, 31, 20, 0), "Ужин", 410);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, 02, 20, 17, 0), "New meal", 333);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2021, 02, 20, 17, 0));
        updated.setDescription("Updated meal");
        updated.setCalories(700);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal ... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
