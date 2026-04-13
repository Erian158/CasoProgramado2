/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.dao;

import com.mycompany.caso2.model.Caja;
import com.mycompany.caso2.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eryan
 */
public class CajaDAO {

    public boolean Create(Caja b) throws Exception {
        String sql = "INSERT INTO cajas (num_referencia, contenido, precio, almacen_codigo) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, b.getNumReferencia());
            ps.setString(2, b.getContenido());
            ps.setDouble(3, b.getPrecio());
            ps.setInt(4, b.getAlmacenCodigo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(Caja b) throws Exception {
        String sql = "UPDATE cajas "
                + "SET contenido = ?,"
                + " precio = ?,"
                + " almacen_codigo = ? "
                + "WHERE num_referencia = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(4, b.getNumReferencia());
            ps.setString(1, b.getContenido());
            ps.setDouble(2, b.getPrecio());
            ps.setInt(3, b.getAlmacenCodigo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(Integer numReferencia) throws Exception {
        String sql = "DELETE FROM cajas "
                + "WHERE num_referencia = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, numReferencia);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            return true;
        }
    }

    public Caja findByReferencia(Integer numReferencia) throws Exception {
        String sql = "SELECT num_referencia, contenido, precio, almacen_codigo "
                + "FROM cajas "
                + "WHERE num_referencia = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, numReferencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Caja b = new Caja();
                    b.setNumReferencia(rs.getInt("num_referencia"));
                    b.setContenido(rs.getString("contenido"));
                    b.setPrecio(rs.getDouble("precio"));
                    b.setAlmacenCodigo(rs.getInt("almacen_codigo"));
                    return b;
                }
                return null;
            }
        }
    }

    public Caja findByContenido(String contenido) throws Exception {
        String sql = "SELECT num_referencia, contenido, precio, almacen_codigo "
                + "FROM cajas "
                + "WHERE contenido = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, contenido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Caja b = new Caja();
                    b.setNumReferencia(rs.getInt("num_referencia"));
                    b.setContenido(rs.getString("contenido"));
                    b.setPrecio(rs.getDouble("precio"));
                    b.setAlmacenCodigo(rs.getInt("almacen_codigo"));

                    return b;
                }
                return null;
            }
        }
    }

    public Caja findByAlmacen(Integer almacenCodigo) throws Exception {
        String sql = "SELECT num_referencia, contenido, precio, almacen_codigo "
                + "FROM cajas "
                + "WHERE almacen_codigo = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, almacenCodigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Caja b = new Caja();
                    b.setNumReferencia(rs.getInt("num_referencia"));
                    b.setContenido(rs.getString("contenido"));
                    b.setPrecio(rs.getDouble("precio"));
                    b.setAlmacenCodigo(rs.getInt("almacen_codigo"));

                    return b;
                }
                return null;
            }
        }

    }

    public List<Caja> getCaja() throws Exception {
        List<Caja> caja = new ArrayList<>();
        String sql = "SELECT num_referencia, contenido, precio, almacen_codigo "
                + "FROM cajas";
        
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    caja.add(new Caja(
                            rs.getInt("num_referencia"),
                            rs.getString("contenido"),
                            rs.getDouble("precio"),
                            rs.getInt("almacen_codigo")
            ));
                }
            }
            return caja;
        }

    }

}
