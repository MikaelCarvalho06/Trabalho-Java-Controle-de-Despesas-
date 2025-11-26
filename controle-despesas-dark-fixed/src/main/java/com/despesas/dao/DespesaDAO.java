package com.despesas.dao;

import com.despesas.model.Despesa;
import com.despesas.util.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    public void salvar(Despesa d) {
        String sql = "INSERT INTO despesas (descricao, categoria, valor, data) VALUES (?,?,?,?)";
        try (Connection c = DatabaseHelper.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getDescricao());
            ps.setString(2, d.getCategoria());
            ps.setDouble(3, d.getValor());
            ps.setString(4, d.getData());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) d.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Despesa> listar() {
        List<Despesa> lista = new ArrayList<>();
        String sql = "SELECT id, descricao, categoria, valor, DATE_FORMAT(data, '%d/%m/%Y') AS data FROM despesas ORDER BY id DESC";
        try (Connection c = DatabaseHelper.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Despesa d = new Despesa(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getString("categoria"),
                        rs.getDouble("valor"),
                        rs.getString("data")
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void excluir(Despesa d) {
        String sql = "DELETE FROM despesas WHERE id = ?";
        try (Connection c = DatabaseHelper.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
