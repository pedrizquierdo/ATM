/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public Transaccion retirarEfectivo(String numeroCuenta, double monto) throws SQLException {
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

    public Cuenta obtenerCuentaPorTarjeta(String numeroTarjeta) throws SQLException {
    return repositorioCuentas.buscarPorTarjeta(numeroTarjeta);
}
    
    public Transaccion depositarEfectivo(String numeroCuenta, double monto) throws SQLException {
        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            // Bloquear la fila para evitar condiciones de carrera
            Cuenta cuenta = repositorioCuentas.buscarPorNumero(numeroCuenta);
            if (cuenta == null || monto <= 0) {
                ConexionDB.rollback();
                return null;
            }

            // Actualizar saldo
            double nuevoSaldo = cuenta.getSaldo() + monto;
            
            // Actualizar en BD con bloqueo exclusivo
            String updateSql = "UPDATE cuentas SET saldo = ? WHERE numero_cuenta = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setDouble(1, nuevoSaldo);
                updateStmt.setString(2, numeroCuenta);
                int updated = updateStmt.executeUpdate();
                if (updated != 1) {
                    ConexionDB.rollback();
                    return null;
                }
            }

            // Registrar transacción
            Transaccion transaccion = new Transaccion(
                "DEPOSITO",
                monto,
                numeroCuenta,
                LocalDateTime.now()
            );
            transaccion.completar();
            
            // Insertar transacción
            String insertSql = "INSERT INTO transacciones (IdTransaccion, tipo, monto, numero_cuenta, fecha_hora, estado) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, transaccion.getIdTransaccion());
                insertStmt.setString(2, transaccion.getTipo());
                insertStmt.setDouble(3, transaccion.getMonto());
                insertStmt.setString(4, transaccion.getNumeroCuenta());
                insertStmt.setTimestamp(5, Timestamp.valueOf(transaccion.getFechaHora()));
                insertStmt.setString(6, transaccion.getEstado());
                insertStmt.executeUpdate();
            }

            // Actualizar en memoria solo si la transacción fue exitosa
            cuenta.setSaldo(nuevoSaldo);
            ConexionDB.commit();
            return transaccion;
        } catch (SQLException e) {
            ConexionDB.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

     
    public boolean transferir(String origen, String destino, double monto) throws SQLException {
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
    
    public double obtenerSaldoCuenta(String numeroCuenta) throws SQLException {
        // Primero intenta obtener de la base de datos
        String sql = "SELECT saldo FROM cuentas WHERE numero_cuenta = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, numeroCuenta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double saldoBD = rs.getDouble("saldo");
                    
                    // Sincroniza con la memoria
                    Cuenta cuentaMemoria = repositorioCuentas.buscarPorNumero(numeroCuenta);
                    if (cuentaMemoria != null) {
                        cuentaMemoria.setSaldo(saldoBD);
                    }
                    
                    return saldoBD;
                }
                throw new SQLException("Cuenta no encontrada");
            }
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

