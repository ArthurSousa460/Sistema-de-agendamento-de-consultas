package com.example.clinica.backend.Services;


import com.example.clinica.backend.DAO.PacienteDAO;
import com.example.clinica.backend.Models.PacienteModel;

import java.sql.Connection;
import java.util.List;

public class PacienteService {

    private Connection conn;
    private PacienteDAO pacienteDAO;

    public PacienteService() {
        this.conn = ConnectionDatabase.connect();
        this.pacienteDAO = new PacienteDAO(this.conn);
    }

    public boolean criarPaciente(String nome, String dataNascimento){
        return this.pacienteDAO.criarPaciente(nome, dataNascimento);
    }

    public List<PacienteModel> getAllPacientes(){
        return this.pacienteDAO.getAllPacientes();
    }

    public boolean updatePaciente(int idPaciente, String nome, String dataNascimento){
        return this.pacienteDAO.updatePaciente(idPaciente, nome, dataNascimento);
    }

    public boolean deletePaciente(int idPaciente){
        return this.pacienteDAO.deletePaciente(idPaciente);
    }
}

