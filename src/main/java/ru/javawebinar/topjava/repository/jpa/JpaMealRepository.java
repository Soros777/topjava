package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    private static final Logger log = getLogger(JpaMealRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        log.info("Start save. meal : {} ; userId : {}", meal, userId);
        if(meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        } else {
            Meal entity = get(meal.id(), userId);
            if(entity == null) {
                return null;
            }
            User user = em.getReference(User.class, userId);
            meal.setUser(user);
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

        /* one of ways
        Meal mealReference = em.getReference(Meal.class, id);
        // Can I avoid the logic below just here and move it to MealService?
        try {
            if(mealReference.getUser().getId() != userId) {
                log.info("Not owner!");
                return false;
            }
        } catch (EntityNotFoundException nfe) {
            log.info("Not found Meal entity");
            return false;
        }
        em.remove(mealReference);
        return true;
*/

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if(meal == null || meal.getUser().getId() != userId) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.FOR_PERIOD_SORTED, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }
}