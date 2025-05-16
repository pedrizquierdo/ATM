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
import javax.swing.JTextField;
import mx.itson.atm.controller.ControladorATM;
import mx.itson.atm.model.Cuenta;

/**
 *
 * @author alang
 */
public class InterfazTransferencia extends JFrame {
    public InterfazTransferencia(Cuenta cuenta, ControladorATM controlador) {
        setTitle("Transferencia");
        setSize(350, 180);
        setLocationRelativeTo(null);

        JTextField campoDestino = new JTextField();
        JTextField campoMonto = new JTextField();
        JButton botonTransferir = new JButton("Transferir");

        

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Cuenta destino:"));
        panel.add(campoDestino);
        panel.add(new JLabel("Monto:"));
        panel.add(campoMonto);
        panel.add(botonTransferir);

        add(panel);
        setVisible(true);
    }
}

