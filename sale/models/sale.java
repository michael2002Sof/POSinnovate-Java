package sale.models;

import inventory.models.Producto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Sale {

    private int code;
    private String date;
    private String customerName;
    private List<SaleItem> items;
    private String paymentMethod;
    private String status;
    private double total;

    public Sale(int code, String date, String customerName, List<SaleItem> items, String paymentMethod) {
        this.code = code;

        if (date == null || date.isEmpty()) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.date = LocalDateTime.now().format(f);
        } else {
            this.date = date;
        }

        this.customerName = customerName;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.status = "registrada";

        this.total = items.stream()
                          .mapToDouble(SaleItem::getSubtotal)
                          .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("============================================================\n");
        sb.append("VOUCHER DE VENTA #").append(code).append("\n");
        sb.append("Fecha y hora: ").append(date).append("\n");
        sb.append("Cliente: ").append(customerName).append("\n");
        sb.append("Estado: ").append(status).append("\n");
        sb.append("------------------------------------------------------------\n");
        sb.append("Detalle de productos:\n");

        for (SaleItem item : items) {
            Producto p = item.getProduct();

            sb.append(String.format(
                    "- [%s] %s %s %s T%d %s | Cant: %d | PU: $%.2f | Subtotal: $%.2f\n",
                    p.getCodigo(), p.getMarca(), p.getModelo(), p.getTipo(),
                    p.getTalla(), p.getColor(),
                    item.getQuantity(), item.getUnitPrice(), item.getSubtotal()
            ));
        }

        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("TOTAL A PAGAR: $%.2f\n", total));
        sb.append("Forma de pago: ").append(paymentMethod).append("\n");
        sb.append("============================================================");

        return sb.toString();
    }
}
