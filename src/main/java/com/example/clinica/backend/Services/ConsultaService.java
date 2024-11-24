package com.example.clinica.backend.Services;
import com.example.clinica.backend.DAO.ConsultaDAO;
import com.example.clinica.backend.Models.ConsultaModel;
import com.example.clinica.backend.Enums.StatusEnum;
import java.sql.Connection;
import java.util.List;

public class ConsultaService {
    private Connection conn;
    private ConsultaDAO consultaDAO;

    public ConsultaService() {
        this.conn = ConnectionDatabase.connect();
        this.consultaDAO = new ConsultaDAO(this.conn);
    }

    public boolean criarConsulta(int idPaciente, int idMedico, String dataAgendamento, StatusEnum status){
        return consultaDAO.criarConsulta(idPaciente, idMedico, dataAgendamento, status);
    }

    public List<ConsultaModel> getAllConsulta(){
        return this.consultaDAO.getAllConsulta();
    }

    public boolean updateConsulta(int idConsulta,int idPaciente, int idMedico, String dataAgendamento, StatusEnum statusEnum){
        return this.consultaDAO.updateConsulta(idConsulta, idPaciente, idMedico, dataAgendamento, statusEnum);

    }

    public boolean deleteConsulta(int id_paciente, int id_medico){
        return this.consultaDAO.deleteConsulta(id_paciente, id_medico);
    }
}
