package com.ronald.app.prueba;

import com.ronald.app.prueba.presentation.SistemaTareasFx;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PruebaApplication {

    public static void main(String[] args) {
        Application.launch(SistemaTareasFx.class, args);
    }

}
