package com.musicapp;

public class Artist {
    private int id;
    private String name;
    private String type;
    private int formationYear;
    private Integer disbandmentYear;
    private String officialWebsite;

    // Constructor
    public Artist(int id, String name, String type, int formationYear, Integer disbandmentYear, String officialWebsite) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.formationYear = formationYear;
        this.disbandmentYear = disbandmentYear;
        this.officialWebsite = officialWebsite;
    }

    // Getters and Setters
    // ... (omitted for brevity)
}
