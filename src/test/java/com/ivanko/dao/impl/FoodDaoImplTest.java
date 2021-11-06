package com.ivanko.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.Food;
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.util.wrapper.FoodWrapper;

@ExtendWith(MockitoExtension.class)
public class FoodDaoImplTest {
    @Mock
    DatasourceFactory datasourceFactory;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    Statement statement;
    @Mock
    ResultSet resultSet;

    Dao<Food, Long> foodDao;

    @BeforeEach
    public void beforeEach() throws SQLException {
        when(datasourceFactory.getConnection()).thenReturn(connection);

        foodDao = new FoodDaoImpl(datasourceFactory);
    }

    @Test
    public void shouldCreateFoodWithFields() throws SQLException {
        final String sql = "INSERT INTO food (name, food_category_id, description, calories_per_100_g) VALUES (?, ?, ?, ?)";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Food food = new Food("bananas");
        food.setDescription("lorem ipsum");
        food.setCaloriesPer100g(100);
        food.setFoodCategoryId(5);
        food.setId(10);

        foodDao.create(food);

        verify(preparedStatement, times(1)).setString(1, food.getName());
        verify(preparedStatement, times(1)).setLong(2, food.getFoodCategoryId());
        verify(preparedStatement, times(1)).setString(3, food.getDescription());
        verify(preparedStatement, times(1)).setInt(4, food.getCaloriesPer100g());
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    public void shouldCreateFoodWithoutCategory() throws SQLException {
        final String sql = "INSERT INTO food (name, food_category_id, description, calories_per_100_g) VALUES (?, ?, ?, ?)";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Food food = new Food("bananas");

        foodDao.create(food);

        verify(preparedStatement, times(1)).setString(1, food.getName());
        verify(preparedStatement, times(1)).setNull(2, Types.BIGINT);
        verify(preparedStatement, times(1)).setString(3, food.getDescription());
        verify(preparedStatement, times(1)).setInt(4, food.getCaloriesPer100g());
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    void shouldUpdateFoodWithProvidedObject() throws SQLException {
        final String sql = "UPDATE food SET name = ?, food_category_id = ?, description = ?, calories_per_100_g = ? WHERE id = ?";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        Food food = new Food("bananas");
        food.setDescription("lorem ipsum");
        food.setCaloriesPer100g(100);
        food.setFoodCategoryId(5);
        food.setId(10);

        foodDao.update(food.getId(), food);

        verify(preparedStatement, times(1)).setString(1, food.getName());
        verify(preparedStatement, times(1)).setLong(2, food.getFoodCategoryId());
        verify(preparedStatement, times(1)).setString(3, food.getDescription());
        verify(preparedStatement, times(1)).setInt(4, food.getCaloriesPer100g());
        verify(preparedStatement, times(1)).setLong(5, food.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void shouldDeleteFoodById() throws SQLException {
        final String sql = "DELETE FROM food WHERE id = ?";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        long id = 10L;

        foodDao.delete(id);

        verify(preparedStatement, times(1)).setLong(1, id);
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    public void shouldReturnListOfFoods() throws SQLException {
        final String sql = "SELECT * FROM food";

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        List<Food> foods = foodDao.findAll();

        verify(statement, times(1)).executeQuery(sql);
        Assertions.assertEquals(2, foods.size());
    }

    @Test
    public void shouldReturnListOfFoodWrappers() throws SQLException {
        final String sql = "SELECT f.id AS id, f.name AS name, f_c.name AS category, f.food_category_id as food_category_id, f.description AS description, f.calories_per_100_g AS calories_per_100_g, f.created_at AS created_at FROM food f LEFT JOIN food_category f_c ON f_c.id = f.food_category_id";

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        List<? extends Food> foods = foodDao.findJoinedAll();

        verify(statement, times(1)).executeQuery(sql);
        Assertions.assertEquals(2, foods.size());
        Assertions.assertTrue(foods.get(0) instanceof FoodWrapper);
    }

    @Test
    public void shouldFindFoodById() throws SQLException {
        final String sql = "SELECT id, name, food_category_id, description, calories_per_100_g, created_at FROM food WHERE id = ?";
        final long id = 10L;

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Optional<Food> foodResult = foodDao.findById(id);

        verify(preparedStatement, times(1)).setLong(1, id);
        verify(preparedStatement, times(1)).executeQuery();
        Assertions.assertTrue(foodResult.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenCannotFindFoodById() throws SQLException {
        final String sql = "SELECT id, name, food_category_id, description, calories_per_100_g, created_at FROM food WHERE id = ?";
        final long id = 10L;

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<Food> foodResult = foodDao.findById(id);

        verify(preparedStatement, times(1)).setLong(1, id);
        verify(preparedStatement, times(1)).executeQuery();
        Assertions.assertTrue(foodResult.isEmpty());
    }
}
