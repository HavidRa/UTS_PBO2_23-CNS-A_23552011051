package model;

public class Dokter extends TenagaMedis {
    public Dokter(String id, String nama) {
        super(id, nama);
    }

    @Override
    public void verifikasi() {
        System.out.println("Dokter " + nama + " memverifikasi resep.");
    }
}