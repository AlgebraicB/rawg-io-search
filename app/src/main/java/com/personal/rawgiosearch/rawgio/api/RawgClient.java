package com.personal.rawgiosearch.rawgio.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RawgClient {

    private static final String rawgioBaseUrl = "https://api.rawg.io/api/";
    private static Retrofit rawgRetrofit = null;
    private static RawgInterface rawgInterface = null;

    private static Retrofit getRawgClient() {
        if (rawgRetrofit == null) {
            rawgRetrofit = new Retrofit.Builder()
                    .baseUrl(rawgioBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rawgRetrofit;
    }

    public static RawgInterface getRawgInterface() {
        if (rawgInterface == null) {
            rawgInterface = getRawgClient().create(RawgInterface.class);
        }
        return rawgInterface;
    }
}
