package ru.bit66.catalogfootballplayers.exceptions;

public class FootballTeamNotFoundException extends Throwable {
    private final String message;

    public FootballTeamNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
