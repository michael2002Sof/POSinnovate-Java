package sale.models;

import inventory.models.Producto;

public class SaleItem {

    private Producto product;
    private int quantity;
    private double unitPrice;
    private double subtotal;

    public SaleItem(Producto product, int quantity, double unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }

    public Producto getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getSubtotal() { return subtotal; }
}
