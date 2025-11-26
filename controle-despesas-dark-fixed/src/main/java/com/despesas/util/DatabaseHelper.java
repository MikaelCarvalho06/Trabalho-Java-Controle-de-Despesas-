package com.despesas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database helper padrão (user=root, senha vazia).
 */
public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/despesasdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "senac";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS despesas (
                id INT AUTO_INCREMENT PRIMARY KEY,
                descricao VARCHAR(255),
                categoria VARCHAR(100),
                valor DECIMAL(10,2),
                data DATE
            );
        """;
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Aviso: não foi possível criar tabela automaticamente: " + e.getMessage());
        }
    }
}
