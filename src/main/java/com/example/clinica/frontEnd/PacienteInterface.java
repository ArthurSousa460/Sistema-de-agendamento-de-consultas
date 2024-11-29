package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.PacienteModel;
import com.example.clinica.backend.Services.PacienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class PacienteInterface {

    // Instância do serviço de pacientes
    private final PacienteService pacienteService = new PacienteService();

    public VBox getScreen() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #F4F4F4;");

        // Campo de texto para o nome do paciente
        Label lblNome = new Label("Nome do paciente:");
        TextField txtNome = new TextField();

        // Campo de texto para a data de nascimento
        Label lblDataNascimento = new Label("Data de Nascimento:");
        TextField txtDataNascimento = new TextField();

        // Botão para adicionar paciente
        Button btnAdicionar = new Button("Adicionar Paciente");

        // Tabela para listar pacientes
        TableView<PacienteModel> tabelaPacientes = new TableView<>();
        ObservableList<PacienteModel> pacientesData = FXCollections.observableArrayList();

        TableColumn<PacienteModel, Integer> colId = new TableColumn<>("ID");
        TableColumn<PacienteModel, String> colNome = new TableColumn<>("Nome");
        TableColumn<PacienteModel, String> colDataNascimento = new TableColumn<>("Data de Nascimento");
        TableColumn<PacienteModel, String> colAcoes = new TableColumn<>("Ações");

        // Configurando as colunas
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        colNome.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNome()));
        colDataNascimento.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDataNascimento()));

        // Coluna de ações (editar e deletar)
        colAcoes.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>("Ações"));
        colAcoes.setCellFactory(column -> new TableCell<PacienteModel, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Criar um painel com os botões de editar e deletar
                    VBox hBox = new VBox(10);
                    Button btnEditar = new Button("Editar");
                    btnEditar.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                    Button btnDeletar = new Button("Deletar");
                    btnDeletar.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                    // Evento para editar o paciente
                    btnEditar.setOnAction(e -> {
                        PacienteModel pacienteSelecionado = getTableRow().getItem();
                        if (pacienteSelecionado != null) {
                            // Exibe os dados do paciente no formulário para edição
                            txtNome.setText(pacienteSelecionado.getNome());
                            txtDataNascimento.setText(pacienteSelecionado.getDataNascimento());

                            // Ao confirmar a edição, atualiza os dados
                            btnAdicionar.setText("Atualizar Paciente");
                            btnAdicionar.setOnAction(event -> {
                                String nomePaciente = txtNome.getText().trim();
                                String dataNascimentoPaciente = txtDataNascimento.getText().trim();

                                if (!nomePaciente.isEmpty() && !dataNascimentoPaciente.isEmpty()) {
                                    boolean sucesso = pacienteService.updatePaciente(pacienteSelecionado.getId(), nomePaciente, dataNascimentoPaciente);

                                    if (sucesso) {
                                        pacienteSelecionado.setNome(nomePaciente);
                                        pacienteSelecionado.setDataNascimento(dataNascimentoPaciente);
                                        tabelaPacientes.refresh();

                                        // Limpa os campos de texto
                                        txtNome.clear();
                                        txtDataNascimento.clear();
                                        btnAdicionar.setText("Adicionar Paciente");

                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Sucesso");
                                        alert.setContentText("Paciente atualizado com sucesso.");
                                        alert.showAndWait();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Erro");
                                        alert.setContentText("Erro ao atualizar paciente.");
                                        alert.showAndWait();
                                    }
                                }
                            });
                        }
                    });

                    // Evento para deletar o paciente
                    btnDeletar.setOnAction(e -> {
                        PacienteModel pacienteSelecionado = getTableRow().getItem();
                        if (pacienteSelecionado != null) {
                            boolean sucesso = pacienteService.deletePaciente(pacienteSelecionado.getId());
                            if (sucesso) {
                                pacientesData.remove(pacienteSelecionado);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Sucesso");
                                alert.setContentText("Paciente excluído com sucesso.");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erro");
                                alert.setContentText("Erro ao excluir paciente.");
                                alert.showAndWait();
                            }
                        }
                    });

                    hBox.getChildren().addAll(btnEditar, btnDeletar);
                    setGraphic(hBox);
                }
            }
        });

        tabelaPacientes.getColumns().addAll(colId, colNome, colDataNascimento, colAcoes);

        // Adiciona os pacientes existentes à tabela
        pacientesData.addAll(getPacientes());
        tabelaPacientes.setItems(pacientesData);

        // Evento de clique no botão para adicionar novo paciente
        btnAdicionar.setOnAction(e -> {
            String nomePaciente = txtNome.getText().trim();
            String dataNascimentoPaciente = txtDataNascimento.getText().trim();

            if (!nomePaciente.isEmpty() && !dataNascimentoPaciente.isEmpty()) {
                boolean sucesso = pacienteService.criarPaciente(nomePaciente, dataNascimentoPaciente);

                if (sucesso) {
                    // Atualiza a tabela com o novo paciente
                    PacienteModel novoPaciente = new PacienteModel(nomePaciente, dataNascimentoPaciente);
                    pacientesData.add(novoPaciente);

                    // Limpa os campos de texto
                    txtNome.clear();
                    txtDataNascimento.clear();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setContentText("Paciente adicionado com sucesso!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setContentText("Erro ao adicionar paciente.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setContentText("Por favor, preencha todos os campos.");
                alert.showAndWait();
            }
        });

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, lblDataNascimento, txtDataNascimento, btnAdicionar, tabelaPacientes);
        return layout;
    }

    public List<PacienteModel> getPacientes() {
        // Obtém os pacientes do banco de dados
        List<PacienteModel> pacientes = pacienteService.getAllPacientes();
        return pacientes != null ? pacientes : new ArrayList<>();
    }
}
