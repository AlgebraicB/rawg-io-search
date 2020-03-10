package com.personal.rawgiosearch.rawgio.model.games.results.platforms;

public class PlatformParent {

    private PlatformChild platform;

    public PlatformParent(PlatformChild platform) {
        this.platform = platform;
    }

    public PlatformChild getPlatform() {
        return platform;
    }
}
