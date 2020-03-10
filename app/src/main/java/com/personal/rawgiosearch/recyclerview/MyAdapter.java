package com.personal.rawgiosearch.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.personal.rawgiosearch.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context aContext;
    private ArrayList<String> aImageUrl;
    private ArrayList<String> aGameName;
    private ArrayList<String> aReleased;
    private ArrayList<String> aPlatforms;
    private ArrayList<String> aGenres;

    public MyAdapter(Context aContext, ArrayList<String> aImageUrl, ArrayList<String> aGameName, ArrayList<String> aReleased, ArrayList<String> aPlatforms, ArrayList<String> aGenres) {
        this.aContext = aContext;
        this.aImageUrl = aImageUrl;
        this.aGameName = aGameName;
        this.aReleased = aReleased;
        this.aPlatforms = aPlatforms;
        this.aGenres = aGenres;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(aContext);
        View view = inflater.inflate(R.layout.gameslist_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(aContext)
                .asBitmap()
                .load(aImageUrl.get(position))
                .apply(new RequestOptions().override(350, 350))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.aImageUrl);
        holder.aGameName.setText(aGameName.get(position));
        holder.aReleased.setText(aReleased.get(position));
        holder.aPlatforms.setText(aPlatforms.get(position));
        holder.aGenres.setText(aGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return aGameName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView aImageUrl;
        TextView aGameName, aReleased, aPlatforms, aGenres;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aImageUrl = itemView.findViewById(R.id.game_image);
            aGameName = itemView.findViewById(R.id.game_name);
            aReleased = itemView.findViewById(R.id.game_release_date);
            aPlatforms = itemView.findViewById(R.id.game_platforms);
            aGenres = itemView.findViewById(R.id.game_genres);
        }
    }

}
