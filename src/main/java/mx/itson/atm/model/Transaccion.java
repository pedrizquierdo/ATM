/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mx.itson.atm.utilities.ConfiguracionATM;

/**
 *
 * @author pedrizquierdo
 */
public class Transaccion {

    /**
     * @return the idTransaccion
     */
    public String getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * @param idTransaccion the idTransaccion to set
     */
    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the fechaHora
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora the fechaHora to set
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return the codigoCajero
     */
    public String getCodigoCajero() {
        return codigoCajero;
    }

    /**
     * @param codigoCajero the codigoCajero to set
     */
    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    private String idTransaccion;
    private String tipo; // "RETIRO", "DEPOSITO", "CONSULTA", "TRANSFERENCIA"
    private double monto;
    private String numeroCuenta;
    private LocalDateTime fechaHora;
    private String codigoCajero;
    private String estado; // "COMPLETADA", "FALLIDA", "PENDIENTE"
    
    public Transaccion(String tipo, double monto, String numeroCuenta, LocalDateTime fechaHora) {
        this.idTransaccion = generarIdTransaccion();
        this.tipo = tipo;
        this.monto = monto;
        this.numeroCuenta = numeroCuenta;
        this.fechaHora = fechaHora;
        this.codigoCajero = ConfiguracionATM.CODIGO_CAJERO;
        this.estado = "PENDIENTE";
    }
    
    // Métodos principales
    
    private String generarIdTransaccion() {
        return "TXN-" + System.currentTimeMillis();
    }
    
    /**
     * Marca la transacción como completada
     */
    public void completar() {
        this.setEstado("COMPLETADA");
    }
    
    /**
     * Marca la transacción como fallida
     */
    public void fallar(String motivo) {
        this.setEstado("FALLIDA: " + motivo);
    }
    
    /**
     * Genera un resumen de la transacción para el comprobante
     */
    public String generarComprobante() {
        return String.format("COMPROBANTE BANCARIO\n" +
            "Banco: %s\n" +
            "Cajero: %s\n" +
            "Fecha/Hora: %s\n" +
            "Tipo: %s\n" +
            "Cuenta: %s\n" +
            "Monto: %.2f\n" +
            "Estado: %s\n" +
            "ID Transacción: %s",
            ConfiguracionATM.NOMBRE_BANCO,
            getCodigoCajero(), getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), getTipo(), getNumeroCuenta(), getMonto(), getEstado(), getIdTransaccion());
    }
}
