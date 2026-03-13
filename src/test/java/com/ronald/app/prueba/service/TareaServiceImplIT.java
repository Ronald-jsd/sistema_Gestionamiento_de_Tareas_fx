package com.ronald.app.prueba.service;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class TareaServiceImplIT {

    @Autowired
    private TareaServiceImpl tareaService;

    @Test
    void shouldSaveAndRetrieveTask() {

        Tarea tarea = new Tarea(
                null,
                "Estudiar microservicios",
                "Ronald",
                Estado.PENDIENTE
        );
        Tarea saved = tareaService.guardarTarea(tarea);

        Tarea found = tareaService.buscarTareaPorId(saved.getId());

        assertEquals("Estudiar microservicios",
                found.getNombreTarea());
    }
}