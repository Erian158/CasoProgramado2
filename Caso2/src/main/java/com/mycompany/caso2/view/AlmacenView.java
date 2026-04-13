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

public class AlmacenView extends JFrame {

    public JTextField txtCodigo;
    public JTextField txtLugar;
    public JTextField txtCapacidad;

    public JButton btnBuscarCodigo;
    public JButton btnBuscarLugar;

    public JButton btnInsertar;
    public JButton btnActualizar;
    public JButton btnEliminar;
    public JButton btnConsultar;

    public JTable tblAlmacenes;
    public DefaultTableModel tableModel;

    public AlmacenView() {
        super("Almacen");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 230));

        // aqui estoy creando un panel para poder ponerle fondo y que se vea estetico
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


        JLabel lblSubtitulo = new JLabel("Almacen", SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 80, 200));
        lblSubtitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        panelForm.add(lblSubtitulo);
        panelForm.add(new JLabel(""));
        panelForm.add(new JLabel(""));


        panelForm.add(new JLabel("Codigo:"));
        txtCodigo = new JTextField(20);
        panelForm.add(txtCodigo);
        btnBuscarCodigo = new JButton("Buscar");
        estilizarBoton(btnBuscarCodigo);
        panelForm.add(btnBuscarCodigo);

        panelForm.add(new JLabel("Lugar:"));
        txtLugar = new JTextField(20);
        panelForm.add(txtLugar);
        btnBuscarLugar = new JButton("Buscar");
        estilizarBoton(btnBuscarLugar);
        panelForm.add(btnBuscarLugar);
        panelForm.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField(20);
        panelForm.add(txtCapacidad);
        panelForm.add(new JLabel(""));

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


        String[] columnas = {"Codigo", "Lugar", "Capacidad"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblAlmacenes = new JTable(tableModel);
        tblAlmacenes.setBackground(Color.WHITE);
        tblAlmacenes.setRowHeight(25);
        tblAlmacenes.setGridColor(Color.LIGHT_GRAY);
        tblAlmacenes.setShowVerticalLines(false);
        tblAlmacenes.setSelectionBackground(new Color(200, 150, 230));

        tblAlmacenes.getTableHeader().setBackground(new Color(200, 200, 200));
        tblAlmacenes.getTableHeader().setForeground(Color.BLACK);
        tblAlmacenes.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(tblAlmacenes);
        scroll.setPreferredSize(new Dimension(600, 200));

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(new EmptyBorder(10, 50, 15, 50));
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