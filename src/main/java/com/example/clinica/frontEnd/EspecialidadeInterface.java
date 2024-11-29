package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Services.EspecialidadeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;


import java.util.ArrayList;
import java.util.List;

public class EspecialidadeInterface {

    private TableView <EspecialidadeModel> tableEspecialidadesView;
    private EspecialidadeService especialidadeService;
    private ObservableList<EspecialidadeModel> especialidadesData;

    public EspecialidadeInterface() {
        this.especialidadeService = new EspecialidadeService();
        this.tableEspecialidadesView = new TableView<>();
    }

    public void setEspecialidadeData() {
        List<EspecialidadeModel> especialidades = this.especialidadeService.getAllEspecialidades();
        this.especialidadesData = FXCollections.observableArrayList(especialidades);
    }

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

        TableColumn<EspecialidadeModel, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TableColumn<EspecialidadeModel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

        // Configurando as colunas (substitua getId e getNome pelos métodos corretos de EspecialidadeModel)
        //colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        //colNome.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));

        this.tableEspecialidadesView.getColumns().addAll(colId, colNome);
        getEspecialidades();


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
        layout.getChildren().addAll(lblNome, txtNome, btnAdicionar, this.tableEspecialidadesView);
        return layout;
    }

    public void getEspecialidades() {
        this.setEspecialidadeData();

        if(this.especialidadesData != null){
            this.tableEspecialidadesView.getItems().clear();

            this.tableEspecialidadesView.setItems(especialidadesData);
        }else{
            System.out.println("Não há especialidades listadas!");
        }
    }
}