
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.server;

import com.mycompany.caso2.client.Request;
import com.mycompany.caso2.client.Response;
import com.mycompany.caso2.dao.AlmacenDAO;
import com.mycompany.caso2.dao.CajaDAO;
import com.mycompany.caso2.model.Almacen;
import com.mycompany.caso2.model.Caja;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eryan
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private AlmacenDAO almacenDAO = new AlmacenDAO();
    private CajaDAO cajaDAO = new CajaDAO();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Object obj;
            while ((obj = in.readObject()) != null) {
                if (!(obj instanceof Request)) {
                    continue;
                }
                Request req = (Request) obj;
                Response resp = handleRequest(req);
                out.writeObject(resp);
                out.flush();
            }
        } catch (EOFException eof) {
            // cliente desconectó
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }

    private Response handleRequest(Request req) {
        try {
            String action = req.getAction();
            Map<String, Object> p = req.getPayload();

            if ("createAlmacen".equalsIgnoreCase(action)) {
                Integer codigo = (Integer) p.get("codigo");
                String lugar = (String) p.get("lugar");
                Integer capacidad = (Integer) p.get("capacidad");

                if (almacenDAO.findByCodigo(codigo) != null) {
                    return new Response(false, "Ya existe un almacén con ese código.");
                }

                Almacen a = new Almacen();
                a.setCodigo(codigo);
                a.setLugar(lugar);
                a.setCapacidad(capacidad);

                boolean ok = almacenDAO.Create(a);
                return ok ? new Response(true, "Almacén creado correctamente.")
                        : new Response(false, "Error al crear el almacén.");

            } else if ("updateAlmacen".equalsIgnoreCase(action)) {
                Integer codigo = (Integer) p.get("codigo");
                String lugar = (String) p.get("lugar");
                Integer capacidad = (Integer) p.get("capacidad");
                
                Almacen alm = almacenDAO.findByCodigo(codigo);
                if (almacenDAO.findByCodigo(codigo) == null) {
                    return new Response(false, "Almacén no encontrado.");
                }

                Almacen a = new Almacen();
                a.setCodigo(codigo);
                a.setLugar(lugar);
                a.setCapacidad(capacidad);

                boolean ok = almacenDAO.update(a);
                return ok ? new Response(true, "Almacén actualizado correctamente.")
                        : new Response(false, "Error al actualizar el almacén.");

            } else if ("deleteAlmacen".equalsIgnoreCase(action)) {
                Integer codigo = (Integer) p.get("codigo");

                if (almacenDAO.findByCodigo(codigo) == null) {
                    return new Response(false, "Almacén no encontrado.");
                }

                boolean ok = almacenDAO.delete(codigo);
                return ok ? new Response(true, "Almacén eliminado correctamente.")
                        : new Response(false, "Error al eliminar el almacén.");

            } else if ("listAlmacenes".equalsIgnoreCase(action)) {
                List<Almacen> lista = almacenDAO.getAlmacen();
                Response r = new Response(true, "Lista de almacenes obtenida.");
                r.setData(lista);
                return r;

            } else if ("searchAlmacen".equalsIgnoreCase(action)) {
                String tipo = (String) p.get("tipo");

                if ("codigo".equalsIgnoreCase(tipo)) {
                    Integer codigo = (Integer) p.get("valor");
                    Almacen a = almacenDAO.findByCodigo(codigo);
                    if (a == null) {
                        return new Response(false, "Almacén no encontrado.");
                    }
                    Response r = new Response(true, "Almacén encontrado.");
                    r.setData(a);
                    return r;
                } else {
                    String lugar = (String) p.get("valor");
                    Almacen lista = almacenDAO.findByLugar(lugar);
                    Response r = new Response(true, "Búsqueda completada.");
                    r.setData(lista);
                    return r;
                }

            } else if ("createCaja".equalsIgnoreCase(action)) {
                Integer numReferencia = (Integer) p.get("numReferencia");
                String contenido = (String) p.get("contenido");
                Double precio = (Double) p.get("precio");
                Integer almacenCodigo = (Integer) p.get("almacenCodigo");

                if (cajaDAO.findByReferencia(numReferencia) != null) {
                    return new Response(false, "Ya existe una caja con ese número de referencia.");
                }

                if (almacenDAO.findByCodigo(almacenCodigo) == null) {
                    return new Response(false, "El almacén indicado no existe.");
                }

                Caja c = new Caja();
                c.setNumReferencia(numReferencia);
                c.setContenido(contenido);
                c.setPrecio(precio);
                c.setAlmacenCodigo(almacenCodigo);

                boolean ok = cajaDAO.Create(c);
                return ok ? new Response(true, "Caja creada correctamente.")
                        : new Response(false, "Error al crear la caja.");

            } else if ("updateCaja".equalsIgnoreCase(action)) {
                Integer numReferencia = (Integer) p.get("numReferencia");
                String contenido = (String) p.get("contenido");
                Double precio = (Double) p.get("precio");
                Integer almacenCodigo = (Integer) p.get("almacenCodigo");

                if (cajaDAO.findByReferencia(numReferencia) == null) {
                    return new Response(false, "Caja no encontrada.");
                }

                if (almacenDAO.findByCodigo(almacenCodigo) == null) {
                    return new Response(false, "El almacén indicado no existe.");
                }

                Caja c = new Caja();
                c.setNumReferencia(numReferencia);
                c.setContenido(contenido);
                c.setPrecio(precio);
                c.setAlmacenCodigo(almacenCodigo);

                boolean ok = cajaDAO.update(c);
                return ok ? new Response(true, "Caja actualizada correctamente.")
                        : new Response(false, "Error al actualizar la caja.");

            } else if ("deleteCaja".equalsIgnoreCase(action)) {
                Integer numReferencia = (Integer) p.get("numReferencia");

                if (cajaDAO.findByReferencia(numReferencia) == null) {
                    return new Response(false, "Caja no encontrada.");
                }

                boolean ok = cajaDAO.delete(numReferencia);
                return ok ? new Response(true, "Caja eliminada correctamente.")
                        : new Response(false, "Error al eliminar la caja.");

            } else if ("listCajas".equalsIgnoreCase(action)) {
                List<Caja> lista = cajaDAO.getCaja();
                Response r = new Response(true, "Lista de cajas obtenida.");
                r.setData(lista);
                return r;

            } else if ("searchCaja".equalsIgnoreCase(action)) {
                String tipo = (String) p.get("tipo");

                if ("numReferencia".equalsIgnoreCase(tipo)) {
                    Integer numRef = (Integer) p.get("valor");
                    Caja c = cajaDAO.findByReferencia(numRef);
                    if (c == null) {
                        return new Response(false, "Caja no encontrada.");
                    }
                    Response r = new Response(true, "Caja encontrada.");
                    r.setData(c);
                    return r;
                } else if ("contenido".equalsIgnoreCase(tipo)) {
                    String contenido = (String) p.get("valor");
                    Caja lista = cajaDAO.findByContenido(contenido);
                    Response r = new Response(true, "Búsqueda completada.");
                    r.setData(lista);
                    return r;
                } else {
                    Integer almacenCodigo = (Integer) p.get("valor");
                    Caja lista= cajaDAO.findByAlmacen(almacenCodigo);
                    Response r = new Response(true, "Búsqueda completada.");
                    r.setData(lista);
                    return r;
                }

            } else {
                return new Response(false, "Acción no soportada: " + action);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error interno: " + ex.getMessage());
        }
    }

}
