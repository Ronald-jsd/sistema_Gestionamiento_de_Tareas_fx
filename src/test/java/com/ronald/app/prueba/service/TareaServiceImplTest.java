package com.ronald.app.prueba.service;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import com.ronald.app.prueba.repository.TareaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de TareaServiceImpl")
class TareaServiceImplTest {

    @InjectMocks
    private TareaServiceImpl tareaService;

    @Mock
    private TareaRepository tareaRepository;

    @Nested
    @DisplayName("Listar tareas")
    class ListarTareasTests {

        @Test
        @DisplayName("Debe listar tareas correctamente")
        void listarTareasDebeRetornarLista() {

            Tarea tarea = new Tarea(1L,
                    "Resumir microservicios",
                    "Aldair",
                    Estado.PENDIENTE);

            when(tareaRepository.findAll()).thenReturn(List.of(tarea));

            List<Tarea> tareasEncontradas = tareaService.listarTareas();

            assertNotNull(tareasEncontradas);
            assertEquals(1, tareasEncontradas.size());
            assertEquals(Estado.PENDIENTE, tareasEncontradas.get(0).getEstado());
        }
    }

    @Nested
    @DisplayName("Buscar tarea por id")
    class BuscarTareaTests {

        @Test
        @DisplayName("Debe encontrar tarea por id")
        void buscarTareaPorIdDebeRetornarTarea() {

            Tarea tarea = new Tarea(1L,
                    "Resumir microservicios",
                    "Aldair",
                    Estado.PENDIENTE);

            when(tareaRepository.findById(1L))
                    .thenReturn(Optional.of(tarea));

            Tarea tareaEncontrada = tareaService.buscarTareaPorId(1L);

            assertNotNull(tareaEncontrada);
            assertEquals("Resumir microservicios", tareaEncontrada.getNombreTarea());
            assertEquals("Pendiente", Estado.PENDIENTE.getDescripcion());

            verify(tareaRepository).findById(1L);
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando la tarea no existe")
        void buscarTareaPorIdDebeLanzarExcepcion() {

            when(tareaRepository.findById(1L))
                    .thenReturn(Optional.empty());

            Throwable throwable = assertThrows(
                    EntityNotFoundException.class,
                    () -> tareaService.buscarTareaPorId(1L)
            );

            assertEquals("Entidad no encontrada con id: 1", throwable.getMessage());

            verify(tareaRepository).findById(1L);
        }
    }

    @Nested
    @DisplayName("Guardar tarea")
    class GuardarTareaTests {

        @Test
        @DisplayName("Debe guardar tarea correctamente")
        void guardarTareaDebePersistirEntidad() {

            Tarea tarea = new Tarea(1L,
                    "Resumir microservicios",
                    "Aldair",
                    Estado.PENDIENTE);

            when(tareaRepository.save(any(Tarea.class)))
                    .thenReturn(tarea);

            Tarea tareaGuardada = tareaService.guardarTarea(tarea);

            assertNotNull(tareaGuardada);
            assertEquals(1L, tareaGuardada.getId());

            verify(tareaRepository).save(any(Tarea.class));
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando la tarea es null")
        void guardarTareaDebeLanzarExcepcionCuandoEsNull() {

            Tarea tarea = null;

            Throwable throwable = assertThrows(
                    NullPointerException.class,
                    () -> tareaService.guardarTarea(tarea)
            );

            assertEquals("Entidad sin atributos", throwable.getMessage());

            verify(tareaRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Eliminar tarea")
    class EliminarTareaTests {

        @Test
        @DisplayName("Debe eliminar tarea existente")
        void eliminarTareaDebeEliminarCorrectamente() {

            Long id = 2L;

            when(tareaRepository.existsById(id))
                    .thenReturn(true);

            tareaService.eliminarTarea(id);

            verify(tareaRepository).existsById(id);
            verify(tareaRepository).deleteById(id);
        }

        @Test
        @DisplayName("Debe lanzar excepción si la tarea no existe")
        void eliminarTareaDebeLanzarExcepcionSiNoExiste() {

            Long id = 2L;

            when(tareaRepository.existsById(id))
                    .thenReturn(false);

            Throwable throwable = assertThrows(
                    EntityNotFoundException.class,
                    () -> tareaService.eliminarTarea(id)
            );

            assertEquals("Tarea no encontrada con id: " + id, throwable.getMessage());

            verify(tareaRepository).existsById(id);
            verify(tareaRepository, never()).deleteById(id);
        }
    }

    @Nested
    @DisplayName("Contar tareas por estado")
    class ContarTareasTests {

        @Test
        @DisplayName("Debe retornar cantidad cuando el estado no es null")
        void cantidadTareasByEstadoDebeUsarCountByEstado() {

            Long cantidad = 4L;
            Estado estado = Estado.PENDIENTE;

            when(tareaRepository.countByEstado(estado))
                    .thenReturn(cantidad);

            Long cantidadRetornada = tareaService.cantidadTareasByEstado(estado);

            assertNotNull(cantidadRetornada);
            assertEquals(cantidad, cantidadRetornada);

            verify(tareaRepository, never()).count();
            verify(tareaRepository).countByEstado(estado);
        }

        @Test
        @DisplayName("Debe retornar total cuando el estado es null")
        void cantidadTareasByEstadoDebeUsarCount() {

            Long cantidad = 3L;

            when(tareaRepository.count()).thenReturn(cantidad);

            Long cantidadRetornada = tareaService.cantidadTareasByEstado(null);

            assertNotNull(cantidadRetornada);
            assertEquals(cantidad, cantidadRetornada);

            verify(tareaRepository).count();
            verify(tareaRepository, never()).countByEstado(null);
        }
    }
}