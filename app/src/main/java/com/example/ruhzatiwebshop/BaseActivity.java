package com.example.ruhzatiwebshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            menu.findItem(R.id.menu_bejelentkezes).setVisible(false);
            menu.findItem(R.id.menu_reg).setVisible(false);
        } else {
            menu.findItem(R.id.menu_profile).setVisible(false);
            menu.findItem(R.id.menu_kosar).setVisible(false);
            menu.findItem(R.id.menu_logout).setVisible(false);
        }
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
            return true;
        } else if (id == R.id.menu_fooldal) {
            startActivity(new Intent(this, FooldalActivity.class));
            return true;
        }  else if (id == R.id.menu_reg) {
            startActivity(new Intent(this, RegisztracioActivity.class));
            return true;
        }  else if (id == R.id.menu_kosar) {
            startActivity(new Intent(this, KosarActivity.class));
            return true;
        }  else if (id == R.id.menu_bejelentkezes) {
            startActivity(new Intent(this, MainActivity.class));
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
