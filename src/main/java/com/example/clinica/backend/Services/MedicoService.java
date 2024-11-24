package com.example.clinica.backend.Services;

import com.example.clinica.backend.DAO.MedicoDAO;
import com.example.clinica.backend.Models.MedicoModel;

import java.sql.Connection;
import java.util.List;

public class MedicoService {

    private Connection conn;
    private MedicoDAO medicoDAO;

    public MedicoService() {
        this.conn = ConnectionDatabase.connect();
        this.medicoDAO = new MedicoDAO(this.conn);
    }

    public boolean criarMedico(String nome, int idEspecialidade){
        return medicoDAO.criarMedico(nome, idEspecialidade);
    }

    public List<MedicoModel> getAllMedico(){
        return this.medicoDAO.getAllMedico();
    }

    public boolean updateMedico(int idMedico, String nome){
        return this.medicoDAO.updateMedico(idMedico, nome);
    }

    public boolean deleteMedico(int idMedico){
        return this.medicoDAO.deleteMedico(idMedico);
    }
}
