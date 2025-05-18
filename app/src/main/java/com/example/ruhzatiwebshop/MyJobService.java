package com.example.ruhzatiwebshop;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        // Itt végezd el a háttérfeladatot, pl. adatfrissítés vagy értesítés
        Toast.makeText(this, "JobScheduler fut", Toast.LENGTH_SHORT).show();

        // Ha hosszabb futó művelet, akkor true, különben false:
        return false; // munka befejeződött azonnal
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Ha a munka megszakad (pl. rendszer leállítás)
        return false; // nem akarjuk újraindítani
    }
}
