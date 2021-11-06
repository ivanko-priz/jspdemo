package com.ivanko.model;

import java.util.Date;

/**
 * FoodCategory stores information about a category to which a particular food may belong
 */
public class FoodCategory {
    private long id;
    private String name;
    private Date createdAt;

    public FoodCategory() {}

    public FoodCategory(String name) {
        this.name = name;
    }

    public FoodCategory(long id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}