package com.stashinvest.stashchallenge.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetadataResponse {
    @SerializedName("images")
    private List<Metadata> metadata;

    public List<Metadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Metadata> metadata) {
        this.metadata = metadata;
    }
}
