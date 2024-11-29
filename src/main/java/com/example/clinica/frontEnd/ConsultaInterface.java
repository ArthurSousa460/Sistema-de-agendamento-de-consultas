package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.ConsultaModel;
import com.example.clinica.backend.Models.MedicoModel;
import com.example.clinica.backend.Models.PacienteModel;
import com.example.clinica.backend.Enums.StatusEnum;
import com.example.clinica.backend.Services.ConsultaService;
import com.example.clinica.backend.Services.MedicoService;
import com.example.clinica.backend.Services.PacienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class ConsultaInterface {

    private final ConsultaService consultaService = new ConsultaService();
    private final MedicoService medicoService = new MedicoService();
    private final PacienteService pacienteService = new PacienteService();
    private TableView<ConsultaModel> tabelaConsultas;

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campos e botões para criar consulta
        Label lblPaciente = new Label("Paciente:");
        ComboBox<PacienteModel> cbPaciente = new ComboBox<>();
        carregarPacientes(cbPaciente);

        Label lblMedico = new Label("Médico:");
        ComboBox<MedicoModel> cbMedico = new ComboBox<>();
        carregarMedicos(cbMedico);

        Label lblStatus = new Label("Status:");
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Agendado", "Concluido");

        Label lblData = new Label("Data de Agendamento:");
        TextField txtData = new TextField();

        Button btnAdicionar = new Button("Adicionar Consulta");
        btnAdicionar.setOnAction(e -> {
            criarConsulta(cbPaciente, cbMedico, cbStatus, txtData);
            atualizarTabela();
        });

        // Tabela para exibir consultas
        tabelaConsultas = new TableView<>();
        configurarTabela();

        // Layout para os componentes
        layout.getChildren().addAll(
                lblPaciente, cbPaciente,
                lblMedico, cbMedico,
                lblStatus, cbStatus,
                lblData, txtData,
                btnAdicionar, tabelaConsultas
        );

        return layout;
    }

    private void carregarPacientes(ComboBox<PacienteModel> cbPaciente) {
        List<PacienteModel> pacientes = pacienteService.getAllPacientes();
        if (pacientes != null && !pacientes.isEmpty()) {
            cbPaciente.setItems(FXCollections.observableArrayList(pacientes));
            cbPaciente.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(PacienteModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            cbPaciente.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(PacienteModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        }
    }

    private void carregarMedicos(ComboBox<MedicoModel> cbMedico) {
        List<MedicoModel> medicos = medicoService.getAllMedico();
        if (medicos != null && !medicos.isEmpty()) {
            cbMedico.setItems(FXCollections.observableArrayList(medicos));
            cbMedico.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(MedicoModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            cbMedico.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(MedicoModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        }
    }

    private void configurarTabela() {

        TableColumn<ConsultaModel, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<ConsultaModel, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataAgendamento"));

        TableColumn<ConsultaModel, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnDeletar = new Button("Deletar");

            {
                btnEditar.setOnAction(e -> editarConsulta(getIndex()));
                btnDeletar.setOnAction(e -> deletarConsulta(getIndex()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    VBox box = new VBox(10, btnEditar, btnDeletar);
                    setGraphic(box);
                }
            }
        });

        tabelaConsultas.getColumns().addAll(colStatus, colData, colAcoes);
        atualizarTabela();
    }

    private void atualizarTabela() {
        List<ConsultaModel> consultas = consultaService.getAllConsulta();
        tabelaConsultas.setItems(FXCollections.observableArrayList(consultas));
    }

    private void criarConsulta(ComboBox<PacienteModel> cbPaciente, ComboBox<MedicoModel> cbMedico, ComboBox<String> cbStatus, TextField txtData) {
        PacienteModel paciente = cbPaciente.getValue();
        MedicoModel medico = cbMedico.getValue();
        String status = cbStatus.getValue();
        String data = txtData.getText().trim();

        if (paciente != null && medico != null && status != null && !data.isEmpty()) {
            boolean sucesso = consultaService.criarConsulta(paciente.getId(), medico.getId(), data, StatusEnum.valueOf(status.toUpperCase()));
            if (sucesso) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Consulta criada com sucesso!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao criar consulta.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.");
            alert.showAndWait();
        }
    }

    private void editarConsulta(int index) {
        ConsultaModel consulta = tabelaConsultas.getItems().get(index);
        // Pegue os dados da consulta e abra um modal ou tela para edição
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Editar consulta - ID: " + consulta.getIdConsulta());
        alert.showAndWait();
    }

    private void deletarConsulta(int index) {
        ConsultaModel consulta = tabelaConsultas.getItems().get(index);
        boolean sucesso = consultaService.deleteConsulta(consulta.getIdPaciente(), consulta.getIdMedico());
        if (sucesso) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Consulta deletada com sucesso!");
            alert.showAndWait();
            atualizarTabela(); // Atualiza a tabela após exclusão
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao deletar consulta.");
            alert.showAndWait();
        }
    }
}
