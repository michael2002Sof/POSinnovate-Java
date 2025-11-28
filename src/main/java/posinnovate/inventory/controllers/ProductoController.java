package posinnovate.inventory.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.inventory.models.*;

public class ProductoController {

    private final SystemPOS system;
    private final Scanner scanner = new Scanner(System.in);

    public ProductoController(SystemPOS system) {
        this.system = system;
    }

    public void cargarProductosIniciales() {
        if (!system.products.isEmpty()) return;
        system.products.add(new Producto("P001", "Nike airmax", 60000, 300));
        system.products.add(new Producto("P002", "MoyaShoes", 75000, 250));
        system.products.add(new Producto("P003", "adidas sena", 80000, 200));
    }

    public void registrarProductos() {
        System.out.println("REGISTRO DE PRODUCTO");
        System.out.print("Código: ");
        String codigo = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Precio de venta: ");
        double precio = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Stock inicial: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());

        Producto p = new Producto(codigo, nombre, precio, stock);
        system.products.add(p);
        System.out.println("Producto registrado correctamente.");
    }

    public void consultarInsumosProduccion() {
        System.out.println("CONSULTA DE INSUMOS (ejemplo simple):");
        if (system.supplies.isEmpty()) {
            System.out.println("No hay insumos registrados.");
            return;
        }
        for (Insumo i : system.supplies) {
            System.out.println(i);
        }
    }
}
