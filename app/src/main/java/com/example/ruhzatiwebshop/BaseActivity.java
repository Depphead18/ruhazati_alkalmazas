package com.example.ruhzatiwebshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
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
    }


}
