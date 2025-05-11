# UTS Pemrograman Berorientasi Obyek 2
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 2</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Profil
<ul>
  <li>Nama: Havid Restu Afyantara</li>
  <li>NIM: 20220801189</li>
  <li>Studi Kasus: Kasir Apotek</li>
</ul>

## Judul Studi Kasus
<p>Sistem Informasi Apotek dengan Implementasi OOP dan Database MySQL</p>

## Penjelasan Studi Kasus
<p>Sistem Informasi Apotek ini adalah aplikasi yang dirancang untuk mengelola transaksi pembelian obat di apotek. Aplikasi ini mendukung dua jenis transaksi: pembelian langsung dan pembelian dengan resep. Sistem ini mengimplementasikan konsep Object-Oriented Programming (OOP) dan menggunakan database MySQL untuk penyimpanan data. Fitur utama meliputi manajemen data pasien, obat, resep, dan transaksi, serta verifikasi oleh tenaga medis (dokter dan apoteker).</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>Inheritance diimplementasikan melalui hierarki class TenagaMedis sebagai parent class, dengan Dokter dan Apoteker sebagai child class. Parent class TenagaMedis memiliki atribut umum seperti id dan nama, yang diwarisi oleh child class. Child class dapat menambahkan atau mengoverride method sesuai kebutuhan spesifik mereka. Contoh implementasi:</p>

```java
public abstract class TenagaMedis {
    protected String id;
    protected String nama;
    
    public TenagaMedis(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }
}

public class Dokter extends TenagaMedis {
    public Dokter(String id, String nama) {
        super(id, nama);
    }
}
```

### 2. Encapsulation
<p>Encapsulation diimplementasikan dengan membungkus data dan method dalam class, menggunakan modifier private untuk atribut dan public untuk method akses. Setiap entitas (Pasien, Obat, Resep, Transaksi) memiliki class tersendiri yang mengelola data dan operasinya. Contoh implementasi:</p>

```java
public class Pasien {
    private int id;
    private String nama;
    private int umur;
    
    // Getter methods
    public int getId() { return id; }
    public String getNama() { return nama; }
    public int getUmur() { return umur; }
}
```

### 3. Polymorphism
<p>Polymorphism diimplementasikan melalui method verifikasi() yang memiliki perilaku berbeda di class Dokter dan Apoteker. Method ini dipanggil melalui interface TenagaMedis, memungkinkan perilaku yang berbeda tergantung tipe objek. Contoh implementasi:</p>

```java
// Di Dokter
public void verifikasi() {
    System.out.println("Dokter " + nama + " memverifikasi resep...");
}

// Di Apoteker
public void verifikasi() {
    System.out.println("Apoteker " + nama + " memverifikasi obat...");
}
```

### 4. Abstract
<p>Abstraction diimplementasikan melalui abstract class TenagaMedis yang mendefinisikan template untuk class-class terkait. Class ini memiliki abstract method verifikasi() yang harus diimplementasikan oleh child class. Contoh implementasi:</p>

```java
public abstract class TenagaMedis {
    public abstract void verifikasi();
}
```

## Demo Proyek
<ul>
  <li>Github: <a href="https://github.com/yourusername/ApotekOOP">Github</a></li>
  <li>Gdrive: <a href="null">Gdrive</a></li>
</ul>
