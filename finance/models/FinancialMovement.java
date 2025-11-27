package finance.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FinancialMovement {

    private int code;
    private String date;
    private String movementType;    // "gasto" o "ingreso"
    private String concept;
    private double amount;
    private Object reference;       // Puede ser Venta o Insumo

    public FinancialMovement(int code, String date, String movementType,
                             String concept, double amount, Object reference) {

        this.code = code;

        if (date == null || date.isEmpty()) {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.date = LocalDateTime.now().format(f);
        } else {
            this.date = date;
        }

        this.movementType = movementType;
        this.concept = concept;
        this.amount = amount;
        this.reference = reference;
    }

    public int getCode() { return code; }
    public String getDate() { return date; }
    public String getMovementType() { return movementType; }
    public String getConcept() { return concept; }
    public double getAmount() { return amount; }
    public Object getReference() { return reference; }

    @Override
    public String toString() {
        String tipo = movementType.equals("gasto") ? "GASTO" : "INGRESO";

        return String.format(
                "[%d] %s | %-7s | $%.2f | %s",
                code, date, tipo, amount, concept
        );
    }
}
