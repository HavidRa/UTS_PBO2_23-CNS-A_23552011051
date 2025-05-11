package model;

import java.util.Date;
import java.util.List;

public class Resep {
    private int id;
    private int pasienId;
    private int dokterId;
    private Date tanggal;
    private List<DetailResep> detailResep;

    public Resep(int id, int pasienId, int dokterId, Date tanggal, List<DetailResep> detailResep) {
        this.id = id;
        this.pasienId = pasienId;
        this.dokterId = dokterId;
        this.tanggal = tanggal;
        this.detailResep = detailResep;
    }

    public int getId() { return id; }
    public int getPasienId() { return pasienId; }
    public int getDokterId() { return dokterId; }
    public Date getTanggal() { return tanggal; }
    public List<DetailResep> getDetailResep() { return detailResep; }
}