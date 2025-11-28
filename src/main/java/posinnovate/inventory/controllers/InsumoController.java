package posinnovate.inventory.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.inventory.models.Insumo;

public class InsumoController {

    private final SystemPOS system;
    private final Scanner scanner = new Scanner(System.in);

    public InsumoController(SystemPOS system) {
        this.system = system;
    }

    public void cargarInsumosIniciales() {
        if (!system.supplies.isEmpty()) return;
        // Guardamos nombres y unidades en minúscula
        system.supplies.add(new Insumo("I001", "super",  "litros",    100, 20, 75000, ""));
        system.supplies.add(new Insumo("I002", "suelas", "unidades",  80,  50, 30000, ""));
        system.supplies.add(new Insumo("I003", "malla",  "metros",    50, 100, 80000, ""));
    }

    // =========================
    // Código automático I001...
    // =========================
    private String generarCodigoInsumo() {
        int maxNumero = 0;

        for (Insumo insumo : system.supplies) {
            String codigo = insumo.getCodigo();
            if (codigo == null) continue;

            String soloNumeros = codigo.replaceAll("\\D", "");
            if (soloNumeros.isEmpty()) continue;

            try {
                int num = Integer.parseInt(soloNumeros);
                if (num > maxNumero) {
                    maxNumero = num;
                }
            } catch (NumberFormatException e) {
                // Ignorar códigos raros
            }
        }

        int siguiente = maxNumero + 1;
        return String.format("I%03d", siguiente);
    }

    // =========================
    // Buscar por nombre + unidad
    // =========================
    private Insumo buscarInsumoPorNombreYUnidad(String nombre, String unidad) {
        for (Insumo i : system.supplies) {
            if (i.getNombre().equalsIgnoreCase(nombre)
                    && i.getUnidadMedida().equalsIgnoreCase(unidad)) {
                return i;
            }
        }
        return null;
    }

    public void registrarInsumos() {
        System.out.println("REGISTRO DE INSUMO");

        // Forzar a minúsculas
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim().toLowerCase();

        System.out.print("Unidad de medida: ");
        String unidad = scanner.nextLine().trim().toLowerCase();

        // Verificar si ya existe un insumo con ese nombre + unidad
        Insumo existente = buscarInsumoPorNombreYUnidad(nombre, unidad);

        if (existente != null) {
            // ============================
            // YA EXISTE → actualizar stock y costo
            // ============================
            System.out.println("El insumo ya está registrado.");
            System.out.println("Código: " + existente.getCodigo());
            System.out.println("Stock actual: " + existente.getStockActual()
                    + " | Costo actual: " + existente.getCosto());

            System.out.print("Cantidad adicional a agregar al stock: ");
            double cantidadAdicional;
            try {
                cantidadAdicional = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                return;
            }
            if (cantidadAdicional <= 0) {
                System.out.println("La cantidad debe ser mayor a cero.");
                return;
            }

            System.out.print("Costo adicional a agregar: ");
            double costoAdicional;
            try {
                costoAdicional = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Costo inválido.");
                return;
            }
            if (costoAdicional < 0) {
                System.out.println("El costo no puede ser negativo.");
                return;
            }

            // Calcular nuevos valores
            double nuevoStock = existente.getStockActual() + cantidadAdicional;
            double nuevoCosto = existente.getCosto() + costoAdicional;

            // Crear un nuevo objeto Insumo con los valores actualizados
            Insumo actualizado = new Insumo(
                    existente.getCodigo(),
                    existente.getNombre().toLowerCase(),
                    existente.getUnidadMedida().toLowerCase(),
                    nuevoStock,
                    existente.getStockMinimo(),
                    nuevoCosto,
                    existente.getFechaRegistro() // mantenemos la misma fecha de registro
            );

            // Reemplazar el objeto en la lista
            int idx = system.supplies.indexOf(existente);
            if (idx != -1) {
                system.supplies.set(idx, actualizado);
            }

            System.out.println("Insumo actualizado correctamente.");
            System.out.println("Nuevo stock: " + nuevoStock
                    + " | Nuevo costo: " + nuevoCosto);
            return;
        }

        // ============================
        // NO EXISTE → registrar nuevo
        // ============================
        System.out.print("Stock actual inicial: ");
        double stockActual;
        try {
            stockActual = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Stock inválido.");
            return;
        }

        System.out.print("Stock mínimo: ");
        double stockMin;
        try {
            stockMin = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Stock mínimo inválido.");
            return;
        }

        System.out.print("Costo: ");
        double costo;
        try {
            costo = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Costo inválido.");
            return;
        }

        String codigo = generarCodigoInsumo();
        System.out.println("Código generado: " + codigo);

        Insumo insumo = new Insumo(codigo, nombre, unidad, stockActual, stockMin, costo, "");
        system.supplies.add(insumo);
        System.out.println("Insumo registrado correctamente.");
    }

    public void consultarInsumos() {
        if (system.supplies.isEmpty()) {
            System.out.println("No hay insumos registrados.");
            return;
        }
        System.out.println("LISTA DE INSUMOS:");
        for (Insumo i : system.supplies) {
            System.out.println(i);
        }
    }

    public void mostrarAlertasStock() {
        boolean hayAlertas = false;
        for (Insumo i : system.supplies) {
            if (i.getStockActual() <= i.getStockMinimo()) {
                if (!hayAlertas) {
                    System.out.println("ALERTAS DE STOCK MÍNIMO:");
                    hayAlertas = true;
                }
                System.out.println(" - " + i.getNombre() + " | Stock: " + i.getStockActual()
                        + " | Mínimo: " + i.getStockMinimo());
            }
        }
        if (!hayAlertas) {
            System.out.println("No hay alertas de stock.");
        }
    }
}
