package com.example.ruhzatiwebshop;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ruhzatiwebshop.adapter.RuhaAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.example.ruhzatiwebshop.model.Ruha;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Collections;
import java.util.Comparator;


public class RuhalistaActivity extends BaseActivity {
    private FirebaseUser user;
    private FirebaseFirestore db;

    private List<Ruha> ruhaLista = new ArrayList<>();
    private RecyclerView recyclerView;
    private RuhaAdapter adapter;

    private List<Ruha> kosarLista = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruhalista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        setupToolbar();

        if (user == null) {
            finish();
            return;
        }

        betoltRuhakatFirestorebol();

        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // A-Z
                        Collections.sort(ruhaLista, Comparator.comparing(Ruha::getNev));
                        break;
                    case 1: // Z-A
                        Collections.sort(ruhaLista, (r1, r2) -> r2.getNev().compareTo(r1.getNev()));
                        break;
                    case 2: // Legolcsóbb
                        Collections.sort(ruhaLista, Comparator.comparingInt(Ruha::getAr));
                        break;
                    case 3: // Legdrágább
                        Collections.sort(ruhaLista, (r1, r2) -> Integer.compare(r2.getAr(), r1.getAr()));
                        break;
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    private void betoltRuhakatFirestorebol() {
        db.collection("ruhak")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ruhaLista.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Ruha ruha = doc.toObject(Ruha.class);
                        ruha.setId(doc.getId());  // dokumentum ID beállítása
                        ruhaLista.add(ruha);
                    }
                    adapter = new RuhaAdapter(this, ruhaLista);
                    recyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Hiba történt az adatok betöltésekor", Toast.LENGTH_SHORT).show());
    }
}
