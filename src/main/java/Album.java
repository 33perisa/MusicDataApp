package com.musicapp;

public class Album {
    private int id;
    private int artistId;
    private String title;
    private int releaseYear;
    private String recordLabel;

    // Constructor
    public Album(int id, int artistId, String title, int releaseYear, String recordLabel) {
        this.id = id;
        this.artistId = artistId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.recordLabel = recordLabel;
    }

    // Getters and Setters
    // ... (omitted for brevity)
}
