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
import mx.itson.atm.model.Transaccion;

/**
 *
 * @author alang
 */
public class InterfazDeposito extends JFrame {
    public InterfazDeposito(Cuenta cuenta, ControladorATM controlador) {
        setTitle("Depósito");
        setSize(300, 150);
        setLocationRelativeTo(null);

        JTextField campoMonto = new JTextField();
        JButton botonDepositar = new JButton("Depositar");

        botonDepositar.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(campoMonto.getText());
                Transaccion txn = controlador.depositar(cuenta.getNumeroCuenta(), monto);
                if (txn != null) {
                    JOptionPane.showMessageDialog(this, "Depósito exitoso:\n" + txn.generarComprobante());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo realizar el depósito.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al realizar el depósito:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Monto:"));
        panel.add(campoMonto);
        panel.add(botonDepositar);

        add(panel);
        setVisible(true);
    }
}


