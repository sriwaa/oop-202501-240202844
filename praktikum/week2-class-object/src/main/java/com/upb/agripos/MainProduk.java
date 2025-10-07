package com.upb.agripos;

import com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {

        Produk p1 = new Produk("WWA-002", "Benih Alpukat 01", 80000.0, 100);
        Produk p2 = new Produk("SSR-005", "Benih Anggur 02", 90000.0, 100);
        Produk p3 = new Produk("SRW-025", "Benih Strobewry 05", 70000.0, 100);

        p1.tampilkanInfo();
        p2.tampilkanInfo();
        p3.tampilkanInfo();

        System.out.println("\nMenambah stok Benih Alpukat");
        p1.tambahStok(10);
        p1.tampilkanInfo();

        System.out.println("\nMengurangi stok Benih Strobewry");
        p3.kurangiStok(5);
        p3.tampilkanInfo();

        CreditBy.print();
    }
}
