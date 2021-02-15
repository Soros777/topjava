package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<MealTo> getFiltered(String fromDate, String toDate, String fromTime, String toTime) {
        log.info("get filtered from date {} to date {} from time {} to time {}", fromDate, toDate, fromTime, toTime);
        return service.getFiltered(fromDate, toDate, fromTime, toTime);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id {}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}