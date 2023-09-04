package com.example.webdemo.constants;

public enum Sex {
    MALE(true), FEMALE(false);

    private boolean isMale;

    Sex(boolean isMale) {
        this.isMale = isMale;
    }

    public boolean isMale() {
        return this.isMale;
    }
}
