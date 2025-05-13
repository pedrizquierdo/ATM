/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mx.itson.atm.controller.ControladorATM;
import mx.itson.atm.model.Cuenta;

public class InterfazATM extends JFrame {
    private ControladorATM controlador;
    private JTextField campoNumeroTarjeta;
    private JPasswordField campoNip;
    private final JPanel panelPrincipal;
    
    public InterfazATM() {
        setTitle("ATM - Ingreso");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new GridLayout(4, 1, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        campoNumeroTarjeta = new JTextField();
        campoNip = new JPasswordField();

        JButton botonIngresar = new JButton("Ingresar");
        botonIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroTarjeta = campoNumeroTarjeta.getText().trim();
                String nip = new String(campoNip.getPassword()).trim();
                if (!numeroTarjeta.isEmpty() && !nip.isEmpty()) {
                    controlador.manejarIngreso(numeroTarjeta, nip);
                } else {
                    mostrarError("Debe llenar ambos campos.");
                }
            }
        });

        panelPrincipal.add(crearCampoConEtiqueta("Número de Tarjeta:", campoNumeroTarjeta));
        panelPrincipal.add(crearCampoConEtiqueta("NIP:", campoNip));
        panelPrincipal.add(botonIngresar);

        add(panelPrincipal);
    }

    private JPanel crearCampoConEtiqueta(String etiqueta, JComponent campo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel(etiqueta), BorderLayout.WEST);
        panel.add(campo, BorderLayout.CENTER);
        return panel;
    }

    public void setControlador(ControladorATM controlador) {
        this.controlador = controlador;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMenuPrincipal(Cuenta cuenta) {
        // Simulación de un menú simple de opciones. Puede expandirse según funcionalidad
        JOptionPane.showMessageDialog(this, "Bienvenido. Su saldo es: $" + cuenta.getSaldo(),
                "Menú Principal", JOptionPane.INFORMATION_MESSAGE);
        // Aquí podrías cambiar a otro panel con más opciones
    }
}
