package com.example.witchblog.components.divination;

public enum FirstSentence {
    NAME("{first_name}"),
    DATE("{date}");

    private final String value;

    FirstSentence(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
