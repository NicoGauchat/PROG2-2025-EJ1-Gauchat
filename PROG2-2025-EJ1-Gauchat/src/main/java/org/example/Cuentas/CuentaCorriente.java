package org.example.Cuentas;

import org.example.Dto.CuentaCorrienteDto;

public class CuentaCorriente extends Cuenta implements IGestionSaldo{
    double giroDescubierto;
    int operaciones;
    private final Object lock = new Object();
    private CuentaCorriente(double saldo, int operaciones, double giroDescubierto, int id) {
        this.saldo = saldo;
        this.operaciones = operaciones;
        this.giroDescubierto = giroDescubierto;
        this.id = id;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean agregarSaldo(double monto) {
        synchronized (lock) {
            saldo += monto;
            operaciones++;
            return true;
        }
    }

    @Override
    public boolean quitarSaldo(double monto) {
        synchronized (lock){
            double nuevoSaldo;
        nuevoSaldo = saldo - monto;
        if ( nuevoSaldo <= -giroDescubierto) {
            return false;
        }
        operaciones++;
        return true;
        }
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public int getOperaciones() {
        return operaciones;
    }
    public static class Builder {
        private double saldo;
        private int operaciones;
        private double giroDescubierto;
        private int id;
        public Builder conId(int id) {
            this.id = id;
            return this;
        }
        public Builder conSaldo(double saldo) {
            this.saldo = saldo;
            return this;
        }

        public Builder conOperaciones(int operaciones) {
            this.operaciones = operaciones;
            return this;
        }

        public Builder conGiroDescubierto(double giroDescubierto) {
            this.giroDescubierto = giroDescubierto;
            return this;
        }

        public CuentaCorriente build() {
            return new CuentaCorriente(saldo, operaciones, giroDescubierto, id);
        }
    }
}
