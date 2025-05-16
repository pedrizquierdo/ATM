/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.ui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import mx.itson.atm.controller.ControladorATM;
import mx.itson.atm.model.Cuenta;

/**
 *
 * @author alang
 */
public class InterfazCambioNIP extends JFrame {
    public InterfazCambioNIP(Cuenta cuenta, ControladorATM controlador) {
        setTitle("Cambio de NIP");
        setSize(350, 150);
        setLocationRelativeTo(null);

        JPasswordField campoNuevoNip = new JPasswordField();
        JButton botonCambiar = new JButton("Cambiar NIP");

        botonCambiar.addActionListener(e -> {
            try {
                String nuevoNip = new String(campoNuevoNip.getPassword());
                boolean exito = controlador.cambiarNip(cuenta.getTarjetaAsociada(), nuevoNip);
                JOptionPane.showMessageDialog(this, exito ? "NIP actualizado correctamente." : "Error al actualizar el NIP.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar el NIP:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nuevo NIP:"));
        panel.add(campoNuevoNip);
        panel.add(botonCambiar);

        add(panel);
        setVisible(true);
    }
}


