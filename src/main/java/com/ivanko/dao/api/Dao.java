package com.ivanko.dao.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    /**
     * Create new object of type T.
     *
     * @param obj object to be saved into a database
     * @return boolean on success
     * @throws SQLException
     */
    boolean create(T obj) throws SQLException;

    /**
     * Update object of type T by id of type ID.
     *
     * @param id identifier of an object to be updated
     * @param obj object containing fields to be updated
     * @return boolean on success
     * @throws SQLException
     */
    boolean update(ID id, T obj) throws SQLException;

    /**
     * Delete object by id of type ID
     *
     * @param id identifier of an object to be updated.
     * @return boolean on success
     * @throws SQLException
     */
    boolean delete(ID id) throws SQLException;

    /**
     * Fetches object by its id.
     *
     * @param id identifier of an object to be searched by
     * @return Optional with object of type T or empty Optional
     * @throws SQLException
     */
    Optional<T> findById(ID id) throws SQLException;

    /**
     * Fetches all objects from a database.
     *
     * @return List of type T with all records from a database
     * @throws SQLException
     */
    List<T> findAll() throws SQLException;

    /**
     * Fetches all objects from a database. Unlike .findAll() this method is
     * intended to use various joins when implemented to fetch data from multiple tables.
     *
     * @return List of type that extends T
     * @throws SQLException
     */
    List<? extends T> findJoinedAll() throws SQLException;
}