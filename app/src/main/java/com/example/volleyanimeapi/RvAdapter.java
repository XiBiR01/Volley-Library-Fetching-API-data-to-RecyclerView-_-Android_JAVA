package com.example.volleyanimeapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.viewHolder>{
    Context ctx;
    ArrayList<AnimeModel> list;
    RequestOptions option;

    public RvAdapter(Context ctx, ArrayList<AnimeModel> list) {
        this.ctx = ctx;
        this.list = list;
        option=new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(ctx).inflate(R.layout.rv_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AnimeModel obj=list.get(position);
        holder.animeTitle.setText(obj.getAnimeTitle());
        Glide.with(ctx).load(obj.getAnimeArt()).apply(option).into(holder.animeArt);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView animeTitle;
        ImageView animeArt;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            animeTitle=itemView.findViewById(R.id.animeTitle);
            animeArt=itemView.findViewById(R.id.animeArt);
        }
    }
}
