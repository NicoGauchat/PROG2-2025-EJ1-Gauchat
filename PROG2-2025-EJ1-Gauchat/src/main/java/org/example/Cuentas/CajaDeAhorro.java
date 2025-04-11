package org.example.Cuentas;

import org.example.Dto.CajaDeAhorroDto;

public  class CajaDeAhorro extends Cuenta implements IGestionSaldo {
    int operaciones;
    private final Object lock = new Object();

    private CajaDeAhorro(double saldo, int operaciones, int id) {
        this.saldo = saldo;
        this.operaciones = operaciones;
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
            if ( nuevoSaldo < 0) {
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
       private int id;
        public Builder conId( int id) {
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

        public CajaDeAhorro build() {
            return new CajaDeAhorro(saldo, operaciones,id);
        }
    }
}
