package org.example.Dto;

public class CajaDeAhorroDto {
    private double saldo;
    private int operaciones;

    public CajaDeAhorroDto(double saldo, int operaciones) {
        this.saldo = saldo;
        this.operaciones = operaciones;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getOperaciones() {
        return operaciones;
    }

}
