package com.example.ruhzatiwebshop;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruhzatiwebshop.adapter.KosarAdapter;
import com.example.ruhzatiwebshop.model.Ruha;
import android.Manifest;


import java.util.List;

public class KosarActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private KosarAdapter adapter;
    private Button vasarlasGomb;
    private static final int SMS_PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kosar);

        setupToolbar();

        recyclerView = findViewById(R.id.kosarRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Ruha> kosarLista = KosarManager.getInstance().getKosarLista();
        Log.d("KosarActivity", "Kosár betöltve | Méret: " + kosarLista.size());

        adapter = new KosarAdapter(kosarLista);
        recyclerView.setAdapter(adapter);

        vasarlasGomb = findViewById(R.id.vasarlasGomb);  // legyen a layout-ban gomb!
        vasarlasGomb.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
            } else {
                sendOrderConfirmationSMS();
            }
        });
    }

    private void sendOrderConfirmationSMS() {
        try {
            String phoneNumber = "06123456789";  // ide a címzett telefonszámát írd, vagy dinamikusan add meg
            String message = "Köszönjük rendelésed! A csomagod úton van.";

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Toast.makeText(this, "Rendelés visszaigazolás elküldve SMS-ben!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS küldési hiba: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOrderConfirmationSMS();
            } else {
                Toast.makeText(this, "SMS küldés engedély megtagadva", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
