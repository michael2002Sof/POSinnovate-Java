package posinnovate.sale.models;

import posinnovate.inventory.models.Producto;

public class SaleItem {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public SaleItem(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x " + cantidad + " = " + getSubtotal();
    }
}
