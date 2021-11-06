package com.ivanko.util.wrapper;

import com.ivanko.model.Food;

import java.util.Date;

public class FoodWrapper extends Food {
    private String category;

    public FoodWrapper(long id, String name, String category, long categoryId, String description, int caloriesPer100g, Date createdAt) {
        super(id, name, categoryId, description, caloriesPer100g, createdAt);
        this.category = category;
    }

    // Empty constructor for mocks tests
    public FoodWrapper() {}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
