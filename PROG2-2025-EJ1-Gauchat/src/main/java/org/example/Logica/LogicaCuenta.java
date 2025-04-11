package org.example.Logica;

import org.example.Cuentas.CajaDeAhorro;
import org.example.Cuentas.Cuenta;
import org.example.Cuentas.CuentaCorriente;
import org.example.Dto.CajaDeAhorroDto;

import java.util.ArrayList;

public class LogicaCuenta {
    private static volatile LogicaCuenta instancia;
    private static final Object lock = new Object();
    private ArrayList<Cuenta> cuentas = new ArrayList<>();
    private LogicaCuenta(){
    }
    public Cuenta buscarCuenta(int id) {
        return cuentas.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }
    public static LogicaCuenta obtenerInstancia() {
        if (instancia == null) {
            synchronized (lock) {
                instancia = new LogicaCuenta();
            }
        }
        return instancia;
    }
   public boolean agregarSaldo(int cuentaId, double monto) {
        Cuenta cuenta = cuentas.stream()
                .filter(c -> c.getId() == cuentaId)
                .findFirst()
                .orElse(null);

        if (cuenta instanceof CajaDeAhorro caja) {
            return caja.agregarSaldo(monto);
        }
        if (cuenta instanceof CuentaCorriente cuentaCorriente) {
            return cuentaCorriente.agregarSaldo(monto);
        }
        return false;
    }


    public boolean quitarSaldo(int cuentaId, double monto){
        Cuenta cuenta = buscarCuenta(cuentaId);

        if (cuenta instanceof CajaDeAhorro caja) {
            return caja.quitarSaldo(monto);
        }
        if (cuenta instanceof CuentaCorriente cuentaCorriente) {
            return cuentaCorriente.quitarSaldo(monto);
        }
        return false;

    }

    public double consultarSaldo(int cuentaId){
        Cuenta cuenta = buscarCuenta(cuentaId);
        if (cuenta instanceof CajaDeAhorro caja) {
            return caja.getSaldo();
        }
        if (cuenta instanceof CuentaCorriente cuentaCorriente) {
            return cuentaCorriente.getSaldo();
        }
        return 0;
    }

}
