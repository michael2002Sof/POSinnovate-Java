package inventory.models;

import java.util.List;

public class SolicitudInsumo {

    private String codigo;
    private String fechaSolicitud;
    private String responsable;
    private String estado;
    private List<ItemSolicitud> items;

    public SolicitudInsumo(String codigo, String fechaSolicitud,
                           String responsable, List<ItemSolicitud> items,
                           String estado) {

        this.codigo = codigo;
        this.fechaSolicitud = fechaSolicitud;
        this.responsable = responsable;
        this.items = items;
        this.estado = (estado == null) ? "pendiente" : estado;
    }

    // Constructor alterno cuando no se envía estado
    public SolicitudInsumo(String codigo, String fechaSolicitud,
                           String responsable, List<ItemSolicitud> items) {
        this(codigo, fechaSolicitud, responsable, items, "pendiente");
    }

    // --- GETTERS (para controladores) ---
    public String getCodigo() { return codigo; }
    public String getFechaSolicitud() { return fechaSolicitud; }
    public String getResponsable() { return responsable; }
    public String getEstado() { return estado; }
    public List<ItemSolicitud> getItems() { return items; }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder();

        txt.append("================================================================\n");
        txt.append("Solicitud " + codigo + " - Fecha: " + fechaSolicitud + "\n");
        txt.append("Responsable: " + responsable + " | Estado: " + estado + "\n");
        txt.append("Items solicitados:\n\n");

        for (ItemSolicitud item : items) {
            Insumo insumo = item.getInsumo();
            double cantidad = item.getCantidad();

            txt.append("- " + insumo.getNombre() +
                       " | Código: " + insumo.getCodigo() +
                       " - Cantidad Solicitada: " + cantidad +
                       " " + insumo.getUnidadMedida() + "\n");
        }

        txt.append("================================================================");

        return txt.toString();
    }
}
