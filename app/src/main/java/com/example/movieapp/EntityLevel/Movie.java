package com.example.movieapp.EntityLevel;

public class Movie {
    private int movie_id;
    private String name;
    private String description;
    private String image;
    private String link;
    private int releaseYear;
    private float rating;

    public Movie(int movie_id, String name, String description, String image, String link, int releaseYear, float rating) {
        this.movie_id = movie_id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.link = link;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
