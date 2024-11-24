package com.example.clinica.backend.Models;

public class MedicoModel {
    private int id;
    private int idEspecialidade;
    private String nome;


    public MedicoModel(String nome, int idEspecialidade) {
        this.nome = nome;
        this.idEspecialidade = idEspecialidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(int idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
