package com.bbots;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SchemaCheck {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://15.206.148.49:5432/postgres";
        String user = "bav";
        String password = "Bav@1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT prosrc FROM pg_proc WHERE proname = 'pr_handle_authorization'";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("prosrc"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
