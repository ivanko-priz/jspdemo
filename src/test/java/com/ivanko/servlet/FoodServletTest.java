package com.ivanko.servlet;

import com.ivanko.exception.BusinessRuleException;
import com.ivanko.model.FoodCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ivanko.model.Food;
import com.ivanko.util.wrapper.FoodWrapper;
import com.ivanko.service.api.FoodService;
import com.ivanko.service.api.FoodCategoryService;

@ExtendWith(MockitoExtension.class)
public class FoodServletTest {
    @Mock
    FoodService foodService;
    @Mock
    FoodCategoryService foodCategoryService;

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher rd;

    @Test
    public void shouldForwardAndFetchListOfFoods() throws ServletException, IOException, SQLException {
        List<? extends Food> foods = java.util.List.of(new FoodWrapper());

        when(req.getServletPath()).thenReturn("/food");
        when(req.getRequestDispatcher("listFood.jsp")).thenReturn(rd);
        doReturn(foods).when(foodService).findAll();

        new FoodServlet(foodService, foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher("listFood.jsp");
        verify(req, times(1)).setAttribute("foods", foods);
    }

    @Test
    public void shouldForwardToCreateForm() throws ServletException, IOException, SQLException {
        String forwardTo = "../createFood.jsp";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());

        when(req.getServletPath()).thenReturn("/food/create");
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);
        doReturn(categories).when(foodCategoryService).findAll();

        new FoodServlet(foodService, foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(req, times(1)).setAttribute("categories", categories);
    }

    @Test
    public void shouldForwardToUpdateForm() throws SQLException, ServletException, IOException {
        String forwardTo = "../editFood.jsp";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        Optional<Food> foodResult = Optional.of(new Food());
        String id = "1";

        when(req.getServletPath()).thenReturn("/food/edit");
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);
        when(req.getParameter("id")).thenReturn(id);
        when(foodService.findById(Long.parseLong(id))).thenReturn(foodResult);
        doReturn(categories).when(foodCategoryService).findAll();

        new FoodServlet(foodService, foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(req, times(1)).setAttribute("categories", categories);
        verify(req, times(1)).setAttribute("food", foodResult.get());
    }

    @Test
    public void shouldForwardToErrorWhenCannotFindFoodToUpdate() throws SQLException, ServletException, IOException {
        String redirectTo = "../error";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        Optional<Food> foodResult = Optional.empty();
        String id = "1";

        when(req.getServletPath()).thenReturn("/food/edit");
        when(req.getParameter("id")).thenReturn(id);
        when(foodService.findById(Long.parseLong(id))).thenReturn(foodResult);

        new FoodServlet(foodService, foodCategoryService).doGet(req, resp);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    public void shouldForwardToDeleteForm() throws ServletException, IOException, SQLException {
        String forwardTo = "../deleteFood.jsp";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        Optional<Food> foodResult = Optional.of(new Food());
        String id = "1";

        when(req.getServletPath()).thenReturn("/food/delete");
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);
        when(req.getParameter("id")).thenReturn(id);
        when(foodService.findById(Long.parseLong(id))).thenReturn(foodResult);

        new FoodServlet(foodService, foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
    }

    @Test
    public void shouldForwardToErrorWhenCannotFindFoodToDelete() throws SQLException, ServletException, IOException {
        String redirectTo = "../error";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        Optional<Food> foodResult = Optional.empty();
        String id = "1";

        when(req.getServletPath()).thenReturn("/food/delete");
        when(req.getParameter("id")).thenReturn(id);
        when(foodService.findById(Long.parseLong(id))).thenReturn(foodResult);

        new FoodServlet(foodService, foodCategoryService).doGet(req, resp);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    public void shouldCallFoodServiceToCreateFood() throws ServletException, IOException, BusinessRuleException, SQLException {
        final String action = "create";
        final String redirectTo = "/food";
        final String contextPath = "contextPath";

        final String name = "chocolate";
        final String category = "1";
        final String description = "";
        final String caloriesPer100g = "0";

        when(req.getParameter(anyString())).thenAnswer(invocationOnMock -> {
            switch((String)invocationOnMock.getArgument(0)) {
               case "action":
                   return action;
               case "name":
                   return name;
               case "category":
                   return category;
               case "description":
                   return description;
               case "caloriesPer100g":
                   return caloriesPer100g;
           }

           throw new Exception("Unknown parameter in req.getParameter()");
        });
        when(req.getContextPath()).thenReturn(contextPath);

        new FoodServlet(foodService, foodCategoryService).doPost(req, resp);

        verify(foodService, times(1)).create(any(Food.class));
        verify(resp, times(1)).sendRedirect(contextPath + redirectTo);
    }

    @Test
    public void shouldCallFoodServiceToUpdateFood() throws BusinessRuleException, SQLException, ServletException, IOException {
        final String action = "update";
        final String redirectTo = "/food";
        final String contextPath = "contextPath";

        final String name = "chocolate";
        final String category = "1";
        final String description = "";
        final String caloriesPer100g = "0";
        final String id = "1";

        when(req.getParameter(anyString())).thenAnswer(invocationOnMock -> {
            switch((String)invocationOnMock.getArgument(0)) {
                case "action":
                    return action;
                case "name":
                    return name;
                case "category":
                    return category;
                case "description":
                    return description;
                case "caloriesPer100g":
                    return caloriesPer100g;
                case "id":
                    return id;
            }

            throw new Exception("Unknown parameter in req.getParameter()");
        });
        when(req.getContextPath()).thenReturn(contextPath);

        new FoodServlet(foodService, foodCategoryService).doPost(req, resp);

        verify(foodService, times(1)).update(anyLong(), any(Food.class));
        verify(resp, times(1)).sendRedirect(contextPath + redirectTo);
    }

    @Test
    public void shouldCallFoodServiceToDeleteFood() throws ServletException, IOException, BusinessRuleException, SQLException {
        final String action = "delete";
        final String redirectTo = "/food";
        final String contextPath = "contextPath";

        final String name = "chocolate";
        final String category = "1";
        final String description = "";
        final String caloriesPer100g = "0";
        final String id = "1";

        when(req.getParameter(anyString())).thenAnswer(invocationOnMock -> {
            switch((String)invocationOnMock.getArgument(0)) {
                case "action":
                    return action;
                case "name":
                    return name;
                case "category":
                    return category;
                case "description":
                    return description;
                case "caloriesPer100g":
                    return caloriesPer100g;
                case "id":
                    return id;
            }

            throw new Exception("Unknown parameter in req.getParameter()");
        });
        when(req.getContextPath()).thenReturn(contextPath);

        new FoodServlet(foodService, foodCategoryService).doPost(req, resp);

        verify(foodService, times(1)).delete(anyLong());
        verify(resp, times(1)).sendRedirect(contextPath + redirectTo);
    }
}