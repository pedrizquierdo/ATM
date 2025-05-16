/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.ui;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mx.itson.atm.controller.ControladorATM;
import mx.itson.atm.model.Cuenta;
import mx.itson.atm.model.Transaccion;

/**
 *
 * @author alang
 */
public class InterfazRetiro extends JFrame {
    public InterfazRetiro(Cuenta cuenta, ControladorATM controlador) {
        setTitle("Retiro de efectivo");
        setSize(300, 150);
        setLocationRelativeTo(null);

        JTextField campoMonto = new JTextField();
        JButton botonRetirar = new JButton("Retirar");


        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Monto:"));
        panel.add(campoMonto);
        panel.add(botonRetirar);

        add(panel);
        setVisible(true);
    }
}


