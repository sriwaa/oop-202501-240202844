package com.upb.agripos;

import com.upb.agripos.model.Produk;
import com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {

        Produk p1 = new Produk("WWA-002", "Benih Strobewryy AW55", 80000.0, 100);
        Produk p2 = new Produk("SSR-005", "Pupuk Hayati 25kg", 90000.0, 100);
        Produk p3 = new Produk("SRW-025", "Sekop Tangan", 50000.0, 100);

        System.out.println("=== Info Awal Produk ===");
        p1.tampilkanInfo();
        p2.tampilkanInfo();
        p3.tampilkanInfo();

        System.out.println("\n=== Menambah Stok Produk ===");
        System.out.println("Menambah stok Benih Strobewryy AW55 sebanyak 20");
        p1.tambahStok(20);
        p1.tampilkanInfo();

        System.out.println("\n=== Mengurangi Stok Produk ===");
        System.out.println("Mengurangi stok Sekop Tangan sebanyak 10");
        p3.kurangiStok(10);
        p3.tampilkanInfo();

        CreditBy.print();
    }
}
