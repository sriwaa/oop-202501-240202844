package main.java.com.upb.agripos.dao;

import main.java.com.upb.agripos.model.Produk;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlProductRepository implements ProductRepository {
    private final Connection conn;

    public SqlProductRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Produk> findAll() throws Exception {
        List<Produk> list = new ArrayList<>();
        try (Statement s = conn.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM products ORDER BY code")) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public Produk findByCode(String code) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE code = ?")) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public void save(Produk p) throws Exception {
        String sql = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getKode());
            ps.setString(2, p.getNama());
            ps.setDouble(3, p.getHarga());
            ps.setInt(4, p.getStok());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Produk p) throws Exception {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE code=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setDouble(2, p.getHarga());
            ps.setInt(3, p.getStok());
            ps.setString(4, p.getKode());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String code) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM products WHERE code=?")) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }

    // --- FITUR BARU: KURANGI STOK ---
    public void reduceStock(String code, int qty) throws Exception {
        String sql = "UPDATE products SET stock = stock - ? WHERE code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setString(2, code);
            ps.executeUpdate();
        }
    }

    private Produk mapRow(ResultSet rs) throws SQLException {
        return new Produk(rs.getString("code"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"));
    }
}