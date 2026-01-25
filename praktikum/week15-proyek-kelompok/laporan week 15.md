# Laporan Praktikum Minggu 15
**Topik:** Final Project - Rancang Bangun Sistem Kasir "Agri-POS" (MVC, DAO, Database)

## 1. Identitas Kelompok & Peran
| NIM | Nama Mahasiswa | Peran / Kontribusi Utama |
| :--- | :--- | :--- |
| **[240202837]** | **[KAYLA PUTRI ARSONISR]** | **Backend & Testing:** Implementasi DAO (`SqlProductRepository`), Skema Database, Unit Testing (`AgriPosTest`). |
| **[240202828]** | **[AKHMAD AKBAR SYARIFUDIN]** | **Frontend:** Desain GUI FXML (`MainView`), CSS Styling, Form Login, Integrasi UI. |
| **[240202844]** | **[SRI WAHYUNINGSIH]** | **Logic & Integration:** Controller (`MainController`), Security (`AuthService`), Logika Pembayaran (`PaymentStrategy`). |
| **[240202833]** | **[EGALIAN LALINTANG]** | **QA & Docs:** Manual Testing (UAT), Penyusunan Laporan, Diagram UML. |
| **[240202838]** | **[KHANSA AMANDA I]** | **System Analysis:** Perancangan ERD, Analisis Kebutuhan, Materi Presentasi. |

---

## 2. Ringkasan Sistem
**Agri-POS** adalah aplikasi Point of Sale (POS) berbasis desktop yang dirancang khusus untuk toko pertanian. Sistem ini bertujuan menggantikan pencatatan manual dengan solusi digital yang terintegrasi database.

* **Fitur Utama:** Manajemen Produk (CRUD), Transaksi Penjualan, Manajemen Member (Diskon), Laporan Harian, dan Multi-role Access.
* **Scope:** Operasional kasir (Front-end) dan manajemen data admin (Back-office).
* **Keunggulan:** Mendukung pembayaran QRIS (E-Wallet) dan diskon loyalitas member.

---

## 3. Desain Sistem (Arsitektur & UML)

### A. Arsitektur Layer (MVC + DAO)
Aplikasi menggunakan arsitektur **MVC** yang dipisahkan dengan layer **DAO** untuk akses data yang aman (Dependency Injection Principle).
1.  **View (GUI):** `MainView.fxml` (Tampilan).
2.  **Controller:** `MainController.java` (Penghubung event).
3.  **Service:** `CartService.java` (Logika Bisnis).
4.  **DAO/Repository:** `SqlProductRepository.java` (Akses Database).
5.  **Model:** `Produk`, `Member`, `Transaction` (Entitas Data).

### B. UML Diagrams
1.  **Use Case:** Admin (CRUD Produk, Laporan) vs Kasir (Checkout, Cek Member).
2.  **Class Diagram:** Menunjukkan relasi `MainController` yang bergantung pada `CartService` dan `ProductRepository`.
3.  **Sequence Diagram (SD-Checkout):**
    * *Alur:* View (Bayar) $\rightarrow$ Controller $\rightarrow$ Service (Hitung) $\rightarrow$ DAO (Simpan Transaksi & Update Stok) $\rightarrow$ Database.

---

## 4. Desain Database (ERD)
Database **PostgreSQL** terdiri dari 4 tabel utama. Akses data dilakukan via `PreparedStatement` di DAO.

**Skema Tabel:**
* `users`: ID, Username, Password, Role.
* `products`: Code (PK), Name, Price, Stock.
* `members`: Member_ID (PK), Name, Poin.
* `transactions`: ID, Date, Total, Payment_Method.

![ERD Database](dilampirkan di folder screenshot)

---

## 5. Traceability Matrix (WAJIB)
Pemetaan antara Kebutuhan Fungsional (FR) dengan Implementasi Kode dan Bukti Fitur.

