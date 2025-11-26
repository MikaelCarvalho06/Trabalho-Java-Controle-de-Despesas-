package com.despesas.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.despesas.util.DatabaseHelper;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DatabaseHelper.initializeDatabase();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/despesas/view/TelaPrincipal.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/com/despesas/view/dark-theme.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Controle de Despesas - Dark");
        stage.setWidth(900);
        stage.setHeight(600);
        stage.show();
    }
    public static void main(String[] args){ launch(args); }
}
