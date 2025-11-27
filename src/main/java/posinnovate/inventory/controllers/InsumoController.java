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
        system.supplies.add(new Insumo("I001", "Harina", "kg", 100, 20, 2500, ""));
        system.supplies.add(new Insumo("I002", "Azúcar", "kg", 80, 15, 3000, ""));
        system.supplies.add(new Insumo("I003", "Café", "kg", 50, 10, 8000, ""));
    }

    public void registrarInsumos() {
        System.out.println("REGISTRO DE INSUMO");
        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Unidad de medida: ");
        String unidad = scanner.nextLine().trim();
        System.out.print("Stock actual: ");
        double stockActual = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Stock mínimo: ");
        double stockMin = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Costo por unidad: ");
        double costo = Double.parseDouble(scanner.nextLine().trim());

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
                System.out.println(" - " + i.getNombre() + " | Stock: " + i.getStockActual() +
                                   " | Mínimo: " + i.getStockMinimo());
            }
        }
        if (!hayAlertas) {
            System.out.println("No hay alertas de stock.");
        }
    }
}
