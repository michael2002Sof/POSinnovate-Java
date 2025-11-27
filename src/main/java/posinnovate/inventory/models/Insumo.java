package posinnovate.inventory.models;

import java.time.LocalDate;
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
                  double stockActual, double stockMinimo, double costo, String fechaRegistro) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.costo = costo;
        if (fechaRegistro == null || fechaRegistro.isEmpty()) {
            this.fechaRegistro = LocalDate.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            this.fechaRegistro = fechaRegistro;
        }
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getUnidadMedida() { return unidadMedida; }
    public double getStockActual() { return stockActual; }
    public double getStockMinimo() { return stockMinimo; }
    public double getCosto() { return costo; }
    public String getFechaRegistro() { return fechaRegistro; }

    public void setStockActual(double stockActual) { this.stockActual = stockActual; }

    @Override
    public String toString() {
        return "\n================================================================\n" +
               "Código: " + codigo +
               " | Nombre: " + nombre +
               " | Unidad: " + unidadMedida +
               " | Stock actual: " + stockActual +
               " | Stock mínimo: " + stockMinimo +
               " | Costo: " + costo +
               " | Fecha registro: " + fechaRegistro +
               "\n================================================================\n";
    }
}
