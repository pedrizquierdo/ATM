/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import mx.itson.atm.connection.ConexionDB;
import mx.itson.atm.model.Cuenta;
import mx.itson.atm.model.Tarjeta;
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
     public Transaccion depositarEfectivo(String numeroCuenta, double monto) {
    Cuenta cuenta = repositorioCuentas.buscarPorNumero(numeroCuenta);
    if (cuenta != null && monto > 0) {
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        repositorioCuentas.guardar(cuenta);

        Transaccion transaccion = new Transaccion(
            "DEPOSITO",
            monto,
            numeroCuenta,
            LocalDateTime.now()
        );
        transaccion.completar();
        return repositorioTransacciones.guardar(transaccion);
    }
    return null;
}
    public boolean transferir(String origen, String destino, double monto) {
    Cuenta cuentaOrigen = repositorioCuentas.buscarPorNumero(origen);
    Cuenta cuentaDestino = repositorioCuentas.buscarPorNumero(destino);

    if (cuentaOrigen != null && cuentaDestino != null && cuentaOrigen.retirar(monto)) {
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
        repositorioCuentas.guardar(cuentaDestino);

        Transaccion transaccion = new Transaccion("TRANSFERENCIA", monto, origen, LocalDateTime.now());
        transaccion.completar();
        repositorioTransacciones.guardar(transaccion);
        return true;
    }

    return false;
}
    public boolean cambiarNip(Tarjeta tarjeta, String nuevoNip) {
    if (nuevoNip == null || nuevoNip.length() < 4) return false;

    tarjeta.setNip(nuevoNip);
    // Guardar en BD si deseas persistencia
    return true;
}
    public void guardarComprobanteEnArchivo(Transaccion transaccion) {
    try (PrintWriter out = new PrintWriter("comprobante_" + transaccion.getIdTransaccion() + ".txt")) {
        out.println(transaccion.generarComprobante());
    } catch (IOException e) {
        System.err.println("Error al guardar comprobante: " + e.getMessage());
    }
}
    public static Connection getConnectionConReintento() throws SQLException {
    int intentos = 3;
    while (intentos-- > 0) {
        try {
            return ConexionDB.getConnection();
        } catch (SQLException e) {
            System.err.println("Reintentando conexión... (" + intentos + ")");
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }
    }
    throw new SQLException("No se pudo conectar después de varios intentos.");
}
}

