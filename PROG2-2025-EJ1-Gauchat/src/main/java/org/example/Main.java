package org.example;

import org.example.Cuentas.CajaDeAhorro;
import org.example.Cuentas.Cuenta;
import org.example.Cuentas.CuentaCorriente;
import org.example.Cuentas.IGestionSaldo;
import org.example.Logica.LogicaCuenta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LogicaCuenta logica = LogicaCuenta.obtenerInstancia();
        Random rand = new Random();

        // Crear 10 cuentas aleatorias
        for (int i = 0; i < 10; i++) {
            if (rand.nextBoolean()) {
                CajaDeAhorro cuenta = new CajaDeAhorro.Builder()
                        .conId(i)
                        .conSaldo(1000)
                        .conOperaciones(0)
                        .build();
                logica.agregarCuenta(cuenta);
            } else {
                CuentaCorriente cuenta = new CuentaCorriente.Builder()
                        .conId(i)
                        .conSaldo(1000)
                        .conOperaciones(0)
                        .conGiroDescubierto(500)
                        .build();
                logica.agregarCuenta(cuenta);
            }
        }

        // Medir tiempo de ejecución
        long inicio = System.currentTimeMillis();

        // Crear 100 hilos con 100 iteraciones cada uno
        List<Thread> hilos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    int id = rand.nextInt(10);
                    double monto = rand.nextDouble(1000);
                    if (rand.nextBoolean()) {
                        logica.agregarSaldo(id, monto);
                    } else {
                        logica.quitarSaldo(id, monto);
                    }
                }
            });
            t.start();
            hilos.add(t);
        }

        // Esperar que terminen todos los hilos
        for (Thread t : hilos) {
            t.join();
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo total: " + (fin - inicio) + " ms");

        // Imprimir resultados finales
        System.out.println("----- RESULTADOS FINALES -----");
        for (int i = 0; i < 10; i++) {
            Cuenta cuenta = logica.buscarCuenta(i);
            if (cuenta != null) {
                IGestionSaldo gestion = (IGestionSaldo) cuenta;
                System.out.println("Cuenta ID: " + i);
                System.out.println("Tipo: " + cuenta.getClass().getSimpleName());
                System.out.println("Saldo: " + gestion.getSaldo());
                System.out.println("Operaciones: " + gestion.getOperaciones());

                System.out.println("-------------------------------");
            }
        }
    }
}

