package com.ivanko.dao.impl;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.Food;
import com.ivanko.factory.DatasourceFactory;

@Testcontainers
public class FoodDaoImplTest {
    @Container
    public MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("food")
            .withUsername("vano")
            .withPassword("vano")
            .withInitScript("sql/init.sql");;

    @Test
    @Tag("integration")
    public void shouldCreateNewFood() throws SQLException {
        DatasourceFactory datasourceFactory = new DatasourceFactory(mysql.getMappedPort(3306));
        Dao<Food, Long> foodDao = new FoodDaoImpl(datasourceFactory);

        Food food = new Food("bananas");

        foodDao.create(food);

        Food fromDb = foodDao.findById(1L).orElse(null);

        assertNotNull(fromDb);
        assertEquals("bananas", fromDb.getName());
    }
}
