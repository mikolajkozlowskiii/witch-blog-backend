package com.example.witchblog.components.divination;

public enum LastSentence {
    VERY_LOW(0),
    LOW(25),
    MEDIUM(50),
    HIGH(75),
    VERY_HIGH(100);
    private final int percentage;

    LastSentence(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getPercentageKey() {
        return String.valueOf(percentage);
    }
}
