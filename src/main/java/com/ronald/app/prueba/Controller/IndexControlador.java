package com.ronald.app.prueba.Controller;

import com.ronald.app.prueba.model.Estado;
import com.ronald.app.prueba.model.Tarea;
import com.ronald.app.prueba.service.ITareaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexControlador implements Initializable {


    private static final Logger logger =
            LoggerFactory.getLogger(IndexControlador.class);

    private final ObservableList<Tarea> tareaObservableList =
            FXCollections.observableArrayList();


    @Autowired
    private ITareaService iTareaService;

    @FXML
    private TableView<Tarea> tareaTabla;

    @FXML
    private TableColumn<Tarea, Long> idTareaColumna;

    @FXML
    private TableColumn<Tarea, String> nombreTareaColumna;

    @FXML
    private TableColumn<Tarea, String> responsableColumna;

    @FXML
    private TableColumn<Tarea, String> estadoColumna;

    @FXML
    private TextField tareaTexto;

    @FXML
    private TextField responsableTexto;

    @FXML
    private ComboBox<Estado> estadoCBX;

    private Long idTareaInterno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        configurarComboBox();
        listarTareas();
    }

    private void configurarColumnas() {
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreTareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estadoColumna.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getEstado().getDescripcion()
                )
        );

    }

    private void configurarComboBox() {
        estadoCBX.setItems(
                FXCollections.observableArrayList(Estado.values())
        );
        estadoCBX.setPromptText("Seleccionar");
        estadoCBX.setValue(null);
    }

    private void listarTareas() {
        logger.info("Ejecutando listado de tareas");

        tareaObservableList.clear();
        tareaObservableList.addAll(iTareaService.listarTareas());
        tareaTabla.setItems(tareaObservableList);
    }

    public void agregarTarea(ActionEvent actionEvent) {
        if (tareaTexto.getText().isBlank()) {
            mostrarMensaje("Error de validación",
                    "Debe proporcionar una tarea.");
            tareaTexto.requestFocus();
            return;
        }

        Tarea tarea = recolectarDatosFormulario(new Tarea());
        guardar(tarea, "Tarea guardada correctamente.");
    }

    public void editarBoton(ActionEvent actionEvent) {
        Tarea tareaSeleccionada =
                tareaTabla.getSelectionModel().getSelectedItem();

        if (tareaSeleccionada == null) {
            mostrarMensaje("Error",
                    "Debe seleccionar una tarea para editar.");
            return;
        }

        Tarea tareaDB =
                iTareaService.buscarTareaPorId(tareaSeleccionada.getId());

        Tarea tareaModificada =
                recolectarDatosFormulario(tareaDB);

        guardar(tareaModificada,
                "Tarea modificada correctamente.");
    }

    public void eliminarBoton(ActionEvent actionEvent) {
        Tarea tarea =
                tareaTabla.getSelectionModel().getSelectedItem();

        if (tarea == null) {
            mostrarMensaje("Error",
                    "Debe seleccionar una tarea para eliminar.");
            return;
        }

        logger.info("Registro a eliminar: " + tarea);

        iTareaService.eliminarTarea(tarea.getId());

        mostrarMensaje("Información",
                "Tarea eliminada correctamente.");

        limpiarFormulario();
        listarTareas();
    }

    public void cargarTareaFormulario(MouseEvent mouseEvent) {
        Tarea tarea =
                tareaTabla.getSelectionModel().getSelectedItem();

        if (tarea != null) {
            idTareaInterno = tarea.getId();
            tareaTexto.setText(tarea.getNombreTarea());
            responsableTexto.setText(tarea.getResponsable());
            estadoCBX.setValue((tarea.getEstado()));
        }
    }

    private Tarea recolectarDatosFormulario(Tarea tarea) {
        if (tareaTexto.getText().isBlank()
                || responsableTexto.getText().isBlank()
                || estadoCBX.getValue() == null) {
            mostrarMensaje("Información",
                    "Debe completar todos los campos.");
            return tarea;
        }

        tarea.setNombreTarea(tareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstado(estadoCBX.getValue());
        return tarea;
    }

    private void guardar(Tarea tarea, String mensaje) {
        iTareaService.guardarTarea(tarea);

        mostrarMensaje("Información", mensaje);
        limpiarFormulario();
        listarTareas();
    }

    private void limpiarFormulario() {
        tareaTexto.clear();
        responsableTexto.clear();

        estadoCBX.setValue(null);
        estadoCBX.getSelectionModel().clearSelection();
        idTareaInterno = null;
    }

    public void limpiarFormulario(ActionEvent actionEvent) {
        limpiarFormulario();
    }

    public void mostrarTareasPendientesBtn(ActionEvent actionEvent) {
        Long c = this.iTareaService.cantidadTareasByEstado(Estado.PENDIENTE);
        mostrarMensaje("Aviso", "Cantidad tareas pendientes: " + c);
    }

    public void mostrarTareasEnProcesoBtn(ActionEvent actionEvent) {
        Long c = this.iTareaService.cantidadTareasByEstado(Estado.EN_PROCESO);
        mostrarMensaje("Aviso", "Cantidad tareas en proceso: " + c);
    }

    public void mostrarTareasFinalizadasBtn(ActionEvent actionEvent) {
        Long c = this.iTareaService.cantidadTareasByEstado(Estado.FINALIZADO);
        mostrarMensaje("Aviso", "Cantidad tareas finalizadas: " + c);
    }

    public void mostrarTareasTotalesBtn(ActionEvent actionEvent) {
        Long c = this.iTareaService.cantidadTareasByEstado(null);
        mostrarMensaje("Aviso", "Cantidad total de tareas: " + c);
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
