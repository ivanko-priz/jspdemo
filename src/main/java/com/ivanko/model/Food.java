package com.ivanko.model;

import java.util.Date;

public class Food {
    private long id;
    private String name;
    private long foodCategoryId;
    private String description;
    private int caloriesPer100g;
    private Date createdAt;

    public Food() {}

    public Food(String name) {
        this.name = name;
    }

    public Food(long id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Food(long id, String name, long foodCategoryId, Date createdAt) {
        this.id = id;
        this.name = name;
        this.foodCategoryId = foodCategoryId;
        this.createdAt = createdAt;
    }

    public Food(long id, String name, long foodCategoryId, String description, Date createdAt) {
        this.id = id;
        this.name = name;
        this.foodCategoryId = foodCategoryId;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Food(long id, String name, long foodCategoryId, String description, int caloriesPer100g, Date createdAt) {
        this.id = id;
        this.name = name;
        this.foodCategoryId = foodCategoryId;
        this.description = description;
        this.caloriesPer100g = caloriesPer100g;
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

    public long getFoodCategoryId() { return foodCategoryId; }

    public void setFoodCategoryId(long foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(int caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", foodCategoryId=" + foodCategoryId +
                ", description='" + description + '\'' +
                ", caloriesPer100g=" + caloriesPer100g +
                ", createdAt=" + createdAt +
                '}';
    }
}