package com.personal.rawgiosearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.personal.rawgiosearch.rawgio.model.games.Games;
import com.personal.rawgiosearch.rawgio.api.RawgClient;
import com.personal.rawgiosearch.rawgio.api.RawgInterface;
import com.personal.rawgiosearch.rawgio.model.games.results.Results;
import com.personal.rawgiosearch.rawgio.model.games.results.genres.Genres;
import com.personal.rawgiosearch.rawgio.model.games.results.platforms.PlatformParent;
import com.personal.rawgiosearch.recyclerview.MyAdapter;

import java.util.ArrayList;
import java.util.StringJoiner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Integer pageNumber;

    private MyAdapter myAdapter;

    private EditText editTextSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> released = new ArrayList<>();
    private ArrayList<String> background_image = new ArrayList<>();
    private ArrayList<String> platforms = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();

    private RawgInterface rawgInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        editTextSearch = findViewById(R.id.edit_text_search_query);
        progressBar = findViewById(R.id.progress_bar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLastItemDisplayed(recyclerView)) {
                    recyclerViewPagination(editTextSearch.getText().toString());
                }
            }
        };
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        myAdapter = new MyAdapter(
                this, background_image, name, released, platforms, genres
        );
        recyclerView.setAdapter(myAdapter);

        rawgInterface = RawgClient.getRawgInterface();
    }

    public void btnGo(View view) {
        name.clear();
        released.clear();
        background_image.clear();
        platforms.clear();
        genres.clear();
        pageNumber = 0;

        String search = editTextSearch.getText().toString();

        if (search.isEmpty()) {
            Toast.makeText(this,
                    "Please input a game to search.",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        searchQuery(search);
    }

    private void searchQuery(String search) {
        progressBar.setVisibility(View.VISIBLE);
        pageNumber++;
        Call<Games> call = rawgInterface.getSearchQuery(
                "games", pageNumber, 10, search
        );
        call.enqueue(new Callback<Games>() {
            @Override
            public void onResponse(Call<Games> call, Response<Games> response) {
                if (!response.isSuccessful()) {
                    String errCode = "Error..Code: " + response.code();
                    Toast.makeText(
                            MainActivity.this,
                            errCode,
                            Toast.LENGTH_LONG)
                            .show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                Games games = response.body();

                initAddGames(games);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Games> call, Throwable t) {
                String errMsg = "Error..Message: " + t.getMessage();
                Toast.makeText(MainActivity.this,
                        errMsg,
                        Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initAddGames(Games games) {

        for (Results r : games.getResults()) {
            background_image.add(r.getBackground_image());
            name.add(r.getName());
            released.add(r.getReleased());
            StringJoiner platform = new StringJoiner(", ");
            for (PlatformParent p : r.getPlatforms()) {
                platform.add(p.getPlatform().getName());
            }
            platforms.add(platform.toString());
            StringJoiner genre = new StringJoiner(", ");
            for (Genres g : r.getGenres()) {
                genre.add(g.getName());
            }
            genres.add(genre.toString());
        }
    }

    private void recyclerViewPagination(String search) {
        Call<Games> call = rawgInterface.getSearchQuery(
                "games", pageNumber, 10, search
        );
        call.enqueue(new Callback<Games>() {
            @Override
            public void onResponse(Call<Games> call, Response<Games> response) {
                if (response.body().getNext() != null) {
                    searchQuery(search);

                } else {
                    Toast.makeText(MainActivity.this,
                            "No more results.",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Games> call, Throwable t) {
                String errMsg = "Error..Message: " + t.getMessage();
                Toast.makeText(MainActivity.this,
                        errMsg,
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private boolean isLastItemDisplayed(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION &&
                    lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }
}