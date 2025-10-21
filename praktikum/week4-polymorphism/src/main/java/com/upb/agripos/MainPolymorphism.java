package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {

        Produk[] daftarProduk = {
            new Benih("WWA-002", "Benih Strobewryy AW55", 80000, 100, "AW55"),
            new Pupuk("SSR-005", "Pupuk Hayati 25kg", 90000, 50, "Hayati"),
            new AlatPertanian("SRW-025", "Sekop Tangan", 50000, 75, "Baja"),
            new ObatHama("AWA-555", "Obat Hama Ulat Grayak", 60000, 50, "Deltametrin")
        };

        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo());
        }

        CreditBy.print();
    }
}
