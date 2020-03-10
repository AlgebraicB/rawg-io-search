package com.personal.rawgiosearch.rawgio.model.games.results;

import com.personal.rawgiosearch.rawgio.model.games.results.genres.Genres;
import com.personal.rawgiosearch.rawgio.model.games.results.platforms.PlatformParent;

import java.util.List;

public class Results {

    private String slug, name, released, background_image;
    private List<PlatformParent> platforms;
    private List<Genres> genres;

    public Results(String slug, String name, String released, String background_image, List<PlatformParent> platforms, List<Genres> genres) {
        this.slug = slug;
        this.name = name;
        this.released = released;
        this.background_image = background_image;
        this.platforms = platforms;
        this.genres = genres;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getReleased() {
        return released;
    }

    public String getBackground_image() {
        return background_image;
    }

    public List<PlatformParent> getPlatforms() {
        return platforms;
    }

    public List<Genres> getGenres() {
        return genres;
    }
}