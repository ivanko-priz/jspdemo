package com.ivanko.util.mapper;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ivanko.model.FoodCategory;

public class MapSqlResultToFoodCategory {
    public static FoodCategory map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Date date = resultSet.getDate("created_at");

        return new FoodCategory(id, name, date);
    }
}
