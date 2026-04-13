/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.App;
import com.mycompany.caso2.view.InicioView;
import javax.swing.*;
/**
 *
 * @author Eryan
 */
public class AppClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InicioView pv = new InicioView();
            pv.setVisible(true);
        });
    }
}
