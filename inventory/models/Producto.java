package inventory.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Producto {

    private String codigo;
    private String fechaRegistro;
    private String marca;
    private String modelo;
    private String tipo;
    private int talla;
    private String color;
    private int cantidad;
    private double precioVenta;

    private String genero;
    private String estado;

    public Producto(String codigo, String fechaRegistro, String marca, String modelo,
                    String tipo, int talla, String color, int cantidad, double precioVenta) {

        this.codigo = codigo;

        if (fechaRegistro == null || fechaRegistro.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.fechaRegistro = LocalDateTime.now().format(formatter);
        } else {
            this.fechaRegistro = fechaRegistro;
        }

        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.talla = talla;
        this.color = color;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;

        // Género según talla
        if (talla >= 36 && talla <= 39) {
            this.genero = "dama";
        } else if (talla >= 40 && talla <= 45) {
            this.genero = "caballero";
        } else {
            this.genero = "no definido";
        }

        // Estado del producto
        this.estado = cantidad > 0 ? "disponible" : "agotado";
    }

    // --- GETTERS ---
    public String getCodigo() { return codigo; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getTipo() { return tipo; }
    public int getTalla() { return talla; }
    public String getColor() { return color; }
    public int getCantidad() { return cantidad; }
    public double getPrecioVenta() { return precioVenta; }
    public String getGenero() { return genero; }
    public String getEstado() { return estado; }

    @Override
    public String toString() {
        return "\n================================================================\n" +
                "Fecha registro: " + fechaRegistro + "\n" +
                "Código: " + codigo + "\n" +
                "Marca: " + marca + " | Modelo: " + modelo + " | Tipo: " + tipo + "\n" +
                "Talla: " + talla + " | Color: " + color + " | Género: " + genero + "\n" +
                "Cantidad: " + cantidad + " par(es)\n" +
                "Precio unitario: $" + precioVenta + "\n" +
                "Estado: " + estado + "\n" +
                "================================================================";
    }
}
