package model;

public abstract class TenagaMedis {
    protected String id;
    protected String nama;

    public TenagaMedis(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }

    public abstract void verifikasi();
}