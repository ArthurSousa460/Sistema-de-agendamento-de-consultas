package com.example.clinica.frontEnd;

import com.example.clinica.backend.Models.EspecialidadeModel;
import com.example.clinica.backend.Models.MedicoModel;
import com.example.clinica.backend.Services.MedicoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import com.example.clinica.backend.Services.EspecialidadeService;


public class MedicoInterface {

    private TableView<MedicoModel> tableMedicoView;
    private MedicoService medicoService;


    public MedicoInterface(){
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
        List<EspecialidadeModel> especialidades = new ArrayList<>();
        especialidades = getEspecialidades();
        if (especialidades != null && !especialidades.isEmpty()) {
            for (EspecialidadeModel especialidade : especialidades) {
                cbEspecialidade.getItems().add(especialidade.getNome()); // Adiciona apenas o nome
            }
        } else {
            cbEspecialidade.setPromptText("Erro ao carregar especialidades");
        }

        // Botão para adicionar médico
        Button btnAdicionar = new Button("Adicionar Médico");


        TableColumn<MedicoModel, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<MedicoModel, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));

        TableColumn<MedicoModel, String> colEspecialidade = new TableColumn<>("Especialidade");
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("idEspecialidade"));

        //TableColumn<MedicoModel, String> colAcoes = new TableColumn<>("Ações");


        this.tableMedicoView.getColumns().addAll(colId, colNome, colEspecialidade);
        getAllMedicos();

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblNome, txtNome, lblEspecialidade, cbEspecialidade, btnAdicionar, this.tableMedicoView);
        return layout;

    }

    public List<EspecialidadeModel> getEspecialidades(){
        EspecialidadeService especialidadeService = new EspecialidadeService();
        List<EspecialidadeModel> especialidades = new ArrayList<>();
        especialidades = especialidadeService.getAllEspecialidades();

        return especialidades;
    }

    public void getAllMedicos(){
        List<MedicoModel> medicos = this.medicoService.getAllMedico();
        if(medicos != null){
            this.tableMedicoView.getItems().clear();
            ObservableList<MedicoModel> medicosData = FXCollections.observableArrayList(medicos);
            this.tableMedicoView.setItems(medicosData);
        }else{
            System.out.println("Não medicos cadastrados!");
        }



    }

}