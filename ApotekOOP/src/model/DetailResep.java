package model;

public class DetailResep {
    private int id;
    private int obatId;
    private int jumlah;

    public DetailResep(int id, int obatId, int jumlah) {
        this.id = id;
        this.obatId = obatId;
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObatId() {
        return obatId;
    }

    public void setObatId(int obatId) {
        this.obatId = obatId;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}