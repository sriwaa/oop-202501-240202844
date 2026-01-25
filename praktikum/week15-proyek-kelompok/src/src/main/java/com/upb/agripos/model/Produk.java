package main.java.com.upb.agripos.model;

import java.util.Objects;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // ... (Getter dan Setter tetap sama, tidak perlu diubah) ...
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    
    public void tambahStok(int jumlah) {
        if (jumlah > 0) {
            this.stok += jumlah;
        } else {
            // Model sebaiknya melempar error atau mengembalikan false, 
            // tapi untuk latihan ini System.out tidak apa-apa.
            System.out.println("Jumlah stok harus lebih dari 0!");
        }
    }

    public void kurangiStok(int jumlah) {
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
        } else {
            System.out.println("Stok tidak mencukupi!");
        }
    }

    public void tampilkanData() {
        System.out.println("  Kode Produk: " + kode);
        System.out.println("  Nama Produk: " + nama);
        System.out.println("  Harga (Rp): " + harga);
        System.out.println("  Stok Tersedia: " + stok);
    }

    @Override
    public String toString() {
        return kode + " - " + nama + " (Rp" + String.format("%,.0f", harga) + ") | Stok: " + stok;
    }

    // @Override equals & hashCode tetap sama
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produk produk = (Produk) o;
        return Objects.equals(kode, produk.kode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kode);
    }
}