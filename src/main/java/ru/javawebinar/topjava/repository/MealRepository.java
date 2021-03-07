package ru.javawebinar.topjava.repository;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.datajpa.CrudMealRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal does not belong to userId
    boolean delete(int id, int userId);

    // null if meal does not belong to userId
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    // ORDERED dateTime desc
    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    default Meal getWithUser(int id, int userId) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            ConfigurableEnvironment env = appCtx.getEnvironment();
            env.setActiveProfiles("postgres", "datajpa");
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();
            CrudMealRepository crudMealRepository = appCtx.getBean(CrudMealRepository.class);
            return crudMealRepository.getWithUser(id, userId);
        }
    }
}
