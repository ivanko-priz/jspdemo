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
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.model.FoodCategory;
import com.ivanko.service.api.FoodCategoryService;
import com.ivanko.service.impl.FoodCategoryServiceImpl;

public class FoodCategoryServlet extends HttpServlet {
    FoodCategoryService foodCategoryService;

    public FoodCategoryServlet() {
        DatasourceFactory datasourceFactory = new DatasourceFactory();
        Dao<FoodCategory, Long> foodCategoryDao = new FoodCategoryDaoImpl(datasourceFactory);

        foodCategoryService = new FoodCategoryServiceImpl(foodCategoryDao);
    }

    public FoodCategoryServlet(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlePost(req, resp);
    }

    /*
        This method handles get requests to make doGet less cluttered.
        Depending on .servletPath() different forwardings take place.
        An exception is thrown if servletPath is not valid
    */
    private void handleGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] servletPath = req.getServletPath().substring(1).split("/");
        String path = servletPath.length == 1 ? "/" : servletPath[servletPath.length - 1];

        try {
            if (path.equals("/")) {
                List<FoodCategory> foodCategories = foodCategoryService.findAll();

                req.setAttribute("categories", foodCategories);
                req.getRequestDispatcher("listCategory.jsp").forward(req, resp);
            } else if (path.equals("edit")) {
                long id = Long.parseLong(req.getParameter("id"));

                Optional<FoodCategory> foodCategoryResult = foodCategoryService.findById(id);

                if (foodCategoryResult.isEmpty()) {
                    resp.sendRedirect("../error");
                    return;
                }

                req.setAttribute("category", foodCategoryResult.get());
                req.getRequestDispatcher("../editFoodCategory.jsp").forward(req, resp);
            } else if (path.equals("create")) {
                req.getRequestDispatcher("../createFoodCategory.jsp").forward(req, resp);
            } else if (path.equals("delete")) {
                long id = Long.parseLong(req.getParameter("id"));

                Optional<FoodCategory> foodCategoryResult = foodCategoryService.findById(id);

                if (foodCategoryResult.isEmpty()) {
                    resp.sendRedirect("../error");
                    return;
                }

                req.setAttribute("category", foodCategoryResult.get());
                req.getRequestDispatcher("../deleteFoodCategory.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();

            resp.sendRedirect("../error");
        }
    }

    /*
        Similar to handleGet, but is called in doPost.
        Depending on 'action' param different service methods get called
        If action is invalid a redirect to the error page happens.
     */
    private void handlePost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if (action.equals("create")) {
                String name = req.getParameter("name");
                FoodCategory foodCategory = new FoodCategory(name);

                foodCategoryService.create(foodCategory);
            } else if (action.equals("update")) {
                long id = Long.parseLong(req.getParameter("id"));
                String name = req.getParameter("name");
                FoodCategory foodCategory = new FoodCategory(name);

                foodCategoryService.update(id, foodCategory);
            } else if (action.equals("delete")) {
                long id = Long.parseLong(req.getParameter("id"));

                foodCategoryService.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/category");
        } catch (Exception e) {
            e.printStackTrace();

            resp.sendRedirect("../error");
        }
    }
}