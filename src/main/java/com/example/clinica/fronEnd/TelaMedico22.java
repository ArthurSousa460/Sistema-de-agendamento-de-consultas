package com.example.clinica.fronEnd;

import com.example.clinica.backend.Models.MedicoModel;
import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Services.EspecialidadeService;
import com.example.clinica.backend.Services.MedicoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class TelaMedico22 {

    private TableView<MedicoModel> tabelaMedicos;
    private final MedicoService medicoService;
    private final ObservableList<EspecialidadeModel> especialidades;

    public TelaMedico22() {
        this.medicoService = new MedicoService();
        EspecialidadeService especialidadeService = new EspecialidadeService();
        this.especialidades = FXCollections.observableArrayList(especialidadeService.getAllEspecialidades());
    }

    public VBox getTela() {
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(15);

        // Campo para nome do médico
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do Médico");


        ComboBox<EspecialidadeModel> especialidadeBox = getEspecialidadeModelComboBox();

        // Botão de adicionar médico
        Button btnAdicionar = new Button("Adicionar Médico");
        btnAdicionar.setOnAction(e -> {
            String nome = nomeField.getText();
            EspecialidadeModel especialidadeSelecionada = especialidadeBox.getValue();

            if (nome.isEmpty() || especialidadeSelecionada == null) {
                mostrarAlerta("Erro", "Por favor, preencha todos os campos.");
            } else {
                boolean sucesso = medicoService.criarMedico(nome, especialidadeSelecionada.getId());
                if (sucesso) {
                    mostrarAlerta("Sucesso", "Médico adicionado com sucesso!");
                    nomeField.clear();
                    especialidadeBox.getSelectionModel().clearSelection();
                    atualizarTabela();
                } else {
                    mostrarAlerta("Erro", "Erro ao adicionar médico.");
                }
            }
        });

        // Criar a tabela
        tabelaMedicos = criarTabelaMedicos();
        atualizarTabela();

        HBox formLayout = new HBox(10, nomeField, especialidadeBox, btnAdicionar);

        mainLayout.getChildren().addAll(formLayout, tabelaMedicos);

        return mainLayout;
    }

    private ComboBox<EspecialidadeModel> getEspecialidadeModelComboBox() {
        ComboBox<EspecialidadeModel> especialidadeBox = new ComboBox<>();
        especialidadeBox.setPromptText("Especialidade");
        especialidadeBox.setItems(especialidades);

        // Configuração para exibir apenas o nome da especialidade no ComboBox
        especialidadeBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EspecialidadeModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
        especialidadeBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(EspecialidadeModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
        return especialidadeBox;
    }

    private TableView<MedicoModel> criarTabelaMedicos() {
        TableView<MedicoModel> table = new TableView<>();

        TableColumn<MedicoModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<MedicoModel, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        TableColumn<MedicoModel, String> especialidadeColumn = new TableColumn<>("Especialidade");
        especialidadeColumn.setCellValueFactory(cellData -> cellData.getValue().especialidadeProperty());

        TableColumn<MedicoModel, Void> actionsColumn = new TableColumn<>("Ações");
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editarButton = new Button("Editar");
            private final Button excluirButton = new Button("Excluir");

            {
                editarButton.setOnAction(e -> {
                    MedicoModel medico = getTableRow().getItem();
                    if (medico != null) {
                        mostrarAlerta("Editar", "Editar médico: " + medico.getNome());
                    }
                });

                excluirButton.setOnAction(e -> {
                    MedicoModel medico = getTableRow().getItem();
                    if (medico != null && medicoService.deleteMedico(medico.getId())) {
                        mostrarAlerta("Sucesso", "Médico excluído com sucesso!");
                        atualizarTabela();
                    } else {
                        mostrarAlerta("Erro", "Erro ao excluir médico.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(editarButton, excluirButton);
                    actionButtons.setSpacing(5);
                    setGraphic(actionButtons);
                }
            }
        });
        Arrays Arrays;
        table.getColumns().addAll(java.util.Arrays.asList(nomeColumn, especialidadeColumn));
        return table;
    }

    private void atualizarTabela() {
        List<MedicoModel> medicos = medicoService.getAllMedico();
        tabelaMedicos.setItems(FXCollections.observableArrayList(medicos));
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}

