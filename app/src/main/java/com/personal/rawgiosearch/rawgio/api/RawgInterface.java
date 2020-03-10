package com.personal.rawgiosearch.rawgio.api;

import com.personal.rawgiosearch.rawgio.model.games.Games;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RawgInterface {

    @GET
    Call<Games> getSearchQuery(@Url String url,
                               @Query("page") Integer pageNumber,
                               @Query("page_size") int pageSize,
                               @Query("search") String search
    );
}
