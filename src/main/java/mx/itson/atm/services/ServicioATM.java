/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.services;

import java.time.LocalDateTime;
import mx.itson.atm.model.Cuenta;
import mx.itson.atm.model.Transaccion;
import mx.itson.atm.repositories.RepositorioCuentas;
import mx.itson.atm.repositories.RepositorioTransacciones;

/**
 *
 * @author pedrizquierdo
 */
public class ServicioATM {
    private RepositorioCuentas repositorioCuentas;
    private RepositorioTransacciones repositorioTransacciones;

    public ServicioATM(RepositorioCuentas rc, RepositorioTransacciones rt) {
        this.repositorioCuentas = rc;
        this.repositorioTransacciones = rt;
    }

    public Transaccion retirarEfectivo(String numeroCuenta, double monto) {
        Cuenta cuenta = repositorioCuentas.buscarPorNumero(numeroCuenta);
        if (cuenta != null && cuenta.retirar(monto)) {
            Transaccion transaccion = new Transaccion(
                "RETIRO", 
                monto, 
                numeroCuenta, 
                LocalDateTime.now()
            );
            transaccion.completar();
            return repositorioTransacciones.guardar(transaccion);
        }
        return null;
    }

    public Cuenta obtenerCuentaPorTarjeta(String numeroTarjeta) {
        return repositorioCuentas.buscarPorTarjeta(numeroTarjeta);
    }
}
