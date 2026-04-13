/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.model;

/**
 *
 * @author Eryan
 */
public class Caja {

    //atributos iguales a los de la db
    private Integer numReferencia;
    private String contenido;
    private Double precio;
    private Integer almacenCodigo;

    public Caja() {

    }

    //constructor 
    public Caja(Integer numReferencia, String contenido, Double precio, Integer almacenCodigo) {
        this.numReferencia = numReferencia;
        this.contenido = contenido;
        this.precio = precio;
        this.almacenCodigo = almacenCodigo;
    }

    //getters y setters
    public Integer getNumReferencia() {
        return numReferencia;
    }

    public void setNumReferencia(Integer numReferencia) {
        this.numReferencia = numReferencia;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        try {
            this.precio = validarPrecio(precio);
        } catch (FileException e) {
 
            System.out.println("¡¡ERROR!!: " + e.getMessage());
        }
        this.precio = precio;
    }

    public Integer getAlmacenCodigo() {
        return almacenCodigo;
    }

    public void setAlmacenCodigo(Integer almacenCodigo) {
        this.almacenCodigo = almacenCodigo;
    }

    public Double validarPrecio(Double precio) throws FileException {
        //aqui valido el precio
        if (precio == null) {
            throw new FileException("El precio no puede estar vacío.");
        }
        if (precio < 0) {
            throw new FileException("El precio no puede ser negativo.");
        }

        return precio;
    }

}
