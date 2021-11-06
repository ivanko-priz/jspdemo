package com.ivanko.dao.impl;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.Food;
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.model.FoodCategory;
import com.ivanko.util.wrapper.FoodWrapper;

@Testcontainers
public class FoodDaoImplIntegrationTest {
    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("food")
            .withUsername("vano")
            .withPassword("vano")
            .withInitScript("sql/init.sql");

    private static DatasourceFactory datasourceFactory;
    private static Dao<Food, Long> foodDao;
    private static Dao<FoodCategory, Long> foodCategoryDao;

    private Food food;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        datasourceFactory = new DatasourceFactory(mysql.getMappedPort(3306));
        foodDao = new FoodDaoImpl(datasourceFactory);
        foodCategoryDao = new FoodCategoryDaoImpl(datasourceFactory);

        foodCategoryDao.create(new FoodCategory("healthy"));
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        food = new Food();

        food.setName("happy monkey");
        food.setCaloriesPer100g(5000);
        food.setDescription("yummy but expensive");
        food.setFoodCategoryId(1);

        foodDao.create(food);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        try (Connection conn = datasourceFactory.getConnection()) {
            Statement s = conn.createStatement();
            s.execute("TRUNCATE food;");
        }
    }

    @Test
    @Tag("integration")
    public void shouldCreateNewFood() throws SQLException {
        Food fromDb = foodDao.findById(1L).orElse(null);

        assertNotNull(fromDb);
        assertEquals(food.getName(), fromDb.getName());
        assertEquals(food.getDescription(), fromDb.getDescription());
        assertEquals(food.getCaloriesPer100g(), fromDb.getCaloriesPer100g());
        assertEquals(food.getFoodCategoryId(), fromDb.getFoodCategoryId());
    }

    @Test
    public void shouldUpdateFoodById() throws SQLException {
        Food newFood = new Food();
        newFood.setDescription("new description");
        newFood.setName("crunchy fruit loops");
        newFood.setCaloriesPer100g(200);

        foodDao.update(1L, newFood);

        Food fromDb = foodDao.findById(1L).orElse(null);

        assertNotNull(fromDb);
        assertEquals(newFood.getName(), fromDb.getName());
        assertEquals(newFood.getDescription(), fromDb.getDescription());
        assertEquals(newFood.getCaloriesPer100g(), fromDb.getCaloriesPer100g());
        assertEquals(newFood.getFoodCategoryId(), fromDb.getFoodCategoryId());
    }

    @Test
    public void shouldDeleteFoodById() throws SQLException {
        assertTrue(foodDao.delete(1L));
        assertTrue(foodDao.findById(1L).isEmpty());
    }

    @Test
    public void shouldFindFoodById() throws SQLException {
        Food fromDb = foodDao.findById(1L).orElse(null);

        assertNotNull(fromDb);
        assertEquals(food.getName(), fromDb.getName());
        assertEquals(food.getDescription(), fromDb.getDescription());
        assertEquals(food.getCaloriesPer100g(), fromDb.getCaloriesPer100g());
        assertEquals(food.getFoodCategoryId(), fromDb.getFoodCategoryId());
    }

    @Test
    public void shouldFetchAllFood() throws SQLException {
        List<Food> foods = List.of(new Food("bananas"), new Food("ice-cream"));

        for(Food food : foods) {
            foodDao.create(food);
        }

        List<Food> foodsFromDb = foodDao.findAll();

        // Before each test we create new food in empty food table,
        // so overall we'll have 3 food items
        assertEquals(foods.size() + 1, foodsFromDb.size());
    }

    @Test
    public void shouldFetchAllWrappedFood() throws SQLException {
        List<Food> foods = List.of(new Food("bananas"), new Food("ice-cream"));

        for(Food food : foods) {
            foodDao.create(food);
        }

        List<?> foodsFromDb = foodDao.findJoinedAll();
        FoodCategory foodCategoryFromDb = foodCategoryDao.findById(1L).orElse(null);

        assertNotNull(foodCategoryFromDb);
        assertTrue(foodsFromDb.get(0) instanceof FoodWrapper);
        assertEquals(foodCategoryFromDb.getName(), ((FoodWrapper) foodsFromDb.get(0)).getCategory());
    }
}