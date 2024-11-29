package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Models.MedicoModel;
import com.example.clinica.backend.Services.EspecialidadeService;
import com.example.clinica.backend.Services.MedicoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.security.cert.PolicyNode;
import java.util.List;

public class MedicoInterface {

    private TableView<MedicoModel> tableMedicoView;
    private MedicoService medicoService;

    public MedicoInterface() {
        this.tableMedicoView = new TableView<>();
        this.medicoService = new MedicoService();
    }

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
        ComboBox<String> cbEspecialidade = new ComboBox<>();

        // Carregar especialidades do banco de dados
        carregarEspecialidades(cbEspecialidade);

        // Botão para adicionar médico
        Button btnAdicionar = new Button("Adicionar Médico");

        btnAdicionar.setOnAction(event -> {
            String nomeMedico = txtNome.getText().trim();
            String especialidadeNome = cbEspecialidade.getValue();

            if (nomeMedico.isEmpty() || especialidadeNome == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Por favor, preencha todos os campos.");
            } else {
                EspecialidadeModel especialidade = buscarEspecialidadePorNome(especialidadeNome);
                if (especialidade != null) {
                    boolean sucesso = medicoService.criarMedico(nomeMedico, especialidade.getId());
                    if (sucesso) {
                        txtNome.clear();
                        cbEspecialidade.getSelectionModel().clearSelection();
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Médico adicionado com sucesso!");
                        atualizarTabelaMedicos();
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao adicionar médico.");
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Especialidade selecionada é inválida.");
                }
            }
        });

        // Configurar a tabela de médicos
        configurarTabela();

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, lblEspecialidade, cbEspecialidade, btnAdicionar, this.tableMedicoView);
        return layout;
    }

    private void carregarEspecialidades(ComboBox<String> cbEspecialidade) {
        EspecialidadeService especialidadeService = new EspecialidadeService();
        List<EspecialidadeModel> especialidades = especialidadeService.getAllEspecialidades();

        if (especialidades != null && !especialidades.isEmpty()) {
            for (EspecialidadeModel especialidade : especialidades) {
                cbEspecialidade.getItems().add(especialidade.getNome());
            }
        } else {
            cbEspecialidade.setPromptText("Erro ao carregar especialidades");
        }
    }

    private EspecialidadeModel buscarEspecialidadePorNome(String nome) {
        EspecialidadeService especialidadeService = new EspecialidadeService();
        List<EspecialidadeModel> especialidades = especialidadeService.getAllEspecialidades();
        return especialidades.stream()
                .filter(especialidade -> especialidade.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    private void configurarTabela() {
        TableColumn<MedicoModel, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MedicoModel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<MedicoModel, Integer> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("idEspecialidade"));

        TableColumn<MedicoModel, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnDeletar = new Button("Deletar");
            private final Button btnEditar = new Button("Editar");
            private final HBox actionButtons = new HBox(10, btnEditar, btnDeletar); // Agrupar botões

            {
                btnDeletar.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                btnDeletar.setOnAction(event -> {
                    MedicoModel medico = getTableView().getItems().get(getIndex());
                    boolean sucesso = medicoService.deleteMedico(medico.getId());
                    if (sucesso) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Médico deletado com sucesso!");
                        atualizarTabelaMedicos();
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao deletar médico.");
                    }
                });

                btnEditar.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                btnEditar.setOnAction(event -> {
                    MedicoModel medico = getTableView().getItems().get(getIndex());
                    exibirDialogoEditar(medico);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButtons);
                }
            }
        });

        tableMedicoView.getColumns().addAll(colId, colNome, colEspecialidade, colAcoes);
        atualizarTabelaMedicos();
    }

    private void exibirDialogoEditar(MedicoModel medico) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Médico");
        dialog.setHeaderText("Editando: " + medico.getNome());

        // Campo para editar o nome
        TextField txtNovoNome = new TextField(medico.getNome());
        VBox dialogLayout = new VBox(10, new Label("Novo Nome:"), txtNovoNome);
        dialogLayout.setPadding(new Insets(10));

        dialog.getDialogPane().setContent(dialogLayout);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String novoNome = txtNovoNome.getText().trim();
                if (!novoNome.isEmpty()) {
                    boolean sucesso = medicoService.updateMedico(medico.getId(), novoNome);
                    if (sucesso) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Médico atualizado com sucesso!");
                        atualizarTabelaMedicos();
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar médico.");
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "O nome não pode estar vazio.");
                }
            }
        });
    }


    private void atualizarTabelaMedicos() {
        List<MedicoModel> medicos = medicoService.getAllMedico();
        if (medicos != null && !medicos.isEmpty()) {
            ObservableList<MedicoModel> medicosData = FXCollections.observableArrayList(medicos);
            tableMedicoView.setItems(medicosData);
        } else {
            tableMedicoView.getItems().clear();
            System.out.println("Nenhum médico cadastrado!");
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
