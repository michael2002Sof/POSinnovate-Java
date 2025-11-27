package posinnovate.sale.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Sale {

    private static int COUNTER = 1;

    private int id;
    private String fechaHora;
    private List<SaleItem> items = new ArrayList<>();
    private double total;
    private String metodoPago;

    public Sale(String metodoPago) {
        this.id = COUNTER++;
        this.metodoPago = metodoPago;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public int getId() { return id; }
    public String getFechaHora() { return fechaHora; }
    public List<SaleItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getMetodoPago() { return metodoPago; }

    public void addItem(SaleItem item) {
        items.add(item);
        total += item.getSubtotal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VENTA #").append(id).append(" | Fecha: ").append(fechaHora)
          .append(" | Método de pago: ").append(metodoPago).append("\n");
        for (SaleItem it : items) {
            sb.append("  - ").append(it.toString()).append("\n");
        }
        sb.append("TOTAL: ").append(total);
        return sb.toString();
    }
}
