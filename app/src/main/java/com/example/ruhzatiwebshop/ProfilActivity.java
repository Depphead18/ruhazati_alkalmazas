package com.example.ruhzatiwebshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilActivity extends BaseActivity {

    private static final String LOG_TAG = ProfilActivity.class.getName();

    private TextView textViewUsername;
    private TextView textViewEmail;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private FirebaseUser currentUser;
    private String uid; // <-- Osztályszintű uid változó

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolbar();

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        currentUser = mAuth.getCurrentUser();
        Button buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        if (currentUser != null) {
            if (currentUser.isAnonymous()) {
                // Anoním felhasználó esetén
                textViewUsername.setText("Név: Anoním");
                textViewEmail.setText("Email: -");
                // A törlés gombot akár elrejtheted, mert nincs mit törölni
                buttonDeleteAccount.setVisibility(View.GONE);
            } else {
                // Normál felhasználó: Firestore adatokat betöltjük
                uid = currentUser.getUid();
                db.collection("felhasznalok").document(uid).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String nev = documentSnapshot.getString("nev");
                                String email = documentSnapshot.getString("email");

                                textViewUsername.setText("Név: " + nev);
                                textViewEmail.setText("Email: " + email);
                            } else {
                                Log.d(LOG_TAG, "A felhasználói dokumentum nem létezik.");
                                Toast.makeText(this, "Nem található a felhasználói adat!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e(LOG_TAG, "Hiba a felhasználó adatainak lekérdezésekor", e);
                            Toast.makeText(this, "Hiba történt az adatok lekérdezésekor!", Toast.LENGTH_SHORT).show();
                        });
            }
        } else {
            Toast.makeText(this, "Nincs bejelentkezett felhasználó", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Nincs bejelentkezett felhasználó.");
        }

        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(ProfilActivity.this, "Sikeres kijelentkezés", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        buttonDeleteAccount.setOnClickListener(v -> {
            if (uid != null && currentUser != null) {
                db.collection("felhasznalok").document(uid)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            currentUser.delete()
                                    .addOnSuccessListener(aVoid1 -> {
                                        Toast.makeText(ProfilActivity.this, "Fiók sikeresen törölve", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfilActivity.this, RegisztracioActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(ProfilActivity.this, "Hiba a fiók törlésekor: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ProfilActivity.this, "Hiba az adatok törlésekor: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            } else {
                Toast.makeText(ProfilActivity.this, "Nem található felhasználó azonosító.", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonEditProfile = findViewById(R.id.buttonEditProfile);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });


    }
}