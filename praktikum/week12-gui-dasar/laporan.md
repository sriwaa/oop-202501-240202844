# Laporan Praktikum Minggu 12
Topik: GUI Dasar JavaFX (Event-Driven Programming)

## Identitas
- Nama  : [SRI WAHYUNINGSIH]
- NIM   : [240202844]
- Kelas : [3IKRA]

---

## Tujuan
1. Membangun antarmuka grafis (GUI) sederhana menggunakan JavaFX.
2. Menerapkan konsep event-driven programming pada tombol "Tambah Produk".
3. Mengintegrasikan GUI dengan modul backend (Service & DAO) sesuai prinsip MVC dan SOLID (DIP).

---

## Dasar Teori
1. Event-Driven Programming: Paradigma di mana alur program ditentukan oleh peristiwa (event) seperti klik tombol atau input keyboard.
2. JavaFX: Framework modern untuk membangun GUI di Java yang memisahkan antara struktur tampilan dan logika program.
3. Traceability: Menjamin bahwa implementasi kode (GUI) selaras dengan desain perangkat lunak yang dibuat di Bab 6 (UML).

---

## Langkah Praktikum
1. Setup Environment: Konfigurasi file pom.xml untuk mendownload library JavaFX secara otomatis melalui Maven.
2. Implementasi Model: Menyiapkan class Product sebagai entitas data.
3. Implementasi DAO & Service: Menggunakan kembali logic database dari Minggu 11 (JDBC PostgreSQL).
4. Membangun View: Membuat class ProductFormView yang berisi komponen TextField, Button, dan ListView.
5. Menghubungkan Controller: Menggunakan btnAdd.setOnAction untuk menangkap input user, mengirimnya ke Service, dan memperbarui UI.

---

## Kode Program
KODE UTAMA YANG DIBUAT
package com.upb.agripos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;
import com.upb.agripos.controller.ProductController;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/agripos",
            "postgres",
            "225522"
        );

        ProductFormView view = new ProductFormView();
        ProductService service = new ProductService(conn);
        new ProductController(service, view);

        stage.setTitle("Agri-POS – Kelola Produk");
        stage.setScene(new Scene(view, 400, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


---

## Hasil Eksekusi
1. Hasil Eksekusi (tampilan tambah barang)
![alt text](<week12_tambah barang.png>)

2. Hasil Eksekusi (diisi contoh barang)
![alt text](<week12_contoh tambah barang.png>)

3. Hasil Eksekusi (barang berhasil ditambahkan)
![alt text](<week12_berhasil tambah barang.png>)

4. Hasil Eksekusi (barang berhasil disimpan di database)
![alt text](<week12_masuk di database.png>)

---

## Analisis
Aplikasi ini sudah mengikuti arsitektur MVC. Pendekatan minggu ini jauh lebih interaktif dibanding minggu lalu yang hanya menggunakan terminal (CLI).

Artefak Bab 6	Referensi	Handler GUI	Controller/Service	DAO	Dampak UI/DB
Use Case	UC-01 Tambah Produk	Tombol Tambah	ProductController.add()	ProductDAO.insert()	UI list bertambah + DB insert
Activity	AD-01 Tambah Produk	Tombol Tambah	Ambil input → Validasi	ProductService.insert()	Data tersimpan permanen
Sequence	SD-01 Tambah Produk	setOnAction	View → Controller → Service	DAO → DB	Urutan panggilan sesuai SD Bab 6

Kendala: Terjadi error "Package mismatch" dan library JavaFX tidak terbaca. Solusi: Memperbaiki struktur folder sesuai standar Maven (src/main/java/...) dan menambahkan dependency JavaFX di pom.xml.


---

## Kesimpulan
Dengan JavaFX, pengembangan aplikasi Agri-POS menjadi lebih profesional. Penggunaan pola MVC memastikan kode UI tidak tercampur dengan kode SQL, sehingga memenuhi prinsip SOLID (DIP) di mana View tidak memanggil DAO secara langsung.

---
