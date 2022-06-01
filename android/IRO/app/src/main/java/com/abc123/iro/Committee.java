package com.abc123.iro;

public class Committee {
    public int id;
    public String committee;

    public Committee(int id, String committee) {
        this.id = id;
        this.committee = committee;
    }

    public int getId() {
        return id;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }
}
