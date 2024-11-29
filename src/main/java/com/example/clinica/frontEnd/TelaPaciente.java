package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.PacienteModel;
import com.example.clinica.backend.Services.PacienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class TelaPaciente {

    // Instância do serviço de pacientes
    private final PacienteService pacienteService = new PacienteService();

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de texto para o nome do paciente
        Label lblNome = new Label("Nome do paciente:");
        TextField txtNome = new TextField();

        // Campo de texto para a data de nascimento
        Label lblDataNascimento = new Label("Data de Nascimento:");
        TextField txtDataNascimento = new TextField();

        // Botão para adicionar paciente
        Button btnAdicionar = new Button("Adicionar Paciente");

        // Tabela para listar pacientes
        TableView<PacienteModel> tabelaPacientes = new TableView<>();
        ObservableList<PacienteModel> pacientesData = FXCollections.observableArrayList();

        TableColumn<PacienteModel, Integer> colId = new TableColumn<>("ID");
        TableColumn<PacienteModel, String> colNome = new TableColumn<>("Nome");
        TableColumn<PacienteModel, String> colDataNascimento = new TableColumn<>("Data de Nascimento");

        // Configurando as colunas
        //colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        //colNome.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));
        //colDataNascimento.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDataNascimento()));

        tabelaPacientes.getColumns().addAll(colId, colNome, colDataNascimento);

        // Adiciona os pacientes existentes à tabela
        pacientesData.addAll(getPacientes());
        tabelaPacientes.setItems(pacientesData);

        // Evento de clique no botão
        btnAdicionar.setOnAction(e -> {
            String nomePaciente = txtNome.getText().trim();
            String dataNascimentoPaciente = txtDataNascimento.getText().trim();

            if (!nomePaciente.isEmpty() && !dataNascimentoPaciente.isEmpty()) {
                boolean sucesso = pacienteService.criarPaciente(nomePaciente, dataNascimentoPaciente);

                if (sucesso) {
                    // Atualiza a tabela com o novo paciente
                    PacienteModel novoPaciente = new PacienteModel(nomePaciente, dataNascimentoPaciente); // Ajuste conforme o seu modelo
                    pacientesData.add(novoPaciente);

                    // Limpa os campos de texto
                    txtNome.clear();
                    txtDataNascimento.clear();

                    // Mensagem de sucesso
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Paciente adicionado com sucesso!");
                    alert.showAndWait();
                } else {
                    // Mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao adicionar paciente.");
                    alert.showAndWait();
                }
            } else {
                // Campo vazio
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, preencha todos os campos.");
                alert.showAndWait();
            }
        });

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, lblDataNascimento, txtDataNascimento, btnAdicionar, tabelaPacientes);
        return layout;
    }

    public List<PacienteModel> getPacientes() {
        // Obtém os pacientes do banco de dados
        List<PacienteModel> pacientes = pacienteService.getAllPacientes();
        return pacientes != null ? pacientes : new ArrayList<>();
    }
}
