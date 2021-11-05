package com.ivanko.service.api;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

import com.ivanko.model.FoodCategory;

public interface FoodCategoryService {
    boolean create(FoodCategory foodCategory) throws SQLException;
    boolean update(long id, FoodCategory foodCategory) throws SQLException;
    boolean delete(long id) throws SQLException;
    List<FoodCategory> findAll() throws SQLException;
    Optional<FoodCategory> findById(long id) throws SQLException;
}
