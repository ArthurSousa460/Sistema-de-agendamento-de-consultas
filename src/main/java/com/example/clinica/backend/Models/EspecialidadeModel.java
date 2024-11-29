package com.example.clinica.backend.Models;

public class EspecialidadeModel {
    private int id;
    private String nome;


    public EspecialidadeModel(String nome){
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
