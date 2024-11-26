package com.example.clinica.backend.DAO;

import com.example.clinica.backend.Models.MedicoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    private Connection conn;

    public MedicoDAO(Connection conn) {
        this.conn = conn;
    }


    public Boolean criarMedico(String nome, int id_especialidade) {

        MedicoModel newMedico = new MedicoModel(nome, id_especialidade);

        String sql = "INSERT INTO medico(nome, id_especialidade) VALUES(?, ?)";
        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, newMedico.getNome());
            preparedStatement.setInt(2, newMedico.getIdEspecialidade());

            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MedicoModel> getAllMedico(){
        List<MedicoModel> medicos = new ArrayList<>();

        String sql = "SELECT * FROM medico";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                int idMedico = rs.getInt("id_medico");
                int idEspecialidade = rs.getInt("id_especialidade");
                String nome = rs.getString("nome");

                MedicoModel medico = new MedicoModel(nome, idEspecialidade);
                medico.setId(idMedico);

                medicos.add(medico);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return medicos;
    }

    public boolean updateMedico(int idMedico, String nome){
        String sql = "UPDATE medico SET nome = ? WHERE id_medico = ?";

        try{
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

    public boolean deleteMedico(int idMedico){
        String sql = "DELETE FROM medico WHERE id_medico = ?";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, idMedico);

            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}