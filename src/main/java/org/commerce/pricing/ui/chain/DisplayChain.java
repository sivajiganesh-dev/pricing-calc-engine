package org.commerce.pricing.ui.chain;

import java.util.Scanner;

public interface DisplayChain {
    Scanner scanner = new Scanner(System.in);

    void goNext();

    void goPrev();

    void process();

    void display();

    default boolean validate(int input, int start, int end) {
        return (input >= start && input <= end);
    }

    default String getStringInput(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    default Integer getNumberInput(String message) {
        System.out.print(message + ": ");
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }
}
