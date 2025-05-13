/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.utilities;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author pedrizquierdo
 */
public class UtilidadSeguridad {
    
    public static String hashearNip(String nip) {
        return BCrypt.hashpw(nip, BCrypt.gensalt());
    }
    
    public static boolean verificarNip(String nipIngresado, String nipHasheado) {
        return BCrypt.checkpw(nipIngresado, nipHasheado);
    }
}
