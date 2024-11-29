package com.example.clinica.backend.DAO;

import com.example.clinica.backend.Models.EspecialidadeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {
    private Connection conn;


    public EspecialidadeDAO(Connection conn){
        this.conn = conn;
    }


    public boolean criarEspecialidade(String nome){
        String sql = "INSERT INTO especialidade(nome) VALUES(?)";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, nome);

            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public List<EspecialidadeModel> getAllEspecialidades(){
        String sql = "SELECT * FROM especialidade";
        List<EspecialidadeModel> listaEspecialidades = new ArrayList<>();


        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()){
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                EspecialidadeModel especialidade = new EspecialidadeModel(nome);
                especialidade.setId(id);

                listaEspecialidades.add(especialidade);

            }

            return listaEspecialidades;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteEspecialidade(int idEspecialidade){
        String sql = "DELETE FROM especialidade WHERE id_especialidade = ?";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, idEspecialidade);

            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

}
