package org.example.Cuentas;

public interface IGestionSaldo {
    boolean agregarSaldo(double monto);
    boolean quitarSaldo(double monto);
    double getSaldo();
    int getOperaciones();

}
