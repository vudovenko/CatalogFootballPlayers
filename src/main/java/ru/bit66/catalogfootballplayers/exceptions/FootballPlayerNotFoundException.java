package ru.bit66.catalogfootballplayers.exceptions;

public class FootballPlayerNotFoundException extends Exception {

    private final String message;

    public FootballPlayerNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
