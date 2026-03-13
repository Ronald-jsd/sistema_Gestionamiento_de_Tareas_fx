package com.ronald.app.prueba.repository;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TareaRepositoryTest {

    @Autowired
    private TareaRepository tareaRepository;
    @Test
    void  CountByEstado(){
        Tarea tarea;
        for (int i = 1; i<=5; i ++) {
             tarea = new Tarea(null,
                    "tarea "+i,
                    "Persona " +i,
                    Estado.PENDIENTE);
            tareaRepository.save(tarea);
        }

        Long total = tareaRepository.countByEstado(Estado.PENDIENTE);
        assertNotNull(total);
        assertEquals(5, total);
    }
}