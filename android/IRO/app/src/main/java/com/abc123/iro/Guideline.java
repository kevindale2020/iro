package com.abc123.iro;

public class Guideline {
    public int id;
    public String guideline;

    public Guideline(int id, String guideline) {
        this.id = id;
        this.guideline = guideline;
    }

    public int getId() {
        return id;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }
}
