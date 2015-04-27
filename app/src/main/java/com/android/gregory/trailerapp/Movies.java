package com.android.gregory.trailerapp;

/**
 * Created by Gregory on 4/26/2015.
 */
public class Movies {



    private int id;
    private String name;
    private String description;
    private String thumbLocation;
    private String trailerLocation;

    private String rating;

    public Movies(){}

    public Movies(String movie, String description, String trailerLocation,
                  String thumbLocation, String rating ) {
        super();
        this.name = movie;
        this.description = description;
        this.trailerLocation = trailerLocation;
        this.thumbLocation = thumbLocation;
        this.rating = rating;
    }

    //getters & setters

    @Override
    public String toString() {
        return "Movie [id=" + id + ", movie=" + name + ", description=" + description
                +", trailerLocation=" + trailerLocation +", thumbLocation=" + thumbLocation +
                "]";
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String movie) {
        this.name = movie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbLocation() {
        return thumbLocation;
    }

    public void setThumbLocation(String thumbLocation) {
        this.thumbLocation = thumbLocation;
    }

    public String getTrailerLocation() {
        return trailerLocation;
    }

    public void setTrailerLocation(String trailerLocation) {
        this.trailerLocation = trailerLocation;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
