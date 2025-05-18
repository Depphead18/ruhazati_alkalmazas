package com.example.ruhzatiwebshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisztracioActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisztracioActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText felhasznalonev;
    EditText userEmail;
    EditText jelszo;
    EditText jelszoujra;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regisztracio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if(secret_key != 99){
            finish();
        }

        felhasznalonev = findViewById(R.id.editTextUserName);
        userEmail = findViewById(R.id.editTextEmail);
        jelszo = findViewById(R.id.editTextPassword);
        jelszoujra = findViewById(R.id.editTextPasswordAgain);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        felhasznalonev.setText(userName);
        jelszo.setText(password);
        jelszoujra.setText(password);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");

    }

    public void backToLogin(View view) {
        Log.d("asd", "asd");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void register(View view) {
        String userName = felhasznalonev.getText().toString();
        String email = userEmail.getText().toString();
        String password = jelszo.getText().toString();
        String passwordAgain = jelszoujra.getText().toString();

        if (!password.equals(passwordAgain)) {
            Log.e(LOG_TAG, "A két jelszó nem egyezik!");
            return;
        }

        Log.i(LOG_TAG, "Regisztrált: " + userName + ", e-mail: " + email);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");

                    String userId = mAuth.getCurrentUser().getUid();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> ujFelhasznalo = new HashMap<>();
                    ujFelhasznalo.put("nev", userName);
                    ujFelhasznalo.put("email", email);

                    db.collection("felhasznalok")
                            .document(userId)
                            .set(ujFelhasznalo)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(LOG_TAG, "Felhasználó sikeresen mentve Firestore-ba.");
                                // Csak ezután navigálunk vissza!
                                backToLogin(null);
                            })
                            .addOnFailureListener(e -> Log.e(LOG_TAG, "Hiba a felhasználó Firestore-ba mentésekor", e));
                } else {
                    Log.d(LOG_TAG, "User wasn't created successfully:", task.getException());
                    Toast.makeText(RegisztracioActivity.this, "User wasn't created successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.putString("email", email);
        editor.apply();
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }
}