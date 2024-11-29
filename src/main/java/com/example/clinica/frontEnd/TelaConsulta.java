package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.MedicoModel;
import com.example.clinica.backend.Models.PacienteModel;
import com.example.clinica.backend.Services.ConsultaService;
import com.example.clinica.backend.Services.MedicoService;
import com.example.clinica.backend.Services.PacienteService;
import com.example.clinica.backend.Enums.StatusEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class TelaConsulta {

    private final ConsultaService consultaService = new ConsultaService();
    private final MedicoService medicoService = new MedicoService();
    private final PacienteService pacienteService = new PacienteService();

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de texto para o nome do paciente
        Label lblPaciente = new Label("Paciente:");
        ComboBox<PacienteModel> cbPaciente = new ComboBox<>();

        // Carregar pacientes do banco de dados
        List<PacienteModel> pacientes = getPacientes();
        if (pacientes != null && !pacientes.isEmpty()) {
            cbPaciente.setItems(FXCollections.observableArrayList(pacientes));
            cbPaciente.setCellFactory(param -> new ListCell<PacienteModel>() {
                @Override
                protected void updateItem(PacienteModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            cbPaciente.setButtonCell(new ListCell<PacienteModel>() {
                @Override
                protected void updateItem(PacienteModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        }

        // Campo de texto para o nome do médico
        Label lblMedico = new Label("Médico:");
        ComboBox<MedicoModel> cbMedico = new ComboBox<>();

        // Carregar médicos do banco de dados
        List<MedicoModel> medicos = getMedicos();
        if (medicos != null && !medicos.isEmpty()) {
            cbMedico.setItems(FXCollections.observableArrayList(medicos));
            cbMedico.setCellFactory(param -> new ListCell<MedicoModel>() {
                @Override
                protected void updateItem(MedicoModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            cbMedico.setButtonCell(new ListCell<MedicoModel>() {
                @Override
                protected void updateItem(MedicoModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        }

        // Caixa de seleção para o status da consulta
        Label lblStatus = new Label("Status:");
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Agendado", "Concluído");

        // Campo de texto para data de agendamento
        Label lblData = new Label("Data de Agendamento:");
        TextField txtData = new TextField();

        // Botão para adicionar consulta
        Button btnAdicionar = new Button("Adicionar Consulta");

        // Evento de clique no botão
        btnAdicionar.setOnAction(e -> {
            PacienteModel pacienteSelecionado = cbPaciente.getValue();
            MedicoModel medicoSelecionado = cbMedico.getValue();
            String statusSelecionado = cbStatus.getValue();
            String dataAgendamento = txtData.getText().trim();

            if (pacienteSelecionado != null && medicoSelecionado != null && statusSelecionado != null && !dataAgendamento.isEmpty()) {
                StatusEnum status = StatusEnum.valueOf(statusSelecionado.toUpperCase());
                boolean sucesso = consultaService.criarConsulta(pacienteSelecionado.getId(), medicoSelecionado.getId(), dataAgendamento, status);

                if (sucesso) {
                    // Limpa os campos após adicionar
                    cbPaciente.getSelectionModel().clearSelection();
                    cbMedico.getSelectionModel().clearSelection();
                    cbStatus.getSelectionModel().clearSelection();
                    txtData.clear();

                    // Mensagem de sucesso
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Consulta agendada com sucesso!");
                    alert.showAndWait();
                } else {
                    // Mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao agendar consulta.");
                    alert.showAndWait();
                }
            } else {
                // Mensagem de alerta se campos não forem preenchidos corretamente
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, insira todos os dados.");
                alert.showAndWait();
            }
        });

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblPaciente, cbPaciente, lblMedico, cbMedico, lblStatus, cbStatus, lblData, txtData, btnAdicionar);
        return layout;
    }

    // Método para carregar pacientes
    public List<PacienteModel> getPacientes() {
        PacienteService pacienteService = new PacienteService();
        return pacienteService.getAllPacientes();
    }

    // Método para carregar médicos
    public List<MedicoModel> getMedicos() {
        MedicoService medicoService = new MedicoService();
        return medicoService.getAllMedico();
    }
}
