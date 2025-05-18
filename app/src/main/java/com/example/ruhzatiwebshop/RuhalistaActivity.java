package com.example.ruhzatiwebshop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ruhzatiwebshop.adapter.RuhaAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.example.ruhzatiwebshop.model.Ruha;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Collections;
import java.util.Comparator;


public class RuhalistaActivity extends BaseActivity {
    private FirebaseUser user;
    private FirebaseFirestore db;

    private List<Ruha> ruhaLista = new ArrayList<>();
    private RecyclerView recyclerView;
    private RuhaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruhalista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        setupToolbar();

        if (user == null) {
            finish();
            return;
        }

        betoltRuhakatFirestorebol();

        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // A-Z
                        Collections.sort(ruhaLista, Comparator.comparing(Ruha::getNev));
                        break;
                    case 1: // Z-A
                        Collections.sort(ruhaLista, (r1, r2) -> r2.getNev().compareTo(r1.getNev()));
                        break;
                    case 2: // Legolcsóbb
                        Collections.sort(ruhaLista, Comparator.comparingInt(Ruha::getAr));
                        break;
                    case 3: // Legdrágább
                        Collections.sort(ruhaLista, (r1, r2) -> Integer.compare(r2.getAr(), r1.getAr()));
                        break;
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }

    private void betoltRuhakatFirestorebol() {
        db.collection("ruhak")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ruhaLista.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Ruha ruha = doc.toObject(Ruha.class);
                        ruha.setId(doc.getId());  // dokumentum ID beállítása
                        ruhaLista.add(ruha);
                    }
                    adapter = new RuhaAdapter(this, ruhaLista);
                    recyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Hiba történt az adatok betöltésekor", Toast.LENGTH_SHORT).show());
    }


    // 2 különböző rendszerszolgáltatás (háttér szolgáltatás)
    private void showNotification(String title, String content) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "ruha_webshop_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Ruha Webshop Értesítések",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(false)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = jobScheduler.schedule(jobInfo);

        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, "JobScheduler ütemezve", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "JobScheduler ütemezése sikertelen", Toast.LENGTH_SHORT).show();
        }
    }
}
