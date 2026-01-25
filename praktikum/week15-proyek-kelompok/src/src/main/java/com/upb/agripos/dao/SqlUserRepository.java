package main.java.com.upb.agripos.dao;

import main.java.com.upb.agripos.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlUserRepository implements UserRepository {
    private final Connection conn;

    public SqlUserRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User findByUsername(String username) throws Exception {
        // PERHATIKAN: WAJIB ADA KOLOM 'role' DI SINI
        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role") // <--- JANGAN SAMPAI KETINGGALAN
                    );
                }
            }
        }
        return null;
    }
}