package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    MealDao mealDao = new MealDaoMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("start doGet");

        if("delete".equals(request.getParameter("action"))) {
            String mealId = request.getParameter("id");
            log.debug("Meal id : {}", mealId);
            mealDao.deleteMeal(Integer.parseInt(mealId));
        }

        List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay);
        request.setAttribute("meals", meals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("start doPost");

        request.setCharacterEncoding("UTF-8");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String action = request.getParameter("action");
        log.debug("Got parameters:   dateTime : {}, description : {}, calories : {}, action : {}", dateTime, description, calories, action);

        if("add".equals(action) && !dateTime.equals("") && !description.equals("") && !calories.equals("")) {
            Meal newMeal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
            mealDao.addMeal(newMeal);
        } else if("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            LocalDateTime mealDateTime = LocalDateTime.parse(dateTime);
            mealDao.updateMeal(new Meal(id, mealDateTime, description, Integer.parseInt(calories)));
        }

        doGet(request, response);
    }
}
