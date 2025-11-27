package posinnovate.finance.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.finance.models.FinancialMovement;
import posinnovate.sale.models.Sale;

public class FinanceController {

    private final SystemPOS system;

    public FinanceController(SystemPOS system) {
        this.system = system;
    }

    public void reporteGastosInsumos() {
        double totalGastos = system.financialMovements.stream()
                .filter(m -> "GASTO".equalsIgnoreCase(m.getTipo()))
                .mapToDouble(FinancialMovement::getMonto)
                .sum();
        System.out.println("REPORTE DE GASTOS (INSUMOS):");
        System.out.println("Total de gastos registrados: " + totalGastos);
    }

    public void reporteIngresosVentas() {
        double totalIngresos = system.sales.stream()
                .mapToDouble(Sale::getTotal)
                .sum();
        System.out.println("REPORTE DE INGRESOS POR VENTAS:");
        System.out.println("Total de ingresos: " + totalIngresos);
    }

    public void historialMovimientos() {
        if (system.financialMovements.isEmpty()) {
            System.out.println("No hay movimientos financieros registrados.");
            return;
        }
        System.out.println("HISTORIAL DE MOVIMIENTOS FINANCIEROS:");
        for (FinancialMovement m : system.financialMovements) {
            System.out.println(m);
        }
    }
}
