package com.example.ruhzatiwebshop;

public class Ruha {
    private String nev;
    private String leiras;
    private int ar;
    private int kepResource;

    public Ruha(String nev, String leiras, int ar, int kepResource) {
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.kepResource = kepResource;
    }

    // Getter metódusok
    public String getNev() {
        return nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public int getAr() {
        return ar;
    }

    public int getKepResource() {
        return kepResource;
    }
}