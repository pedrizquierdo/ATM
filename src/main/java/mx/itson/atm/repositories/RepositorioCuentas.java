/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itson.atm.connection.ConexionDB;
import mx.itson.atm.model.Cuenta;

/**
 *
 * @author pedrizquierdo
 */
public class RepositorioCuentas {
    
    private Map<String, Cuenta> cuentasPorNumero;
    private Map<String, Cuenta> cuentasPorTarjeta;

    public RepositorioCuentas() {
        this.cuentasPorNumero = new HashMap<>();
        this.cuentasPorTarjeta = new HashMap<>();
        cargarDatosIniciales();
    }

    /**
     * Busca una cuenta por su número
     * @param numeroCuenta Número de cuenta a buscar
     * @return La cuenta encontrada o null si no existe
     */
    public Cuenta buscarPorNumero(String numeroCuenta) {
        return cuentasPorNumero.get(numeroCuenta);
    }

    /**
     * Busca una cuenta asociada a una tarjeta
     * @param numeroTarjeta Número de tarjeta a buscar
     * @return La cuenta asociada o null si no existe
     */
   public Cuenta buscarPorTarjeta(String numeroTarjeta) throws SQLException {
        String sql = "SELECT c.* FROM cuentas c " +
                    "JOIN tarjetas t ON c.numero_cuenta = t.numeroCuenta " +
                    "WHERE t.numero_Tarjeta = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, numeroTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cuenta cuenta = new Cuenta(
                        rs.getString("numero_cuenta"),
                        rs.getDouble("saldo"),
                        rs.getString("id_propietario")
                    );
                    // You might want to set other fields here
                    return cuenta;
                }
                return null;
            }
        }
    }

    /**
     * Guarda o actualiza una cuenta en el repositorio
     * @param cuenta La cuenta a guardar
     */
    public void guardar(Cuenta cuenta) {
        cuentasPorNumero.put(cuenta.getNumeroCuenta(), cuenta);
        
        if (cuenta.getTarjetaAsociada() != null) {
            cuentasPorTarjeta.put(cuenta.getTarjetaAsociada().getNumeroTarjeta(), cuenta);
        }
    }

    /**
     * Obtiene todas las cuentas registradas
     * @return Lista de cuentas
     */
    public List<Cuenta> obtenerTodas() {
        return new ArrayList<>(cuentasPorNumero.values());
    }

    /**
     * Carga datos iniciales de prueba
     */
    private void cargarDatosIniciales() {
        // Cuenta de ejemplo
        Cuenta cuentaEjemplo = new Cuenta("123456789", 10000.0, "CLI10001");
        guardar(cuentaEjemplo);
        
        // Puedes agregar más cuentas de prueba aquí
    }

    /**
     * Actualiza el saldo de una cuenta
     * @param numeroCuenta Número de cuenta
     * @param nuevoSaldo Nuevo saldo a establecer
     * @return true si se actualizó correctamente
     */
    public boolean actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        Cuenta cuenta = buscarPorNumero(numeroCuenta);
        if (cuenta != null) {
            cuenta.setSaldo(nuevoSaldo);
            return true;
        }
        return false;
    }
}
