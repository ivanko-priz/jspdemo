package com.ivanko.service.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ivanko.exception.BusinessRuleException;
import com.ivanko.model.Food;

public interface FoodService {
    boolean create(Food food) throws SQLException, BusinessRuleException;
    boolean update(long id, Food food) throws SQLException, BusinessRuleException;
    boolean delete(long id) throws SQLException;
    List<Food> findAll() throws SQLException;
    Optional<Food> findById(long id) throws SQLException;
}