package org.commerce.pricing.ui.options;

/**
 * Main menu enum for displaying initial options
 */
public enum MainMenu {
    CREATE(1, "Add components to Cycle"),
    UPDATE_PRICE(2, "Update price by %"),
    CALCULATE(3, "Calculate total price"),
    PRINT_CATALOG(4, "Display all components added"),
    RESET(5, "Reset catalog"),
    EXIT(6, "Exit!");

    private final int command;
    private final String message;

    MainMenu(int command, String message) {
        this.command = command;
        this.message = message;
    }

    public int getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }

}
