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
import com.ivanko.factory.DatasourceFactory;
import com.ivanko.model.FoodCategory;

@Testcontainers
public class FoodCategoryDaoImplIntegrationTest {
    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("food")
            .withUsername("vano")
            .withPassword("vano")
            .withInitScript("sql/init.sql");

    private static DatasourceFactory datasourceFactory;
    private static Dao<FoodCategory, Long> foodCategoryDao;

    private FoodCategory foodCategory;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        datasourceFactory = new DatasourceFactory(mysql.getMappedPort(3306));
        foodCategoryDao = new FoodCategoryDaoImpl(datasourceFactory);
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        foodCategory = new FoodCategory("healthy");

        foodCategoryDao.create(foodCategory);
    }

    @AfterEach
    public void afterEach() throws SQLException {
        try (Connection conn = datasourceFactory.getConnection()) {
            Statement s = conn.createStatement();
            /*
                Truncate table to nullify auto increment
                and have newly inserted record have id of 1L.
                Because table has foreign key constrains
                we need to set FOREIGN_KEY_CHECKS = 0 first
             */
            s.execute("SET FOREIGN_KEY_CHECKS = 0;");
            s.execute("TRUNCATE food_category;");
        }
    }

    @Test
    @Tag("integration")
    public void shouldCreateNewFoodCategory() throws SQLException {
        FoodCategory fromDb = foodCategoryDao.findById(1L).orElse(null);

        assertNotNull(fromDb);
        assertEquals(foodCategory.getName(), fromDb.getName());
    }

    @Test
    public void shouldUpdateFoodCategoryById() throws SQLException {
        FoodCategory newFoodCategory = new FoodCategory();
        newFoodCategory.setName("beverage");

        foodCategoryDao.update(1L, newFoodCategory);

        FoodCategory fromDb = foodCategoryDao.findAll().get(0);

        assertNotNull(fromDb);
        assertEquals(newFoodCategory.getName(), fromDb.getName());
    }

    @Test
    public void shouldDeleteFoodCategoryById() throws SQLException {
        assertTrue(foodCategoryDao.delete(1L));
        assertTrue(foodCategoryDao.findById(foodCategory.getId()).isEmpty());
    }

    @Test
    public void shouldFindFoodCategoryById() throws SQLException {
        FoodCategory foodCategory = foodCategoryDao.findById(1L).orElse(null);

        assertNotNull(foodCategory);
        assertEquals(foodCategory.getName(), foodCategory.getName());
    }

    @Test
    public void shouldFetchAllFoodCategories() throws SQLException {
        List<FoodCategory> categories = List.of(new FoodCategory("fastfoods"), new FoodCategory("candies"));

        for(FoodCategory category : categories) {
            foodCategoryDao.create(category);
        }

        List<FoodCategory> categoriesFromDb = foodCategoryDao.findAll();

        // Before each test we create new food in empty food table,
        // so overall we'll have 3 items
        assertEquals(categories.size() + 1, categoriesFromDb.size());
    }
}
