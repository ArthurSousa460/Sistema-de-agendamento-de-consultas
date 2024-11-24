package com.example.clinica.backend.DAO;
import com.example.clinica.backend.Models.ConsultaModel;
import com.example.clinica.backend.Enums.StatusEnum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private Connection conn;

    public ConsultaDAO(Connection conn) {
        this.conn = conn;
    }


    public Boolean criarConsulta(int idPaciente, int idMedico, String dataAgendamento, StatusEnum status) {

        ConsultaModel newConsulta = new ConsultaModel(idPaciente, idMedico, dataAgendamento, status);

        try {
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO consulta(id_paciente, id_medico, data_agendamento, status_agendamento) VALUES("
                    + newConsulta.getIdPaciente() + ", "
                    + newConsulta.getIdMedico() + ", '"
                    + newConsulta.getDataAgendamento() + "', '"
                    + newConsulta.getStatus() + "')";
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ConsultaModel> getAllConsulta(){
        List<ConsultaModel> consultas = new ArrayList<>();

        String sql = "SELECT * FROM consulta";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                int idPaciente = rs.getInt("id_paciente");
                int idMedico = rs.getInt("id_medico");
                String dataAgendamento = rs.getString("data_agendamento");
                String status = rs.getString("status_agendamento");

                ConsultaModel consulta = new ConsultaModel(idPaciente, idMedico, dataAgendamento, StatusEnum.valueOf(status));


                consultas.add(consulta);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return consultas;
    }

    public boolean updateConsulta(int idConsulta, int idPaciente, int idMedico, String dataAgendamento, StatusEnum statusEnum){
        String sql = """
                UPDATE consulta SET id_paciente = ?, id_medico = ?, data_agendamento = ?, status_agendamento = ?
                WHERE id_consulta = ?
                """;

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);

            preparedStatement.setInt(1, idPaciente);
            preparedStatement.setInt(2,idMedico);
            preparedStatement.setString(3, dataAgendamento);
            preparedStatement.setString(4, statusEnum.name());
            preparedStatement.setInt(5, idConsulta);

            preparedStatement.execute();

            return true;
        }catch (SQLException e ){
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteConsulta(int idPaciente, int idMedico){
        String sql = "DELETE FROM consulta WHERE id_paciente = ? AND id_medico = ?";

        try{
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);

            preparedStatement.setInt(1, idPaciente);
            preparedStatement.setInt(2, idMedico);

            preparedStatement.execute();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

}
