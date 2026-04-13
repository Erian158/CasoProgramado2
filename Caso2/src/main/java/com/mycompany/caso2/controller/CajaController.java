/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.controller;

import com.mycompany.caso2.client.ClientConnector;
import com.mycompany.caso2.client.Request;
import com.mycompany.caso2.client.Response;
import com.mycompany.caso2.model.Caja;
import com.mycompany.caso2.view.CajaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eryan
 */
public class CajaController {

    private CajaView cajaView;
    private String host = "localhost";
    private int port = 5555;

    public CajaController(CajaView cajaView) {
        this.cajaView = cajaView;
        initActions();
        cargarCajas();
    }

    private void initActions() {
        this.cajaView.btnInsertar.addActionListener(e -> doInsertar());
        this.cajaView.btnActualizar.addActionListener(e -> doActualizar());
        this.cajaView.btnEliminar.addActionListener(e -> doBorrar());
        this.cajaView.btnConsultar.addActionListener(e -> cargarCajas());
        this.cajaView.btnBuscarNumRef.addActionListener(e -> doBuscarPorReferencia());

        // Al seleccionar una fila se cargan los datos en el formulario
        this.cajaView.tblCajas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCajaSeleccionada();
            }
        });
    }

    private void doInsertar() {
        String contenido = cajaView.txtContenido.getText().trim();
        String precioTexto = cajaView.txtPrecio.getText().trim();
        String almacenTexto = cajaView.txtAlmacenCodigo.getText().trim();

        if (contenido.isEmpty() || precioTexto.isEmpty() || almacenTexto.isEmpty()) {
            JOptionPane.showMessageDialog(cajaView,
                    "Contenido, Precio y Código de Almacén son obligatorios.",
                    "Insertar Caja",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int almacenCodigo;
        try {
            precio = Double.parseDouble(precioTexto);
            almacenCodigo = Integer.parseInt(almacenTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cajaView,
                    "Precio debe ser numérico y Código de Almacén debe ser entero.",
                    "Insertar Caja",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("insertCaja");

            Map<String, Object> p = new HashMap<>();
            p.put("contenido", contenido);
            p.put("precio", precio);
            p.put("almacenCodigo", almacenCodigo);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);
            JOptionPane.showMessageDialog(cajaView, resp.getMessage());

            if (resp.isSuccess()) {
                limpiarcampos();
                cargarCajas();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void doActualizar() {
        int selectedRow = cajaView.tblCajas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(cajaView,
                    "Seleccione una caja de la tabla.",
                    "Actualizar Caja",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer numReferencia = (Integer) cajaView.tblCajas.getValueAt(selectedRow, 0);
        String contenido = cajaView.txtContenido.getText().trim();
        String precioTexto = cajaView.txtPrecio.getText().trim();
        String almacenTexto = cajaView.txtAlmacenCodigo.getText().trim();

        if (contenido.isEmpty() || precioTexto.isEmpty() || almacenTexto.isEmpty()) {
            JOptionPane.showMessageDialog(cajaView,
                    "Contenido, Precio y Código de Almacén son obligatorios.",
                    "Actualizar Caja",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int almacenCodigo;
        try {
            precio = Double.parseDouble(precioTexto);
            almacenCodigo = Integer.parseInt(almacenTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cajaView,
                    "Precio debe ser numérico y Código de Almacén debe ser entero.",
                    "Actualizar Caja",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("updateCaja");

            Map<String, Object> p = new HashMap<>();
            p.put("numReferencia", numReferencia);
            p.put("contenido", contenido);
            p.put("precio", precio);
            p.put("almacenCodigo", almacenCodigo);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess()) {
                JOptionPane.showMessageDialog(cajaView,
                        resp.getMessage(),
                        "Actualizar Caja",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarcampos();
                cargarCajas();
            } else {
                JOptionPane.showMessageDialog(cajaView,
                        resp.getMessage(),
                        "Actualizar Caja",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView,
                    "Error de conexión: " + ex.getMessage(),
                    "Actualizar Caja",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doBorrar() {
        int selectedRow = cajaView.tblCajas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(cajaView,
                    "Seleccione una caja de la tabla.",
                    "Eliminar Caja",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(cajaView,
                "¿Está seguro de eliminar esta caja?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Integer numReferencia = (Integer) cajaView.tblCajas.getValueAt(selectedRow, 0);

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("deleteCaja");

            Map<String, Object> p = new HashMap<>();
            p.put("numReferencia", numReferencia);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);
            JOptionPane.showMessageDialog(cajaView, resp.getMessage());

            if (resp.isSuccess()) {
                limpiarcampos();
                cargarCajas();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void doBuscarPorReferencia() {
        String refTexto = cajaView.txtNumReferencia.getText().trim();

        if (refTexto.isEmpty()) {
            JOptionPane.showMessageDialog(cajaView,
                    "Ingrese un número de referencia para buscar.",
                    "Buscar Caja",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numReferencia;
        try {
            numReferencia = Integer.parseInt(refTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cajaView,
                    "El número de referencia debe ser un entero.",
                    "Buscar Caja",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("getCajaByReferencia");

            Map<String, Object> p = new HashMap<>();
            p.put("numReferencia", numReferencia);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess() && resp.getData() != null) {
                Caja c = (Caja) resp.getData();
                cajaView.txtContenido.setText(c.getContenido());
                cajaView.txtPrecio.setText(String.valueOf(c.getPrecio()));
                cajaView.txtAlmacenCodigo.setText(String.valueOf(c.getAlmacenCodigo()));

                seleccionarFilaPorReferencia(c.getNumReferencia());
            } else {
                JOptionPane.showMessageDialog(cajaView,
                        resp.getMessage(),
                        "Buscar Caja",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView,
                    "Error de conexión: " + ex.getMessage());
        }
    }

    private void doBuscarPorAlmacen() {
        String almacenTexto = cajaView.txtAlmacenCodigo.getText().trim();

        if (almacenTexto.isEmpty()) {
            JOptionPane.showMessageDialog(cajaView,
                    "Ingrese un código de almacén para buscar.",
                    "Buscar Caja", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int almacenCodigo;
        try {
            almacenCodigo = Integer.parseInt(almacenTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cajaView,
                    "El código de almacén debe ser un entero.",
                    "Buscar Caja", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("getCajasByAlmacen");
            Map<String, Object> p = new HashMap<>();
            p.put("almacenCodigo", almacenCodigo);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);
            if (resp.isSuccess() && resp.getData() != null) {
                // Un almacén puede tener varias cajas → actualiza toda la tabla
                List<Caja> cajas = (List<Caja>) resp.getData();
                updateTable(cajas);
            } else {
                JOptionPane.showMessageDialog(cajaView, resp.getMessage(),
                        "Buscar Caja", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView, "Error de conexión: " + ex.getMessage());
        }
    }

    private void doBuscarPorContenido() {
        String contenido = cajaView.txtContenido.getText().trim();

        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(cajaView,
                    "Ingrese un contenido para buscar.",
                    "Buscar Caja", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("getCajaByContenido");
            Map<String, Object> p = new HashMap<>();
            p.put("contenido", contenido);
            r.setPayload(p);

            Response resp = conn.sendRequest(r);
            if (resp.isSuccess() && resp.getData() != null) {
                Caja c = (Caja) resp.getData();
                llenarform(c);
                seleccionarFilaPorReferencia(c.getNumReferencia());
            } else {
                JOptionPane.showMessageDialog(cajaView, resp.getMessage(),
                        "Buscar Caja", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView, "Error de conexión: " + ex.getMessage());
        }
    }
    
        private void llenarform(Caja c) {
        cajaView.txtNumReferencia.setText(String.valueOf(c.getNumReferencia()));
        cajaView.txtContenido.setText(c.getContenido());
        cajaView.txtPrecio.setText(String.valueOf(c.getPrecio()));
        cajaView.txtAlmacenCodigo.setText(String.valueOf(c.getAlmacenCodigo()));
    }

    private void limpiarcampos() {
        cajaView.txtNumReferencia.setText("");
        cajaView.txtContenido.setText("");
        cajaView.txtPrecio.setText("");
        cajaView.txtAlmacenCodigo.setText("");
        cajaView.tblCajas.clearSelection();
    }

    private void cargarCajas() {
        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("listCajas");
            r.setPayload(new HashMap<>());

            Response resp = conn.sendRequest(r);

            if (resp.isSuccess() && resp.getData() != null) {
                List<Caja> cajas = (List<Caja>) resp.getData();
                updateTable(cajas);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(cajaView,
                    "Error al cargar cajas: " + ex.getMessage());
        }
    }

    private void updateTable(List<Caja> cajas) {
        DefaultTableModel model = (DefaultTableModel) cajaView.tblCajas.getModel();
        model.setRowCount(0);

        for (Caja c : cajas) {
            model.addRow(new Object[]{
                c.getNumReferencia(),
                c.getContenido(),
                c.getPrecio(),
                c.getAlmacenCodigo()
            });
        }
    }

    private void cargarCajaSeleccionada() {
        int selectedRow = cajaView.tblCajas.getSelectedRow();
        if (selectedRow != -1) {
            cajaView.txtNumReferencia.setText(
                    String.valueOf(cajaView.tblCajas.getValueAt(selectedRow, 0)));
            cajaView.txtContenido.setText(
                    (String) cajaView.tblCajas.getValueAt(selectedRow, 1));
            cajaView.txtPrecio.setText(
                    String.valueOf(cajaView.tblCajas.getValueAt(selectedRow, 2)));
            cajaView.txtAlmacenCodigo.setText(
                    String.valueOf(cajaView.tblCajas.getValueAt(selectedRow, 3)));
        }
    }

    private void seleccionarFilaPorReferencia(Integer numReferencia) {
        for (int i = 0; i < cajaView.tblCajas.getRowCount(); i++) {
            Object valor = cajaView.tblCajas.getValueAt(i, 0);
            if (valor != null && valor.equals(numReferencia)) {
                cajaView.tblCajas.setRowSelectionInterval(i, i);
                cajaView.tblCajas.scrollRectToVisible(
                        cajaView.tblCajas.getCellRect(i, 0, true));
                return;
            }
        }
    }

}
