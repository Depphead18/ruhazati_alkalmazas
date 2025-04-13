package com.example.ruhzatiwebshop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RuhalistaActivity extends BaseActivity {
    private static final String LOG_TAG = RegisztracioActivity.class.getName();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruhalista);

        user = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();

        if (user != null) {
            Log.d(LOG_TAG, "AU user");
        } else {
            Log.d(LOG_TAG, "Unau user");
            finish();
        }

        LinearLayout container = findViewById(R.id.ruhaContainer);
        addRuhaCard(container, "Fehér Blúz", "Elegáns pamut blúz irodába.", R.drawable.superthumb);
        addRuhaCard(container, "Sportos póló", "Laza viselet szabadidős programokhoz.", R.drawable.superthumb);
    }

    private void addRuhaCard(LinearLayout parent, String nev, String leiras, int kepRes) {
        View card = LayoutInflater.from(this).inflate(R.layout.activity_ruha_reszletek, parent, false);

        ImageView kep = card.findViewById(R.id.ruhaKep);
        TextView nevText = card.findViewById(R.id.ruhaNev);
        TextView leirasText = card.findViewById(R.id.ruhaLeiras);
        TextView mennyisegText = card.findViewById(R.id.mennyisegText);
        Button plus = card.findViewById(R.id.plusButton);
        Button minus = card.findViewById(R.id.minusButton);
        Button kosarba = card.findViewById(R.id.kosarbaButton);

        nevText.setText(nev);
        leirasText.setText(leiras);
        kep.setImageResource(kepRes);


        final int[] count = {1};
        plus.setOnClickListener(v -> {
            count[0]++;
            mennyisegText.setText(String.valueOf(count[0]));
        });
        minus.setOnClickListener(v -> {
            if (count[0] > 1) count[0]--;
            mennyisegText.setText(String.valueOf(count[0]));
        });

        kosarba.setOnClickListener(v -> {
            Toast.makeText(this, nev + " (" + count[0] + " db) a kosárba került 🛒", Toast.LENGTH_SHORT).show();
        });

        parent.addView(card);
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