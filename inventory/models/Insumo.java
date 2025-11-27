package inventory.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Insumo {

    private String codigo;
    private String nombre;
    private String unidadMedida;
    private double stockActual;
    private double stockMinimo;
    private double costo;
    private String fechaRegistro;

    public Insumo(String codigo, String nombre, String unidadMedida,
                  double stockActual, double stockMinimo, double costo,
                  String fechaRegistro) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.costo = costo;

        if (fechaRegistro == null || fechaRegistro.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.fechaRegistro = LocalDateTime.now().format(formatter);
        } else {
            this.fechaRegistro = fechaRegistro;
        }
    }

    // Constructor alterno sin fecha
    public Insumo(String codigo, String nombre, String unidadMedida,
                  double stockActual, double stockMinimo, double costo) {
        this(codigo, nombre, unidadMedida, stockActual, stockMinimo, costo, null);
    }

    // --- GETTERS necesarios para los controladores ---
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getUnidadMedida() { return unidadMedida; }
    public double getStockActual() { return stockActual; }
    public double getStockMinimo() { return stockMinimo; }
    public double getCosto() { return costo; }
    public String getFechaRegistro() { return fechaRegistro; }

    @Override
    public String toString() {
        return "\n================================================================\n" +
                "Fecha: " + fechaRegistro + "\n" +
                "Código: " + codigo + " | Nombre: " + nombre + "\n" +
                "Cantidad: " + stockActual + " " + unidadMedida +
                " - Costo acumulado: $" + costo + "\n" +
                "================================================================";
    }
}
