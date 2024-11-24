package com.example.clinica.backend.Models;

import java.sql.Date;

public class PacienteModel {

    private int id;
    private String name;
    private Date dataNascimento;

    public PacienteModel(String name, Date dataNascimento) {
        this.dataNascimento = dataNascimento;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}
