package com.makarewk.angulartutorial.webservice.enums;


public enum CategoryEnum {
    FRIDGE("fridge"),
    PHONE("phone"),
    HEADPHONES("headphones");

    private String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
