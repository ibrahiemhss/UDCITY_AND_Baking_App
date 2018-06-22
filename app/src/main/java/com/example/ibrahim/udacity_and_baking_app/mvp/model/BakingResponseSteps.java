package com.example.ibrahim.udacity_and_baking_app.mvp.model;


/**
 *
 * Created by ibrahim on 24/05/18.
 */
@SuppressWarnings("unused")
public class BakingResponseSteps {
    private String videoURL;
    private String description;
    private int id;
    private String shortDescription;
    private String thumbnailURL;

    public String getVideoURL() {
        return this.videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getThumbnailURL() {
        return this.thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
