package com.ivanko.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ivanko.dao.api.Dao;
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.model.Food;
import com.ivanko.util.mapper.SqlResultToFoodMapper;
import com.ivanko.util.wrapper.FoodWrapper;

public class FoodDaoImpl implements Dao<Food, Long> {
    private DatasourceFactory datasourceFactory;

    public FoodDaoImpl() {
        this.datasourceFactory = new DatasourceFactory();
    }

    public FoodDaoImpl(DatasourceFactory datasourceFactory) {
        this.datasourceFactory = datasourceFactory;
    }

    @Override
    public boolean create(Food obj) throws SQLException {
        final String sql = "INSERT INTO food (name, food_category_id, description, calories_per_100_g) VALUES (?, ?, ?, ?)";

        try (Connection conn = datasourceFactory.getConnection()) {
            PreparedStatement statement;
            statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getName());

            if (obj.getFoodCategoryId() == 0) {
                statement.setNull(2, Types.BIGINT);
            } else {
                statement.setLong(2, obj.getFoodCategoryId());
            }

            statement.setString(3, obj.getDescription());
            statement.setInt(4, obj.getCaloriesPer100g());

            return statement.execute();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean update(Long aLong, Food obj) throws SQLException {
        final String sql = "UPDATE food SET name = ?, food_category_id = ?, description = ?, calories_per_100_g = ? WHERE id = ?";

        try (Connection conn = datasourceFactory.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, obj.getName());
            if (obj.getFoodCategoryId() == 0) {
                statement.setNull(2, Types.BIGINT);
            } else {
                statement.setLong(2, obj.getFoodCategoryId());
            }

            statement.setString(3, obj.getDescription());
            statement.setInt(4, obj.getCaloriesPer100g());

            statement.setLong(5, aLong);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean delete(Long aLong) throws SQLException {
        final String sql = "DELETE FROM food WHERE id = ?";

        try (Connection conn = datasourceFactory.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, aLong);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Optional<Food> findById(Long aLong) throws SQLException {
        final String sql = "SELECT id, name, food_category_id, description, calories_per_100_g, created_at FROM food WHERE id = ?";

        try (Connection conn = datasourceFactory.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, aLong);

            ResultSet resultSet = statement.executeQuery();
            Optional<Food> result;

            if (resultSet.next()) {
                Food food = SqlResultToFoodMapper.mapToFood(resultSet);
                result = Optional.of(food);
            } else {
                result = Optional.empty();
            }

            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Food> findAll() throws SQLException {
        final String sql = "SELECT * FROM food";

        try (Connection conn = datasourceFactory.getConnection()) {
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Food> result = new ArrayList<>(resultSet.getFetchSize());

            while(resultSet.next()) {
                Food food = SqlResultToFoodMapper.mapToFood(resultSet);
                result.add(food);
            }

            return result;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<FoodWrapper> findJoinedAll() throws SQLException {
        final String sql = "SELECT f.id AS id, f.name AS name, f_c.name AS category, f.food_category_id as food_category_id, f.description AS description, f.calories_per_100_g AS calories_per_100_g, f.created_at AS created_at FROM food f LEFT JOIN food_category f_c ON f_c.id = f.food_category_id";

        try (Connection conn = datasourceFactory.getConnection()) {
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<FoodWrapper> result = new ArrayList<>(resultSet.getFetchSize());

            while(resultSet.next()) {
                FoodWrapper food = SqlResultToFoodMapper.mapToWrapper(resultSet);
                result.add(food);
            }

            return result;
        } catch (SQLException e) {
            throw e;
        }
    }
}
