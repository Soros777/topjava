package ru.javawebinar.topjava.repository.datajpa;

import org.slf4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Logger log = getLogger(DataJpaMealRepository.class);

    private final CrudMealRepository crudMealRepository;
    private final DataJpaUserRepository dataJpaUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, DataJpaUserRepository dataJpaUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.dataJpaUserRepository = dataJpaUserRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(!meal.isNew() && get(meal.id(), userId) == null) {
            return null;
        }
        User user = dataJpaUserRepository.get(userId);
        meal.setUser(user);
        return crudMealRepository.save(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        log.info("start get");
        return crudMealRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId) // getId() - without extra query to DB, but id() - with extra query.
                .orElse(null);
//        Meal entity = crudMealRepository.findById(id).orElse(null);
//        log.info("before return");
//        return entity == null || entity.getUser().getId() != userId ? null : entity;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return crudMealRepository.getWithUser(id, userId);
    }
}
