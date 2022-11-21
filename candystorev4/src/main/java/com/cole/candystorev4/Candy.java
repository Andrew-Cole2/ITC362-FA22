package com.cole.candystorev4;

public class Candy {
    private int id;
    private String name;
    private Double price;

    public Candy() {
        name = "";
        price = 0.0;
    }

    public Candy(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    public String toString() {
        return "(" + id + ", " + name + ", " + price + ")";
    }
}
