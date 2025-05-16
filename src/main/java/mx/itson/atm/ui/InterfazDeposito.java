/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.ui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
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
        setTitle("Depósito - Cuenta: " + cuenta.getNumeroCuenta());
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCuenta = new JLabel("Cuenta:");
        JLabel lblNumeroCuenta = new JLabel(cuenta.getNumeroCuenta());
        JLabel lblMonto = new JLabel("Monto a depositar:");
        JTextField campoMonto = new JTextField();
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnDepositar = new JButton("Depositar");

        btnDepositar.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(campoMonto.getText());
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "El monto debe ser positivo", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Transaccion txn = controlador.depositar(cuenta.getNumeroCuenta(), monto);
                if (txn != null) {
                    JOptionPane.showMessageDialog(this, 
                        "Depósito exitoso!\nNuevo saldo: $" + controlador.obtenerSaldo(cuenta.getNumeroCuenta()),
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Ingrese un monto válido", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error en la operación: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        panel.add(lblCuenta);
        panel.add(lblNumeroCuenta);
        panel.add(lblMonto);
        panel.add(campoMonto);
        panel.add(btnCancelar);
        panel.add(btnDepositar);

        add(panel);
        setVisible(true);
    }
}


