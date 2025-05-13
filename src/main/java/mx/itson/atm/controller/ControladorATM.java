/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.controller;

import java.sql.SQLException;
import mx.itson.atm.connection.ConexionDB;
import mx.itson.atm.model.Cuenta;
import mx.itson.atm.model.Tarjeta;
import mx.itson.atm.repositories.RepositorioCuentas;
import mx.itson.atm.repositories.RepositorioTarjetas;
import mx.itson.atm.repositories.RepositorioTransacciones;
import mx.itson.atm.services.ServicioATM;
import mx.itson.atm.services.ServicioAutentificacion;
import mx.itson.atm.ui.InterfazATM;

/**
 *
 * @author pedrizquierdo
 */
public class ControladorATM {
    
    private InterfazATM vista;
    private ServicioAutentificacion servicioAuth;
    private ServicioATM servicioATM;
    
    public ControladorATM(RepositorioTarjetas repositorioTarjetas, 
                        RepositorioCuentas repositorioCuentas,
                        RepositorioTransacciones repositorioTransacciones) {
        this.servicioAuth = new ServicioAutentificacion(repositorioTarjetas);
        this.servicioATM = new ServicioATM(repositorioCuentas, repositorioTransacciones);
        this.vista = new InterfazATM();
        vista.setControlador(this);
        vista.setVisible(true);
    }
    
    public void manejarIngreso(String numeroTarjeta, String nip) {
        try {
            // Validación básica de entrada
            if(numeroTarjeta == null || nip == null) {
                vista.mostrarError("Datos inválidos");
                return;
            }
            
            Tarjeta tarjeta = servicioAuth.buscarTarjeta(numeroTarjeta);
            if(tarjeta != null && servicioAuth.autenticar(tarjeta, nip)) {
                Cuenta cuenta = servicioATM.obtenerCuentaPorTarjeta(numeroTarjeta);
                if(cuenta != null) {
                    vista.mostrarMenuPrincipal(cuenta);
                } else {
                    vista.mostrarError("No se encontró la cuenta asociada");
                }
            } else {
                vista.mostrarError("Autenticación fallida. Verifique sus datos.");
            }
        } catch (SQLException e) {
            vista.mostrarError("Error en el sistema. Intente nuevamente.");
            System.err.println("Error en manejarIngreso: " + e.getMessage());
        }
    }
    
    public void cerrarSistema() {
        try {
            ConexionDB.cerrarConexiones();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexiones: " + e.getMessage());
        }
    }
}