package com.ivanko.service.impl;

import com.ivanko.exception.BusinessRuleException;
import com.ivanko.service.api.FoodService;
import com.ivanko.util.wrapper.FoodWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.Food;
import com.ivanko.model.FoodCategory;

import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodServiceImplTest {
    @Mock
    Dao<Food, Long> foodDaoMock;

    @Mock
    Dao<FoodCategory, Long> foodCategoryDaoMock;

    @Test
    public void shouldCreateFood() throws SQLException, BusinessRuleException {
        when(foodDaoMock.create(any(Food.class))).thenReturn(true);

        FoodService foodService = new FoodServiceImpl(foodDaoMock, foodCategoryDaoMock);

        Food food = new Food();

        foodService.create(food);

        verify(foodDaoMock, times(1)).create(food);
    }

    @Test
    public void shouldThrowExceptionWhenNegativeCaloriesProvided() throws SQLException, BusinessRuleException {
        FoodService foodService = new FoodServiceImpl(foodDaoMock, foodCategoryDaoMock);

        Food food = new Food();
        food.setCaloriesPer100g(-100);

        Assertions.assertThrows(BusinessRuleException.class, () -> {
            foodService.create(food);
        });
    }

    @Test
    public void shouldUpdateFoodUsingDao() throws SQLException, BusinessRuleException {
        when(foodDaoMock.update(anyLong(), any(Food.class))).thenReturn(true);

        FoodService foodService = new FoodServiceImpl(foodDaoMock, foodCategoryDaoMock);

        Food food = new Food();
        long id = 10L;

        foodService.update(id, food);
        verify(foodDaoMock, times(1)).update(id, food);
    }

    @Test
    void shouldDeleteFoodUsingDao() throws SQLException {
        when(foodDaoMock.delete(anyLong())).thenReturn(true);

        FoodService foodService = new FoodServiceImpl(foodDaoMock, foodCategoryDaoMock);

        long id = 10L;

        foodService.delete(id);
        verify(foodDaoMock, times(1)).delete(id);
    }

    @Test
    void shouldFetchAllWrappedFoods() throws SQLException {
        doReturn(List.of(new FoodWrapper())).when(foodDaoMock).findJoinedAll();

        FoodService foodService = new FoodServiceImpl(foodDaoMock, foodCategoryDaoMock);

        List<? extends Food> wrappedFoods = foodService.findAll();

        Assertions.assertEquals(1, wrappedFoods.size());
        Assertions.assertTrue(wrappedFoods.get(0) instanceof FoodWrapper);
        verify(foodDaoMock, times(1)).findJoinedAll();
    }
}
