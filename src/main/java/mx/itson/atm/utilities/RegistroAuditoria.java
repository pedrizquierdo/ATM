/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.utilities;

import java.time.LocalDateTime;

/**
 *
 * @author pedrizquierdo
 */
public class RegistroAuditoria {
    
    public static void registrarEvento(String tipoEvento, String mensaje) {
        String registro = String.format("[%s] %s: %s", 
            LocalDateTime.now().toString(), 
            tipoEvento, 
            mensaje);
        
        // Aquí iría la lógica para guardar en archivo o base de datos
        System.out.println(registro); // Ejemplo: solo imprime en consola
    }
}
