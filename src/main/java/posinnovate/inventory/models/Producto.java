package posinnovate.inventory.models;

public class Producto {

    private String codigo;
    private String nombre;
    private double precioVenta;
    private int stock;

    public Producto(String codigo, String nombre, double precioVenta, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioVenta() { return precioVenta; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Código: " + codigo +
               " | Nombre: " + nombre +
               " | Precio Venta: " + precioVenta +
               " | Stock: " + stock;
    }
}
