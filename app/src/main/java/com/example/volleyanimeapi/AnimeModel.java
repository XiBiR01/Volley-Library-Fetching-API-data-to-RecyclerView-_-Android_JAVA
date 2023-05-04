package com.example.volleyanimeapi;

public class AnimeModel {
    private String animeArt;
    private String animeTitle;

    public String getAnimeArt() {
        return animeArt;
    }

    public void setAnimeArt(String animeArt) {
        this.animeArt = animeArt;
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    public AnimeModel(String animeArt, String animeTitle) {
        this.animeArt = animeArt;
        this.animeTitle = animeTitle;
    }
}