| Artefak | Referensi FR | Implementasi (Kelas/Metode) | Bukti (Screenshot) |
| :--- | :--- | :--- | :--- |
| **FR** | **FR-1 Manajemen Produk** | `ProductController` / `SqlProductRepository.save()` | ![CRUD](dilampirkan di folder screenshot) |
| **FR** | **FR-2 Transaksi Penjualan** | `CartService.addToCart()` / `MainController` | ![Keranjang](dilampirkan di folder screenshot) |
| **FR** | **FR-3 Metode Pembayaran** | `PaymentStrategy` (Interface) $\rightarrow$ `Cash`, `EWallet` | ![Payment](dilampirkan di folder screenshot) |
| **FR** | **FR-4 Struk & Laporan** | `ReceiptService` / `TransactionRepository.getIncome()` | ![Struk](dilampirkan di folder screenshot)  |
| **FR** | **FR-5 Login & Role** | `AuthService.login()` / `MainController.applyRoleAccess()` | ![Role](dilampirkan di folder screenshot) |
| **SD** | **SD-Checkout** |  'Controller' 'Service'  'DAO' | *(Lihat Diagram di Bab 3)* |
| **FR (Ops)** | **OFR-1 Diskon Member** | `CartService` (Logic 10%) / `SqlMemberRepository` | ![Diskon](dilampirkan di folder screenshot) |

---

## 6. Test Plan & Test Case

### A. Manual Test Cases (Minimal 8 Alur Inti)
Pengujian fungsional dilakukan secara manual (Black-box testing).

| ID | Skenario | Langkah Uji | Hasil Diharapkan | Status |
| :--- | :--- | :--- | :--- | :--- |
| **TC-01** | Login Admin | Input user 'admin' | Masuk dashboard, menu kasir disabled | **PASS** |
| **TC-02** | Login Kasir | Input user 'kasir' | Masuk dashboard, menu edit data disabled | **PASS** |
| **TC-03** | Login Gagal | Input password salah | Muncul Alert "Login Gagal" | **PASS** |
| **TC-04** | Tambah Produk | Admin input data produk baru | Data tersimpan di DB & muncul di tabel | **PASS** |
| **TC-05** | Update Stok | Admin ubah stok barang | Stok di tabel berubah | **PASS** |
| **TC-06** | Transaksi Tunai | Kasir checkout tunai | Transaksi sukses, stok berkurang | **PASS** |
| **TC-07** | **Bayar E-Wallet** | Pilih metode E-Wallet | Muncul QR Code pada struk | **PASS** |
| **TC-08** | **Diskon Member** | Input ID Member Valid | Total belanja terpotong 10% | **PASS** |

### B. Unit Testing (JUnit)
Pengujian White-box dilakukan pada logika bisnis `CartService` dan `Diskon`.
* **Unit Test:** `src/test/java/com/upb/agripos/AgriPosTest.java`
* **Coverage:** Perhitungan Total, Penambahan Qty, Logika Diskon.

**Screenshot Hasil JUnit:**
![JUnit Result](dilampirkan di folder screenshot)

---

## 7. Kendala & Solusi

1.  **Kendala:** *Stok produk di tabel GUI tidak berkurang otomatis setelah transaksi.*
    * **Solusi:** Menambahkan method `refreshTable()` yang dipanggil secara eksplisit di blok `finally` pada proses checkout untuk menarik data terbaru dari DB.
2.  **Kendala:** *Kesulitan mengatur tampilan agar responsif saat window di-resize.*
    * **Solusi:** Menggunakan properti `VBox.vgrow="ALWAYS"` dan `AnchorPane` constraints pada file FXML.
3.  **Kendala:** *Kasir bisa tidak sengaja mengedit data produk.*
    * **Solusi:** Menerapkan logika `applyRoleAccess()` di Controller yang men-disable tombol `Simpan`, `Hapus`, dan `Update` jika role user adalah "KASIR".

---

## 8. Kesimpulan
Proyek Agri-POS telah berhasil memenuhi seluruh persyaratan sistem. Implementasi **MVC** dan **DAO** menjadikan kode terstruktur dan aman. Fitur-fitur esensial seperti Transaksi, Manajemen Stok, dan Laporan berjalan dengan baik (Passed 8/8 Test Cases). Fitur tambahan **Diskon Member** dan **QRIS** memberikan nilai tambah modern pada aplikasi.

---