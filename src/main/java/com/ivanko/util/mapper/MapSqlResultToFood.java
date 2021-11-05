package com.ivanko.util.mapper;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ivanko.model.Food;
import com.ivanko.util.wrapper.FoodWrapper;

public class MapSqlResultToFood {
    public static Food mapToFood(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        long foodCategoryId = resultSet.getLong("food_category_id");
        String description = resultSet.getString("description");
        int caloriesPer100g = resultSet.getInt("calories_per_100_g");
        Date date = resultSet.getDate("created_at");

        return new Food(id, name, foodCategoryId, description, caloriesPer100g, date);
    }

    public static FoodWrapper mapToWrapper(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String foodCategory = resultSet.getString("category");
        long foodCategoryId = resultSet.getLong("food_category_id");
        String description = resultSet.getString("description");
        int caloriesPer100g = resultSet.getInt("calories_per_100_g");

        Date date = resultSet.getDate("created_at");

        return new FoodWrapper(id, name, foodCategory, foodCategoryId, description, caloriesPer100g, date);
    }
}
