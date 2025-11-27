package sale.controllers;

import sale.models.Sale;
import sale.models.SaleItem;
import inventory.models.Producto;
import system.SystemApp;   

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaleController {

    private SystemApp system;
    private Scanner scanner;

    public SaleController(SystemApp system) {
        this.system = system;
        this.scanner = SystemApp.input; // Scanner global
    }

    public void registrarVenta() {

        if (system.getProducts().isEmpty()) {
            System.out.println("\n============================================================");
            System.out.println("¡NO HAY PRODUCTOS REGISTRADOS EN INVENTARIO!");
            System.out.println("============================================================");
            return;
        }

        System.out.println("\n============================================================");
        System.out.println("REGISTRO DE VENTA");
        System.out.println("============================================================");

        System.out.print("Nombre del cliente: ");
        String customer = scanner.nextLine().trim();

        if (customer.isEmpty()) {
            System.out.println("Nombre obligatorio. Venta cancelada.");
            return;
        }

        List<SaleItem> items = new ArrayList<>();

        while (true) {
            System.out.print("\nCódigo del producto (0 para finalizar): ");
            String codigo = scanner.nextLine().trim();

            if (codigo.equals("0")) break;

            Producto producto = system.getProducts().stream()
                    .filter(p -> p.getCodigo().equals(codigo))
                    .findFirst()
                    .orElse(null);

            if (producto == null) {
                System.out.println("¡PRODUCTO NO ENCONTRADO!");
                continue;
            }

            if (producto.getCantidad() <= 0) {
                System.out.println("Producto sin stock disponible.");
                continue;
            }

            System.out.println(producto);

            int cantidad;
            while (true) {
                System.out.print("Cantidad a vender: ");
                String c = scanner.nextLine().trim();

                try {
                    cantidad = Integer.parseInt(c);
                    if (cantidad > 0 && cantidad <= producto.getCantidad()) break;
                    System.out.println("Cantidad inválida.");
                } catch (Exception e) {
                    System.out.println("Cantidad inválida.");
                }
            }

            items.add(new SaleItem(producto, cantidad, producto.getPrecioVenta()));

            System.out.print("¿Agregar otro producto? (SI/NO): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("si")) break;
        }

        if (items.isEmpty()) {
            System.out.println("\nNo se agregaron productos. Venta cancelada.");
            return;
        }

        System.out.println("\nFORMA DE PAGO");
        System.out.println("1. Efectivo");
        System.out.println("2. Transferencia");
        System.out.println("3. Tarjeta");

        System.out.print("Opción: ");
        String pago = scanner.nextLine().trim();

        String metodoPago = switch (pago) {
            case "1" -> "Efectivo";
            case "2" -> "Transferencia";
            case "3" -> "Tarjeta";
            default -> "No especificado";
        };

        int code = system.getSales().size() + 7001;
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        Sale venta = new Sale(code, fecha, customer, items, metodoPago);

        for (SaleItem item : items) {
            Producto p = item.getProduct();
            p.setCantidad(p.getCantidad() - item.getQuantity());
        }

        system.getSales().add(venta);

        System.out.println("\n============================================================");
        System.out.println("¡VENTA REGISTRADA CORRECTAMENTE!");
        System.out.println("============================================================");

        if (system.getControllerFinance() != null) {
            system.getControllerFinance().registrarIngresoVenta(venta);
        }

        mostrarVoucher(venta);
    }

    public void mostrarVoucher(Sale venta) {
        System.out.println();
        System.out.println(venta);
    }

    public void consultarProductosDisponibles() {

        if (system.getProducts().isEmpty()) {
            System.out.println("\n========================================");
            System.out.println("¡NO HAY PRODUCTOS REGISTRADOS!");
            System.out.println("========================================");
            return;
        }

        while (true) {
            System.out.println("\n========================================");
            System.out.println("CONSULTA DE PRODUCTOS");
            System.out.println("========================================");
            System.out.println("1. Buscar por código");
            System.out.println("2. Buscar por marca/modelo/tipo");
            System.out.println("3. Ver todos");
            System.out.println("4. Volver");
            System.out.println("========================================");
            System.out.print("Opción: ");

            String op = scanner.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Código: ");
                    String codigo = scanner.nextLine().trim();

                    var encontrado = system.getProducts().stream()
                            .filter(p -> p.getCodigo().equals(codigo))
                            .findFirst()
                            .orElse(null);

                    System.out.println(encontrado != null ? encontrado : "¡PRODUCTO NO ENCONTRADO!");
                }

                case "2" -> {
                    System.out.print("Texto a buscar: ");
                    String texto = scanner.nextLine().trim().toLowerCase();

                    boolean alguno = false;

                    for (Producto p : system.getProducts()) {
                        if (p.getMarca().contains(texto) ||
                                p.getModelo().contains(texto) ||
                                p.getTipo().contains(texto)) {
                            System.out.println(p);
                            alguno = true;
                        }
                    }

                    if (!alguno) System.out.println("¡NO SE ENCONTRARON PRODUCTOS!");
                }

                case "3" -> system.getProducts().forEach(System.out::println);
                case "4" -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}
