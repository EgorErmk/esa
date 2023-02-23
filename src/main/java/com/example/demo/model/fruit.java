package com.example.demo.model;
import java.io.Serializable;

public class fruit implements Serializable {
    private String name;
    private int calories;
    private int sugar;

    public fruit() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public int getCalories() {
        return this.calories;
    }

    public void setCalories(int var1) {
        this.calories = var1;
    }

    public int getSugar() {
        return this.sugar;
    }

    public void setSugar(int var1) {
        this.sugar = var1;
    }
}
