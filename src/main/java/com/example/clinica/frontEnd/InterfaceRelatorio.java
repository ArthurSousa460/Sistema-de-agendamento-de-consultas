package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.RelatorioConsultaModel;
import com.example.clinica.backend.Services.RelatorioConsultaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class InterfaceRelatorio {

    private TableView<RelatorioConsultaModel> tableRelatorioView;
    private RelatorioConsultaService relatorioConsultaService;

    public InterfaceRelatorio() {
        this.tableRelatorioView = new TableView<>();
        this.relatorioConsultaService = new RelatorioConsultaService();
    }

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Botão para carregar relatório
        Button btnCarregarRelatorio = new Button("Carregar Relatório");

        btnCarregarRelatorio.setOnAction(event -> {
            carregarRelatorio();
        });

        // Colunas da tabela de relatório
        TableColumn<RelatorioConsultaModel, Integer> colIdConsulta = new TableColumn<>("ID Consulta");
        colIdConsulta.setCellValueFactory(new PropertyValueFactory<>("id_consulta"));

        TableColumn<RelatorioConsultaModel, String> colNomeMedico = new TableColumn<>("Nome Médico");
        colNomeMedico.setCellValueFactory(new PropertyValueFactory<>("nome_medico"));

        TableColumn<RelatorioConsultaModel, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        TableColumn<RelatorioConsultaModel, String> colNomePaciente = new TableColumn<>("Nome Paciente");
        colNomePaciente.setCellValueFactory(new PropertyValueFactory<>("nome_paciente"));

        TableColumn<RelatorioConsultaModel, String> colDataAgendamento = new TableColumn<>("Data Agendamento");
        colDataAgendamento.setCellValueFactory(new PropertyValueFactory<>("data_agendamento"));

        TableColumn<RelatorioConsultaModel, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        this.tableRelatorioView.getColumns().addAll(colIdConsulta, colNomeMedico, colEspecialidade, colNomePaciente, colDataAgendamento, colStatus);

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(btnCarregarRelatorio, this.tableRelatorioView);

        return layout;
    }

    // Método para carregar o relatório de consultas
    public void carregarRelatorio() {
        List<RelatorioConsultaModel> relatorios = this.relatorioConsultaService.getRelatorio();
        if (relatorios != null && !relatorios.isEmpty()) {
            ObservableList<RelatorioConsultaModel> relatoriosData = FXCollections.observableArrayList(relatorios);
            this.tableRelatorioView.setItems(relatoriosData);
        } else {
            showAlert("Erro", "Não há relatórios disponíveis.");
        }
    }

    // Exibe um alerta de erro
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}