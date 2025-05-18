package com.example.ruhzatiwebshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ruhzatiwebshop.KosarManager;
import com.example.ruhzatiwebshop.R;
import com.example.ruhzatiwebshop.RuhaReszletekActivity;
import com.example.ruhzatiwebshop.model.Ruha;

import java.util.ArrayList;
import java.util.List;

public class RuhaAdapter extends RecyclerView.Adapter<RuhaAdapter.RuhaViewHolder> {

    private Context context;
    private List<Ruha> ruhaLista;
    private boolean kosarMod;

    public RuhaAdapter(Context context, List<Ruha> ruhak, boolean kosarMod) {
        this.context = context;
        this.ruhaLista = ruhak != null ? ruhak : new ArrayList<>();
        this.kosarMod = kosarMod;
    }

    public RuhaAdapter(Context context, List<Ruha> ruhak) {
        this(context, ruhak, false);
    }

    @NonNull
    @Override
    public RuhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ruha, parent, false);
        return new RuhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RuhaViewHolder holder, int position) {
        Ruha ruha = ruhaLista.get(position);

        holder.ruhaNev.setText(ruha.getNev());
        Glide.with(context).load(ruha.getKep()).into(holder.ruhaKep);

        // ÁR beállítása (EZ HIÁNYZOTT)
        holder.ruhaAr.setText(ruha.getAr() + " Ft");

        // kattintás az itemen (kártyán)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RuhaReszletekActivity.class);
            intent.putExtra("ruhaId", ruha.getId());  // csak az ID-t adjuk át
            context.startActivity(intent);
        });

        if (kosarMod) {
            holder.kosarbaButton.setText("Eltávolítás");
            holder.kosarbaButton.setOnClickListener(v -> {
                KosarManager.getInstance().removeFromKosar(ruha);
                ruhaLista.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ruhaLista.size());
            });
        } else {
            holder.kosarbaButton.setText("Kosárba");
            holder.kosarbaButton.setOnClickListener(v -> {
                KosarManager.getInstance().addToKosar(ruha);
                Toast.makeText(context, "Hozzáadva a kosárhoz", Toast.LENGTH_SHORT).show();
            });
        }
    }


    @Override
    public int getItemCount() {
        return ruhaLista != null ? ruhaLista.size() : 0;
    }

    public static class RuhaViewHolder extends RecyclerView.ViewHolder {
        ImageView ruhaKep;
        TextView ruhaNev, ruhaAr;
        Button kosarbaButton;

        public RuhaViewHolder(@NonNull View itemView) {
            super(itemView);
            ruhaKep = itemView.findViewById(R.id.ruhaKep);
            ruhaNev = itemView.findViewById(R.id.ruhaNev);
            ruhaAr = itemView.findViewById(R.id.ruhaAr);
            kosarbaButton = itemView.findViewById(R.id.kosarbaButton);
        }
    }
}
