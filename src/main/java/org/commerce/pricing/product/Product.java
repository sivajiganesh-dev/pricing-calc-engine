package org.commerce.pricing.product;

import org.commerce.pricing.component.Component;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private List<Component> components;
    private double totalPrice;
    private int qty;
    private String name;

    public Product() {
        this.components = new ArrayList<>();
        this.qty = 1;
    }

    public void updatePrice(double percentage) {
        updatePrice(this.components, percentage);
    }

    public void updatePrice(List<Component> components, double percentage) {
        for (Component component : components) {
            component.updatePrice(percentage);
            if (component.getComponents().size() > 0) {
                updatePrice(component.getComponents(), percentage);
            }
        }
    }

    public void printValues(List<Component> components, String visualPadding) {
        for (Component component : components) {
            System.out.println(visualPadding + " Name: " + component.getName() + " Qty: " + component.getQty() + " Price: " + component.getPrice());
            if (component.getComponents().size() > 0) {
                printValues(component.getComponents(), visualPadding.concat(visualPadding));
            }
        }
    }

    public synchronized void addToTotal(double cost) {
        this.totalPrice += cost;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
