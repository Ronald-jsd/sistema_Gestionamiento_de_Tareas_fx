package com.ronald.app.prueba.presentation;

import com.ronald.app.prueba.PruebaApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SistemaTareasFx extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        this.applicationContext =
                new SpringApplicationBuilder(
                        PruebaApplication.class
                ).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(PruebaApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);

        Scene escena = new Scene(loader.load());
        stage.setScene(escena);
        stage.setTitle("Sistema de tareas");
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

}
