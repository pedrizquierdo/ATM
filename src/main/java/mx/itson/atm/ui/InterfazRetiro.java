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

        botonRetirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = campoMonto.getText().trim();
                // Validación: campo vacío
                if (texto.isEmpty()) {
                    JOptionPane.showMessageDialog(InterfazRetiro.this, "Ingrese un monto válido.");
                    return;
                }
                try {
                    // Reemplaza coma por punto si el usuario pone 100,50 en lugar de 100.50
                    texto = texto.replace(",", ".");
                    // Intenta convertir a número
                    double monto = Double.parseDouble(texto);
                    // Validación: monto mayor que cero
                    if (monto <= 0) {
                        JOptionPane.showMessageDialog(InterfazRetiro.this, "El monto debe ser mayor que cero.");
                        return;
                    }
                    // DEBUG opcional
                    System.out.println("Monto ingresado: " + monto);
                    System.out.println("Cuenta actual: " + cuenta.getNumeroCuenta() + " - Saldo: " + cuenta.getSaldo());
                    // Lógica de retiro
                    Transaccion txn = controlador.retirar(cuenta.getNumeroCuenta(), monto);
                    if (txn != null) {
                        JOptionPane.showMessageDialog(InterfazRetiro.this, "Retiro exitoso:\n" + txn.generarComprobante());
                    } else {
                        JOptionPane.showMessageDialog(InterfazRetiro.this, "No se pudo realizar el retiro (¿Saldo insuficiente?).");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InterfazRetiro.this, "El monto ingresado no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(InterfazRetiro.this, "Error inesperado:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Monto:"));
        panel.add(campoMonto);
        panel.add(botonRetirar);

        add(panel);
        setVisible(true);
    }
}


