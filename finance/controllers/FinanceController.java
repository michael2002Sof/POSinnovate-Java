package finance.controllers;

import finance.models.FinancialMovement;
import sale.models.Sale;
import inventory.models.Insumo;
import system.SystemApp;   

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinanceController {

    private SystemApp system;

    public FinanceController(SystemApp system) {
        this.system = system;
    }

    public void registrarGastoInsumo(Insumo insumo, double monto, String detalleExtra) {
        if (monto <= 0) return;

        int code = system.getFinancialMovements().size() + 9001;
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        String concept = ("Compra insumo '" + insumo.getNombre() + "' " +
                (detalleExtra != null ? detalleExtra : "")).trim();

        FinancialMovement mov = new FinancialMovement(
                code, date, "gasto", concept, monto, insumo
        );

        system.getFinancialMovements().add(mov);
    }

    public void registrarIngresoVenta(Sale venta) {
        if (venta.getTotal() <= 0) return;

        int code = system.getFinancialMovements().size() + 9001;

        FinancialMovement mov = new FinancialMovement(
                code,
                venta.getDate(),
                "ingreso",
                "Venta #" + venta.getCode() + " al cliente " + venta.getCustomerName(),
                venta.getTotal(),
                venta
        );

        system.getFinancialMovements().add(mov);
    }

    public void reporteGastosInsumos() {
        var gastos = system.getFinancialMovements().stream()
                .filter(m -> m.getMovementType().equals("gasto"))
                .toList();

        if (gastos.isEmpty()) {
            System.out.println("\n============================================================");
            System.out.println("NO HAY GASTOS REGISTRADOS (INSUMOS).");
            System.out.println("============================================================");
            return;
        }

        System.out.println("\n============================================================");
        System.out.println("REPORTE DE GASTOS - INSUMOS");
        System.out.println("============================================================");

        double total = 0;
        for (FinancialMovement m : gastos) {
            System.out.println(m);
            total += m.getAmount();
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("TOTAL GASTOS EN INSUMOS: $" + total);
        System.out.println("============================================================");
    }

    public void reporteIngresosVentas() {
        var ingresos = system.getFinancialMovements().stream()
                .filter(m -> m.getMovementType().equals("ingreso"))
                .toList();

        if (ingresos.isEmpty()) {
            System.out.println("\n============================================================");
            System.out.println("NO HAY INGRESOS REGISTRADOS (VENTAS).");
            System.out.println("============================================================");
            return;
        }

        System.out.println("\n============================================================");
        System.out.println("REPORTE DE INGRESOS - VENTAS");
        System.out.println("============================================================");

        double total = 0;
        for (FinancialMovement m : ingresos) {
            System.out.println(m);
            total += m.getAmount();
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("TOTAL INGRESOS POR VENTAS: $" + total);
        System.out.println("============================================================");
    }

    public void historialMovimientos() {
        if (system.getFinancialMovements().isEmpty()) {
            System.out.println("\n============================================================");
            System.out.println("NO HAY MOVIMIENTOS FINANCIEROS REGISTRADOS.");
            System.out.println("============================================================");
            return;
        }

        System.out.println("\n============================================================");
        System.out.println("HISTORIAL DE MOVIMIENTOS FINANCIEROS");
        System.out.println("============================================================");

        for (FinancialMovement m : system.getFinancialMovements()) {
            System.out.println(m);
        }

        System.out.println("============================================================");
    }
}
