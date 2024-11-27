package com.example.clinica.fronEnd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.clinica.backend.Models.MedicoModel;
import com.example.clinica.backend.Services.MedicoService;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TelaMedico {

    private TableView<MedicoModel> tabelaMedicos;
    private MedicoService medicoService;

    // Definindo as especialidades e seus IDs
    private static final Map<Integer, String> especialidadesMap = new HashMap<>();
    static {
        especialidadesMap.put(1, "Cardiologia");
        especialidadesMap.put(2, "Neurologia");
        especialidadesMap.put(3, "Ortopedia");
        especialidadesMap.put(4, "Pediatria");
        especialidadesMap.put(5, "Ginecologia");
    }

    public TelaMedico() {
        this.medicoService = new MedicoService();
    }

    // Método getTela que retorna uma Scene configurada
    public Scene getTela() {
        // Layout principal
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setPadding(new Insets(15));

        // Inicializando a tabela
        tabelaMedicos = criarTabelaMedicos();

        // Campo para nome do médico
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome do médico");

        // ComboBox para especialidades
        ComboBox<String> especialidadeBox = new ComboBox<>();
        especialidadeBox.setPromptText("Selecione uma especialidade");
        carregarEspecialidades(especialidadeBox); // Preenche o ComboBox com especialidades definidas no código

        // Botão para adicionar médico
        Button adicionarButton = new Button("Adicionar Médico");
        adicionarButton.setOnAction(e -> {
            String nome = nomeField.getText();
            String especialidade = especialidadeBox.getValue();

            if (nome.isEmpty() || especialidade == null) {
                mostrarAlerta("Erro", "Por favor, preencha todos os campos!");
            } else {
                boolean sucesso = medicoService.criarMedico(nome, obterIdEspecialidade(especialidade));
                if (sucesso) {
                    mostrarAlerta("Sucesso", "Médico adicionado com sucesso!");
                    nomeField.clear();
                    especialidadeBox.getSelectionModel().clearSelection();
                    atualizarTabela(); // Atualiza a tabela após adicionar
                } else {
                    mostrarAlerta("Erro", "Não foi possível adicionar o médico.");
                }
            }
        });

        // Adiciona os elementos ao layout principal
        mainLayout.getChildren().addAll(
                new HBox(10, nomeField, especialidadeBox, adicionarButton),
                tabelaMedicos
        );

        // Retorna a Scene com o layout configurado
        return new Scene(mainLayout, 800, 600);
    }

    // Criação da tabela de médicos
    private TableView<MedicoModel> criarTabelaMedicos() {
        TableView<MedicoModel> table = new TableView<>();

        TableColumn<MedicoModel, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<MedicoModel, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        TableColumn<MedicoModel, String> especialidadeColumn = new TableColumn<>("Especialidade");
        especialidadeColumn.setCellValueFactory(cellData -> cellData.getValue().especialidadeProperty());

        TableColumn<MedicoModel, Void> actionsColumn = new TableColumn<>("Ações");
        actionsColumn.setCellFactory(param -> new TableCell<MedicoModel, Void>() {
            private final Button editarButton = new Button("Editar");
            private final Button excluirButton = new Button("Excluir");

            {
                editarButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                excluirButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                
                editarButton.setOnAction(e -> editarMedico(getTableRow().getItem()));
                excluirButton.setOnAction(e -> excluirMedico(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(10, editarButton, excluirButton);
                    setGraphic(box);
                }
            }
        });

        table.getColumns().addAll(idColumn, nomeColumn, especialidadeColumn, actionsColumn);

        return table;
    }

    // Carregar especialidades no ComboBox (usando o Map pré-definido)
    private void carregarEspecialidades(ComboBox<String> especialidadeBox) {
        especialidadeBox.setItems(FXCollections.observableArrayList(especialidadesMap.values()));
    }

    // Obter ID da especialidade a partir do nome
    private int obterIdEspecialidade(String especialidade) {
        for (Map.Entry<Integer, String> entry : especialidadesMap.entrySet()) {
            if (entry.getValue().equals(especialidade)) {
                return entry.getKey();
            }
        }
        return -1;  // Retorna -1 se não encontrar a especialidade
    }

    // Atualizar a tabela de médicos
    private void atualizarTabela() {
        List<MedicoModel> medicos = medicoService.getAllMedico();
        tabelaMedicos.setItems(FXCollections.observableArrayList(medicos));
    }

    // Função para mostrar alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Função para editar um médico
    private void editarMedico(MedicoModel medico) {
        mostrarAlerta("Editar", "Editar médico: " + medico.getNome());
    }

    // Função para excluir um médico
    private void excluirMedico(MedicoModel medico) {
        boolean sucesso = medicoService.deleteMedico(medico.getId());
        if (sucesso) {
            mostrarAlerta("Sucesso", "Médico excluído com sucesso!");
            atualizarTabela(); // Atualiza a tabela após excluir
        } else {
            mostrarAlerta("Erro", "Não foi possível excluir o médico.");
        }
    }
}
