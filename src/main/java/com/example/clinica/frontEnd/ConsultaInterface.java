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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        TableColumn<ConsultaModel, String> colIdConsulta = new TableColumn<>("consulta_id");
        colIdConsulta.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));

        TableColumn<ConsultaModel, String> colMedicoId = new TableColumn<>("medico_id");
        colMedicoId.setCellValueFactory(new PropertyValueFactory<>("idMedico"));

        TableColumn<ConsultaModel, String> colPacienteId = new TableColumn<>("paciente_id");
        colMedicoId.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));

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

        tabelaConsultas.getColumns().addAll(colIdConsulta, colMedicoId,colPacienteId ,colStatus, colData, colAcoes);
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

        // Cria a janela de edição
        Stage editarStage = new Stage();
        editarStage.setTitle("Editar Consulta");

        // Layout da janela
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #F4F4F4;");

        // Campos de texto para editar os dados da consulta
        Label lblStatus = new Label("Status:");
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("Agendado", "Concluido");
        cbStatus.setValue(consulta.getStatus().toString());

        Label lblData = new Label("Data de Agendamento:");
        TextField txtData = new TextField(consulta.getDataAgendamento());

        // Botão para salvar as alterações
        Button btnSalvar = new Button("Salvar Alterações");
        btnSalvar.setOnAction(e -> {
            String novoStatus = cbStatus.getValue();
            String novaData = txtData.getText().trim();

            if (novoStatus != null && !novaData.isEmpty()) {
                boolean sucesso = consultaService.updateConsulta(consulta.getIdPaciente(), consulta.getIdConsulta(), consulta.getIdConsulta(), novaData, StatusEnum.valueOf(novoStatus.toUpperCase()));
                if (sucesso) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Consulta atualizada com sucesso!");
                    alert.showAndWait();
                    editarStage.close();
                    atualizarTabela(); // Atualiza a tabela após a edição
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao atualizar consulta.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.");
                alert.showAndWait();
            }
        });

        // Botão para cancelar a edição
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> editarStage.close());

        // Adiciona os elementos ao layout
        vbox.getChildren().addAll(lblStatus, cbStatus, lblData, txtData, btnSalvar, btnCancelar);

        // Define a cena e exibe a janela
        Scene scene = new Scene(vbox, 300, 250);
        editarStage.setScene(scene);
        editarStage.show();
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
