/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Eryan
 */
public class InicioView extends JFrame{
    
    public JButton btnVerCajas;
    public JButton btnVerAlmacenes;

    public InicioView() {
        initComponents();
    }

    private void initComponents() {

        setTitle("Sistema de venta y envío de productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(230, 230, 230));
        setLayout(new GridBagLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setPreferredSize(new Dimension(620, 250));
        panelCentral.setBackground(new Color(230, 230, 230));

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(90, 20, 140));
        panelTitulo.setPreferredSize(new Dimension(620, 90));

        JLabel lblTitulo = new JLabel("Sistema de venta y envío de productos", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

  
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(new Color(230, 230, 230));
        panelBotones.setPreferredSize(new Dimension(620, 100));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnVerCajas = new JButton("Ver Cajas");
        btnVerCajas.setBackground(new Color(160, 80, 200));
        btnVerCajas.setForeground(Color.WHITE);
        btnVerCajas.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnVerCajas.setFocusPainted(false);
        btnVerCajas.setBorderPainted(false);
        btnVerCajas.setCursor(new Cursor(Cursor.HAND_CURSOR));


        btnVerAlmacenes = new JButton("Ver almacenes");
        btnVerAlmacenes.setBackground(new Color(160, 80, 200));
        btnVerAlmacenes.setForeground(Color.WHITE);
        btnVerAlmacenes.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnVerAlmacenes.setFocusPainted(false);
        btnVerAlmacenes.setBorderPainted(false);
        btnVerAlmacenes.setCursor(new Cursor(Cursor.HAND_CURSOR));
;

        panelBotones.add(btnVerCajas);
        panelBotones.add(btnVerAlmacenes);

        JPanel panelFooter = new JPanel(new BorderLayout());
        panelFooter.setBackground(new Color(230, 230, 230));
        panelFooter.setPreferredSize(new Dimension(620, 60));

        JLabel lblFooter = new JLabel("Hecho por Eryan Santamaria", SwingConstants.CENTER);
        lblFooter.setForeground(new Color(100, 100, 100));
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblFooter, BorderLayout.CENTER);

        panelCentral.add(panelTitulo, BorderLayout.NORTH);
        panelCentral.add(panelBotones, BorderLayout.CENTER);
        panelCentral.add(panelFooter, BorderLayout.SOUTH);

        add(panelCentral);
    }
}