package com.example.parcial_v2.Data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial_v2.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, duration;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            duration = (TextView) itemView.findViewById(R.id.duration);
        }
    }

    public ArrayList<Music> Musics;

    public Adapter(ArrayList<Music> Music) {
        this.Musics = Music;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        view.setOnClickListener(this);

        Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.name.setText("Cancion : "+Musics.get(position).title);
        holder.duration.setText("Duracion : "+Musics.get(position).duration);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }


    @Override
    public int getItemCount() {
        return Musics.size();
    }
}