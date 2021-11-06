package com.ivanko.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class FoodTest {
    @Test
    public void shouldSetAndGetId() {
        Food food = new Food();

        food.setId(5);

        Assertions.assertEquals(5, food.getId());
    }

    @Test
    public void shouldSetAndGetName() {
        Food food = new Food();

        food.setName("test");

        Assertions.assertEquals("test", food.getName());
    }

    @Test
    public void shouldSetAndGetDescription() {
        Food food = new Food();

        food.setDescription("test");

        Assertions.assertEquals("test", food.getDescription());
    }

    @Test
    public void shouldSetAndGetFoodCategoryId() {
        Food food = new Food();

        food.setFoodCategoryId(5);

        Assertions.assertEquals(5, food.getFoodCategoryId());
    }

    @Test
    public void shouldSetAndGetCaloriesPer100g() {
        Food food = new Food();

        food.setCaloriesPer100g(5);

        Assertions.assertEquals(5, food.getCaloriesPer100g());
    }


    @Test
    public void shouldSetAndGetCreatedAt() {
        Food food = new Food();

        Date date = new Date();

        food.setCreatedAt(date);

        Assertions.assertEquals(date, food.getCreatedAt());
    }
}