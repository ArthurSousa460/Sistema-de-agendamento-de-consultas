package com.example.clinica;

import com.example.clinica.frontEnd.EspecialidadeInterface;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.clinica.frontEnd.MedicoInterface;


public class ClinicaApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Configuração do menu lateral
        VBox sideMenu = new VBox();
        sideMenu.setPadding(new Insets(20));
        sideMenu.setSpacing(15);
        sideMenu.setStyle("-fx-background-color: #2A2A2A; -fx-pref-width: 200px;");

        Label lblTitle = new Label("Menu");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        Label lblMedicos = createMenuLabel("Médicos");
        Label lblPacientes = createMenuLabel("Pacientes");
        Label lblConsultas = createMenuLabel("Consultas");
        Label lblTest = createMenuLabel("test");
        Label lblEspecialidades = createMenuLabel("Especialidades");
        sideMenu.getChildren().addAll(lblTitle, lblMedicos, lblPacientes, lblConsultas, lblEspecialidades);

        // Configuração da área de conteúdo
        StackPane contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #F4F4F4; -fx-padding: 20px;");
        Label lblWelcome = new Label("Bem-vindo ao Sistema de Clínica");
        lblWelcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        contentArea.getChildren().add(lblWelcome);


        EspecialidadeInterface telaEspecialidade = new EspecialidadeInterface();
        MedicoInterface telaMedico = new MedicoInterface() ;

        lblMedicos.setOnMouseClicked(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(telaMedico.getScreen());
        });
        lblPacientes.setOnMouseClicked(e -> lblWelcome.setText("Gerenciar Pacientes"));
        lblConsultas.setOnMouseClicked(e -> lblWelcome.setText("Gerenciar Consultas"));

        lblEspecialidades.setOnMouseClicked(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(telaEspecialidade.getScreen());
        });


        // Configuração do BorderPane
        BorderPane root = new BorderPane();
        root.setLeft(sideMenu);  // Adiciona o menu lateral à esquerda
        root.setCenter(contentArea);  // Adiciona a área de conteúdo ao centro

        // Configuração da cena
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema de Clínica");
        primaryStage.show();
    }

    private Label createMenuLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffffff; -fx-cursor: hand;");
        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}