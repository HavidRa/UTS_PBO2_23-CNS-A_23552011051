package model;

public class Obat {
    private int id;
    private String nama;
    private int harga;
    private int stok;

    public Obat(int id, String nama, int harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getStok() { return stok; }
    public void kurangiStok(int jumlah) { this.stok -= jumlah; }
}