package com.example.clinica.backend.DAO;

import com.example.clinica.backend.Models.PacienteModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private Connection conn;

    public PacienteDAO(Connection conn) {
        this.conn = conn;
    }


    public Boolean criarPaciente(String nome, String dataNascimento){
        PacienteModel newPaciente = new PacienteModel(nome, dataNascimento);

        String sql =  "INSERT INTO paciente(nome, data_nascimento) VALUES(?, ?)";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, newPaciente.getNome());
            preparedStatement.setString(2, newPaciente.getDataNascimento());

            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }


    public List<PacienteModel> getAllPacientes(){
        List<PacienteModel> pacientes = new ArrayList<>();

        String sql = "SELECT * FROM paciente";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id_paciente");
                String nome = rs.getString("nome");
                String dataNacimento = rs.getString("data_nascimento");
                PacienteModel paciente = new PacienteModel(nome, dataNacimento);
                paciente.setId(id);


                pacientes.add(paciente);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return pacientes;
    }

    public boolean updatePaciente(int idPaciente, String nome, String dataNascimento){
        String sql = "UPDATE paciente SET nome = ?, data_nascimento = ? WHERE id_paciente = ?";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, dataNascimento);
            preparedStatement.setInt(3, idPaciente);

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePaciente(int idPAciente){
        String sql = "DELETE FROM paciente WHERE id_paciente = ?";
        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, idPAciente);

            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
