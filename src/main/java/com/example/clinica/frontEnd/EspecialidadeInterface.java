package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Services.EspecialidadeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class EspecialidadeInterface {

    // Instância do serviço de especialidade
    private final EspecialidadeService especialidadeService = new EspecialidadeService();
    private final TableView<EspecialidadeModel> tabelaEspecialidades = new TableView<>();
    private final ObservableList<EspecialidadeModel> especialidadesData = FXCollections.observableArrayList();

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de texto para o nome da especialidade
        Label lblNome = new Label("Nome da especialidade:");
        TextField txtNome = new TextField();

        // Botão para adicionar especialidade
        Button btnAdicionar = new Button("Adicionar Especialidade");

        // Configurar tabela de especialidades
        configurarTabela();

        // Adiciona as especialidades existentes à tabela
        atualizarTabela();

        // Evento de clique no botão para adicionar especialidade
        btnAdicionar.setOnAction(e -> {
            String nomeEspecialidade = txtNome.getText().trim();

            if (!nomeEspecialidade.isEmpty()) {
                boolean sucesso = especialidadeService.criarEspecialidade(nomeEspecialidade);

                if (sucesso) {
                    txtNome.clear();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Especialidade adicionada com sucesso!");
                    atualizarTabela();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao adicionar especialidade.");
                }
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Por favor, insira um nome para a especialidade.");
            }
        });

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, btnAdicionar, tabelaEspecialidades);
        return layout;
    }

    private void configurarTabela() {
        TableColumn<EspecialidadeModel, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<EspecialidadeModel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<EspecialidadeModel, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnDeletar = new Button("Deletar");

            {
                btnDeletar.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                btnDeletar.setOnAction(event -> {
                    EspecialidadeModel especialidade = getTableView().getItems().get(getIndex());
                    boolean sucesso = especialidadeService.deleteEspecialidade(especialidade.getId());
                    if (sucesso) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Especialidade deletada com sucesso!");
                        atualizarTabela();
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao deletar especialidade.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDeletar);
                }
            }
        });

        tabelaEspecialidades.getColumns().addAll(colId, colNome, colAcoes);
    }

    private void atualizarTabela() {
        List<EspecialidadeModel> especialidades = especialidadeService.getAllEspecialidades();
        if (especialidades != null && !especialidades.isEmpty()) {
            especialidadesData.setAll(especialidades);
            tabelaEspecialidades.setItems(especialidadesData);
        } else {
            tabelaEspecialidades.getItems().clear();
            System.out.println("Nenhuma especialidade cadastrada!");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
