package com.ivanko.exception;

/*
    Custom exception which signalizes about broken business logic rule;
    is currently thrown within FoodService if
    negative caloriesPer100g is provided.
 */
public class BusinessRuleException extends Exception {
    public BusinessRuleException(String msg) {
        super(msg);
    }
}
