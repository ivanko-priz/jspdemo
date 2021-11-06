package com.ivanko.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.FoodCategory;
import com.ivanko.service.api.FoodCategoryService;


@ExtendWith(MockitoExtension.class)
public class FoodCategoryServiceImplTest {
    @Mock
    Dao<FoodCategory, Long> foodCategoryDaoMock;

    @InjectMocks
    FoodCategoryService foodCategoryService = new FoodCategoryServiceImpl(foodCategoryDaoMock);

    @Test
    @Tag("unit")
    public void shouldCreateFoodCategoryUsingDao() throws Exception {
        when(foodCategoryDaoMock.create(any(FoodCategory.class))).thenReturn(true);

        FoodCategory foodCategory = new FoodCategory();

        foodCategoryService.create(foodCategory);

        verify(foodCategoryDaoMock, times(1)).create(foodCategory);
    }

    @Test
    public void shouldUpdateFoodCategoryUsingDao() throws Exception {
        when(foodCategoryDaoMock.update(anyLong(), any(FoodCategory.class))).thenReturn(true);

        FoodCategory foodCategory = new FoodCategory();
        long id = 10L;

        foodCategoryService.update(id, foodCategory);

        verify(foodCategoryDaoMock, times(1)).update(id, foodCategory);
    }

    @Test
    public void shouldDeleteFoodCategoryUsingDao() throws Exception {
        when(foodCategoryDaoMock.delete(anyLong())).thenReturn(true);
        long id = 10L;

        foodCategoryService.delete(id);

        verify(foodCategoryDaoMock, times(1)).delete(id);
    }

    @Test
    public void shouldFindByIdUsingDao() throws Exception {
        when(foodCategoryDaoMock.findById(anyLong())).thenReturn(Optional.of(new FoodCategory()));

        long id = 10L;

        foodCategoryService.findById(id);
        verify(foodCategoryDaoMock, times(1)).findById(id);
    }

    @Test
    public void shouldFindAllUsingDao() throws Exception {
        when(foodCategoryDaoMock.findAll()).thenReturn(List.of(new FoodCategory()));

        List<FoodCategory> foodCategories = foodCategoryService.findAll();

        verify(foodCategoryDaoMock, times(1)).findAll();
        Assertions.assertEquals(1, foodCategories.size());
    }
}
