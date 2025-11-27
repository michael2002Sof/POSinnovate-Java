package inventory.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolicitudController {

    private Sistema system;
    private Scanner sc;

    public SolicitudController(Sistema system) {
        this.system = system;
        this.sc = Sistema.input; // Scanner global
    }

    // =====================================================================
    // SOLICITAR INSUMOS (RF 5.3)
    // =====================================================================

    public void solicitarInsumos() {
        if (system.getSupplies().isEmpty()) {
            System.out.println("");
            System.out.println("========================================");
            System.out.println("¡NO HAY INSUMOS REGISTRADOS!");
            System.out.println("========================================");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaSolicitud = LocalDateTime.now().format(formatter);
        System.out.println("\nFecha de Registro: " + fechaSolicitud);

        System.out.print("Responsable: ");
        String responsable = sc.nextLine().trim();
        if (responsable.isEmpty()) {
            responsable = "Producción";
        }

        List<ItemSolicitud> itemsSolicitados = new ArrayList<>();

        while (true) {
            int codigoInt;
            while (true) {
                System.out.print("Código del insumo: ");
                String input = sc.nextLine().trim();
                try {
                    codigoInt = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Código inválido.\n");
                    System.out.print("¿Intentar otro? (SI/NO): ");
                    String retry = sc.nextLine().trim().toLowerCase();
                    if (!retry.equals("si")) {
                        break;
                    }
                }
            }

            // Si no se logró un código válido, salir
            if (codigoInt == 0) {
                // este caso se usa solo si decides manejar 0 como salida,
                // si no, simplemente sigue
            }

            String codigoStr = String.valueOf(codigoInt);

            Insumo insumo = null;
            for (Insumo i : system.getSupplies()) {
                if (i.getCodigo().equals(codigoStr)) {
                    insumo = i;
                    break;
                }
            }

            if (insumo == null) {
                System.out.println("!TU CODIGO DE INSUMO, NO FUE ENCONTRADO¡\n");
                System.out.print("¿Deseas intentar nuevamente? (SI/NO): ");
                String retry = sc.nextLine().trim().toLowerCase();
                if (!retry.equals("si")) {
                    break;
                }
                continue;
            }

            double cantidad;
            while (true) {
                System.out.print("Cantidad solicitada: ");
                String input = sc.nextLine().trim();
                try {
                    cantidad = Double.parseDouble(input);
                    if (cantidad > 0) {
                        break;
                    } else {
                        System.out.println("La cantidad debe ser mayor a 0.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad inválida, ingresa un número válido.\n");
                }
            }

            if (cantidad > insumo.getStockActual()) {
                System.out.println("========================================");
                System.out.println("ADVERTENCIA DE STOCK");
                System.out.println("Stock disponible: " + insumo.getStockActual());
                System.out.println("Cantidad solicitada: " + cantidad + " (NO DISPONIBLE)");
                System.out.println("========================================");
            }

            itemsSolicitados.add(new ItemSolicitud(insumo, cantidad));

            System.out.print("\n¿Deseas agregar otro insumo? (SI/NO): ");
            String continuar = sc.nextLine().trim().toLowerCase();
            if (!continuar.equals("si")) {
                break;
            }
        }

        if (itemsSolicitados.isEmpty()) {
            System.out.println("\nSolicitud sin items.\n");
            return;
        }

        String codigoSolicitud = String.valueOf(system.getRequisitions().size() + 5001);

        SolicitudInsumo solicitud = new SolicitudInsumo(
                codigoSolicitud,
                fechaSolicitud,
                responsable,
                itemsSolicitados
        );

        system.getRequisitions().add(solicitud);

        System.out.println("");
        System.out.println("================================================================");
        System.out.println("SOLICITUD REGISTRADA CORRECTAMENTE");
        System.out.println(solicitud);
        System.out.println("================================================================");
    }

    // =====================================================================
    // GESTIONAR SOLICITUDES (RF 2.4)
    // =====================================================================

    public List<SolicitudInsumo> obtenerSolicitudesPendientes() {
        List<SolicitudInsumo> pendientes = new ArrayList<>();
        for (SolicitudInsumo s : system.getRequisitions()) {
            if (s.getEstado().equals("pendiente")) {
                pendientes.add(s);
            }
        }
        return pendientes;
    }

    // Devuelve una lista de items cuya cantidad solicitada excede el stock
    public List<ItemSolicitud> verificarDisponibilidadSolicitud(SolicitudInsumo solicitud) {
        List<ItemSolicitud> faltantes = new ArrayList<>();
        for (ItemSolicitud item : solicitud.getItems()) {
            Insumo insumo = item.getInsumo();
            double cantidadSolicitada = item.getCantidad();
            if (cantidadSolicitada > insumo.getStockActual()) {
                faltantes.add(item);
            }
        }
        return faltantes;
    }

    public void gestionarSolicitudesInventario() {
        while (true) {
            List<SolicitudInsumo> pendientes = obtenerSolicitudesPendientes();

            if (pendientes.isEmpty()) {
                System.out.println("========================================");
                System.out.println("!NO HAY SOLICITUDES PENDIENTES¡");
                System.out.println("========================================");
                break;
            }

            System.out.println("========================================");
            System.out.println("SOLICITUDES PENDIENTES:");
            for (SolicitudInsumo s : pendientes) {
                System.out.println(
                        s.getCodigo() + " | " +
                        s.getFechaSolicitud() + " | " +
                        s.getResponsable() + " | Items: " +
                        s.getItems().size()
                );
            }
            System.out.println("========================================");

            System.out.print("\nCódigo a Gestionar (0 para Regresar): ");
            String input = sc.nextLine().trim();
            int codigoInt;

            try {
                codigoInt = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Código inválido.\n");
                continue;
            }

            if (codigoInt == 0) {
                break;
            }

            String codigoStr = String.valueOf(codigoInt);
            SolicitudInsumo solicitud = null;

            for (SolicitudInsumo s : pendientes) {
                if (s.getCodigo().equals(codigoStr)) {
                    solicitud = s;
                    break;
                }
            }

            if (solicitud == null) {
                System.out.println("¡CODIGO DE SOLICITUD NO ENCONTRADO!\n");
                continue;
            }

            System.out.println(solicitud);

            List<ItemSolicitud> faltantes = verificarDisponibilidadSolicitud(solicitud);

            if (!faltantes.isEmpty()) {
                System.out.println("\n!NO HAY STOCK SUFICIENTE PARA SER APROBADO¡\n");
                for (ItemSolicitud item : faltantes) {
                    Insumo insumo = item.getInsumo();
                    double faltante = item.getCantidad() - insumo.getStockActual();
                    System.out.println("- " + insumo.getNombre() + ": faltan " + faltante);
                }
            } else {
                System.out.println("\n!STOCK SUFICIENTE PARA SER APROBADO¡");
            }

            System.out.println("\nA = Aprobar | R = Rechazar | N = Nada");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim().toLowerCase();

            if (op.equals("a")) {
                if (!faltantes.isEmpty()) {
                    System.out.println("\nNo se puede aprobar.\n");
                    continue;
                }

                for (ItemSolicitud item : solicitud.getItems()) {
                    Insumo insumo = item.getInsumo();
                    double nuevoStock = insumo.getStockActual() - item.getCantidad();
                    insumo.setStockActual(nuevoStock);
                }

                solicitud.setEstado("aprobada");
                System.out.println("");
                System.out.println("========================================");
                System.out.println("!SOLICITUD APROBADA¡");
                System.out.println("========================================");
                System.out.println("");

            } else if (op.equals("r")) {
                solicitud.setEstado("rechazada");
                System.out.println("");
                System.out.println("========================================");
                System.out.println("!SOLICITUD RECHAZADA¡");
                System.out.println("========================================");
                System.out.println("");

            } else if (op.equals("n")) {
                System.out.println("\nSin cambios.\n");
            } else {
                System.out.println("\nOpción inválida.\n");
            }
        }
    }
}
