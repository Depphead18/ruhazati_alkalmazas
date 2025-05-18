package com.example.ruhzatiwebshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ruhzatiwebshop.R;
import com.example.ruhzatiwebshop.model.Ruha;

import java.util.List;

public class KosarAdapter extends RecyclerView.Adapter<KosarAdapter.ViewHolder> {

    private List<Ruha> kosarLista;

    public KosarAdapter(List<Ruha> kosarLista) {
        this.kosarLista = kosarLista;
    }

    @NonNull
    @Override
    public KosarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ruha_kosar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KosarAdapter.ViewHolder holder, int position) {
        Ruha ruha = kosarLista.get(position);
        holder.nevView.setText(ruha.getNev());
        holder.arView.setText(ruha.getAr() + " Ft");
        Glide.with(holder.kepView.getContext()).load(ruha.getKep()).into(holder.kepView);
    }

    @Override
    public int getItemCount() {
        return kosarLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nevView, arView;
        ImageView kepView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nevView = itemView.findViewById(R.id.kosarRuhaNev);
            arView = itemView.findViewById(R.id.kosarRuhaAr);
            kepView = itemView.findViewById(R.id.kosarRuhaKep);
        }
    }
}
