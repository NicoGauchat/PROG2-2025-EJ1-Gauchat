package org.example.Dto;

public class CuentaCorrienteDto {
    private double saldo;
    private int operaciones;
    private double giroDescubierto;

    private CuentaCorrienteDto(double saldo, int operaciones, double giroDescubierto) {
        this.saldo = saldo;
        this.operaciones = operaciones;
        this.giroDescubierto = giroDescubierto;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getOperaciones() {
        return operaciones;
    }

    public double getGiroDescubierto() {
        return giroDescubierto;
    }

}
