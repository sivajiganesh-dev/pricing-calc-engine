package org.commerce.pricing.ui.chain.impl;

import org.commerce.pricing.component.Component;
import org.commerce.pricing.ui.chain.DisplayChain;

import java.util.List;

public class ComponentChain implements DisplayChain {
    private final List<Component> refList;
    private final List<Component> workingList;
    private final ComponentChain prev;
    public String name;
    private ComponentChain next;

    public ComponentChain(ComponentChain prev, List<Component> workingList, List<Component> refList, String name) {
        this.prev = prev;
        this.workingList = workingList;
        this.refList = refList;
        this.name = name;
    }

    @Override
    public void goNext() {
        next.display();
    }

    @Override
    public void goPrev() {
        if (prev == null) {
            MainChain.getInstance().display();
            return;
        }

        prev.display();
    }

    @Override
    public void process() {
        int value = this.getNumberInput("Please select an option");
        int index = value - 1;

        if (!validate(value, 1, (prev == null ? refList.size() + 1 : refList.size() + 2))) {
            System.out.println("Invalid input!");
            display();
            return;
        } else {
            if (value >= refList.size() + 1) {
                if (prev == null) MainChain.getInstance().display();
                else prev.display();
                return;
            }
        }

        String selectedName = refList.get(index).getName();

        int qty = getNumberInput("Enter Qty for " + selectedName);
        double price = Double.parseDouble(getStringInput("Enter Price for " + selectedName));

        Component component = new Component(selectedName, price);
        component.setQty(qty);
        workingList.add(component);

        if (refList.get(index).getComponents().size() > 0) {
            String feedback = getStringInput("Part available for " +
                    "" + component.getName() +
                    " \n" +
                    "Do you want to add?(yes/no)");
            if (feedback.equals("yes")) {
                ComponentChain componentChain = new ComponentChain(
                        this,
                        component.getComponents(),
                        refList.get(index).getComponents(),
                        component.getName()
                );
                this.next = componentChain;
                componentChain.display();
            }
        }

        display();

    }

    @Override
    public void display() {
        System.out.println("---------------" + this.name + " Menu---------------");
        System.out.println("Select Components for " + this.name);

        for (int i = 0; i < refList.size(); i++) {
            System.out.println((i + 1) + ". for " + refList.get(i).getName());
        }

        int size = refList.size();
        if (prev != null) {
            System.out.println((++size) + ". for " + this.name);
        }
        System.out.println((++size) + ". for Main menu");
        System.out.println("---------------" + this.name + " Menu---------------");
        process();
    }
}
