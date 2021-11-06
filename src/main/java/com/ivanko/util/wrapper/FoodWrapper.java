package com.ivanko.util.wrapper;

import com.ivanko.model.Food;

import java.util.Date;

/**
 * Helper class that is used to wrap Food object to have category name.
 * In Food model there's only a reference to category name.
 */
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
