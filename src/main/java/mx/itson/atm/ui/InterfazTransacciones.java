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
    private final Cuenta cuenta;
    private final ControladorATM controlador;

    public InterfazTransacciones(Cuenta cuenta, ControladorATM controlador) {
        this.cuenta = cuenta;
        this.controlador = controlador;
        
        setTitle("Operaciones ATM");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnRetirar = new JButton("Retirar");
        JButton btnDepositar = new JButton("Depositar");
        JButton btnTransferir = new JButton("Transferir");
        JButton btnCambiarNIP = new JButton("Cambiar NIP");

        btnRetirar.addActionListener(e -> new InterfazRetiro(cuenta, controlador));
        btnDepositar.addActionListener(e -> new InterfazDeposito(cuenta, controlador));
        btnTransferir.addActionListener(e -> new InterfazTransferencia(cuenta, controlador));
        btnCambiarNIP.addActionListener(e -> new InterfazCambioNIP(cuenta, controlador));

        panel.add(new JLabel("Seleccione una operación:", SwingConstants.CENTER));
        panel.add(btnRetirar);
        panel.add(btnDepositar);
        panel.add(btnTransferir);
        panel.add(btnCambiarNIP);

        add(panel);
        setVisible(true);
    }
}
