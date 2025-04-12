package com.example.ruhzatiwebshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ruhzatiwebshop.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class FooldalActivity extends BaseActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fooldal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        setupToolbar();

        Button shoppingButton = findViewById(R.id.vasarlas_gomb);
        shoppingButton.setOnClickListener(v -> startShopping(v));

        /*shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShopping(v);
            }
        });*/

    }

    public void cancel(View view) {
        finish();
    }

    public void startShopping(View view) {
        Intent intent = new Intent(this, RuhalistaActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_ruhalista) {
            startActivity(new Intent(this, RuhalistaActivity.class));
            return true;
        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(this, ProfilActivity.class));
            Toast.makeText(this, "Fiók funkció készül...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_fooldal) {
            startActivity(new Intent(this, FooldalActivity.class));
            Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
            return true;
        }  else if (id == R.id.menu_reg) {
            startActivity(new Intent(this, RegisztracioActivity.class));
            Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
            return true;
        }  else if (id == R.id.menu_bejelentkezes) {
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/





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