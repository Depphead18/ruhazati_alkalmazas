package com.example.ruhzatiwebshop.model;

import java.io.Serializable;

public class Ruha implements Serializable {
    private String id;
    private String nev;
    private String leiras;
    private String kep;
    private int ar;
    private String szin;
    private String marka;
    private String kategoria;

    public Ruha() {}

    public Ruha(String nev, String leiras, String kep) {
        this.nev = nev;
        this.leiras = leiras;
        this.kep = kep;
    }

    public Ruha(String nev, String leiras, String kep, int ar, String szin, String marka, String kategoria) {
        this.nev = nev;
        this.leiras = leiras;
        this.kep = kep;
        this.ar = ar;
        this.szin = szin;
        this.marka = marka;
        this.kategoria = kategoria;
    }

    public String getId() { return id; }
    public String getNev() { return nev; }
    public String getLeiras() { return leiras; }
    public String getKep() { return kep; }
    public int getAr() { return ar; }
    public String getSzin() { return szin; }
    public String getMarka() { return marka; }
    public String getKategoria() { return kategoria; }


    public void setId(String id) { this.id = id; }
    public void setNev(String nev) { this.nev = nev; }
    public void setLeiras(String leiras) { this.leiras = leiras; }
    public void setKepUrl(String kep) { this.kep = kep; }
    public void setAr(int ar) { this.ar = ar; }
    public void setSzin(String szin) { this.szin = szin; }
    public void setMarka(String marka) { this.marka = marka; }
    public void setKategoria(String kategoria) { this.kategoria = kategoria; }
}



