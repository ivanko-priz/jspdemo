package com.ivanko.service.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ivanko.exception.BusinessRuleException;
import com.ivanko.model.Food;

public interface FoodService {
    /**
     *  Creates food
     *
     * @param food - food item to be created
     * @return true when success
     * @throws SQLException
     * @throws BusinessRuleException if negative calories are provided
     */
    boolean create(Food food) throws SQLException, BusinessRuleException;

    /**
     * Updates food based on id with provided Food object
     *
     * @param id - id of item to be updated
     * @param food - new fields which will be set
     * @return true if success
     * @throws SQLException
     * @throws BusinessRuleException if negative calories are provided
     */
    boolean update(long id, Food food) throws SQLException, BusinessRuleException;

    /**
     *  Deletes food by id
     *
     * @param id - id of item to be deleted
     * @return true if success
     * @throws SQLException
     */
    boolean delete(long id) throws SQLException;

    /**
     * Fetches all Food objects from the database
     *
     * @return List of Food
     * @throws SQLException
     */
    List<Food> findAll() throws SQLException;

    /**
     * Fetches by Food object by id
     *
     * @param id - id of item to be fetched
     * @return Optional with Food inside if food with such id exists
     * @throws SQLException
     */
    Optional<Food> findById(long id) throws SQLException;
}