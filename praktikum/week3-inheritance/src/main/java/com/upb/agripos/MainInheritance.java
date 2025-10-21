package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {

        Benih b = new Benih("WWA-002", "Benih Strobewryy AW55", 80000.0, 100, "AW55");
        Pupuk p = new Pupuk("SSR-005", "Pupuk Hayati 25kg", 90000.0, 50, "Hayati");
        AlatPertanian a = new AlatPertanian("SRW-025", "Sekop Tangan", 50000.0, 75, "Baja");

        System.out.println("=== Data Produk Pertanian ===");
        b.deskripsi();
        p.deskripsi();
        a.deskripsi();

        System.out.println("\n=== Menambah Stok Produk ===");
        System.out.println("Menambah stok Benih Stroberi AW55 sebanyak 20");
        b.tambahStok(20);
        b.deskripsi();

        System.out.println("\n=== Mengurangi Stok Produk ===");
        System.out.println("Mengurangi stok Sekop Tangan sebanyak 10");
        a.kurangiStok(10);
        a.deskripsi();

        CreditBy.print();
    }
}
