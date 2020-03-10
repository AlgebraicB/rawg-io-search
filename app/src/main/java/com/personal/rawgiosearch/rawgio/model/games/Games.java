package com.personal.rawgiosearch.rawgio.model.games;

import com.personal.rawgiosearch.rawgio.model.games.results.Results;

        import java.util.List;

public class Games {

    private String next;
    private String previous;
    private List<Results> results;

    public Games(String next, String previous, List<Results> results) {
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Results> getResults() {
        return results;
    }
}
