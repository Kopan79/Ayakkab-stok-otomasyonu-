package application;

import java.sql.Date;


public class Urun {
    private int urunID;
    private String kategori;
    private String renk;
    private Date tarih;
    private int beden;
    private int adet;
    private float fiyat;
    private int satildi;

    public int getUrunID() {
        return urunID;
    }

    public void setUrunID(int urunID) {
        this.urunID = urunID;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public int getBeden() {
        return beden;
    }

    public void setBeden(int beden) {
        this.beden = beden;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public float getFiyat() {
        return fiyat;
    }

    public void setFiyat(float fiyat) {
        this.fiyat = fiyat;
    }

    public int getSatildi() {
        return satildi;
    }

    public void setSatildi(int satildi) {
        this.satildi = satildi;
    }

    public Urun() {
        // Varsayılan kurucu
    }
    
    


    public Urun(int urunID, String kategori, String renk, Date tarih, int beden, int adet, float fiyat, int satildi) {
        this.urunID = urunID;
        this.kategori = kategori;
        this.renk = renk;
        this.tarih = tarih;
        this.beden = beden;
        this.adet = adet;
        this.fiyat = fiyat;
        this.satildi = satildi;
    }
}
