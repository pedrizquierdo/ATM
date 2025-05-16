/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import mx.itson.atm.connection.ConexionDB;
import mx.itson.atm.model.Tarjeta;

/**
 *
 * @author pedrizquierdo
 */
public class RepositorioTarjetas {

    public Tarjeta buscarPorNumero(String numeroTarjeta) throws SQLException {
        String sql = "SELECT numero_tarjeta, nip, fecha_expiracion FROM tarjetas WHERE numero_tarjeta = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroTarjeta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String numero = rs.getString("numero_tarjeta");
                    String nipPlano = rs.getString("nip"); // Este se hashea al crear el objeto
                    LocalDate fechaExpiracion = rs.getDate("fecha_expiracion").toLocalDate();

                    return new Tarjeta(numero, nipPlano, fechaExpiracion, true);

                } else {
                    return null; // No se encontró la tarjeta
                }
            }
        }
    }
}