package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.PacienteModel;
import com.example.clinica.backend.Services.PacienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class PacienteInterface {

    private TableView<PacienteModel> tablePacienteView;
    private PacienteService pacienteService;

    public PacienteInterface() {
        this.tablePacienteView = new TableView<>();
        this.pacienteService = new PacienteService();
    }

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de texto para o nome do paciente
        Label lblNome = new Label("Nome do Paciente:");
        TextField txtNome = new TextField();

        // Campo de texto para data de nascimento
        Label lblDataNascimento = new Label("Data de Nascimento:");
        TextField txtDataNascimento = new TextField();

        // Botão para adicionar paciente
        Button btnAdicionar = new Button("Adicionar Paciente");

        btnAdicionar.setOnAction(event -> {
            String nomePaciente = txtNome.getText().trim();
            String dataNascimento = txtDataNascimento.getText().trim();

            if (nomePaciente.isEmpty() || dataNascimento.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Por favor, preencha todos os campos.");
            } else {
                boolean sucesso = pacienteService.criarPaciente(nomePaciente, dataNascimento);
                if (sucesso) {
                    txtNome.clear();
                    txtDataNascimento.clear();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Paciente adicionado com sucesso!");
                    atualizarTabelaPacientes();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao adicionar paciente.");
                }
            }
        });

        // Configurar a tabela de pacientes
        configurarTabela();

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, lblDataNascimento, txtDataNascimento, btnAdicionar, this.tablePacienteView);
        return layout;
    }

    private void configurarTabela() {

        this.tablePacienteView.getItems().clear();

        TableColumn<PacienteModel, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PacienteModel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<PacienteModel, String> colDataNascimento = new TableColumn<>("Data de Nascimento");
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        tablePacienteView.getColumns().addAll(colId, colNome, colDataNascimento);
        atualizarTabelaPacientes();

    }

    private void atualizarTabelaPacientes() {
        List<PacienteModel> pacientes = pacienteService.getAllPacientes();
        if (pacientes != null && !pacientes.isEmpty()) {
            ObservableList<PacienteModel> pacientesData = FXCollections.observableArrayList(pacientes);
            tablePacienteView.setItems(pacientesData);
        } else {
            tablePacienteView.getItems().clear();
            System.out.println("Nenhum paciente cadastrado!");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);

    }
}