package com.makarewk.angulartutorial.webservice.enums;

public enum StatusEnum {
    SPRAWNY("0"),
    AWARIA("1");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
