package org.commerce.pricing.ui.chain.impl;

import org.commerce.pricing.data.DataSource;
import org.commerce.pricing.engine.PriceEngine;
import org.commerce.pricing.product.Product;
import org.commerce.pricing.ui.chain.DisplayChain;
import org.commerce.pricing.ui.options.MainMenu;

public class MainChain implements DisplayChain {

    private static final MainChain mainChain = new MainChain();
    private Product product;
    private PriceEngine priceEngine;

    private MainChain() {
    }

    public static MainChain getInstance() {
        return mainChain;
    }

    public MainChain configure(Product product, PriceEngine priceEngine) {
        this.product = product;
        this.priceEngine = priceEngine;
        return mainChain;
    }


    @Override
    public void goNext() {
        ComponentChain componentChain = new ComponentChain(
                null,
                product.getComponents(),
                DataSource.getInstance().getComponents(),
                DataSource.getInstance().getName()
        );
        componentChain.display();
    }

    @Override
    public void goPrev() {
    }

    @Override
    public void process() {
        int value = this.getNumberInput("Please select an option");

        if (!validate(value, 1, MainMenu.values().length)) {
            System.out.println("Invalid input!");
            display();
            return;
        }

        switch (value) {
            case 1: {
                goNext();
                break;
            }
            case 2: {
                System.out.println(product.getComponents().size() + " components configured for " + product.getName());

                if (product.getComponents().size() > 0) {
                    double percent = Double.parseDouble(getStringInput("Please provide change % (-/+)"));
                    product.updatePrice(percent);
                    System.out.println("Prices updated");
                }
                display();
                break;
            }
            case 3: {
                try {
                    int totalQty = getNumberInput("Provide total no of " + product.getName());
                    product.setQty(totalQty);
                    product.setTotalPrice(0);
                    priceEngine.calculatePrice();
                    System.out.println("Total cost for " + product.getQty() + " " + product.getName() + "(s) is " + product.getTotalPrice());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                display();
                break;
            }
            case 4: {
                System.out.println(product.getComponents().size() + " components configured for " + product.getName());
                product.printValues(product.getComponents(), ">");
                display();
                return;
            }
            case 5: {
                product = new Product();
                product.setName(DataSource.getInstance().getName());
                display();
                return;
            }
            case 6: {
                return;
            }
            default: {
                System.out.println("Invalid Input!");
                process();
            }
        }
    }

    @Override
    public void display() {
        MainMenu[] allCommands = MainMenu.values();
        System.out.println("---------------Main Menu---------------");
        for (MainMenu command : allCommands) {
            System.out.println("Use " + command.getCommand() + ". " + command.getMessage());
        }
        System.out.println("---------------Main Menu---------------");
        process();
    }
}
