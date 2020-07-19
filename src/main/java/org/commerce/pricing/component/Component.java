package org.commerce.pricing.component;

import java.util.ArrayList;
import java.util.List;

public class Component {
    private final List<Component> components;
    private final String name;
    private final double price;
    private double offerPrice;
    private int qty = 1;

    public Component(String name, double price) {
        this.name = name;
        this.price = price;
        this.offerPrice = price;
        this.components = new ArrayList<>();
    }

    public void updatePrice(double percentageFactor) {
        double factor = percentageFactor / 100.0;
        this.offerPrice = (factor * this.price) + this.price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public List<Component> getComponents() {
        return components;
    }
}
