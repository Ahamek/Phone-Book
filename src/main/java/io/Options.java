package io;

import java.util.NoSuchElementException;

public enum Options {
    ADD_CONTACT(0, "Add new contact"),
    SEARCH_BY_NAME(1, "Search by name"),
    SEARCH_BY_TEL(2, "Search by phone number"),
    DELETE(3, "Delete contact"),
    CLOSE(4, "End the program");

    private final int shortcut;
    private final String description;

    Options(int shortcut, String description) {
        this.shortcut = shortcut;
        this.description = description;
    }

    public static Options convertToOption(int option){
        if (option >= values().length || option < 0){
            throw new NoSuchElementException("There is no such option to choose");
        }
        return values()[option];
    }

    public int getShortcut() {
        return shortcut;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return shortcut + " - " + description;
    }
}
