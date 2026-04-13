/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.controller;
import com.mycompany.caso2.view.AlmacenView;
import com.mycompany.caso2.view.CajaView;
import com.mycompany.caso2.view.InicioView;
import javax.swing.*;
/**
 *
 * @author Eryan
 */
public class InicioController {

private InicioView inicioView;
 
    public InicioController(InicioView inicioView) {
        this.inicioView = inicioView;
        initActions();
    }
 
    private void initActions() {
        this.inicioView.btnVerAlmacenes.addActionListener(e -> abrirAlmacen());
        this.inicioView.btnVerCajas.addActionListener(e -> abrirCajas());
    }
 
    private void abrirAlmacen() {
        AlmacenView av = new AlmacenView();
        new AlmacenController(av);
        av.setVisible(true);
    }
 
    private void abrirCajas() {
        CajaView cv = new CajaView();
        new CajaController(cv);
        cv.setVisible(true);
    }
}