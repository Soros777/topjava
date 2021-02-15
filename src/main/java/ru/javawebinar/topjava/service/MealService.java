package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ServletUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealTo> getAll() {
        log.info("get all");
        return MealsUtil.getTos(repository.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }


    public List<MealTo> getFiltered(String fromDateStr, String toDateStr, String fromTimeStr, String toTimeStr) {
        log.info("get filtered");
        LocalDate fromDate = ServletUtil.getOrDefault(fromDateStr, LocalDate.MIN);
        LocalDate toDate = ServletUtil.getOrDefault(toDateStr, LocalDate.MAX);
        LocalTime fromTime = ServletUtil.getOrDefault(fromTimeStr, LocalTime.MIN);
        LocalTime toTime = ServletUtil.getOrDefault(toTimeStr, LocalTime.MAX);
        return MealsUtil.getFilteredTos(repository.getFilteredByDate(SecurityUtil.authUserId(), fromDate, toDate),
                SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime);
    }

    public Meal create(Meal meal) {
        log.info("create");
        return repository.save(meal, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get");
        return checkNotFoundWithId(repository.get(id, SecurityUtil.authUserId()), id);
    }

    public void update(Meal meal) {
        log.info("update");
        checkNotFoundWithId(repository.save(meal, SecurityUtil.authUserId()), meal.getId());
    }

    public void delete(int id) {
        log.info("delete");
        checkNotFoundWithId(repository.delete(id, SecurityUtil.authUserId()), id);
    }
}