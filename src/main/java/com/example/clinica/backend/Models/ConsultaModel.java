package com.example.clinica.backend.Models;

import com.example.clinica.backend.Enums.StatusEnum;

public class ConsultaModel {
    private int idPaciente;
    private int idMedico;
    private String dataAgendamento;
    private StatusEnum status;

    public ConsultaModel(int idPaciente, int idMedico, String dataAgendamento, StatusEnum status) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.dataAgendamento = dataAgendamento;
        this.status = status;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
