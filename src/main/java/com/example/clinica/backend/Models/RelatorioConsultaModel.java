package com.example.clinica.backend.Models;

import com.example.clinica.backend.Enums.StatusEnum;

public class RelatorioConsultaModel {
    private int id_consulta;
    private String nome_medico;
    private String especialidade;
    private String nome_paciente;
    private String data_agendamento;
    private StatusEnum status;


    public RelatorioConsultaModel(int id_consulta, String nome_medico, String especialidade, String nome_paciente, String data_agendamento, StatusEnum status) {
        this.id_consulta = id_consulta;
        this.nome_medico = nome_medico;
        this.especialidade = especialidade;
        this.nome_paciente = nome_paciente;
        this.data_agendamento = data_agendamento;
        this.status = status;
    }

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }

    public String getNome_medico() {
        return nome_medico;
    }

    public void setNome_medico(String nome_medico) {
        this.nome_medico = nome_medico;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getNome_paciente() {
        return nome_paciente;
    }

    public void setNome_paciente(String nome_paciente) {
        this.nome_paciente = nome_paciente;
    }

    public String getData_agendamento() {
        return data_agendamento;
    }

    public void setData_agendamento(String data_agendamento) {
        this.data_agendamento = data_agendamento;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
