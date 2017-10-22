package com.stashinvest.stashchallenge.listing;

import com.stashinvest.stashchallenge.api.model.DisplaySize;
import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.Metadata;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by husaynhakeem on 10/22/17.
 */

public class TestsHelper {

    public static final String ANY_KEYWORD = "any_keyword";
    public static final String ANY_GETTY_IMAGE_ID = "any_getty_image_id";
    public static final String ANY_GETTY_IMAGE_URL = "any_getty_image_url";
    public static final String ANY_IMAGE_TITLE = "any_image_title";
    public static final String ANY_IMAGE_ARTIST = "any_image_artist";

    public static final String ANY_SIMILAR_IMAGE_ID = "any_similar_image_id";
    public static final String ANY_SIMILAR_IMAGE_TITLE = "any_similar_image_title";
    public static final int ANY_SIMILAR_IMAGE_INDEX = 0;
    public static final String ANY_SIMILAR_IMAGE_THUMB_URI = "any_similar_image_thumb_uri";

    public static Single<MetadataResponse> anyEmptyMetadataResponseSingle() {
        MetadataResponse metadataResponse = new MetadataResponse();
        metadataResponse.setMetadata(new ArrayList<>());
        return Single.just(metadataResponse);
    }

    public static Single<MetadataResponse> anyMetadataResponseSingle() {
        MetadataResponse metadataResponse = new MetadataResponse();
        metadataResponse.setMetadata(Collections.singletonList(anyMetadata()));
        return Single.just(metadataResponse);
    }

    public static Metadata anyMetadata() {
        Metadata metadata = new Metadata();
        metadata.setId(ANY_GETTY_IMAGE_ID);
        metadata.setTitle(ANY_IMAGE_TITLE);
        metadata.setArtist(ANY_IMAGE_ARTIST);
        return metadata;
    }

    public static Single<ImageResponse> anyEmptyImageResponseSingle() {
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setResultCount(0);
        imageResponse.setImages(null);
        return Single.just(imageResponse);
    }

    public static Single<ImageResponse> anyImageResponseSingle() {
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setResultCount(1);
        imageResponse.setImages(anyImageResult());
        return Single.just(imageResponse);
    }

    public static List<ImageResult> anyImageResult() {
        ImageResult imageResult = new ImageResult(ANY_SIMILAR_IMAGE_ID, ANY_SIMILAR_IMAGE_TITLE);
        imageResult.setDisplaySizes(Collections.singletonList(anyDisplaySize()));
        return Collections.singletonList(imageResult);
    }

    public static DisplaySize anyDisplaySize() {
        DisplaySize displaySize = new DisplaySize();
        displaySize.setName("thumb");
        displaySize.setUri(ANY_SIMILAR_IMAGE_THUMB_URI);
        return displaySize;
    }
}
