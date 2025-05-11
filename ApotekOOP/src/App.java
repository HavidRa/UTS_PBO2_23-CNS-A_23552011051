import controller.TransaksiManager;
import model.*;
import java.util.*;
import java.sql.Timestamp;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== APLIKASI APOTEK ===");
            System.out.println("1. Pembelian Langsung");
            System.out.println("2. Pembelian dengan Resep");
            System.out.println("3. Riwayat Pembelian");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");
            
            try {
                int pilihan = input.nextInt();
                input.nextLine(); // Membersihkan buffer

                if (pilihan == 4) {
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    break;
                }

                switch (pilihan) {
                    case 1:
                        pembelianLangsung(input);
                        break;
                    case 2:
                        pembelianResep(input);
                        break;
                    case 3:
                        tampilkanRiwayat();
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Masukkan angka yang valid!");
                input.nextLine(); // Membersihkan buffer
            }
        }
        input.close();
    }

    private static void pembelianLangsung(Scanner input) {
        System.out.println("\n=== PEMBELIAN LANGSUNG ===");
        
        // Input data pasien
        System.out.print("Masukkan nama pasien: ");
        String namaPasien = input.nextLine();
        System.out.print("Masukkan umur pasien: ");
        int umurPasien = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        
        // Simpan data pasien dan dapatkan ID-nya
        int pasienId = TransaksiManager.tambahPasien(namaPasien, umurPasien);
        
        // Verifikasi ID pasien
        if (pasienId <= 0) {
            System.out.println("Error: Gagal mendapatkan ID pasien!");
            return;
        }
        
        // Tampilkan daftar obat yang tersedia
        System.out.println("\nDaftar Obat yang Tersedia:");
        TransaksiManager.tampilkanDaftarObat();
        
        // Input ID obat yang akan dibeli
        System.out.print("\nMasukkan ID obat yang akan dibeli: ");
        int obatId = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        
        // Ambil data obat dari database
        Obat obat = TransaksiManager.getObat(obatId);
        if (obat == null) {
            System.out.println("Obat tidak ditemukan!");
            return;
        }
        
        // Input jumlah pembelian
        System.out.print("Masukkan jumlah yang dibeli: ");
        int jumlah = input.nextInt();
        input.nextLine(); // Membersihkan buffer

        if (jumlah <= 0) {
            System.out.println("Jumlah harus lebih dari 0!");
            return;
        }

        if (jumlah > obat.getStok()) {
            System.out.println("Stok tidak mencukupi!");
            return;
        }

        // Hitung total dan simpan transaksi
        int total = obat.getHarga() * jumlah;
        TransaksiManager.tambahTransaksi(pasienId, total);

        // Verifikasi oleh Apoteker
        System.out.println("\nProses Verifikasi:");
        Apoteker apoteker = new Apoteker("A001", "Rendi");
        apoteker.verifikasi();

        System.out.println("\nTransaksi berhasil!");
        System.out.println("Pasien: " + namaPasien);
        System.out.println("Obat: " + obat.getNama());
        System.out.println("Harga per unit: Rp " + obat.getHarga());
        System.out.println("Jumlah: " + jumlah);
        System.out.println("Total: Rp " + total);
    }

    private static void pembelianResep(Scanner input) {
        System.out.println("\n=== PEMBELIAN DENGAN RESEP ===");
        
        // Input data pasien
        System.out.print("Masukkan nama pasien: ");
        String namaPasien = input.nextLine();
        System.out.print("Masukkan umur pasien: ");
        int umurPasien = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        
        // Simpan data pasien dan dapatkan ID-nya
        int pasienId = TransaksiManager.tambahPasien(namaPasien, umurPasien);
        
        // Verifikasi ID pasien
        if (pasienId <= 0) {
            System.out.println("Error: Gagal mendapatkan ID pasien!");
            return;
        }
        
        // Input data dokter
        System.out.print("Masukkan ID Dokter (contoh: D001): ");
        String dokterId = input.nextLine().trim();
        while (dokterId.isEmpty()) {
            System.out.println("ID Dokter tidak boleh kosong!");
            System.out.print("Masukkan ID Dokter (contoh: D001): ");
            dokterId = input.nextLine().trim();
        }
        
        // Buat objek dokter
        Dokter dokter = new Dokter(dokterId, "Yayan");

        List<DetailResep> detailResep = new ArrayList<>();
        boolean lanjut = true;
        int totalHarga = 0;

        while (lanjut) {
            // Tampilkan daftar obat yang tersedia
            System.out.println("\nDaftar Obat yang Tersedia:");
            TransaksiManager.tampilkanDaftarObat();
            
            // Input ID obat yang akan dibeli
            System.out.print("\nMasukkan ID obat yang akan dibeli: ");
            int obatId = input.nextInt();
            input.nextLine(); // Membersihkan buffer
            
            // Ambil data obat dari database
            Obat obat = TransaksiManager.getObat(obatId);
            if (obat == null) {
                System.out.println("Obat tidak ditemukan!");
                continue;
            }
            
            System.out.print("Masukkan jumlah: ");
            int jumlah = input.nextInt();
            input.nextLine(); // Membersihkan buffer

            if (jumlah <= 0) {
                System.out.println("Jumlah harus lebih dari 0!");
                continue;
            }

            if (jumlah > obat.getStok()) {
                System.out.println("Stok tidak mencukupi!");
                continue;
            }

            detailResep.add(new DetailResep(0, obatId, jumlah));
            totalHarga += obat.getHarga() * jumlah;

            System.out.print("Tambah obat lagi? (y/n): ");
            lanjut = input.nextLine().equalsIgnoreCase("y");
        }

        // Gunakan Timestamp untuk tanggal
        Resep resep = new Resep(0, pasienId, Integer.parseInt(dokterId.substring(1)), new Timestamp(System.currentTimeMillis()), detailResep);
        TransaksiManager.tambahResep(resep);
        TransaksiManager.tambahTransaksi(pasienId, totalHarga);

        // Verifikasi oleh Dokter dan Apoteker
        List<TenagaMedis> petugas = new ArrayList<>();
        petugas.add(dokter);
        petugas.add(new Apoteker("A001", "Syifa"));

        for (TenagaMedis t : petugas) {
            t.verifikasi();
        }

        System.out.println("\nResep berhasil diproses!");
        System.out.println("Total: Rp " + totalHarga);
    }

    private static void tampilkanRiwayat() {
        System.out.println("\n=== RIWAYAT PEMBELIAN ===");
        TransaksiManager.tampilkanRiwayatTransaksi();
    }
}
