package inventory.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProductController {

    private Sistema system;
    private Scanner sc;

    public ProductController(Sistema system) {
        this.system = system;
        this.sc = Sistema.input; // Scanner global
    }

    // ===============================================================
    // REGISTRAR PRODUCTOS (RF 5.2)
    // ===============================================================

    public void registrarProductos() {

        List<String> tiposValidos = Arrays.asList("deportivo", "casual", "formal", "sandalia", "bota");

        while (true) {

            // Fecha de registro
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaRegistro = LocalDateTime.now().format(formatter);
            System.out.println("\nFecha de Registro: " + fechaRegistro);

            System.out.print("Marca: ");
            String marca = sc.nextLine().trim().toLowerCase();

            System.out.print("Modelo: ");
            String modelo = sc.nextLine().trim().toLowerCase();

            String tipo;
            while (true) {
                System.out.print("Tipo (" + String.join(", ", tiposValidos) + "): ");
                tipo = sc.nextLine().trim().toLowerCase();
                if (tiposValidos.contains(tipo)) break;
                System.out.println("Tipo inválido, ingresa uno permitido.\n");
            }

            int talla;
            while (true) {
                System.out.print("Talla (36-45): ");
                String input = sc.nextLine().trim();
                try {
                    talla = Integer.parseInt(input);
                    if (talla >= 36 && talla <= 45) break;
                    System.out.println("Talla inválida.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Talla inválida.\n");
                }
            }

            System.out.print("Color: ");
            String color = sc.nextLine().trim().toLowerCase();

            // ===================================================================
            // SI EXISTE - MODIFICAR
            // ===================================================================
            Producto existente = null;
            for (Producto p : system.getProducts()) {
                if (p.getMarca().equals(marca) &&
                    p.getModelo().equals(modelo) &&
                    p.getTipo().equals(tipo) &&
                    p.getTalla() == talla &&
                    p.getColor().equals(color)) {

                    existente = p;
                    break;
                }
            }

            if (existente != null) {

                System.out.println("========================================");
                System.out.println("ADVERTENCIA:");
                System.out.println("Producto ya registrado.");
                System.out.println("========================================");

                while (true) {
                    System.out.print("\n¿Desea actualizar cantidad y precio? (SI/NO): ");
                    String op = sc.nextLine().trim().toLowerCase();

                    if (op.equals("si")) {

                        int extra;
                        while (true) {
                            System.out.print("Cantidad a agregar: ");
                            String input = sc.nextLine().trim();
                            try {
                                extra = Integer.parseInt(input);
                                if (extra >= 0) break;
                                System.out.println("La cantidad debe ser 0 o mayor.\n");
                            } catch (NumberFormatException e) {
                                System.out.println("Cantidad inválida.\n");
                            }
                        }

                        double nuevoPrecio;
                        while (true) {
                            System.out.print("Nuevo precio (o enter para mantener): ");
                            String precioInput = sc.nextLine().trim();

                            if (precioInput.equals("")) {
                                nuevoPrecio = existente.getPrecioVenta(); // mantener precio
                                break;
                            }

                            try {
                                double precioTmp = Double.parseDouble(precioInput);
                                if (precioTmp >= 0) {
                                    nuevoPrecio = precioTmp;
                                    break;
                                } else {
                                    System.out.println("El precio debe ser >= 0\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Precio inválido.\n");
                            }
                        }

                        // Actualizar
                        existente.setCantidad(existente.getCantidad() + extra);
                        existente.setPrecioVenta(nuevoPrecio);
                        existente.setEstado(existente.getCantidad() > 0 ? "disponible" : "agotado");

                        System.out.println("");
                        System.out.println("========================================");
                        System.out.println("¡PRODUCTO ACTUALIZADO CORRECTAMENTE!");
                        System.out.println("========================================\n");
                        break;

                    } else if (op.equals("no")) {
                        System.out.println("¡NO SE MODIFICÓ EL PRODUCTO!.\n");
                        break;

                    } else {
                        System.out.println("Opción inválida.\n");
                    }
                }

                System.out.print("¿Registrar otro? (SI/NO): ");
                String cont = sc.nextLine().trim().toLowerCase();
                if (!cont.equals("si")) break;
                continue;
            }

            // ===================================================================
            // NO EXISTE - REGISTRAR NUEVO
            // ===================================================================
            int cantidad;
            while (true) {
                System.out.print("Cantidad: ");
                String input = sc.nextLine().trim();
                try {
                    cantidad = Integer.parseInt(input);
                    if (cantidad > 0) break;
                    System.out.println("La cantidad debe ser mayor a 0.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad inválida.\n");
                }
            }

            double precioVenta;
            while (true) {
                System.out.print("Precio venta: ");
                String input = sc.nextLine().trim();
                try {
                    precioVenta = Double.parseDouble(input);
                    if (precioVenta > 0) break;
                    System.out.println("El precio debe ser mayor a 0.\n");
                } catch (NumberFormatException e) {
                    System.out.println("Precio inválido.\n");
                }
            }

            String codigo = String.valueOf(system.getProducts().size() + 3001);
            Producto producto = new Producto(
                    codigo,
                    fechaRegistro,
                    marca,
                    modelo,
                    tipo,
                    talla,
                    color,
                    cantidad,
                    precioVenta
            );

            system.getProducts().add(producto);

            System.out.println("\nProducto registrado.\n");

            System.out.print("¿Registrar otro? (SI/NO): ");
            String cont = sc.nextLine().trim().toLowerCase();
            if (!cont.equals("si")) break;
        }
    }

    // =====================================================================
    // PRODUCTOS REGISTRADOS DE FORMA INICIAL
    // =====================================================================

    public void cargarProductosIniciales() {
        if (!system.getProducts().isEmpty()) return;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaHoy = LocalDateTime.now().format(formatter);

        Producto p1 = new Producto(
                String.valueOf(system.getProducts().size() + 3001),
                fechaHoy, "nike", "airmax", "deportivo", 40, "negro", 40, 60000
        );
        system.getProducts().add(p1);

        Producto p2 = new Producto(
                String.valueOf(system.getProducts().size() + 3001),
                fechaHoy, "adidas", "runfast", "deportivo", 38, "blanco", 60, 48000
        );
        system.getProducts().add(p2);

        Producto p3 = new Producto(
                String.valueOf(system.getProducts().size() + 3001),
                fechaHoy, "clarks", "classic", "formal", 42, "café", 50, 55000
        );
        system.getProducts().add(p3);
    }

    // ===============================================================
    // CONSULTA DE INSUMOS DESDE PRODUCCIÓN (RF 5.1)
    // ===============================================================

    public void consultarInsumosProduccion() {

        if (system.getSupplies().isEmpty()) {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("¡NO HAY INSUMOS REGISTRADOS!");
            System.out.println("========================================");
            return;
        }

        while (true) {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("CONSULTA DE INSUMOS - PRODUCCIÓN");
            System.out.println("========================================");
            System.out.println("1. Buscar por código");
            System.out.println("2. Buscar por nombre");
            System.out.println("3. Ver todos");
            System.out.println("4. Volver");
            System.out.println("========================================");

            System.out.print("Opción: ");
            String opcion = sc.nextLine().trim();

            if (opcion.equals("1")) {
                System.out.print("Código: ");
                String c = sc.nextLine().trim();

                Insumo encontrado = null;
                for (Insumo i : system.getSupplies()) {
                    if (i.getCodigo().equals(c)) {
                        encontrado = i;
                        break;
                    }
                }

                System.out.println(encontrado != null ? encontrado : "No encontrado.\n");

            } else if (opcion.equals("2")) {
                System.out.print("Nombre: ");
                String n = sc.nextLine().trim().toLowerCase();

                boolean alguno = false;
                for (Insumo i : system.getSupplies()) {
                    if (i.getNombre().toLowerCase().contains(n)) {
                        System.out.println(i);
                        alguno = true;
                    }
                }

                if (!alguno) System.out.println("No encontrados.\n");

            } else if (opcion.equals("3")) {
                for (Insumo i : system.getSupplies()) {
                    System.out.println(i);
                }

            } else if (opcion.equals("4")) {
                break;

            } else {
                System.out.println("Opción inválida.\n");
            }
        }
    }
}
