package com.ivanko.util.mapper;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ivanko.model.FoodCategory;

/**
 *  Mapper that takes in a result set and converts it to FoodCategory object
 *  as database column names use different naming convention
 */
public class SqlResultToFoodCategoryMapper {
    public static FoodCategory map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Date date = resultSet.getDate("created_at");

        return new FoodCategory(id, name, date);
    }
}
