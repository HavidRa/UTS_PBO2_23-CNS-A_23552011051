package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import model.Pasien;
import model.Obat;
import model.Resep;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import model.DetailResep;
import controller.Database;

public class TransaksiManager {

    public static void tambahTransaksi(int pasienId, int total) {
        Connection conn = Database.getConnection();
        try {
            // Verifikasi pasien ada
            Pasien pasien = getPasien(pasienId);
            if (pasien == null) {
                System.out.println("Error: Pasien dengan ID " + pasienId + " tidak ditemukan!");
                return;
            }

            String sql = "INSERT INTO transaksi (pasien_id, total, tanggal) VALUES (?, ?, NOW())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pasienId);
            ps.setInt(2, total);
            ps.executeUpdate();
            System.out.println("Transaksi berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Error: Gagal menambahkan transaksi. " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Pasien getPasien(int id) {
        Connection conn = Database.getConnection();
        try {
            String sql = "SELECT * FROM pasien WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pasien(rs.getInt("id"), rs.getString("nama"), rs.getInt("umur"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Obat getObat(int id) {
        Connection conn = Database.getConnection();
        try {
            String sql = "SELECT * FROM obat WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Obat(rs.getInt("id"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void tambahResep(Resep resep) {
        Connection conn = Database.getConnection();
        try {
            conn.setAutoCommit(false);
            
            // Insert resep
            String sqlResep = "INSERT INTO resep (pasien_id, dokter_id, tanggal) VALUES (?, ?, ?)";
            PreparedStatement psResep = conn.prepareStatement(sqlResep, Statement.RETURN_GENERATED_KEYS);
            psResep.setInt(1, resep.getPasienId());
            psResep.setInt(2, resep.getDokterId());
            psResep.setTimestamp(3, new Timestamp(resep.getTanggal().getTime()));
            psResep.executeUpdate();

            ResultSet rs = psResep.getGeneratedKeys();
            if (rs.next()) {
                int resepId = rs.getInt(1);
                
                // Insert detail resep
                String sqlDetail = "INSERT INTO detail_resep (resep_id, obat_id, jumlah) VALUES (?, ?, ?)";
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                
                for (DetailResep detail : resep.getDetailResep()) {
                    psDetail.setInt(1, resepId);
                    psDetail.setInt(2, detail.getObatId());
                    psDetail.setInt(3, detail.getJumlah());
                    psDetail.executeUpdate();
                }
            }
            
            conn.commit();
            System.out.println("Resep berhasil ditambahkan.");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int tambahPasien(String nama, int umur) {
        Connection conn = Database.getConnection();
        try {
            // Cek apakah pasien sudah ada
            String sqlCek = "SELECT id FROM pasien WHERE nama = ? AND umur = ?";
            PreparedStatement psCek = conn.prepareStatement(sqlCek);
            psCek.setString(1, nama);
            psCek.setInt(2, umur);
            ResultSet rs = psCek.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                System.out.println("Pasien sudah terdaftar dengan ID: " + id);
                return id;
            }
            
            // Jika pasien belum ada, tambahkan data baru
            String sql = "INSERT INTO pasien (nama, umur) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nama);
            ps.setInt(2, umur);
            ps.executeUpdate();
            
            // Dapatkan ID yang baru saja di-generate
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Pasien berhasil ditambahkan dengan ID: " + id);
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error: Gagal menambahkan/mencari pasien. " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public static void tambahObat(String nama, int harga, int stok) {
        Connection conn = Database.getConnection();
        try {
            String sql = "INSERT INTO obat (nama, harga, stok) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, harga);
            ps.setInt(3, stok);
            ps.executeUpdate();
            System.out.println("Obat berhasil ditambahkan.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tampilkanDaftarObat() {
        Connection conn = Database.getConnection();
        try {
            String sql = "SELECT * FROM obat ORDER BY id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("ID\tNama\t\tHarga\t\tStok");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("%d\t%s\t\tRp %d\t%d\n",
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getInt("harga"),
                    rs.getInt("stok")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tampilkanRiwayatTransaksi() {
        Connection conn = Database.getConnection();
        try {
            String sql = "SELECT t.id, p.nama as nama_pasien, t.total, t.tanggal " +
                        "FROM transaksi t " +
                        "JOIN pasien p ON t.pasien_id = p.id " +
                        "ORDER BY t.tanggal DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("ID\tPasien\t\tTotal\t\tTanggal");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("%d\t%s\t\tRp %d\t%s\n",
                    rs.getInt("id"),
                    rs.getString("nama_pasien"),
                    rs.getInt("total"),
                    rs.getTimestamp("tanggal")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLastInsertedPasienId() {
        Connection conn = Database.getConnection();
        try {
            // Menggunakan query untuk mendapatkan ID terakhir dari tabel pasien
            String sql = "SELECT MAX(id) as id FROM pasien";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error: Gagal mendapatkan ID pasien. " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
