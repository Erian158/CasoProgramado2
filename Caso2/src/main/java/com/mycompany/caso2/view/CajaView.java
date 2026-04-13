/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.view;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author Eryan
 */

public class CajaView extends JFrame {

    public JTextField txtNumReferencia;
    public JTextField txtContenido;
    public JTextField txtPrecio;
    public JTextField txtAlmacenCodigo;

    public JButton btnBuscarNumRef;
    public JButton btnBuscarContenido;
    public JButton btnBuscarAlmacen;

    public JButton btnInsertar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnConsultar;

    public JTable tblCajas;
    public DefaultTableModel tableModel;

    public CajaView() {
        super("Cajas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 230));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(90, 20, 140));
        panelTitulo.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitulo = new JLabel("Sistema de venta y envío de productos", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridLayout(0, 3, 10, 10));
        panelForm.setBorder(new EmptyBorder(15, 20, 15, 20));
        panelForm.setBackground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Cajas", SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 80, 200));
        lblSubtitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        panelForm.add(lblSubtitulo);
        panelForm.add(new JLabel(""));
        panelForm.add(new JLabel(""));

        panelForm.add(new JLabel("Numero de Referencia:"));
        txtNumReferencia = new JTextField(20);
        panelForm.add(txtNumReferencia);
        btnBuscarNumRef = new JButton("Buscar");
        estilizarBoton(btnBuscarNumRef);
        panelForm.add(btnBuscarNumRef);

        panelForm.add(new JLabel("Contenido:"));
        txtContenido = new JTextField(20);
        panelForm.add(txtContenido);
        btnBuscarContenido = new JButton("Buscar");
        estilizarBoton(btnBuscarContenido);
        panelForm.add(btnBuscarContenido);

        panelForm.add(new JLabel("Precio:"));
        txtPrecio = new JTextField(20);
        panelForm.add(txtPrecio);
        panelForm.add(new JLabel(""));

        panelForm.add(new JLabel("Código de almacen:"));
       txtAlmacenCodigo = new JTextField(20);
        panelForm.add(txtAlmacenCodigo);
        btnBuscarAlmacen = new JButton("Buscar");
        estilizarBoton(btnBuscarAlmacen);
        panelForm.add(btnBuscarAlmacen);

        btnInsertar = new JButton("Insertar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnConsultar = new JButton("Consultar");

        estilizarBoton(btnInsertar);
        estilizarBoton(btnActualizar);
        estilizarBoton(btnEliminar);
        estilizarBoton(btnConsultar);

        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnInsertar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultar);

        panelForm.add(panelBotones);
        panelForm.add(new JLabel(""));
        panelForm.add(new JLabel(""));
        String[] columnas = {"Numero de Referencia", "Contenido", "Precio", "Código de almacen"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblCajas = new JTable(tableModel);
        tblCajas.setBackground(Color.WHITE);
        tblCajas.setRowHeight(25);
        tblCajas.setGridColor(Color.LIGHT_GRAY);
        tblCajas.setShowVerticalLines(false);
        tblCajas.setSelectionBackground(new Color(200, 150, 230));

        tblCajas.getTableHeader().setBackground(new Color(230, 230, 230));
        tblCajas.getTableHeader().setForeground(Color.BLACK);
        tblCajas.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(tblCajas);
        scroll.setPreferredSize(new Dimension(700, 200));

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(new EmptyBorder(10, 20, 15, 20));
        panelTabla.setBackground(new Color(230, 230, 230));
        panelTabla.add(scroll, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelForm, BorderLayout.CENTER);
        add(panelTabla, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void estilizarBoton(JButton btn) {
        btn.setBackground(new Color(160, 80, 200));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}