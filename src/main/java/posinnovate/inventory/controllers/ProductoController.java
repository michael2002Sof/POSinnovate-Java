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
        // guardo en minúsculas para mantener consistencia
        system.products.add(new Producto("P001", "nike airmax", 60000, 300));
        system.products.add(new Producto("P002", "moyashoes", 75000, 250));
        system.products.add(new Producto("P003", "adidas sena", 80000, 200));
    }

    // ================================
    // Generar código automático P001...
    // ================================
    private String generarCodigoProducto() {
        int maxNumero = 0;

        for (Producto p : system.products) {
            String codigo = p.getCodigo();  // Asumiendo getCodigo() en Producto
            if (codigo == null) continue;

            // Extrae solo números de P001 -> 001
            String soloNumeros = codigo.replaceAll("\\D", "");
            if (soloNumeros.isEmpty()) continue;

            try {
                int num = Integer.parseInt(soloNumeros);
                if (num > maxNumero) {
                    maxNumero = num;
                }
            } catch (NumberFormatException e) {
                // Ignorar códigos que no tengan números válidos
            }
        }

        // Siguiente número
        int siguiente = maxNumero + 1;
        return String.format("P%03d", siguiente);
    }

    // ================================
    // Buscar producto por nombre
    // ================================
    private Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : system.products) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public void registrarProductos() {
        System.out.println("REGISTRO DE PRODUCTO");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim().toLowerCase();

        // ¿ya existe un producto con ese nombre?
        Producto existente = buscarProductoPorNombre(nombre);

        if (existente != null) {
            // ====================================
            // YA EXISTE → actualizar stock y precio
            // ====================================
            System.out.println("El producto ya está registrado.");
            System.out.println("Código: " + existente.getCodigo());
            System.out.println("Precio actual: " + existente.getPrecioVenta()
                    + " | Stock actual: " + existente.getStock());

            System.out.print("Cantidad adicional a agregar al stock: ");
            int cantidadAdicional;
            try {
                cantidadAdicional = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                return;
            }
            if (cantidadAdicional <= 0) {
                System.out.println("La cantidad debe ser mayor a cero.");
                return;
            }

            System.out.print("Nuevo precio de venta (se reemplaza el actual): ");
            double nuevoPrecio;
            try {
                nuevoPrecio = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Precio inválido.");
                return;
            }
            if (nuevoPrecio < 0) {
                System.out.println("El precio no puede ser negativo.");
                return;
            }

            int nuevoStock = existente.getStock() + cantidadAdicional;

            // recreamos el producto con datos actualizados
            Producto actualizado = new Producto(
                    existente.getCodigo(),
                    existente.getNombre().toLowerCase(),
                    nuevoPrecio,
                    nuevoStock
            );

            int idx = system.products.indexOf(existente);
            if (idx != -1) {
                system.products.set(idx, actualizado);
            }

            System.out.println("Producto actualizado correctamente.");
            System.out.println("Nuevo precio: " + nuevoPrecio +
                               " | Nuevo stock: " + nuevoStock);
            return;
        }

        // ================================
        // NO EXISTE → registrar nuevo
        // ================================
        System.out.print("Precio de venta: ");
        double precio;
        try {
            precio = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Precio inválido.");
            return;
        }

        System.out.print("Stock inicial: ");
        int stock;
        try {
            stock = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Stock inválido.");
            return;
        }

        String codigo = generarCodigoProducto();
        System.out.println("Código generado: " + codigo);

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
