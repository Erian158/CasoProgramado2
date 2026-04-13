/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caso2.client;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Eryan
 */
public class Request implements Serializable {

    private String action; // "register", "login", etc.
    private Map<String, Object> payload;
     // getters/setters
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Map<String, Object> getPayload() { return payload; }
    public void setPayload(Map<String, Object> payload) { this.payload = payload; }
}
