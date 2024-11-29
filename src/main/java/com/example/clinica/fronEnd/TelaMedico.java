package com.example.clinica.fronEnd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import com.example.clinica.backend.Services.EspecialidadeService;
import com.example.clinica.backend.Models.EspecialidadeModel;
import java.util.List;

public class TelaMedico {

    private final VBox vbox;

    // Instância do serviço para pegar as especialidades
    private final EspecialidadeService especialidadeService;

    public TelaMedico(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;

        // Criando o Label para o campo de nome
        Label nomeLabel = new Label("Nome do Médico:");

        // Criando o TextField para o nome do médico
        TextField nomeField = new TextField();

        // Criando o Label para a ComboBox de especialidades
        Label especialidadeLabel = new Label("Especialidade:");

        // Criando a ComboBox para as especialidades
        ComboBox<String> especialidadeComboBox = new ComboBox<>();

        // Buscando as especialidades do banco de dados
        ObservableList<String> especialidades = FXCollections.observableArrayList();
        especialidades.addAll(buscarEspecialidadesNoBanco()); // Preenche a ComboBox com os dados do banco

        especialidadeComboBox.setItems(especialidades);

        // Criando um layout VBox (vertical) para organizar os componentes
        vbox = new VBox(10); // 10 é o espaçamento entre os componentes
        vbox.getChildren().addAll(nomeLabel, nomeField, especialidadeLabel, especialidadeComboBox);
    }

    // Método para retornar o layout da tela (VBox)
    public Region getTela() {
        return vbox;
    }

    // Método para buscar as especialidades no banco de dados, usando o serviço
    private ObservableList<String> buscarEspecialidadesNoBanco() {
        ObservableList<String> especialidades = FXCollections.observableArrayList();

        // Chama o serviço para pegar todas as especialidades
        List<EspecialidadeModel> especialidadeList = especialidadeService.getAllEspecialidades();

        // Preenche a lista de especialidades na ComboBox
        for (EspecialidadeModel especialidade : especialidadeList) {
            especialidades.add(especialidade.getNome()); // Adiciona o nome da especialidade
        }

        return especialidades;
    }
}
