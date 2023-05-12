package ru.bit66.catalogfootballplayers.enums;

public enum Country {
    RUSSIA("Russia"),
    USA("USA"),
    ITALY("Italy");

    private final String currentCountry;

    Country(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }
}
