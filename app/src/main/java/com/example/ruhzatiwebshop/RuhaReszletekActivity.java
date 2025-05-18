package com.example.ruhzatiwebshop;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ruhzatiwebshop.model.Ruha;
import com.google.firebase.firestore.FirebaseFirestore;

public class RuhaReszletekActivity extends BaseActivity {
    private FirebaseFirestore db;
    private TextView nevView, leirasView, arView, szinView, markaView, kategoriaView;
    private ImageView kepView;
    private Button kosarbaButton;  // Gomb deklarálása
    private Ruha jelenlegiRuha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruha_reszletek);

        setupToolbar();

        nevView = findViewById(R.id.ruhaNev);
        leirasView = findViewById(R.id.ruhaLeiras);
        arView = findViewById(R.id.ruhaAr);
        szinView = findViewById(R.id.ruhaSzin);
        markaView = findViewById(R.id.ruhaMarka);
        kategoriaView = findViewById(R.id.ruhaKategoria);
        kepView = findViewById(R.id.ruhaKep);
        kosarbaButton = findViewById(R.id.kosarbaButton);

        db = FirebaseFirestore.getInstance();

        String ruhaId = getIntent().getStringExtra("ruhaId");
        if (ruhaId != null) {
            loadRuhaDetails(ruhaId);
        }

        kosarbaButton.setOnClickListener(v -> {
            if (jelenlegiRuha != null) {
                KosarManager.getInstance().addToKosar(jelenlegiRuha);  // csak hozzáadás
                Toast.makeText(this, jelenlegiRuha.getNev() + " hozzáadva a kosárhoz.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadRuhaDetails(String ruhaId) {
        db.collection("ruhak").document(ruhaId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Ruha ruha = documentSnapshot.toObject(Ruha.class);
                        if (ruha != null) {
                            jelenlegiRuha = ruha; // beállítjuk az aktuális ruhát
                            nevView.setText(ruha.getNev());
                            leirasView.setText(ruha.getLeiras());
                            arView.setText(ruha.getAr() + " Ft");
                            szinView.setText(ruha.getSzin());
                            markaView.setText(ruha.getMarka());
                            kategoriaView.setText(ruha.getKategoria());

                            Glide.with(this)
                                    .load(ruha.getKep())
                                    .into(kepView);

                            Log.d("RuhaReszletekActivity", "Kép URL: " + ruha.getKep());
                        }
                    } else {
                        Toast.makeText(this, "Nem található a ruha adat", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba történt az adatok betöltésekor", Toast.LENGTH_SHORT).show();
                });
    }

    private void kosarbaHozzaadas(Ruha ruha) {
        db.collection("kosar")
                .add(ruha)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, ruha.getNev() + " hozzáadva a kosárhoz.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba történt a kosárba helyezéskor", Toast.LENGTH_SHORT).show();
                });
    }

}
