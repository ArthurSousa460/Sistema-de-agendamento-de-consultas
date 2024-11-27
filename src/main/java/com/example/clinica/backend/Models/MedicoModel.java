package com.example.clinica.backend.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MedicoModel {

    private IntegerProperty id;
    private IntegerProperty idEspecialidade;
    private StringProperty nome;
    private StringProperty especialidade; // Especialidade como uma StringProperty

    public MedicoModel(String nome, int idEspecialidade, String especialidade) {
        this.nome = new SimpleStringProperty(nome);
        this.idEspecialidade = new SimpleIntegerProperty(idEspecialidade);
        this.id = new SimpleIntegerProperty();
        this.especialidade = new SimpleStringProperty(especialidade); // A especialidade é uma StringProperty
    }

    // Propriedades JavaFX (StringProperty para nome, especialidade, e IntegerProperty para id e idEspecialidade)
    public StringProperty nomeProperty() {
        return nome;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty idEspecialidadeProperty() {
        return idEspecialidade;
    }

    public StringProperty especialidadeProperty() {
        return especialidade; // Devolvendo a propriedade especialidade
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getIdEspecialidade() {
        return idEspecialidade.get();
    }

    public void setIdEspecialidade(int idEspecialidade) {
        this.idEspecialidade.set(idEspecialidade);
    }

    public String getEspecialidade() {
        return especialidade.get(); // Retorna o nome da especialidade
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade.set(especialidade);
    }
}
