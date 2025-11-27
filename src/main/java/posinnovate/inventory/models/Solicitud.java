package posinnovate.inventory.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Solicitud {

    private static int COUNTER = 1;

    private int id;
    private Insumo insumo;
    private double cantidad;
    private String solicitadoPor;
    private String estado; // PENDIENTE, APROBADA, RECHAZADA
    private String fechaSolicitud;

    public Solicitud(Insumo insumo, double cantidad, String solicitadoPor) {
        this.id = COUNTER++;
        this.insumo = insumo;
        this.cantidad = cantidad;
        this.solicitadoPor = solicitadoPor;
        this.estado = "PENDIENTE";
        this.fechaSolicitud = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getId() { return id; }
    public Insumo getInsumo() { return insumo; }
    public double getCantidad() { return cantidad; }
    public String getSolicitadoPor() { return solicitadoPor; }
    public String getEstado() { return estado; }
    public String getFechaSolicitud() { return fechaSolicitud; }

    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Solicitud #" + id +
               " | Insumo: " + insumo.getNombre() +
               " | Cantidad: " + cantidad +
               " | Solicitado por: " + solicitadoPor +
               " | Estado: " + estado +
               " | Fecha: " + fechaSolicitud;
    }
}
