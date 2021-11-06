package com.ivanko.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ivanko.dao.api.Dao;
import com.ivanko.exception.BusinessRuleException;
import com.ivanko.model.Food;
import com.ivanko.model.FoodCategory;
import com.ivanko.service.api.FoodService;

public class FoodServiceImpl implements FoodService {
    private Dao<Food, Long> foodDao;
    private Dao<FoodCategory, Long> foodCategoryDao;

    public FoodServiceImpl() {}

    public FoodServiceImpl(Dao<Food, Long> foodDao, Dao<FoodCategory, Long> foodCategoryDao) {
        this.foodDao = foodDao;
        this.foodCategoryDao = foodCategoryDao;
    }

    public void setFoodDao(Dao<Food, Long> dao) {
        this.foodDao = dao;
    }

    public void setFoodCategoryDao(Dao<FoodCategory, Long> dao) {
        this.foodCategoryDao = dao;
    }

    @Override
    public boolean create(Food food) throws SQLException, BusinessRuleException {
        if (food.getCaloriesPer100g() < 0) {
            throw new BusinessRuleException("Calories cannot be negative");
        }

        return foodDao.create(food);
    }

    @Override
    public boolean update(long id, Food food) throws SQLException, BusinessRuleException {
        if (food.getCaloriesPer100g() < 0) {
            throw new BusinessRuleException("Calories cannot be negative");
        }

        return foodDao.update(id, food);
    }

    @Override
    public boolean delete(long id) throws SQLException {
        return foodDao.delete(id);
    }

    @Override
    public List<? extends Food> findAll() throws SQLException {
        return foodDao.findJoinedAll();
    }

    @Override
    public Optional<Food> findById(long id) throws SQLException {
        return foodDao.findById(id);
    }
}
