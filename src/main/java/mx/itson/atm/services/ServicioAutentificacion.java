/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import mx.itson.atm.model.Tarjeta;
import mx.itson.atm.repositories.RepositorioTarjetas;

/**
 *
 * @author pedrizquierdo
 */
public class ServicioAutentificacion {
    
    private static final int INTENTOS_MAXIMOS = 3;
    private Map<String, Integer> intentosFallidos = new HashMap<>();
    private RepositorioTarjetas repositorioTarjetas;

    public ServicioAutentificacion(RepositorioTarjetas repositorioTarjetas) {
        this.repositorioTarjetas = repositorioTarjetas;
    }

    public Tarjeta buscarTarjeta(String numeroTarjeta) throws SQLException {
        return repositorioTarjetas.buscarPorNumero(numeroTarjeta);
    }

    public boolean autenticar(Tarjeta tarjeta, String nip) {
        if (tarjeta.estaBloqueada()) {
            return false;
        }

        if (tarjeta.validarNip(nip)) {
            intentosFallidos.remove(tarjeta.getNumeroTarjeta());
            return true;
        } else {
            int intentos = intentosFallidos.getOrDefault(tarjeta.getNumeroTarjeta(), 0) + 1;
            intentosFallidos.put(tarjeta.getNumeroTarjeta(), intentos);

            if (intentos >= INTENTOS_MAXIMOS) {
                tarjeta.bloquear();
                // También podrías persistir este cambio en la BD si lo deseas
            }

            return false;
        }
    }
}

