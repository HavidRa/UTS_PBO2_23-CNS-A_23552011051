# UTS Pemrograman Berorientasi Obyek 2
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 2</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Profil
<ul>
  <li>Nama: Havid Restu Afyantara</li>
  <li>NIM: 23552011051</li>
  <li>Studi Kasus: Kasir Apotek</li>
</ul>

## Judul Studi Kasus
<p>Sistem Informasi Apotek dengan Implementasi OOP dan Database MySQL</p>

## Penjelasan Studi Kasus
<p>Sistem Informasi Apotek ini adalah aplikasi yang dirancang untuk mengelola transaksi pembelian obat di apotek. Aplikasi ini mendukung dua jenis transaksi: pembelian langsung dan pembelian dengan resep. Setiap transaksi harus diverifikasi oleh tenaga medis yang berwenang - pembelian langsung diverifikasi oleh apoteker, sedangkan pembelian dengan resep diverifikasi oleh dokter dan apoteker. Sistem ini mengimplementasikan konsep Object-Oriented Programming (OOP) dan menggunakan database MySQL untuk penyimpanan data. Fitur utama meliputi manajemen data pasien, obat, resep, dan transaksi, serta verifikasi oleh tenaga medis (dokter dan apoteker).</p>

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
<p>Polymorphism diimplementasikan melalui method verifikasi() yang memiliki perilaku berbeda di class Dokter dan Apoteker. Method ini dipanggil melalui interface TenagaMedis, memungkinkan perilaku yang berbeda tergantung tipe objek. Dalam sistem ini, verifikasi dilakukan pada dua jenis transaksi:</p>

<p>1. Pembelian Langsung: Diverifikasi oleh Apoteker</p>

```java
// Di pembelianLangsung()
Apoteker apoteker = new Apoteker("A001", "Rendi");
apoteker.verifikasi();
```

<p>2. Pembelian dengan Resep: Diverifikasi oleh Dokter dan Apoteker</p>

```java
// Di pembelianResep()
List<TenagaMedis> petugas = new ArrayList<>();
petugas.add(dokter);
petugas.add(new Apoteker("A001", "Syifa"));

for (TenagaMedis t : petugas) {
    t.verifikasi();
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
  <li>Github: <a href="https://github.com/HavidRa/UTS_PBO2_23-CNS-A_23552011051">Github</a></li>
  <li>Gdrive: <a href="https://drive.google.com/file/d/1ttexnFqMTjY4F_sYIVIsQuhny1k3Prcp/view?usp=drive_link">Gdrive</a></li>
</ul>
