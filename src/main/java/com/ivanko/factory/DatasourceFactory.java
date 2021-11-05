package com.ivanko.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class DatasourceFactory {
    private DataSource ds;

    public DatasourceFactory() {
        MysqlDataSource ds = new MysqlDataSource();
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("properties/db.properties")).getPath();

        try (InputStream is = new FileInputStream(rootPath)) {
            Properties prop = new Properties();
            prop.load(is);

            ds.setDatabaseName(prop.getProperty("database"));
            ds.setServerName(prop.getProperty("serverName"));
            ds.setPort(Integer.parseInt(prop.getProperty("port")));
            ds.setUser(prop.getProperty("user"));
            ds.setPassword(prop.getProperty("password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.ds = ds;
    }

    public DatasourceFactory(int port) {
        MysqlDataSource ds = new MysqlDataSource();
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("properties/db.properties")).getPath();

        try (InputStream is = new FileInputStream(rootPath)) {
            Properties prop = new Properties();
            prop.load(is);

            ds.setDatabaseName(prop.getProperty("database"));
            ds.setServerName(prop.getProperty("serverName"));
            ds.setPort(port);
            ds.setUser(prop.getProperty("user"));
            ds.setPassword(prop.getProperty("password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.ds = ds;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}