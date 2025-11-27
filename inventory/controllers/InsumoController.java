package inventory.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InsumoController {

    private Sistema system;
    private Scanner sc;

    public InsumoController(Sistema system) {
        this.system = system;
        this.sc = Sistema.input; // Scanner global definido en Sistema
    }

    // ============================================================================================
    // REGISTRO / MODIFICACIÓN DE INSUMO (RF 2.1)
    // ============================================================================================

    public void registrarInsumos() {
        List<String> unidadesValidas = Arrays.asList("litros", "metros", "unidad", "unidades");

        while (true) {
            System.out.print("\nIngresa el nombre del insumo: ");
            String nombre = sc.nextLine().trim().toLowerCase();

            // Verificar si existe
            Insumo insumoExistente = null;
            for (Insumo i : system.getSupplies()) {
                if (i.getNombre().toLowerCase().equals(nombre)) {
                    insumoExistente = i;
                    break;
                }
            }

            // ===================================================================
            // SI EXISTE - MODIFICAR
            // ===================================================================
            if (insumoExistente != null) {
                System.out.println("========================================");
                System.out.println("ADVERTENCIA:");
                System.out.println("El insumo '" + nombre + "' ya está REGISTRADO.");
                System.out.println("========================================");

                while (true) {
                    System.out.print("\n¿Desea modificar cantidad y costo? (SI/NO): ");
                    String opcion = sc.nextLine().trim().toLowerCase();

                    if (opcion.equals("si")) {

                        double cantidadNueva;
                        while (true) {
                            System.out.print("Cantidad a agregar: ");
                            String input = sc.nextLine().trim();
                            try {
                                cantidadNueva = Double.parseDouble(input);
                                if (cantidadNueva > 0) {
                                    break;
                                }
                                System.out.println("Cantidad inválida.\n");
                            } catch (NumberFormatException e) {
                                System.out.println("Cantidad inválida.\n");
                            }
                        }

                        double costoNuevo;
                        while (true) {
                            System.out.print("Costo a sumar: ");
                            String input = sc.nextLine().trim();
                            try {
                                costoNuevo = Double.parseDouble(input);
                                if (costoNuevo > 0) {
                                    break;
                                }
                                System.out.println("Costo inválido.\n");
                            } catch (NumberFormatException e) {
                                System.out.println("Costo inválido.\n");
                            }
                        }

                        // Actualizar insumo
                        insumoExistente.agregarStock(cantidadNueva, costoNuevo);

                        // Registrar gasto en finanzas
                        if (system.getControllerFinance() != null) {
                            system.getControllerFinance()
                                  .registrarGastoInsumo(insumoExistente, costoNuevo);
                        }

                        System.out.println("");
                        System.out.println("========================================");
                        System.out.println("¡INSUMO ACTUALIZADO CORRECTAMENTE!");
                        System.out.println("========================================");
                        System.out.println("");
                        break;

                    } else if (opcion.equals("no")) {
                        System.out.println("¡NO SE MODIFICÓ EL INSUMO!.\n");
                        break;
                    } else {
                        System.out.println("Opción inválida. Responda SI o NO.\n");
                    }
                }

                System.out.print("¿Registrar otro? (SI/NO): ");
                String cont = sc.nextLine().trim().toLowerCase();
                if (!cont.equals("si")) {
                    break;
                }
                continue;
            }

            // ===================================================================
            // NO EXISTE - REGISTRAR NUEVO
            // ===================================================================

            String unidadMedida;
            while (true) {
                System.out.print("Unidad de medida (Litros/Metros/Unidad/Unidades): ");
                unidadMedida = sc.nextLine().trim().toLowerCase();
                if (unidadesValidas.contains(unidadMedida)) {
                    break;
                }
                System.out.println("Unidad inválida, ingresa una unidad de medida permitida.\n");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaRegistro = LocalDateTime.now().format(formatter);
            System.out.println("\nFecha de Registro: " + fechaRegistro);

            double stockActual;
            while (true) {
                System.out.print("Stock inicial: ");
                String input = sc.nextLine().trim();
                try {
                    stockActual = Double.parseDouble(input);
                    if (stockActual > 0) {
                        break;
                    } else {
                        System.out.println("El stock inicial debe ser mayor a 0.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad inválida.\n");
                }
            }

            double stockMinimo;
            while (true) {
                System.out.print("Stock mínimo: ");
                String input = sc.nextLine().trim();
                try {
                    stockMinimo = Double.parseDouble(input);
                    if (stockMinimo > 0) {
                        break;
                    } else {
                        System.out.println("El stock mínimo debe ser mayor a 0.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad inválida.\n");
                }
            }

            double costo;
            while (true) {
                System.out.print("Costo inicial: ");
                String input = sc.nextLine().trim();
                try {
                    costo = Double.parseDouble(input);
                    if (costo > 0) {
                        break;
                    } else {
                        System.out.println("El costo inicial debe ser mayor a 0.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Costo inválido.\n");
                }
            }

            String codigo = String.valueOf(system.getSupplies().size() + 1001);
            Insumo insumo = new Insumo(
                    codigo,
                    nombre,
                    unidadMedida,
                    stockActual,
                    stockMinimo,
                    costo,
                    fechaRegistro
            );
            system.getSupplies().add(insumo);

            // Registrar gasto en finanzas
            if (system.getControllerFinance() != null) {
                system.getControllerFinance().registrarGastoInsumo(insumo, costo);
            }

            System.out.println("");
            System.out.println("========================================");
            System.out.println("INSUMO REGISTRADO CORRECTAMENTE.");
            System.out.println("========================================");

            System.out.print("\n¿Registrar otro? (SI/NO): ");
            String cont = sc.nextLine().trim().toLowerCase();
            if (!cont.equals("si")) {
                break;
            }
        }
    }

    // ============================================================================================
    // CONSULTA DE INSUMOS (RF 2.2)
    // ============================================================================================

    public void consultarInsumos() {
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
            System.out.println("CONSULTA DE INSUMOS");
            System.out.println("========================================");
            System.out.println("1. Buscar por código");
            System.out.println("2. Buscar por nombre");
            System.out.println("3. Ver todos");
            System.out.println("4. Volver");
            System.out.println("========================================");
            System.out.println("");

            System.out.print("Ingresa la opcion que deseas realizar: ");
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

                if (encontrado != null) {
                    System.out.println(encontrado);
                } else {
                    System.out.println("¡INSUMO NO ENCONTRADO!");
                }

            } else if (opcion.equals("2")) {
                System.out.print("Nombre: ");
                String nombre = sc.nextLine().trim().toLowerCase();

                boolean alguno = false;
                for (Insumo i : system.getSupplies()) {
                    if (i.getNombre().toLowerCase().contains(nombre)) {
                        System.out.println(i);
                        alguno = true;
                    }
                }

                if (!alguno) {
                    System.out.println("¡INSUMO NO ENCONTRADO!");
                }

            } else if (opcion.equals("3")) {
                for (Insumo i : system.getSupplies()) {
                    System.out.println(i);
                }

            } else if (opcion.equals("4")) {
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    // ============================================================================================
    // FUNCIONES DE ALERTAS (RF 2.3)
    // ============================================================================================

    public void mostrarAlertasStock() {
        List<Insumo> alertas = system.getSupplies().stream()
                .filter(i -> i.getStockActual() <= i.getStockMinimo())
                .toList();

        if (alertas.isEmpty()) {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("!NO HAY ALERTAS DE STOCK BAJO¡");
            System.out.println("========================================");
            return;
        }

        System.out.println("");
        System.out.println("========================================");
        System.out.println("ALERTAS DE STOCK BAJO");
        for (Insumo i : alertas) {
            System.out.println("[" + i.getCodigo() + "] " +
                    i.getNombre() +
                    " | Stock: " + i.getStockActual() +
                    " / Mínimo: " + i.getStockMinimo());
        }
        System.out.println("========================================");
    }

    public void mostrarResumenAlertas() {
        long cantidadAlertas = system.getSupplies().stream()
                .filter(i -> i.getStockActual() <= i.getStockMinimo())
                .count();

        if (cantidadAlertas > 0) {
            System.out.println("");
            System.out.println("========================================");
            System.out.println(cantidadAlertas + " INSUMO(S) CON STOCK BAJO.");
            System.out.println("========================================");
        }
    }

    // =====================================================================
    // INSUMOS REGISTRADOS DE FORMA INICIAL
    // =====================================================================

    public void cargarInsumosIniciales() {
        if (!system.getSupplies().isEmpty()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaHoy = LocalDateTime.now().format(formatter);

        crearInsumoInicial("super", "litros", 40, 5, 75000, fechaHoy);
        crearInsumoInicial("suelas", "unidades", 80, 10, 48000, fechaHoy);
        crearInsumoInicial("malla", "metros", 50, 8, 90000, fechaHoy);
    }

    private void crearInsumoInicial(String nombre, String unidad,
                                    double stock, double minimo,
                                    double costo, String fechaHoy) {

        String codigo = String.valueOf(system.getSupplies().size() + 1001);

        Insumo insumo = new Insumo(
                codigo,
                nombre,
                unidad,
                stock,
                minimo,
                costo,
                fechaHoy
        );
        system.getSupplies().add(insumo);

        if (system.getControllerFinance() != null) {
            system.getControllerFinance().registrarGastoInsumo(insumo, costo);
        }
    }
}
