/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.model;

import java.time.LocalDate;
import mx.itson.atm.utilities.ConfiguracionATM;
import mx.itson.atm.utilities.RegistroAuditoria;
import mx.itson.atm.utilities.UtilidadSeguridad;

/**
 *
 * @author pedrizquierdo
 */
public class Tarjeta {

    private Cuenta cuentaAsociada;
    private String numeroTarjeta;
    private String nip;
    private boolean bloqueada;
    private LocalDate fechaExpiracion;
    private int intentosFallidos;

  // Constructor para cargar tarjeta con NIP ya hasheado desde la BD
public Tarjeta(String numeroTarjeta, String nip, LocalDate fechaExpiracion, boolean yaHasheado) {
    this.numeroTarjeta = numeroTarjeta;
    this.nip = yaHasheado ? nip : UtilidadSeguridad.hashearNip(nip);
    this.fechaExpiracion = fechaExpiracion;
    this.bloqueada = false;
    this.intentosFallidos = 0;
}

    /**
     * Verifica si la tarjeta está bloqueada
     * @return true si la tarjeta está bloqueada, false en caso contrario
     */
    public boolean estaBloqueada() {
        return bloqueada || LocalDate.now().isAfter(fechaExpiracion);
    }

    /**
     * Bloquea la tarjeta e incrementa el contador de intentos fallidos
     */
    public void bloquear() {
        this.bloqueada = true;
        RegistroAuditoria.registrarEvento("TARJETA_BLOQUEADA", 
            "Tarjeta " + numeroTarjeta + " bloqueada por seguridad");
    }

    /**
     * Valida el NIP ingresado contra el NIP almacenado
     * @param nipIngresado NIP proporcionado por el usuario
     * @return true si el NIP es correcto y la tarjeta no está bloqueada
     */
    public boolean validarNip(String nipIngresado) {
    if (estaBloqueada()) return false;

    if (UtilidadSeguridad.verificarNip(nipIngresado, nip)) {
        intentosFallidos = 0;
        return true;
    } else {
        intentosFallidos++;
        if (intentosFallidos >= ConfiguracionATM.MAX_INTENTOS_NIP) {
            bloquear();
        }
        return false;
    }
}

    // Getters y setters
    public Cuenta getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(Cuenta cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = UtilidadSeguridad.hashearNip(nip);
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }
}
