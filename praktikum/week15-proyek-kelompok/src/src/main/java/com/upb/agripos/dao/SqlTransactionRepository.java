package main.java.com.upb.agripos.dao;

import main.java.com.upb.agripos.model.CartItem;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SqlTransactionRepository {
    private final Connection conn;

    public SqlTransactionRepository(Connection conn) {
        this.conn = conn;
    }

    // --- 1. SIMPAN TRANSAKSI (LAMA) ---
    public void saveTransaction(double total, String method, String kasir, List<CartItem> items) throws Exception {
        String sqlHead = "INSERT INTO transactions (date, total_amount, payment_method, cashier_name) VALUES (CURRENT_TIMESTAMP, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sqlHead)) {
            ps.setDouble(1, total);
            ps.setString(2, method);
            ps.setString(3, kasir);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int transId = rs.getInt(1);
                saveItems(transId, items);
            }
        }
    }

    private void saveItems(int transId, List<CartItem> items) throws SQLException {
        String sqlItem = "INSERT INTO transaction_items (transaction_id, product_code, product_name, qty, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlItem)) {
            for (CartItem item : items) {
                ps.setInt(1, transId);
                ps.setString(2, item.getProduk().getKode());
                ps.setString(3, item.getProduk().getNama());
                ps.setInt(4, item.getQty());
                ps.setDouble(5, item.getSubtotal());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // --- 2. FITUR BARU: LAPORAN HARIAN (ADMIN) ---
    public double getDailyIncome() throws SQLException {
        String sql = "SELECT SUM(total_amount) FROM transactions WHERE DATE(date) = CURRENT_DATE";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    // --- 3. FITUR BARU: CEK MEMBER (KASIR) ---
    public String checkMember(String memberId) throws SQLException {
        String sql = "SELECT nama FROM members WHERE member_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nama"); // Mengembalikan nama jika ada
            }
        }
        return null; // Null jika tidak ditemukan
    }
}