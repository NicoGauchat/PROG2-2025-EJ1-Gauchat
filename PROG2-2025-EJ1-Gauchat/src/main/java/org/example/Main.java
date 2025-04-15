package org.example;

import org.example.Cuentas.CajaDeAhorro;
import org.example.Cuentas.Cuenta;
import org.example.Cuentas.CuentaCorriente;
import org.example.Cuentas.IGestionSaldo;
import org.example.Logica.LogicaCuenta;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        LogicaCuenta logica = LogicaCuenta.obtenerInstancia();
        Random rand = new Random();

        // Crear 10 cuentas aleatorias
        for (int i = 0; i < 10; i++) {
            if (rand.nextBoolean()) {
                CajaDeAhorro cuenta = new CajaDeAhorro.Builder()
                        .conId(i)
                        .build();
                logica.agregarCuenta(cuenta);
            } else {
                CuentaCorriente cuenta = new CuentaCorriente.Builder()
                        .conId(i)
                        .conGiroDescubierto(500)
                        .build();
                logica.agregarCuenta(cuenta);
            }
        }
        long inicio = System.currentTimeMillis();
        // Crear un pool de hilos
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Ejecutar 10.000 operaciones concurrentes
        for (int i = 0; i < 10_000; i++) {
            executor.execute(() -> {
                int id = rand.nextInt(10);
                double monto = rand.nextDouble(100);

                if (rand.nextBoolean()) {
                    logica.agregarSaldo(id, monto);
                } else {
                    logica.quitarSaldo(id, monto);
                }
            });
        }

        // Apagar el pool de hilos y esperar que terminen
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Espera activa
        }
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo total: " + (fin - inicio) + " ms");
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

