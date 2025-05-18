package com.example.ruhzatiwebshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends BaseActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private Button buttonSaveChanges;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_frissitese);

        setupToolbar();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        // Adatok betöltése
        db.collection("felhasznalok").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        editTextName.setText(documentSnapshot.getString("nev"));
                        editTextEmail.setText(documentSnapshot.getString("email"));
                    }
                });

        // Frissítés gomb
        buttonSaveChanges.setOnClickListener(v -> {
            String ujNev = editTextName.getText().toString().trim();
            String ujEmail = editTextEmail.getText().toString().trim();

            Map<String, Object> frissitettAdatok = new HashMap<>();
            frissitettAdatok.put("nev", ujNev);
            frissitettAdatok.put("email", ujEmail);

            db.collection("felhasznalok").document(uid)
                    .update(frissitettAdatok)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Adatok frissítve", Toast.LENGTH_SHORT).show();
                        finish(); // visszalépés
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba frissítéskor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}