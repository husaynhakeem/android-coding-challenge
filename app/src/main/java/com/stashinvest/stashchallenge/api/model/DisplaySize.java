package com.stashinvest.stashchallenge.api.model;

import com.google.gson.annotations.SerializedName;

public class DisplaySize {
    @SerializedName("is_watermarked")
    private boolean watermarked;
    private String name;
    private String uri;

    public boolean isWatermarked() {
        return watermarked;
    }

    public void setWatermarked(boolean watermarked) {
        this.watermarked = watermarked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
