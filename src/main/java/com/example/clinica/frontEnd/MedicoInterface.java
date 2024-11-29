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
import javafx.scene.layout.VBox;

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

        tableMedicoView.getColumns().addAll(colId, colNome, colEspecialidade);
        atualizarTabelaMedicos();
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
