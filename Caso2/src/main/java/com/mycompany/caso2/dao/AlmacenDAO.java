/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.dao;

import com.mycompany.caso2.model.Almacen;
import com.mycompany.caso2.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eryan
 */
public class AlmacenDAO {

    //con este metodo vamos a ingresar los datos a la db 
    public boolean Create(Almacen a) throws Exception {
        String sql = "INSERT INTO almacenes (codigo, lugar, capacidad) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, a.getCodigo());
            ps.setString(2, a.getLugar());
            ps.setInt(3, a.getCapacidad()); //que se supone que ya validamos
            return ps.executeUpdate() > 0;
        }
    }

    //este metodo es para actualizar o modificar datos que ya hay en la tabla
    public boolean update(Almacen a) throws Exception {
        String sql = "UPDATE almacenes SET "
                + "lugar = ?,"
                + " capacidad = ?"
                + " WHERE codigo = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getLugar());
            ps.setInt(2, a.getCapacidad());
            ps.setInt(3, a.getCodigo());
            return ps.executeUpdate() > 0;
        }
    }

    //y este metodo sirve para borrar registros de la tabla
    public boolean delete(Integer codigo) throws Exception {
        String sql = "DELETE FROM almacenes "
                + "WHERE codigo = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            return true;
        }
    }

    //este metodo va a a buscar por codigo de almacen
    public Almacen findByCodigo(Integer codigo) throws Exception {
        String sql = "SELECT codigo, lugar, capacidad"
                + " FROM almacenes "
                + "WHERE codigo = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Almacen a = new Almacen();
                    a.setCodigo(rs.getInt("codigo"));
                    a.setLugar(rs.getString("lugar"));
                    a.setCapacidad(rs.getInt("capacidad"));
                    return a;
                }
                return null;
            }
        }
    }

    //este metodo va a a buscar por lugar de almacen
    public Almacen findByLugar(String lugar) throws Exception {
        String sql = "SELECT codigo, lugar, capacidad FROM almacenes WHERE lugar = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, lugar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Almacen a = new Almacen();
                    a.setCodigo(rs.getInt("codigo"));
                    a.setLugar(rs.getString("lugar"));
                    a.setCapacidad(rs.getInt("capacidad"));
                    return a;
                }
                return null;
            }
        }
    }

    //este metodo va a traer todos los registros de la tabla Almacenes
    public List<Almacen> getAlmacen() throws Exception {
        List<Almacen> almacen = new ArrayList<>();
        String sql = "SELECT codigo, lugar, capacidad FROM almacenes";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    almacen.add(new Almacen(
                            rs.getInt("codigo"),
                            rs.getString("lugar"),
                            rs.getInt("capacidad")
                    ));

                }
            }

            return almacen;
        }
    }

}
