package org.commerce.pricing.engine.worker;

import org.commerce.pricing.component.Component;
import org.commerce.pricing.product.Product;

import java.util.List;

/**
 * Worker thread which calculates and sum up the total cost for given no of products
 */
public class CostCalculatorWorker implements Runnable {
    Product product;
    private double cost = 0;

    public CostCalculatorWorker(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        cost = calcTotalPrice(product.getComponents(), cost);
        product.addToTotal(cost);
    }

    /**
     * Calculate total price recursively by accumulating all individual prices
     *
     * @param components list of components
     * @param cost       pass initial cost, most cases it will be 0
     */
    public double calcTotalPrice(List<Component> components, double cost) {
        for (Component component : components) {
            cost += component.getOfferPrice() * component.getQty();
            if (component.getComponents().size() > 0) {
                calcTotalPrice(component.getComponents(), cost);
            }
        }
        return cost;
    }
}
