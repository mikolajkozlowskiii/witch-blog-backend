package com.example.witchblog.components.divination;

public enum MidSentence {
    POSITIVE("positive"),
    NEGATIVE("negative"),
    CONNECT("connect");

    private final String value;

    MidSentence(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
