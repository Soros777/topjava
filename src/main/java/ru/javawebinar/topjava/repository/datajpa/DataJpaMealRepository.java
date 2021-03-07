package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "dateTime");

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
        if(get(id, userId) == null) {
            return false;
        }
        crudMealRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        Meal entity = crudMealRepository.findById(id).orElse(null);
        return entity == null || entity.getUser().id() != userId ? null : entity;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.findByUserIdOrderByDateTimeDesc(userId, SORT_DATE);
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
