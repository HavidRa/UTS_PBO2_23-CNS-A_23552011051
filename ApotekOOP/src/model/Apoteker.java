package model;

public class Apoteker extends TenagaMedis {
    public Apoteker(String id, String nama) {
        super(id, nama);
    }

    @Override
    public void verifikasi() {
        System.out.println("Apoteker " + nama + " memverifikasi dan menyiapkan obat.");
    }
}