package com.example.clinica.backend.Services;

import com.example.clinica.backend.DAO.EspecialidadeDAO;
import com.example.clinica.backend.Models.EspecialidadeModel;

import java.sql.Connection;
import java.util.List;

public class EspecialidadeService {
    private Connection conn;
    private EspecialidadeDAO especialidadeDAO;

    public EspecialidadeService(){
        this.conn = ConnectionDatabase.connect();
        this.especialidadeDAO = new EspecialidadeDAO(this.conn);
    }

    public boolean criarEspecialidade(String nome){
        return especialidadeDAO.criarEspecialidade(nome);
    }

    public List<EspecialidadeModel> getAllEspecialidades(){
        return this.especialidadeDAO.getAllEspecialidades();
    }

}
