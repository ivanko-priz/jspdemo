package com.ivanko.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.*;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

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
}
