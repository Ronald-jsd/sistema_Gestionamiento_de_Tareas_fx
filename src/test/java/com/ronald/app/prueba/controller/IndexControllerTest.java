package com.ronald.app.prueba.controller;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import com.ronald.app.prueba.service.ITareaService;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de IndexControlador")
class IndexControllerTest {

    @Mock
    private ITareaService iTareaService;

    @InjectMocks
    private IndexController controlador = Mockito.spy(new IndexController());

    @BeforeAll
    static void inicializarJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
        }
    }

    @BeforeEach
    void configurarComponentes() {

        doNothing().when(controlador).mostrarMensaje(any(), any());

        ReflectionTestUtils.setField(controlador, "tareaTabla", new TableView<>());
        ReflectionTestUtils.setField(controlador, "tareaTexto", new TextField());
        ReflectionTestUtils.setField(controlador, "responsableTexto", new TextField());
        ReflectionTestUtils.setField(controlador, "estadoCBX", new ComboBox<>());

        ReflectionTestUtils.setField(controlador, "idTareaColumna", new TableColumn<>());
        ReflectionTestUtils.setField(controlador, "nombreTareaColumna", new TableColumn<>());
        ReflectionTestUtils.setField(controlador, "responsableColumna", new TableColumn<>());
        ReflectionTestUtils.setField(controlador, "estadoColumna", new TableColumn<>());

        ReflectionTestUtils.setField(controlador, "iTareaService", iTareaService);
    }

    @Nested
    @DisplayName("Inicialización")
    class InicializacionTests {

        @Test
        @DisplayName("Debe configurar componentes y listar tareas")
        void initializeDebeConfigurarYListarTareas() {

            when(iTareaService.listarTareas()).thenReturn(List.of());

            controlador.initialize(null, null);

            verify(iTareaService).listarTareas();
        }
    }

    @Nested
    @DisplayName("Listar tareas")
    class ListarTareasTests {

        @Test
        @DisplayName("Debe listar tareas correctamente")
        void listarTareasDebeEjecutarseCorrectamente() {

            List<Tarea> tareas = List.of(new Tarea(1L, "Estudiar", "Ronald", Estado.PENDIENTE));
            when(iTareaService.listarTareas()).thenReturn(tareas);

            controlador.listarTareas();

            verify(iTareaService).listarTareas();
        }
    }

    @Nested
    @DisplayName("Agregar tarea")
    class AgregarTareaTests {

        @Test
        @DisplayName("Debe agregar una tarea correctamente")
        void agregarTareaDebeGuardarCorrectamente() throws Exception {

            CountDownLatch latch = new CountDownLatch(1);

            Platform.runLater(() -> {
                try {
                    TextField tareaTexto = new TextField("Aprender Spring");
                    TextField responsableTexto = new TextField("Ronald");

                    ComboBox<Estado> estado = new ComboBox<>();
                    estado.setValue(Estado.PENDIENTE);

                    ReflectionTestUtils.setField(controlador, "tareaTexto", tareaTexto);
                    ReflectionTestUtils.setField(controlador, "responsableTexto", responsableTexto);
                    ReflectionTestUtils.setField(controlador, "estadoCBX", estado);

                    controlador.agregarTarea();
                } finally {
                    latch.countDown();
                }
            });

            latch.await();

            verify(iTareaService).guardarTarea(any());
        }

        @Test
        @DisplayName("No debe agregar tarea si el nombre está vacío")
        void agregarTareaNoDebeGuardarCuandoNombreVacio() {

            CountDownLatch latch = new CountDownLatch(1);

            Platform.runLater(() -> {
                try {
                    TextField tareaTexto = new TextField("");
                    TextField responsableTexto = new TextField("Ronald");

                    ComboBox<Estado> estado = new ComboBox<>();
                    estado.setValue(Estado.PENDIENTE);

                    ReflectionTestUtils.setField(controlador, "tareaTexto", tareaTexto);
                    ReflectionTestUtils.setField(controlador, "responsableTexto", responsableTexto);
                    ReflectionTestUtils.setField(controlador, "estadoCBX", estado);

                    controlador.agregarTarea();
                } finally {
                    latch.countDown();
                }
            });

            verify(iTareaService, never()).guardarTarea(any());
        }
    }

    @Nested
    @DisplayName("Eliminar tarea")
    class EliminarTareaTests {

        @Test
        @DisplayName("Debe eliminar tarea seleccionada")
        void eliminarBotonDebeEliminarTarea() throws Exception {

            TableView<Tarea> tabla = new TableView<>();
            Tarea tarea = new Tarea(1L, "Test", "Ronald", Estado.PENDIENTE);

            CountDownLatch latch = new CountDownLatch(1);

            Platform.runLater(() -> {
                try {
                    tabla.getItems().add(tarea);
                    tabla.getSelectionModel().select(tarea);

                    ReflectionTestUtils.setField(controlador, "tareaTabla", tabla);

                    controlador.eliminarBoton();
                } finally {
                    latch.countDown();
                }
            });

            latch.await();

            verify(iTareaService).eliminarTarea(1L);
        }

        @Test
        @DisplayName("No debe eliminar cuando la tarea es null")
        void eliminarBotonNoDebeEliminarCuandoTareaNull() throws Exception {

            TableView<Tarea> tabla = new TableView<>();
            Tarea tarea = null;

            CountDownLatch latch = new CountDownLatch(1);

            Platform.runLater(() -> {
                try {
                    tabla.getItems().add(tarea);
                    tabla.getSelectionModel().select(tarea);

                    ReflectionTestUtils.setField(controlador, "tareaTabla", tabla);

                    controlador.eliminarBoton();
                } finally {
                    latch.countDown();
                }
            });

            latch.await();

            verify(iTareaService, never()).eliminarTarea(1L);
        }
    }

    @Nested
    @DisplayName("Editar tarea")
    class EditarTareaTests {

        @Test
        @DisplayName("Debe editar correctamente una tarea")
        void editarBotonDebeActualizarTarea() throws Exception {

            CountDownLatch latch = new CountDownLatch(1);

            Tarea tarea = new Tarea(1L, "Tarea vieja", "Ronald", Estado.PENDIENTE);

            when(iTareaService.buscarTareaPorId(1L)).thenReturn(tarea);

            Platform.runLater(() -> {

                TableView<Tarea> tabla = new TableView<>();
                tabla.getItems().add(tarea);
                tabla.getSelectionModel().select(tarea);

                TextField tareaTexto = new TextField("Tarea modificada");
                TextField responsableTexto = new TextField("Ronald");

                ComboBox<Estado> estadoCBX = new ComboBox<>();
                estadoCBX.setValue(Estado.EN_PROCESO);

                ReflectionTestUtils.setField(controlador, "tareaTabla", tabla);
                ReflectionTestUtils.setField(controlador, "tareaTexto", tareaTexto);
                ReflectionTestUtils.setField(controlador, "responsableTexto", responsableTexto);
                ReflectionTestUtils.setField(controlador, "estadoCBX", estadoCBX);

                controlador.editarBoton();

                latch.countDown();
            });

            latch.await();

            verify(iTareaService).buscarTareaPorId(1L);
            verify(iTareaService).guardarTarea(any());
        }

        @Test
        @DisplayName("No debe editar cuando la tarea es null")
        void editarBotonNoDebeEditarCuandoTareaNull() throws Exception {

            CountDownLatch latch = new CountDownLatch(1);

            Tarea tarea = null;

            Platform.runLater(() -> {

                TableView<Tarea> tabla = new TableView<>();
                tabla.getItems().add(tarea);
                tabla.getSelectionModel().select(tarea);

                controlador.editarBoton();

                latch.countDown();
            });

            latch.await();

            verify(iTareaService, never()).buscarTareaPorId(1L);
            verify(iTareaService, never()).guardarTarea(any());
        }
    }

    @Nested
    @DisplayName("Contadores de tareas")
    class ContadoresTareasTests {

        @Test
        @DisplayName("Debe mostrar correctamente las cantidades de tareas")
        void mostrarCantidadTareasPorEstado() {

            when(iTareaService.cantidadTareasByEstado(Estado.PENDIENTE)).thenReturn(5L);
            when(iTareaService.cantidadTareasByEstado(Estado.EN_PROCESO)).thenReturn(3L);
            when(iTareaService.cantidadTareasByEstado(Estado.FINALIZADO)).thenReturn(7L);
            when(iTareaService.cantidadTareasByEstado(null)).thenReturn(15L);

            controlador.mostrarTareasPendientesBtn();
            verify(controlador).mostrarMensaje("Aviso", "Cantidad tareas pendientes: 5");

            controlador.mostrarTareasEnProcesoBtn();
            verify(controlador).mostrarMensaje("Aviso", "Cantidad tareas en proceso: 3");

            controlador.mostrarTareasFinalizadasBtn();
            verify(controlador).mostrarMensaje("Aviso", "Cantidad tareas finalizadas: 7");

            controlador.mostrarTareasTotalesBtn();
            verify(controlador).mostrarMensaje("Aviso", "Cantidad total de tareas: 15");
        }
    }
}