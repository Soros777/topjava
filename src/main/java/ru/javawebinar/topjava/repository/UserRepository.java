package ru.javawebinar.topjava.repository;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.CrudUserRepository;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(int id) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            ConfigurableEnvironment env = appCtx.getEnvironment();
            env.setActiveProfiles("postgres", "datajpa");
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();
            CrudUserRepository crudUserRepository = appCtx.getBean(CrudUserRepository.class);
            return crudUserRepository.getWithMeals(id);
        }
    }
}