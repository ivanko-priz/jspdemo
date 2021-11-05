package com.ivanko.service.impl;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

import com.ivanko.dao.api.Dao;
import com.ivanko.model.FoodCategory;
import com.ivanko.service.api.FoodCategoryService;

public class FoodCategoryServiceImpl implements FoodCategoryService {
    private Dao<FoodCategory, Long> foodCategoryDao;

    public FoodCategoryServiceImpl(Dao<FoodCategory, Long> foodCategoryDao) {
        this.foodCategoryDao = foodCategoryDao;
    }

    @Override
    public boolean create(FoodCategory foodCategory) throws SQLException {
        return foodCategoryDao.create(foodCategory);
    }

    @Override
    public boolean update(long id, FoodCategory foodCategory) throws SQLException {
        return foodCategoryDao.update(id, foodCategory);
    }

    @Override
    public boolean delete(long id) throws SQLException {
        return foodCategoryDao.delete(id);
    }

    @Override
    public List<FoodCategory> findAll() throws SQLException {
        return foodCategoryDao.findAll();
    }

    @Override
    public Optional<FoodCategory> findById(long id) throws SQLException {
        return foodCategoryDao.findById(id);
    }
}
