package com.example.ruhzatiwebshop;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilActivity extends BaseActivity {

    private static final String LOG_TAG = ProfilActivity.class.getName();

    private TextView textViewUsername;
    private TextView textViewEmail;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
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
        } else {
            Toast.makeText(this, "Nincs bejelentkezett felhasználó", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Nincs bejelentkezett felhasználó.");
        }
    }
}
