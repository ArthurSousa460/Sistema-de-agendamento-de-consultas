package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Models.MedicoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import com.example.clinica.backend.Services.EspecialidadeService;
import com.example.clinica.backend.Services.MedicoService;

public class TelaMedico {

    private final MedicoService medicoService = new MedicoService();

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
        ComboBox<EspecialidadeModel> cbEspecialidade = new ComboBox<>();

        // Carregar especialidades do banco de dados
        List<EspecialidadeModel> especialidades = getEspecialidades();
        if (especialidades != null && !especialidades.isEmpty()) {
            cbEspecialidade.setItems(FXCollections.observableArrayList(especialidades));
            // Exibe apenas o nome da especialidade
            cbEspecialidade.setCellFactory(param -> new ListCell<EspecialidadeModel>() {
                @Override
                protected void updateItem(EspecialidadeModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
            cbEspecialidade.setButtonCell(new ListCell<EspecialidadeModel>() {
                @Override
                protected void updateItem(EspecialidadeModel item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome());
                }
            });
        } else {
            cbEspecialidade.setPromptText("Erro ao carregar especialidades");
        }

        // Botão para adicionar médico
        Button btnAdicionar = new Button("Adicionar Médico");

        // Evento de clique no botão
        btnAdicionar.setOnAction(e -> {
            String nomeMedico = txtNome.getText().trim();
            EspecialidadeModel especialidadeSelecionada = cbEspecialidade.getValue();

            if (!nomeMedico.isEmpty() && especialidadeSelecionada != null) {
                // Chama o serviço para criar o médico com o ID da especialidade
                boolean sucesso = medicoService.criarMedico(nomeMedico, especialidadeSelecionada.getId());

                if (sucesso) {
                    // Limpa os campos após adicionar
                    txtNome.clear();
                    cbEspecialidade.getSelectionModel().clearSelection();

                    // Mensagem de sucesso
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Médico adicionado com sucesso!");
                    alert.showAndWait();
                } else {
                    // Mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao adicionar médico.");
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
        layout.getChildren().addAll(lblNome, txtNome, lblEspecialidade, cbEspecialidade, btnAdicionar);
        return layout;
    }

    // Método para carregar especialidades
    public List<EspecialidadeModel> getEspecialidades() {
        EspecialidadeService especialidadeService = new EspecialidadeService();
        return especialidadeService.getAllEspecialidades();
    }
}
