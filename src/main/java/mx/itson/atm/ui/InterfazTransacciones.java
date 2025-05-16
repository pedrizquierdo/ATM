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
import javax.swing.SwingConstants;
import mx.itson.atm.controller.ControladorATM;
import mx.itson.atm.model.Cuenta;
import mx.itson.atm.model.Tarjeta;
import mx.itson.atm.model.Transaccion;
import mx.itson.atm.services.ServicioATM;

/**
 *
 * @author alang
 */
public class InterfazTransacciones extends JFrame {
    public InterfazTransacciones(Cuenta cuenta, ControladorATM controlador) {
        setTitle("ATM - Menú Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblSaldo = new JLabel("Saldo actual: $" + cuenta.getSaldo());
        JButton btnDeposito = new JButton("Realizar Depósito");
        JButton btnRetiro = new JButton("Retirar Efectivo");
        JButton btnConsulta = new JButton("Consultar Saldo");
        JButton btnSalir = new JButton("Salir");

        btnDeposito.addActionListener(e -> {
            new InterfazDeposito(cuenta, controlador);
            dispose();
        });

        btnRetiro.addActionListener(e -> {
            new InterfazRetiro(cuenta, controlador);
            dispose();
        });

        btnConsulta.addActionListener(e -> {
            double saldo = controlador.obtenerSaldo(cuenta.getNumeroCuenta());
            JOptionPane.showMessageDialog(this, 
                "Saldo actual: $" + saldo, 
                "Consulta de Saldo", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalir.addActionListener(e -> {
            controlador.cerrarSistema();
            dispose();
        });

        panel.add(lblSaldo);
        panel.add(btnDeposito);
        panel.add(btnRetiro);
        panel.add(btnConsulta);
        panel.add(btnSalir);

        add(panel);
        setVisible(true);
    }
}
