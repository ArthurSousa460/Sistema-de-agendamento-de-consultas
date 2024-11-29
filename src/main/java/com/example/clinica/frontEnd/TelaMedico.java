package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Models.MedicoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.example.clinica.backend.Services.EspecialidadeService;

import javax.swing.*;

public class TelaMedico {

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de texto para o nome do médico
        Label lblNome = new Label("Nome do Médico:");
        TextField txtNome = new TextField();

        // Caixa de seleção para especialidades
        Label lblEspecialidade = new Label("Especialidade:");
        ComboBox<String> cbEspecialidade = new ComboBox<>();

        // Carregar especialidades do banco de dados
        List<EspecialidadeModel> especialidades = new ArrayList<>();
        especialidades = getEspecialidades();
        if (especialidades != null && !especialidades.isEmpty()) {
            for (EspecialidadeModel especialidade : especialidades) {
                cbEspecialidade.getItems().add(especialidade.getNome()); // Adiciona apenas o nome
            }
        } else {
            cbEspecialidade.setPromptText("Erro ao carregar especialidades");
        }

        // Botão para adicionar médico
        Button btnAdicionar = new Button("Adicionar Médico");

        // Tabela para listar médicos
        TableView<Object> tabelaMedicos = new TableView<>();
        ObservableList<MedicoModel> medicosData = FXCollections.observableArrayList();;

        TableColumn<Object, String> colId = new TableColumn<>("ID");
        TableColumn<Object, String> colNome = new TableColumn<>("Nome");
        TableColumn<Object, String> colEspecialidade = new TableColumn<>("Especialidade");
        TableColumn<Object, String> colAcoes = new TableColumn<>("Ações");

        tabelaMedicos.getColumns().addAll(colId, colNome, colEspecialidade, colAcoes);

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, lblEspecialidade, cbEspecialidade, btnAdicionar, tabelaMedicos);
        return layout;

    }

    public List<EspecialidadeModel> getEspecialidades(){
        EspecialidadeService especialidadeService = new EspecialidadeService();
        List<EspecialidadeModel> especialidades = new ArrayList<>();
        especialidades = especialidadeService.getAllEspecialidades();

        return especialidades;
    }

}
