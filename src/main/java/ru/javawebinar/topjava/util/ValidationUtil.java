package ru.javawebinar.topjava.util;


import org.slf4j.Logger;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.slf4j.LoggerFactory.getLogger;

public class ValidationUtil {
    private static final Logger log = getLogger(ValidationUtil.class);
    private ValidationUtil() {
    }

    public static void checkMealOwner(Meal meal, int userId) {
        log.info("Start checkMealOwner");
        if(meal == null || meal.getUser() == null || meal.getUser().getId() != userId) {
            throw new NotFoundException(String.format("Meal %s does not own to user with id %d", meal, userId));
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        log.info("Start checkNotFoundWithId");
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}