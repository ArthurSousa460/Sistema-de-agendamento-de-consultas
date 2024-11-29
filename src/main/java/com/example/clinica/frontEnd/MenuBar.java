package com.example.clinica.frontEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuBar {


    private List<String> getEspecialidadesFromDB() {
        List<String> especialidades = new ArrayList<>();
        String url = "jdbc:sqlite:clinica.db"; // Altere para a sua conexão com o banco
        String query = "SELECT nome FROM especialidades";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                especialidades.add(rs.getString("nome"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar especialidades: " + e.getMessage());
        }
        return especialidades;
    }
}
