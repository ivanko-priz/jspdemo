package com.ivanko.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class FoodCategoryTest {
    @Test
    public void shouldSetAndGetId() {
        FoodCategory category = new FoodCategory();

        category.setId(5);

        Assertions.assertEquals(5, category.getId());
    }

    @Test
    public void shouldSetAndGetName() {
        FoodCategory category = new FoodCategory();

        category.setName("test");

        Assertions.assertEquals("test", category.getName());
    }
}
