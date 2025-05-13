/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itson.atm.model.Transaccion;

/**
 *
 * @author pedrizquierdo
 */
public class RepositorioTransacciones {
    
    private Map<String, Transaccion> transaccionesPorId;
    private Map<String, List<Transaccion>> transaccionesPorCuenta;

    public RepositorioTransacciones() {
        this.transaccionesPorId = new HashMap<>();
        this.transaccionesPorCuenta = new HashMap<>();
    }

    /**
     * Guarda una transacción en el repositorio
     * @param transaccion Transacción a guardar
     * @return La transacción guardada
     */
    public Transaccion guardar(Transaccion transaccion) {
        transaccionesPorId.put(transaccion.getIdTransaccion(), transaccion);
        
        String numeroCuenta = transaccion.getNumeroCuenta();
        if (!transaccionesPorCuenta.containsKey(numeroCuenta)) {
            transaccionesPorCuenta.put(numeroCuenta, new ArrayList<>());
        }
        transaccionesPorCuenta.get(numeroCuenta).add(transaccion);
        
        return transaccion;
    }

    /**
     * Busca una transacción por su ID
     * @param idTransaccion ID de la transacción
     * @return La transacción encontrada o null
     */
    public Transaccion buscarPorId(String idTransaccion) {
        return transaccionesPorId.get(idTransaccion);
    }

    /**
     * Obtiene el historial de transacciones de una cuenta
     * @param numeroCuenta Número de cuenta
     * @return Lista de transacciones ordenadas por fecha (más reciente primero)
     */
    public List<Transaccion> obtenerHistorial(String numeroCuenta) {
        List<Transaccion> historial = transaccionesPorCuenta.getOrDefault(numeroCuenta, new ArrayList<>());
        
        // Ordenar por fecha más reciente primero
        historial.sort((t1, t2) -> t2.getFechaHora().compareTo(t1.getFechaHora()));
        
        return historial;
    }

    /**
     * Obtiene todas las transacciones registradas
     * @return Lista de todas las transacciones
     */
    public List<Transaccion> obtenerTodas() {
        return new ArrayList<>(transaccionesPorId.values());
    }

    /**
     * Obtiene el último movimiento de una cuenta
     * @param numeroCuenta Número de cuenta
     * @return La última transacción o null si no hay movimientos
     */
    public Transaccion obtenerUltimaTransaccion(String numeroCuenta) {
        List<Transaccion> historial = obtenerHistorial(numeroCuenta);
        return historial.isEmpty() ? null : historial.get(0);
    }
    
}
