package ru.bit66.catalogfootballplayers.enums;

public enum Gender {
    MAN("Мужской"),
    WOMAN("Женский");

    private final String currentGender;

    Gender(String currentGender) {
        this.currentGender = currentGender;
    }

    public String getCurrentGender() {
        return currentGender;
    }
}
