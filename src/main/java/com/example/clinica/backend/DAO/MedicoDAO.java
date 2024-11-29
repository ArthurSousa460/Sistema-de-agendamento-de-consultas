package com.example.clinica.backend.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.clinica.backend.Models.MedicoModel;

public class MedicoDAO {

    private Connection conn;

    public MedicoDAO(Connection conn) {
        this.conn = conn;
    }

    // Método para criar o médico no banco de dados, com especialidade em formato de string
    public boolean criarMedico(String nome, int idEspecialidade) {
        String sql = "INSERT INTO medico(nome, id_especialidade) VALUES(?, ?)";
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, idEspecialidade); // ID da especialidade
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obter todos os médicos, incluindo a especialidade
    public List<MedicoModel> getAllMedico() {
        List<MedicoModel> medicos = new ArrayList<>();
        String sql = "SELECT m.id_medico, m.nome, m.id_especialidade, e.nome AS especialidade " +
                     "FROM medico m " +
                     "JOIN especialidade e ON m.id_especialidade = e.id_especialidade"; // Join para trazer o nome da especialidade

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idMedico = rs.getInt("id_medico");
                String nome = rs.getString("nome");
                int idEspecialidade = rs.getInt("id_especialidade");
                String especialidade = rs.getString("especialidade"); // Nome da especialidade

                MedicoModel medico = new MedicoModel(nome, idEspecialidade, especialidade); // Passa a especialidade como string
                medico.setId(idMedico);

                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    // Método para atualizar o nome do médico
    public boolean updateMedico(int idMedico, String nome) {
        String sql = "UPDATE medico SET nome = ? WHERE id_medico = ?";
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, idMedico);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para excluir o médico
    public boolean deleteMedico(int idMedico) {
        String sql = "DELETE FROM medico WHERE id_medico = ?";
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, idMedico);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
