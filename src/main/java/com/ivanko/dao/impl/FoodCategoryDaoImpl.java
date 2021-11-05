package com.ivanko.dao.impl;

import com.ivanko.dao.api.Dao;
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.model.FoodCategory;
import com.ivanko.util.mapper.MapSqlResultToFoodCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FoodCategoryDaoImpl implements Dao<FoodCategory, Long> {
    private DatasourceFactory datasourceFactory;

    public FoodCategoryDaoImpl() { this.datasourceFactory = new DatasourceFactory(); }

    public FoodCategoryDaoImpl(DatasourceFactory datasourceFactory) {
        this.datasourceFactory = datasourceFactory;
    }

    @Override
    public boolean create(FoodCategory obj) throws SQLException {
        final String sql = "INSERT INTO food_category (name) VALUES (?)";
        Connection conn = datasourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, obj.getName());

        return statement.execute();
    }

    @Override
    public boolean update(Long aLong, FoodCategory obj) throws SQLException {
        final String sql = "UPDATE food_category SET name = ? WHERE id = ?";
        Connection conn = datasourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, obj.getName());
        statement.setLong(2, aLong);

        return statement.executeUpdate() == 1;    }

    @Override
    public boolean delete(Long aLong) throws SQLException {
        final String sql = "DELETE FROM food_category WHERE id = ?";
        Connection conn = datasourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setLong(1, aLong);

        return statement.execute();
    }

    @Override
    public Optional<FoodCategory> findById(Long aLong) throws SQLException {
        final String sql = "SELECT id, name, created_at FROM food_category WHERE id = ?";
        Connection conn = datasourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setLong(1, aLong);

        ResultSet resultSet = statement.executeQuery();
        Optional<FoodCategory> result;

        if (resultSet.next()) {
            FoodCategory foodCategory = MapSqlResultToFoodCategory.map(resultSet);
            result = Optional.of(foodCategory);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public List<FoodCategory> findAll() throws SQLException {
        final String sql = "SELECT * FROM food_category";
        Connection conn = datasourceFactory.getConnection();
        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<FoodCategory> result = new ArrayList<>(resultSet.getFetchSize());

        while(resultSet.next()) {
            FoodCategory foodCategory = MapSqlResultToFoodCategory.map(resultSet);
            result.add(foodCategory);
        }

        return result;
    }

    @Override
    public List<? extends FoodCategory> findJoinedAll() throws SQLException {
        return findAll();
    }
}
