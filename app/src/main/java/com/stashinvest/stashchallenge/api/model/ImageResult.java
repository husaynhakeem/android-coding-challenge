package com.stashinvest.stashchallenge.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResult {
    private String id;
    private String title;
    @SerializedName("display_sizes")
    private List<DisplaySize> displaySizes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DisplaySize> getDisplaySizes() {
        return displaySizes;
    }

    public void setDisplaySizes(List<DisplaySize> displaySizes) {
        this.displaySizes = displaySizes;
    }

    public String getThumbUri() {
        if (displaySizes == null) {
            return null;
        }

        for (DisplaySize displaySize : displaySizes) {
            if ("thumb".equals(displaySize.getName())) {
                return displaySize.getUri();
            }
        }

        return null;
    }
}
