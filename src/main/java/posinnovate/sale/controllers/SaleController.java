package posinnovate.sale.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.inventory.models.Producto;
import posinnovate.sale.models.Sale;
import posinnovate.sale.models.SaleItem;
import posinnovate.finance.models.FinancialMovement;

public class SaleController {

    private final SystemPOS system;
    private final Scanner scanner = new Scanner(System.in);

    public SaleController(SystemPOS system) {
        this.system = system;
    }

    public void consultarProductosDisponibles() {
        if (system.products.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("PRODUCTOS DISPONIBLES:");
        for (int i = 0; i < system.products.size(); i++) {
            Producto p = system.products.get(i);
            System.out.println((i + 1) + ". " + p.toString());
        }
    }

    public void registrarVenta() {
        if (system.products.isEmpty()) {
            System.out.println("No hay productos para vender.");
            return;
        }

        Sale sale = new Sale(seleccionarMetodoPago());
        while (true) {
            consultarProductosDisponibles();
            System.out.println("0. Terminar venta");
            System.out.print("Seleccione producto: ");
            String opt = scanner.nextLine().trim();
            if ("0".equals(opt)) {
                break;
            }

            int idx;
            try {
                idx = Integer.parseInt(opt) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }
            if (idx < 0 || idx >= system.products.size()) {
                System.out.println("Opción inválida.");
                continue;
            }

            Producto p = system.products.get(idx);
            System.out.print("Cantidad: ");
            int cantidad;
            try {
                cantidad = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor a 0.");
                continue;
            }
            if (cantidad > p.getStock()) {
                System.out.println("Stock insuficiente. Stock actual: " + p.getStock());
                continue;
            }

            p.setStock(p.getStock() - cantidad);
            SaleItem item = new SaleItem(p, cantidad, p.getPrecioVenta());
            sale.addItem(item);
            System.out.println("Producto agregado a la venta.");
        }

        if (sale.getItems().isEmpty()) {
            System.out.println("Venta cancelada (sin ítems).");
            return;
        }

        system.sales.add(sale);
        FinancialMovement ingreso = new FinancialMovement(
                "INGRESO", "Venta #" + sale.getId(), sale.getTotal(), "SALE-" + sale.getId());
        system.financialMovements.add(ingreso);

        System.out.println("VENTA REGISTRADA:");
        System.out.println(sale);
    }

    private String seleccionarMetodoPago() {
        System.out.println("Seleccione método de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        System.out.println("3. Transferencia");
        System.out.print("Opción: ");
        String opt = scanner.nextLine().trim();
        switch (opt) {
            case "1": return "Efectivo";
            case "2": return "Tarjeta";
            case "3": return "Transferencia";
            default: return "Otro";
        }
    }
}
