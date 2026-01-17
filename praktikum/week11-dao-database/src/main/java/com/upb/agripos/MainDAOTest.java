package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class MainDAOTest {

    public static void main(String[] args) throws Exception {

        Class.forName("org.postgresql.Driver");

        // Membuka koneksi ke database agripos
        Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos",
                "postgres",
                "225522" 
        );

        ProductDAO dao = new ProductDAOImpl(conn);

        System.out.println("=== MEMULAI PENGUJIAN CRUD DAO ===");

        // 1. CREATE (Insert)
        System.out.println("\n[1] Menjalankan Operation: CREATE");
        Product newProduct = new Product("P01", "Pupuk Organik", 25000, 10);
        dao.insert(newProduct);
        System.out.println(">>> Berhasil menambahkan: " + newProduct.getName());

        // 2. UPDATE
        System.out.println("\n[2] Menjalankan Operation: UPDATE");
        Product updatedProduct = new Product("P01", "Pupuk Organik Premium", 30000, 8);
        dao.update(updatedProduct);
        System.out.println(">>> Data P01 diperbarui menjadi: " + updatedProduct.getName());

        // 3. READ BY CODE (Find)
        System.out.println("\n[3] Menjalankan Operation: READ BY CODE");
        Product p = dao.findByCode("P01");
        if (p != null) {
            System.out.println(">>> Ditemukan -> Nama: " + p.getName() + ", Harga: " + p.getPrice() + ", Stok: " + p.getStock());
        }

        // 4. READ ALL (List)
        System.out.println("\n[4] Menjalankan Operation: READ ALL");
        List<Product> products = dao.findAll();
        System.out.println(">>> Daftar seluruh produk di database:");
        for (Product prod : products) {
            System.out.println("    - " + prod.getCode() + " | " + prod.getName() + " | Rp" + prod.getPrice());
        }

        // 5. DELETE
        System.out.println("\n[5] Menjalankan Operation: DELETE");
        dao.delete("P01");
        System.out.println(">>> Produk P01 berhasil dihapus.");

        System.out.println("\n=== SEMUA OPERASI BERHASIL DIUJI ===");

        conn.close();
    }
}