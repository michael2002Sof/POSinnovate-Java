package posinnovate.finance.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinancialMovement {

    private static int COUNTER = 1;

    private int id;
    private String tipo; // INGRESO / GASTO
    private String concepto;
    private double monto;
    private String referencia;
    private String fechaHora;

    public FinancialMovement(String tipo, String concepto, double monto, String referencia) {
        this.id = COUNTER++;
        this.tipo = tipo;
        this.concepto = concepto;
        this.monto = monto;
        this.referencia = referencia;
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getConcepto() { return concepto; }
    public double getMonto() { return monto; }
    public String getReferencia() { return referencia; }
    public String getFechaHora() { return fechaHora; }

    @Override
    public String toString() {
        return "Movimiento #" + id +
               " | Tipo: " + tipo +
               " | Concepto: " + concepto +
               " | Monto: " + monto +
               " | Ref: " + referencia +
               " | Fecha: " + fechaHora;
    }
}
