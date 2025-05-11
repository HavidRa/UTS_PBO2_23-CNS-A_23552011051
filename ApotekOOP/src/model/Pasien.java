package model;

public class Pasien {
    private int id;
    private String nama;
    private int umur;

    public Pasien(int id, String nama, int umur) {
        this.id = id;
        this.nama = nama;
        this.umur = umur;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public int getUmur() { return umur; }
}