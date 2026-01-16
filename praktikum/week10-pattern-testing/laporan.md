# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : [Sri Wahyuningsih]
- NIM   : [240202844]
- Kelas : [3IKRA]

---

## Tujuan
   Mahasiswa memahami dan mampu mengimplementasikan structural design pattern (Singleton), architectural pattern (MVC), serta melakukan verifikasi kode menggunakan Unit Testing (JUnit).

---

## Dasar Teori
1. Singleton Pattern: Memastikan sebuah class hanya memiliki satu instance global di seluruh aplikasi.

2. MVC (Model-View-Controller): Pola arsitektur yang memisahkan data (Model), tampilan (View), dan logika bisnis (Controller).

3. Unit Testing: Proses pengujian bagian terkecil dari kode (method/class) secara mandiri untuk memastikan fungsinya benar.

4. JUnit 5: Framework standar di Java untuk melakukan pengujian otomatis (Testing).Class adalah blueprint dari objek.

---

## Langkah Praktikum
1. Membuat class DatabaseConnection dengan pola Singleton (private constructor & static getInstance).

2. Mengimplementasikan pola MVC dengan membuat class Product (Model), ConsoleView (View), dan ProductController (Controller).

3. Menyatukan Singleton dan MVC di dalam class AppMVC.

4. Membuat class pengujian ProductTest di folder src/test/java.

5. Menambahkan library junit-platform-console-standalone-1.10.0.jar ke dalam Referenced Libraries untuk mengatasi error import.
6. Commit message yang digunakan (week10-pattern-testing)

---

## Kode Program
1. Kode Program Product.java
   package com.upb.agripos.model;

public class Product {

    private final String code;
    private final String name;

    public Product(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}


2. Kode Program ConsoleView.java
   package com.upb.agripos.view;

public class ConsoleView {

    public void showMessage(String message) {
        System.out.println(message);
    }
}


3. Kode Program ProductController.java
   package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.view.ConsoleView;

public class ProductController {

    private final Product model;
    private final ConsoleView view;

    public ProductController(Product model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    public void showProduct() {
        view.showMessage(
            "Produk: " + model.getCode() + " - " + model.getName()
        );
    }
}


4. Kode Program DatabaseConnection.java
   package com.upb.agripos.config;

public class DatabaseConnection {

    private static DatabaseConnection instance;

    // constructor private
    private DatabaseConnection() {
        System.out.println("DatabaseConnection dibuat");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}


5. Kode Program AppMVC.java
   package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.view.ConsoleView;
import com.upb.agripos.controller.ProductController;
import com.upb.agripos.config.DatabaseConnection;

public class AppMVC {

    public static void main(String[] args) {

        System.out.println("Hello, I am Sriwa-240202844 (Week10)");

        // Singleton test
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        System.out.println("Apakah instance sama? " + (db1 == db2));

        // MVC
        Product product = new Product("P01", "Pupuk Organik");
        ConsoleView view = new ConsoleView();
        ProductController controller = new ProductController(product, view);

        controller.showProduct();
    }
}


6. Kode Program ProductTest.java
   package test.java.com.upb.agripos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.upb.agripos.model.Product;

public class ProductTest {

    @Test
    public void testProductName() {
        Product p = new Product("P01", "Benih Jagung");
        assertEquals("Benih Jagung", p.getName(), "Nama produk harus sesuai");
    }

    @Test
    public void testProductCode() {
        Product p = new Product("P02", "Pupuk Urea");
        assertEquals("P02", p.getCode(), "Kode produk harus sesuai");
    }
}


---

## Hasil Eksekusi
1. Hasil Eksekusi AppMVC
   ![alt text](<week_10_hasil eksekusi.png>)

2. Hasil Eksekusi JUnit
   ![alt text](<week_10_hasil test.png>)
---

## Analisis
- Cara Kerja Kode: Kode Singleton bekerja dengan mengecek apakah instance sudah ada; jika belum, baru dibuat. Kode MVC bekerja dengan Controller mengambil data dari Model lalu mengirimnya ke View untuk ditampilkan.

- Perbedaan: Minggu ini fokus pada struktur dan kualitas kode (Testing), bukan sekadar fungsionalitas. Penggunaan Unit Test memungkinkan deteksi bug secara otomatis tanpa menjalankan main program berkali-kali.

- Kendala & Solusi: Kendala utama adalah error import org.junit cannot be resolved. Hal ini diatasi dengan mengunduh file JUnit .jar secara manual dan menambahkannya ke Referenced Libraries di VS Code, serta menyesuaikan deklarasi package agar sesuai dengan Source Path.

---

## Kesimpulan
Dengan Singleton, penggunaan resource (seperti koneksi database) menjadi lebih efisien. Pola MVC membuat kode lebih rapi dan mudah dikelola (maintainable). Sementara itu, Unit Testing memberikan kepastian bahwa logika program sudah berjalan sesuai ekspektasi sebelum dideploy.

---

## Quiz
1. [Tuliskan kembali pertanyaan 1 dari panduan]  
   **Jawaban:**
   Agar class tersebut tidak bisa di-instansiasi (dibuat objeknya) menggunakan kata kunci new dari luar class, sehingga kontrol pembuatan objek sepenuhnya diatur oleh method getInstance.

2. [Tuliskan kembali pertanyaan 2 dari panduan]  
   **Jawaban:**
   Controller bertindak sebagai jembatan yang memproses input user, memanipulasi data pada Model, dan memilih View mana yang harus ditampilkan.

3. [Tuliskan kembali pertanyaan 3 dari panduan]  
   **Jawaban:**
   Untuk membandingkan nilai yang diharapkan (expected) dengan nilai yang sebenarnya dihasilkan oleh kode (actual). Jika berbeda, maka test akan dianggap gagal (failed).
