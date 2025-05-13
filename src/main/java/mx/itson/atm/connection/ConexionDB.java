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
    private static Connection conexion;

    public static Connection getConnection() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return conexion;
    }

    public static void cerrarConexiones() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}