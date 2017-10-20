package com.stashinvest.stashchallenge.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {
    @SerializedName("result_count")
    private int resultCount;
    private List<ImageResult> images;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<ImageResult> getImages() {
        return images;
    }

    public void setImages(List<ImageResult> images) {
        this.images = images;
    }
}
