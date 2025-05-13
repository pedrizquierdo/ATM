/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.atm.model;

/**
 *
 * @author pedrizquierdo
 */
public class Cuenta {

    public Cuenta(String numeroCuenta, double saldo, String idPropietario) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.idPropietario = idPropietario;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the idPropietario
     */
    public String getIdPropietario() {
        return idPropietario;
    }

    /**
     * @param idPropietario the idPropietario to set
     */
    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }

    /**
     * @return the tarjetaAsociada
     */
    public Tarjeta getTarjetaAsociada() {
        return tarjetaAsociada;
    }

    /**
     * @param tarjetaAsociada the tarjetaAsociada to set
     */
    public void setTarjetaAsociada(Tarjeta tarjetaAsociada) {
        this.tarjetaAsociada = tarjetaAsociada;
    }
    
    private String numeroCuenta;
    private double saldo;
    private String idPropietario;
    private Tarjeta tarjetaAsociada;
    

    // Getters y setters...

    public boolean retirar(double monto) {
        if (monto > 0 && getSaldo() >= monto) {
            setSaldo(getSaldo() - monto);
            return true;
        }
        return false;
    }
}
