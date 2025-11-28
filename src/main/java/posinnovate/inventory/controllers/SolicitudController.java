package posinnovate.inventory.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.inventory.models.Insumo;
import posinnovate.inventory.models.Solicitud;

public class SolicitudController {

    private final SystemPOS system;
    private final Scanner scanner = new Scanner(System.in);

    public SolicitudController(SystemPOS system) {
        this.system = system;
    }

    public void solicitarInsumos() {
        if (system.supplies.isEmpty()) {
            System.out.println(" !ADVERTENCIA¡ No hay insumos para solicitar.");
            return;
        }

        System.out.println("SOLICITUD DE INSUMOS:");
        System.out.println("----------------------------------------------");
        
        for (int i = 0; i < system.supplies.size(); i++) {
            System.out.println((i + 1) + ". " + system.supplies.get(i).getNombre());
        }
        System.out.println("==============================================");
        System.out.print("\nSeleccione insumo: ");
        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        if (idx < 0 || idx >= system.supplies.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Insumo insumo = system.supplies.get(idx);

        System.out.print("Cantidad solicitada: ");
        double cantidad;
        try {
            cantidad = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad inválida.");
            return;
        }

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a cero.");
            return;
        }

        System.out.print("Solicitado por (nombre): ");
        String solicitadoPor = scanner.nextLine().trim();

        Solicitud solicitud = new Solicitud(insumo, cantidad, solicitadoPor);
        system.requisitions.add(solicitud);
        System.out.println("Solicitud registrada: " + solicitud);
    }

    public void gestionarSolicitudesInventario() {
        if (system.requisitions.isEmpty()) {
            System.out.println("¡ADVERTENCIA! No hay solicitudes registradas.");
            return;
        }

        System.out.println("GESTIÓN DE SOLICITUDES:");
        for (Solicitud s : system.requisitions) {
            System.out.println(s);
        }

        System.out.print("ID de solicitud a gestionar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Solicitud solicitud = system.requisitions.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (solicitud == null) {
            System.out.println("Solicitud no encontrada.");
            return;
        }

        if (!"PENDIENTE".equalsIgnoreCase(solicitud.getEstado())) {
            System.out.println("La solicitud ya fue gestionada.");
            return;
        }

        System.out.println("1. Aprobar");
        System.out.println("2. Rechazar");
        System.out.print("Opción: ");
        String opt = scanner.nextLine().trim();

        if ("1".equals(opt)) {
            double stockActual = solicitud.getInsumo().getStockActual();
            double cantidad = solicitud.getCantidad();

            // Validar que haya suficiente stock para descontar
            if (cantidad > stockActual) {
                System.out.println("No hay stock suficiente. Stock actual: "
                        + stockActual + " | Cantidad solicitada: " + cantidad);
                return;
            }

            double nuevoStock = stockActual - cantidad;
            solicitud.getInsumo().setStockActual(nuevoStock);
            solicitud.setEstado("APROBADA");
            System.out.println("Solicitud aprobada. Stock actualizado. Stock restante: " + nuevoStock);

        } else if ("2".equals(opt)) {
            solicitud.setEstado("RECHAZADA");
            System.out.println("Solicitud rechazada.");
        } else {
            System.out.println("Opción inválida.");
        }
    }
}
