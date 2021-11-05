package com.ivanko.service.impl;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.FoodCategory;
import com.ivanko.service.api.FoodCategoryService;

import static org.mockito.Mockito.*;

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
}
