package com.ivanko.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ivanko.dao.api.Dao;
import com.ivanko.dao.impl.FoodCategoryDaoImpl;
import com.ivanko.dao.impl.FoodDaoImpl;
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.model.Food;
import com.ivanko.model.FoodCategory;
import com.ivanko.service.api.FoodCategoryService;
import com.ivanko.service.api.FoodService;
import com.ivanko.service.impl.FoodServiceImpl;

public class FoodServlet extends HttpServlet {
    FoodService foodService;
    FoodCategoryService foodCategoryService;

    // Helper constructor to be used with mockito to inject service mocks
    public FoodServlet(FoodService foodService, FoodCategoryService foodCategoryService) {
        this.foodService = foodService;
        this.foodCategoryService = foodCategoryService;
    }

    // Use default constructor to set food service, I'm more than sure that this approach is incorrect
    // but I don't know how to inject dependencies in servlets without Spring and javax beans
    public FoodServlet() {
        Dao<Food, Long> foodDao = new FoodDaoImpl(new DatasourceFactory());
        Dao<FoodCategory, Long>  foodCategoryDao = new FoodCategoryDaoImpl(new DatasourceFactory());
        foodService = new FoodServiceImpl(foodDao, foodCategoryDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlePost(req, resp);
    }

    private void handleGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] servletPath = req.getServletPath().substring(1).split("/");
        String path = servletPath.length == 1 ? "/" : servletPath[servletPath.length - 1];

        try {
            if (path.equals("/")) {
                List<?> foods = foodService.findAll();

                req.setAttribute("foods", foods);
                req.getRequestDispatcher("listFood.jsp").forward(req, resp);
            } else if (path.equals("create")) {
                List<FoodCategory> categories = foodCategoryService.findAll();

                req.setAttribute("categories", categories);
                req.getRequestDispatcher("../createFood.jsp").forward(req, resp);
            } else if (path.equals("edit")) {
                long id = Long.parseLong(req.getParameter("id"));

                Optional<Food> foodResult = foodService.findById(id);

                if (foodResult.isEmpty()) {
                    resp.sendRedirect("../error");
                    return;
                }

                List<FoodCategory> categories = foodCategoryService.findAll();

                req.setAttribute("categories", categories);
                req.setAttribute("food", foodResult.get());
                req.getRequestDispatcher("../editFood.jsp").forward(req, resp);
            } else if (path.equals("delete")) {
                long id = Long.parseLong(req.getParameter("id"));

                Optional<Food> foodResult = foodService.findById(id);

                if (foodResult.isEmpty()) {
                    resp.sendRedirect("../error");
                    return;
                }

                req.setAttribute("food", foodResult.get());
                req.getRequestDispatcher("../deleteFood.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();

            resp.sendRedirect("../error");
        }
    }

    private void handlePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if (action.equals("create")) {
                String name = req.getParameter("name");
                long foodCategoryId = Long.parseLong(req.getParameter("category"));
                String description = req.getParameter("description");
                int caloriesPer100g = Integer.parseInt(req.getParameter("caloriesPer100g"));

                Food food = new Food(name);
                food.setName(name);
                food.setFoodCategoryId(foodCategoryId);
                food.setCaloriesPer100g(caloriesPer100g);
                food.setDescription(description);

                foodService.create(food);
            } else if (action.equals("update")) {
                long id = Long.parseLong(req.getParameter("id"));
                String name = req.getParameter("name");
                long foodCategoryId = Long.parseLong(req.getParameter("category"));
                String description = req.getParameter("description");
                int caloriesPer100g = Integer.parseInt(req.getParameter("caloriesPer100g"));

                Food food = new Food(name);
                food.setFoodCategoryId(foodCategoryId);
                food.setDescription(description);
                food.setCaloriesPer100g(caloriesPer100g);

                foodService.update(id, food);
            } else if (action.equals("delete")) {
                long id = Long.parseLong(req.getParameter("id"));

                foodService.delete(id);
            }

            resp.sendRedirect(req.getContextPath() + "/food");
        } catch (Exception e) {
            e.printStackTrace();

            resp.sendRedirect("../error");
        }
    }
}
