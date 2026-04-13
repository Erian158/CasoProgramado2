/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.controller;

import com.mycompany.caso2.client.ClientConnector;
import com.mycompany.caso2.client.Request;
import com.mycompany.caso2.client.Response;
import com.mycompany.caso2.model.Almacen;
import com.mycompany.caso2.view.AlmacenView;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eryan
 */
public class AlmacenController {

    private AlmacenView almacenView;
    private String host = "localhost";
    private int port = 5555;

    public AlmacenController(AlmacenView AlmacenView) {
        this.almacenView = AlmacenView;
        initActions();
        loadAlmacenes();
    }

    private void initActions() {
        this.almacenView.btnInsertar.addActionListener(e -> doInsertar());
        this.almacenView.btnActualizar.addActionListener(e -> doActualizar());
        this.almacenView.btnEliminar.addActionListener(e -> doBorrar());
        this.almacenView.btnConsultar.addActionListener(e -> loadAlmacenes());
        //aqui es para que busque por el boton por codigo y luego por lugar
        this.almacenView.btnBuscarCodigo.addActionListener(e -> doBuscarPorCodigo());
        this.almacenView.btnBuscarLugar.addActionListener(e -> doBuscarPorLugar());

        //para poder cargar los datos en el formulario
        this.almacenView.tblAlmacenes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAlmacenSeleccionado();
            }
        });
    }

    private void doInsertar() {
        String lugar = almacenView.txtLugar.getText().trim();
        String capTexto = almacenView.txtCapacidad.getText().trim();
        int capacidad;
        try {
            capacidad = Integer.parseInt(capTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(almacenView,
                    "¡¡La capacidad debe ser un número entero.!!",
                    "*-* Escribe el Almacén, porfavor *-*",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("insertAlmacen");

            Map<String, Object> p = new HashMap<>();
            p.put("lugar", lugar);
            p.put("capacidad", capacidad);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);
            JOptionPane.showMessageDialog(almacenView, resp.getMessage());

            if (resp.isSuccess()) {
                limpiarcampos();
                loadAlmacenes();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(almacenView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void limpiarcampos() {
        almacenView.txtCodigo.setText("");
        almacenView.txtLugar.setText("");
        almacenView.txtCapacidad.setText("");
        almacenView.tblAlmacenes.clearSelection();
    }

    private void doActualizar() {
        int selectedRow = almacenView.tblAlmacenes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(almacenView,
                    "Seleccione un almacén de la tabla.",
                    "Actualizar Almacén",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer codigo = (Integer) almacenView.tblAlmacenes.getValueAt(selectedRow, 0);
        String lugar = almacenView.txtLugar.getText().trim();
        String capTexto = almacenView.txtCapacidad.getText().trim();

        if (lugar.isEmpty() || capTexto.isEmpty()) {
            JOptionPane.showMessageDialog(almacenView,
                    "Lugar y Capacidad son obligatorios.",
                    "Actualizar Almacén",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(almacenView,
                    "La capacidad debe ser un número entero.Escribelo de nuevo-*-",
                    "Actualizar Almacén",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("updateAlmacen");

            Map<String, Object> p = new HashMap<>();
            p.put("codigo", codigo);
            p.put("lugar", lugar);
            p.put("capacidad", capacidad);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess()) {
                JOptionPane.showMessageDialog(almacenView,
                        resp.getMessage(),
                        "Actualizar Almacén",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarcampos();
                loadAlmacenes();
            } else {
                JOptionPane.showMessageDialog(almacenView,
                        resp.getMessage(),
                        "Actualizar Almacén",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(almacenView,
                    "Error de conexión: " + ex.getMessage(),
                    "Actualizar Almacén",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doBorrar() {
        int selectedRow = almacenView.tblAlmacenes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(almacenView,
                    "Seleccione un almacén de la tabla.",
                    "Eliminar Almacén",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(almacenView,
                "¿Estás seguro de eliminar este almacén?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Integer codigo = (Integer) almacenView.tblAlmacenes.getValueAt(selectedRow, 0);

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("deleteAlmacen");

            Map<String, Object> p = new HashMap<>();
            p.put("codigo", codigo);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);
            JOptionPane.showMessageDialog(almacenView, resp.getMessage());

            if (resp.isSuccess()) {
                limpiarcampos();
                loadAlmacenes();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(almacenView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void doBuscarPorCodigo() {
        String codigoTexto = almacenView.txtCodigo.getText().trim();

        if (codigoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(almacenView,
                    "Ingrese un código para buscar.",
                    "Buscar Almacén",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(almacenView,
                    "El código debe ser un número entero.",
                    "Buscar Almacén",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("getAlmacenByCodigo");

            Map<String, Object> p = new HashMap<>();
            p.put("codigo", codigo);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess() && resp.getData() != null) {
                Almacen a = (Almacen) resp.getData();
                almacenView.txtLugar.setText(a.getLugar());
                almacenView.txtCapacidad.setText(String.valueOf(a.getCapacidad()));

                // Seleccionar la fila correspondiente en la tabla
                seleccionarFilaPorCodigo(a.getCodigo());
            } else {
                JOptionPane.showMessageDialog(almacenView,
                        resp.getMessage(),
                        "Buscar Almacén",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(almacenView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void doBuscarPorLugar() {
        String lugar = almacenView.txtLugar.getText().trim();

        if (lugar.isEmpty()) {
            JOptionPane.showMessageDialog(almacenView,
                    "Ingrese un lugar para buscar.",
                    "Buscar Almacén",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("getAlmacenByLugar");

            Map<String, Object> p = new HashMap<>();
            p.put("lugar", lugar);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess() && resp.getData() != null) {
                Almacen a = (Almacen) resp.getData();
                almacenView.txtCodigo.setText(String.valueOf(a.getCodigo()));
                almacenView.txtCapacidad.setText(String.valueOf(a.getCapacidad()));

                seleccionarFilaPorCodigo(a.getCodigo());
            } else {
                JOptionPane.showMessageDialog(almacenView,
                        resp.getMessage(),
                        "Buscar Almacén",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(almacenView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void loadAlmacenes() {
        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("listAlmacenes");
            r.setPayload(new HashMap<>());

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess() && resp.getData() != null) {
                List<Almacen> almacenes = (List<Almacen>) resp.getData();
                updateTable(almacenes);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(almacenView,
                    "Error al cargar almacenes: " + ex.getMessage());
        }
    }

    private void updateTable(List<Almacen> almacenes) {
        DefaultTableModel model = (DefaultTableModel) almacenView.tblAlmacenes.getModel();
        model.setRowCount(0); // limpiar antes de llenar

        for (Almacen a : almacenes) {
            model.addRow(new Object[]{
                a.getCodigo(),
                a.getLugar(),
                a.getCapacidad()
            });
        }
    }

    private void cargarAlmacenSeleccionado() {
        int selectedRow = almacenView.tblAlmacenes.getSelectedRow();
        if (selectedRow != -1) {
            almacenView.txtCodigo.setText(
                    String.valueOf(almacenView.tblAlmacenes.getValueAt(selectedRow, 0)));
            almacenView.txtLugar.setText(
                    (String) almacenView.tblAlmacenes.getValueAt(selectedRow, 1));
            almacenView.txtCapacidad.setText(
                    String.valueOf(almacenView.tblAlmacenes.getValueAt(selectedRow, 2)));
        }
    }
    
      private void seleccionarFilaPorCodigo(Integer codigo) {
        for (int i = 0; i < almacenView.tblAlmacenes.getRowCount(); i++) {
            Object valor = almacenView.tblAlmacenes.getValueAt(i, 0);
            if (valor != null && valor.equals(codigo)) {
                almacenView.tblAlmacenes.setRowSelectionInterval(i, i);
                almacenView.tblAlmacenes.scrollRectToVisible(
                        almacenView.tblAlmacenes.getCellRect(i, 0, true));
                return;
            }
        }
    }

}
