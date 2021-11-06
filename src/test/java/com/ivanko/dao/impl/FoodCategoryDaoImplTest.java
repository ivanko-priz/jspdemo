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
import com.ivanko.model.FoodCategory;
import com.ivanko.factory.DatasourceFactory;

@ExtendWith(MockitoExtension.class)
public class FoodCategoryDaoImplTest {
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

    Dao<FoodCategory, Long> foodCategoryDao;

    @BeforeEach
    public void beforeEach() throws SQLException {
        when(datasourceFactory.getConnection()).thenReturn(connection);

        foodCategoryDao = new FoodCategoryDaoImpl(datasourceFactory);
    }

    @Test
    public void shouldCreateFoodCategoryWithFields() throws SQLException {
        final String sql = "INSERT INTO food_category (name) VALUES (?)";

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        FoodCategory foodCategory = new FoodCategory("sweets");

        foodCategoryDao.create(foodCategory);

        verify(preparedStatement, times(1)).setString(1, foodCategory.getName());
        verify(preparedStatement, times(1)).execute();
    }

    @Test
    public void shouldUpdateFoodCategoryWithProvidedObject() throws SQLException {
        final String sql = "UPDATE food_category SET name = ? WHERE id = ?";
        final long id = 10L;

        FoodCategory foodCategory = new FoodCategory("sweets");

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        foodCategoryDao.update(id, foodCategory);

        verify(preparedStatement, times(1)).setString(1, foodCategory.getName());
        verify(preparedStatement, times(1)).setLong(2, id);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void shouldDeleteFoodCategoryById() throws SQLException {
        final String sql = "DELETE FROM food_category WHERE id = ?";
        final long id = 10L;

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);

        foodCategoryDao.delete(id);

        verify(preparedStatement, times(1)).setLong(1, id);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void shouldFindFoodCategoryById() throws SQLException {
        final String sql = "SELECT id, name, created_at FROM food_category WHERE id = ?";
        long id = 10L;

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        Optional<FoodCategory> foodCategoryResult = foodCategoryDao.findById(id);
        verify(preparedStatement, times(1)).setLong(1, id);
        verify(preparedStatement, times(1)).executeQuery();
        Assertions.assertTrue(foodCategoryResult.isPresent());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenCannotFindFoodCategoryById() throws SQLException {
        final String sql = "SELECT id, name, created_at FROM food_category WHERE id = ?";
        long id = 10L;

        when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<FoodCategory> foodCategoryResult = foodCategoryDao.findById(id);
        verify(preparedStatement, times(1)).setLong(1, id);
        verify(preparedStatement, times(1)).executeQuery();
        Assertions.assertTrue(foodCategoryResult.isEmpty());
    }

    @Test
    public void shouldFindAllFoodCategories() throws SQLException {
        final String sql = "SELECT * FROM food_category";

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(sql)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        List<FoodCategory> foodCategories = foodCategoryDao.findAll();
        Assertions.assertEquals(2, foodCategories.size());
    }
}