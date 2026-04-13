/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Eryan
 */
public class Almacen {

    //en esta parte hago los atributos, que van a ser los mismos a los que hay en esta tabla de la base de datos
    private Integer codigo;
    private String lugar;
    private Integer capacidad;

    public Almacen(){
        
    }
    
    public Almacen(Integer codigo, String lugar, Integer capacidad) {
        this.codigo = codigo;
        this.lugar = lugar;
        //para guardarlo despues de haber verificado la capacidad
        setCapacidad(capacidad);
    }

    //creo los getters y setters
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    // en el sql se pone de una forma para que la capacidad no se pueda poner negativa
    //por eso es que antes de hacerle el set, quiero que ya esté validado correctamente
    public void setCapacidad(Integer capacidad) {
        try {
            this.capacidad = validarCapacidad(capacidad);
        } catch (FileException e) {
            System.out.println("¡¡ERROR!!: " + e.getMessage());
        }
    }

    public Integer validarCapacidad(Integer capacidad) throws FileException {
        if (capacidad == null) {
            throw new FileException("La capacidad no puede estar vacía.");
        }
        if (capacidad < 0) {
            throw new FileException("La capacidad no puede ser negativa.");
        }
        return capacidad;
    }

}
