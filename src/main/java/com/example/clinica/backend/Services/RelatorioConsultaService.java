package com.example.clinica.backend.Services;

import com.example.clinica.backend.DAO.RelatorioConsultaDAO;
import com.example.clinica.backend.Models.RelatorioConsultaModel;

import java.sql.Connection;
import java.util.List;

public class RelatorioConsultaService {
    private Connection conn;
    private RelatorioConsultaDAO relatorioConsultaDAO;


    public RelatorioConsultaService() {
        this.conn = ConnectionDatabase.connect();
        this.relatorioConsultaDAO = new RelatorioConsultaDAO(this.conn);
    }


    public List<RelatorioConsultaModel> getRelatorio(){
        return this.relatorioConsultaDAO.getRelatorioConsulta();
    }
}
