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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ivanko.service.api.FoodCategoryService;
import com.ivanko.exception.BusinessRuleException;
import com.ivanko.model.FoodCategory;

@ExtendWith(MockitoExtension.class)
public class FoodCategoryServletTest {
    @Mock
    FoodCategoryService foodCategoryService;

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    RequestDispatcher rd;

    @Test
    public void shouldForwardAndFetchListOfFoodCategory() throws ServletException, IOException, SQLException {
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        final String forwardTo = "listCategory.jsp";

        when(req.getServletPath()).thenReturn("/category");
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);
        doReturn(categories).when(foodCategoryService).findAll();

        new FoodCategoryServlet(foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(req, times(1)).setAttribute("categories", categories);
    }

    @Test
    public void shouldForwardToCreateForm() throws ServletException, IOException, SQLException {
        String forwardTo = "../createFoodCategory.jsp";

        when(req.getServletPath()).thenReturn("/category/create");
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);

        new FoodCategoryServlet(foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
    }

    @Test
    public void shouldForwardToUpdateForm() throws ServletException, SQLException, IOException {
        String forwardTo = "../editFoodCategory.jsp";
        String id = "10";

        when(req.getServletPath()).thenReturn("/category/edit");
        when(req.getParameter("id")).thenReturn(id);
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);
        when(foodCategoryService.findById(anyLong())).thenReturn(Optional.of(new FoodCategory()));

        new FoodCategoryServlet(foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
    }

    @Test
    public void shouldForwardToErrorWhenCannotFindFoodCategoryToUpdate() throws SQLException, ServletException, IOException {
        String redirectTo = "../error";
        Optional<FoodCategory> foodResult = Optional.empty();
        String id = "1";

        when(req.getServletPath()).thenReturn("/category/edit");
        when(req.getParameter("id")).thenReturn(id);
        when(foodCategoryService.findById(Long.parseLong(id))).thenReturn(foodResult);

        new FoodCategoryServlet(foodCategoryService).doGet(req, resp);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    public void shouldForwardToDeleteForm() throws ServletException, IOException, SQLException {
        String forwardTo = "../deleteFoodCategory.jsp";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        Optional<FoodCategory> foodResult = Optional.of(new FoodCategory());
        String id = "1";

        when(req.getServletPath()).thenReturn("/category/delete");
        when(req.getRequestDispatcher(forwardTo)).thenReturn(rd);
        when(req.getParameter("id")).thenReturn(id);
        when(foodCategoryService.findById(Long.parseLong(id))).thenReturn(foodResult);

        new FoodCategoryServlet(foodCategoryService).doGet(req, resp);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
    }

    @Test
    public void shouldForwardToErrorWhenCannotFindFoodCategoryToDelete() throws SQLException, ServletException, IOException {
        String redirectTo = "../error";
        List<FoodCategory> categories = java.util.List.of(new FoodCategory());
        Optional<FoodCategory> foodResult = Optional.empty();
        String id = "1";

        when(req.getServletPath()).thenReturn("/category/delete");
        when(req.getParameter("id")).thenReturn(id);
        when(foodCategoryService.findById(Long.parseLong(id))).thenReturn(foodResult);

        new FoodCategoryServlet(foodCategoryService).doGet(req, resp);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    public void shouldCallFoodCategoryServiceToCreateFood() throws ServletException, IOException, BusinessRuleException, SQLException {
        final String action = "create";
        final String redirectTo = "/category";
        final String contextPath = "contextPath";

        final String name = "leafy greens";

        when(req.getParameter(anyString())).thenAnswer(invocationOnMock -> {
            switch((String)invocationOnMock.getArgument(0)) {
                case "action":
                    return action;
                case "name":
                    return name;
            }

            throw new Exception("Unknown parameter in req.getParameter()");
        });
        when(req.getContextPath()).thenReturn(contextPath);

        new FoodCategoryServlet(foodCategoryService).doPost(req, resp);

        verify(foodCategoryService, times(1)).create(any(FoodCategory.class));
        verify(resp, times(1)).sendRedirect(contextPath + redirectTo);
    }

    @Test
    public void shouldCallFoodCategoryServiceToUpdateFood() throws BusinessRuleException, SQLException, ServletException, IOException {
        final String action = "update";
        final String redirectTo = "/category";
        final String contextPath = "contextPath";

        final String name = "veggies";
        final String id = "1";

        when(req.getParameter(anyString())).thenAnswer(invocationOnMock -> {
            switch((String)invocationOnMock.getArgument(0)) {
                case "action":
                    return action;
                case "name":
                    return name;
                case "id":
                    return id;
            }

            throw new Exception("Unknown parameter in req.getParameter()");
        });
        when(req.getContextPath()).thenReturn(contextPath);

        new FoodCategoryServlet(foodCategoryService).doPost(req, resp);

        verify(foodCategoryService, times(1)).update(anyLong(), any(FoodCategory.class));
        verify(resp, times(1)).sendRedirect(contextPath + redirectTo);
    }

    @Test
    public void shouldCallFoodServiceToDeleteFood() throws ServletException, IOException, BusinessRuleException, SQLException {
        final String action = "delete";
        final String redirectTo = "/category";
        final String contextPath = "contextPath";

        final String name = "dairy";
        final String id = "1";

        when(req.getParameter(anyString())).thenAnswer(invocationOnMock -> {
            switch((String)invocationOnMock.getArgument(0)) {
                case "action":
                    return action;
                case "name":
                    return name;
                case "id":
                    return id;
            }

            throw new Exception("Unknown parameter in req.getParameter()");
        });
        when(req.getContextPath()).thenReturn(contextPath);

        new FoodCategoryServlet(foodCategoryService).doPost(req, resp);

        verify(foodCategoryService, times(1)).delete(anyLong());
        verify(resp, times(1)).sendRedirect(contextPath + redirectTo);
    }
}