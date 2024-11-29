package com.example.clinica.backend.Models;

public class PacienteModel {

    private int id;
    private String name;
    private String dataNascimento;

    public PacienteModel(String name, String dataNascimento) {
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
    public void setNome(String name) {
        this.name = name;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}