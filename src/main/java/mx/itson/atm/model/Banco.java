/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import mx.itson.atm.utilities.UtilidadSeguridad;

/**
 *
 * @author pedrizquierdo
 */
public class Banco {
    
    private String codigoBanco;
    private String nombre;
    private List<Cuenta> cuentas;
    private List<Tarjeta> tarjetas;
    
    public Banco(String codigoBanco, String nombre) {
        this.codigoBanco = codigoBanco;
        this.nombre = nombre;
        this.cuentas = new ArrayList<>();
        this.tarjetas = new ArrayList<>();
    }
    
    // Métodos principales
    
    /**
     * Agrega una nueva cuenta al banco
     */
    public void agregarCuenta(Cuenta cuenta) {
        if (!cuentas.contains(cuenta)) {
            cuentas.add(cuenta);
        }
    }
    
    /**
     * Emite una nueva tarjeta asociada a una cuenta
     */
    public Tarjeta emitirTarjeta(String numeroTarjeta, String nip, Cuenta cuenta) {
        Tarjeta nuevaTarjeta = new Tarjeta(
            numeroTarjeta,
            UtilidadSeguridad.hashearNip(nip),
            LocalDate.now().plusYears(3) // 3 años de vigencia
        );
        nuevaTarjeta.setCuentaAsociada(cuenta);
        tarjetas.add(nuevaTarjeta);
        return nuevaTarjeta;
    }
    
    /**
     * Busca una tarjeta por su número
     */
    public Tarjeta buscarTarjeta(String numeroTarjeta) {
        return tarjetas.stream()
            .filter(t -> t.getNumeroTarjeta().equals(numeroTarjeta))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Verifica el saldo disponible en una cuenta
     */
    public double consultarSaldo(String numeroCuenta) {
        return cuentas.stream()
            .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
            .findFirst()
            .map(Cuenta::getSaldo)
            .orElse(0.0);
    }
}
