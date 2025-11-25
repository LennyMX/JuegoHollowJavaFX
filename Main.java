package com.example.juegoplataformafxhollow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game(1920, 1080);
        StackPane root = new StackPane(game.getCanvas());
        Scene scene = new Scene(root);
        game.setupInput(scene);
        primaryStage.setTitle("Juego Plataforma 2D - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
