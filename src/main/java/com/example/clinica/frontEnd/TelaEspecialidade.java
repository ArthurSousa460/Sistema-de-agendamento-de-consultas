package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Services.EspecialidadeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;


import java.util.ArrayList;
import java.util.List;

public class TelaEspecialidade {

    // Instância do serviço de especialidade
    private final EspecialidadeService especialidadeService = new EspecialidadeService();

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

        // Tabela para listar especialidades
        TableView<EspecialidadeModel> tabelaEspecialidades = new TableView<>();
        ObservableList<EspecialidadeModel> especialidadesData = FXCollections.observableArrayList();

        TableColumn<EspecialidadeModel, String> colId = new TableColumn<>("ID");
        TableColumn<EspecialidadeModel, String> colNome = new TableColumn<>("Nome");

        // Configurando as colunas (substitua getId e getNome pelos métodos corretos de EspecialidadeModel)
        //colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        //colNome.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));

        tabelaEspecialidades.getColumns().addAll(colId, colNome);

        // Adiciona as especialidades existentes à tabela
        especialidadesData.addAll(getEspecialidades());
        tabelaEspecialidades.setItems(especialidadesData);

        // Evento de clique no botão
        btnAdicionar.setOnAction(e -> {
            String nomeEspecialidade = txtNome.getText().trim();

            if (!nomeEspecialidade.isEmpty()) {
                boolean sucesso = especialidadeService.criarEspecialidade(nomeEspecialidade);

                if (sucesso) {
                    // Atualiza a tabela com a nova especialidade
                    EspecialidadeModel novaEspecialidade = new EspecialidadeModel(nomeEspecialidade); // Ajuste conforme o seu modelo
                    novaEspecialidade.setNome(nomeEspecialidade);
                    especialidadesData.add(novaEspecialidade);

                    // Limpa o campo de texto
                    txtNome.clear();

                    // Mensagem de sucesso
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Especialidade adicionada com sucesso!");
                    alert.showAndWait();
                } else {
                    // Mensagem de erro
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Erro ao adicionar especialidade.");
                    alert.showAndWait();
                }
            } else {
                // Campo vazio
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, insira um nome para a especialidade.");
                alert.showAndWait();
            }
        });

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, btnAdicionar, tabelaEspecialidades);
        return layout;
    }

    public List<EspecialidadeModel> getEspecialidades() {
        // Obtém as especialidades do banco de dados
        List<EspecialidadeModel> especialidades = especialidadeService.getAllEspecialidades();
        return especialidades != null ? especialidades : new ArrayList<>();
    }
}
