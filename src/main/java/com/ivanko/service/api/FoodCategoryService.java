package com.ivanko.service.api;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

import com.ivanko.model.FoodCategory;

public interface FoodCategoryService {
    /**
     * Creates new FoodCategory object
     *
     * @param  foodCategory - object be added
     * @return boolean when success
     * @throws SQLException
     */
    boolean create(FoodCategory foodCategory) throws SQLException;

    /**
     * Updates FoodCategory instance by id
     *
     * @param  id - id of item to be updated
     * @param  foodCategory -  object with updated fields
     * @return boolean when success
     * @throws SQLException
     */
    boolean update(long id, FoodCategory foodCategory) throws SQLException;

    /**
     * Deletes
     *
     * @param id - id of item to be deleted
     * @return boolean when success
     * @throws SQLException
     */
    boolean delete(long id) throws SQLException;

    /**
     *  Fetches list of FoodCategory of all objects in the table
     *
     * @throws SQLException
     */
    List<FoodCategory> findAll() throws SQLException;

    /**
     * Finds FoodCategory by id
     *
     * @param id - id of item to be fetched
     * @return Optional with possible item by id
     * @throws SQLException
     */
    Optional<FoodCategory> findById(long id) throws SQLException;
}
