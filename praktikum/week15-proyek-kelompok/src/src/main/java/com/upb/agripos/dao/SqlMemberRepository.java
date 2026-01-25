package main.java.com.upb.agripos.dao;

import main.java.com.upb.agripos.model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlMemberRepository {
    private final Connection conn;

    public SqlMemberRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Member> findAll() throws Exception {
        List<Member> list = new ArrayList<>();
        try (Statement s = conn.createStatement(); ResultSet rs = s.executeQuery("SELECT * FROM members ORDER BY member_id")) {
            while (rs.next()) {
                list.add(new Member(rs.getString("member_id"), rs.getString("nama"), rs.getInt("poin")));
            }
        }
        return list;
    }

    public void save(Member m) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO members (member_id, nama, poin) VALUES (?, ?, ?)")) {
            ps.setString(1, m.getId());
            ps.setString(2, m.getNama());
            ps.setInt(3, m.getPoin());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM members WHERE member_id=?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}