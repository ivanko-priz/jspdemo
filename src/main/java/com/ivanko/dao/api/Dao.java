package com.ivanko.dao.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    boolean create(T obj) throws SQLException;
    boolean update(ID id, T obj) throws SQLException;
    boolean delete(ID id) throws SQLException;
    Optional<T> findById(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    List<? extends T> findJoinedAll() throws SQLException;
}