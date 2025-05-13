/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.ui;

import java.sql.SQLException;
import mx.itson.atm.controller.ControladorATM;
import mx.itson.atm.repositories.RepositorioCuentas;
import mx.itson.atm.repositories.RepositorioTarjetas;
import mx.itson.atm.repositories.RepositorioTransacciones;

/**
 *
 * @author pedrizquierdo
 */
public class Main {
    

public static void main(String[] args) {
        
            RepositorioTarjetas rt = new RepositorioTarjetas();
            RepositorioCuentas rc = new RepositorioCuentas();
            RepositorioTransacciones rtr = new RepositorioTransacciones();

            new ControladorATM(rt, rc, rtr);
         
}
}
