/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author pedrizquierdo
 */
public class ConexionDB {

    private static final String URL = "jdbc:mysql://b3gwi4o5xugp4dbzhjkn-mysql.services.clever-cloud.com:3306/b3gwi4o5xugp4dbzhjkn";
    private static final String USERNAME = "ue9zb8usm26dxako";
    private static final String PASSWORD = "IHty4GgYnAFVyCISngn8";
    private static volatile Connection conexion;

    public static Connection getConnection() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            synchronized (ConexionDB.class) {
                if (conexion == null || conexion.isClosed()) {
                    conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    // Configuración adicional recomendada
                    conexion.setAutoCommit(false);
                }
            }
        }
        return conexion;
    }

    public static void cerrarConexiones() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
    
    public static void commit() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.commit();
        }
    }
    
    public static void rollback() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Error en rollback: " + e.getMessage());
        }
    }
}