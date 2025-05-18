package com.example.ruhzatiwebshop;

import com.example.ruhzatiwebshop.model.Ruha;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class KosarManager {
    private static KosarManager instance;
    private final List<Ruha> kosarLista;

    private KosarManager() {
        kosarLista = new ArrayList<>();
    }

    public void addToKosar(Ruha ruha) {
        kosarLista.add(ruha);
        Log.d("KosarManager", "Hozzáadva a kosárhoz: " + ruha.getNev() + " | Új méret: " + kosarLista.size());

    }

    public void removeFromKosar(Ruha ruha) {
        kosarLista.remove(ruha);
    }

    public static KosarManager getInstance() {
        if (instance == null) {
            instance = new KosarManager();
        }
        return instance;
    }

    public List<Ruha> getKosarLista() {
        return new ArrayList<>(kosarLista);
    }
}
