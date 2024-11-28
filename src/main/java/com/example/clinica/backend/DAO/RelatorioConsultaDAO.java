package com.example.clinica.backend.DAO;

import com.example.clinica.backend.Enums.StatusEnum;
import com.example.clinica.backend.Models.RelatorioConsultaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioConsultaDAO {
    private Connection conn;


    public RelatorioConsultaDAO(Connection conn){
        this.conn = conn;
    }

    public List<RelatorioConsultaModel> getRelatorioConsulta(){
        String sql = "SELECT * FROM relatorio_consulta";
        List<RelatorioConsultaModel> relatorio = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id_consulta = rs.getInt(1);
                String nome_medico = rs.getString(2);
                String especialidade = rs.getString(3);
                String nome_paciente = rs.getString(4);
                String data_agendamento = rs.getString(5);
                StatusEnum status = StatusEnum.valueOf(rs.getString(6));

                RelatorioConsultaModel consulta = new RelatorioConsultaModel(
                        id_consulta,
                        nome_medico,
                        especialidade,
                        nome_paciente,
                        data_agendamento,
                        status
                );

                relatorio.add(consulta);

            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return relatorio;
    }
}
