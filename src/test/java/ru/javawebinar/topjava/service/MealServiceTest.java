package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(meal1Id, SecurityUtil.authUserId());
        assertMatch(meal, MealTestData.meal1);
    }

    @Test
    public void delete() {
        service.delete(meal1Id, SecurityUtil.authUserId());
        assertThrows(NotFoundException.class, () -> service.get(meal1Id, SecurityUtil.authUserId()));
    }

    @Test
    public void getBetweenInclusive() {
        final List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, 01, 29), LocalDate.of(2020, 01, 30), SecurityUtil.authUserId());
        assertMatch(meals, meal3, meal2, meal1);

    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(SecurityUtil.authUserId());
        assertMatch(all, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, SecurityUtil.authUserId());
        assertMatch(service.get(meal1Id, SecurityUtil.authUserId()), getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), SecurityUtil.authUserId());
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, SecurityUtil.authUserId()), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new Meal(null, LocalDateTime.of(2020, 01, 30, 13, 0), "Duplicate", 500), SecurityUtil.authUserId()));
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, ()-> service.get(meal1Id, SecurityUtil.authUserId()+1));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, ()-> service.delete(meal1Id, SecurityUtil.authUserId()+1));
    }

    @Test
    public void updateForeign() {
        assertThrows(NotFoundException.class, ()-> service.update(meal2, SecurityUtil.authUserId()+1));
    }
}