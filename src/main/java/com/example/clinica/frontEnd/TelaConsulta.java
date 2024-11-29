package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.ConsultaModel;
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

    // Instâncias dos serviços
    private final ConsultaService consultaService = new ConsultaService();
    private final MedicoService medicoService = new MedicoService();
    private final PacienteService pacienteService = new PacienteService();

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de seleção para o nome do médico
        Label lblMedico = new Label("Nome do Médico:");
        ComboBox<MedicoModel> cbMedico = new ComboBox<>();
        cbMedico.setItems(FXCollections.observableArrayList(getMedicos()));

        // Campo de seleção para o nome do paciente
        Label lblPaciente = new Label("Nome do Paciente:");
        ComboBox<PacienteModel> cbPaciente = new ComboBox<>();
        cbPaciente.setItems(FXCollections.observableArrayList(getPacientes()));

        // Campo de seleção para o status
        Label lblStatus = new Label("Status da Consulta:");
        ComboBox<StatusEnum> cbStatus = new ComboBox<>();
        cbStatus.setItems(FXCollections.observableArrayList(StatusEnum.AGENDADO, StatusEnum.CONCLUIDO));

        // Campo de texto para a data de agendamento
        Label lblDataAgendamento = new Label("Data de Agendamento:");
        TextField txtDataAgendamento = new TextField();

        // Botão para adicionar consulta
        Button btnAdicionar = new Button("Adicionar Consulta");

        // Tabela para listar consultas
        TableView<ConsultaModel> tabelaConsultas = new TableView<>();
        ObservableList<ConsultaModel> consultasData = FXCollections.observableArrayList();

        TableColumn<ConsultaModel, Integer> colId = new TableColumn<>("ID");
        TableColumn<ConsultaModel, String> colPaciente = new TableColumn<>("Paciente");
        TableColumn<ConsultaModel, String> colMedico = new TableColumn<>("Médico");
        TableColumn<ConsultaModel, String> colStatus = new TableColumn<>("Status");
        TableColumn<ConsultaModel, String> colData = new TableColumn<>("Data");

        // Configurando as colunas
        //colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        //colPaciente.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPaciente().getNome()));
        //colMedico.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMedico().getNome()));
        //colStatus.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStatus().toString()));
        //colData.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDataAgendamento()));

        tabelaConsultas.getColumns().addAll(colId, colPaciente, colMedico, colStatus, colData);

        // Adiciona as consultas existentes à tabela
        consultasData.addAll(getConsultas());
        tabelaConsultas.setItems(consultasData);

        // Evento de clique no botão
        btnAdicionar.setOnAction(e -> {
            MedicoModel medicoSelecionado = cbMedico.getValue();
            PacienteModel pacienteSelecionado = cbPaciente.getValue();
            StatusEnum statusSelecionado = cbStatus.getValue();
            String dataAgendamento = txtDataAgendamento.getText().trim();

            if (medicoSelecionado != null && pacienteSelecionado != null && statusSelecionado != null && !dataAgendamento.isEmpty()) {
                boolean sucesso = consultaService.criarConsulta(pacienteSelecionado.getId(), medicoSelecionado.getId(), dataAgendamento, statusSelecionado);

                if (sucesso) {
                    // Atualiza a tabela com a nova consulta
                    ConsultaModel novaConsulta = new ConsultaModel(pacienteSelecionado, medicoSelecionado, dataAgendamento, statusSelecionado);
                    consultasData.add(novaConsulta);

                    // Limpa os campos de texto
                    cbMedico.getSelectionModel().clearSelection();
                    cbPaciente.getSelectionModel().clearSelection();
                    cbStatus.getSelectionModel().clearSelection();
                    txtDataAgendamento.clear();

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
                // Mensagem de alerta para campos vazios
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, preencha todos os campos.");
                alert.showAndWait();
            }
        });

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblMedico, cbMedico, lblPaciente, cbPaciente, lblStatus, cbStatus, lblDataAgendamento, txtDataAgendamento, btnAdicionar, tabelaConsultas);
        return layout;
    }

    public List<MedicoModel> getMedicos() {
        // Obtém os médicos do banco de dados
        return medicoService.getAllMedicos();
    }

    public List<PacienteModel> getPacientes() {
        // Obtém os pacientes do banco de dados
        return pacienteService.getAllPacientes();
    }

    public List<ConsultaModel> getConsultas() {
        // Obtém as consultas do banco de dados
        return consultaService.getAllConsulta();
    }
}
