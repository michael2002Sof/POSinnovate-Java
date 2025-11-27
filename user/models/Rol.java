package user.models;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Rol {

    // 1. ATRIBUTOS (FIELDS)
    private String name;
    // Map<Módulo (String), Lista de Permisos (List<String>)>
    private Map<String, List<String>> modules; 
    private String status;

    // 2. CONSTRUCTOR (Equivalente a __init__ en Python)
    // Recibe el nombre y opcionalmente un mapa de módulos.
    // Si modules es null, inicializa un HashMap vacío (equivalente a {} en Python).
    public Rol(String name, Map<String, List<String>> modules) {
        this.name = name;
        
        // Lógica equivalente a: self.modules = modules if modules else {}
        if (modules == null) {
            this.modules = new HashMap<>();
        } else {
            this.modules = modules;
        }
        
        // Asignación de valor por defecto
        this.status = "Active";
    }

    // Constructor secundario para crear un Rol solo con el nombre (sin módulos iniciales)
    public Rol(String name) {
        this(name, null); // Llama al constructor principal con módulos=null
    }

    // 3. MÉTODO toString() (Equivalente a __str__ en Python)
    @Override
    public String toString() {
        // Inicializa el texto base
        StringBuilder texto = new StringBuilder();
        texto.append("Rol: ").append(this.name).append(" | Estado: ").append(this.status).append("\n");
        texto.append("Módulos y permisos:\n");

        // Itera sobre el Map (equivalente a for module, perms in self.modules.items():)
        for (Map.Entry<String, List<String>> entry : this.modules.entrySet()) {
            String module = entry.getKey();
            List<String> perms = entry.getValue();

            // Convierte la lista de permisos a una cadena separada por comas (equivalente a ", ".join(perms))
            String permisos = String.join(", ", perms);
            
            // Construye la línea del módulo
            texto.append("  - ").append(module).append(": ").append(permisos).append("\n");
        }
        
        return texto.toString();
    }
    
    // 4. GETTERS Y SETTERS (Se omiten para brevedad, pero son necesarios para acceder a 'private' fields)
    // public String getName() { return name; }
    // public void setName(String name) { this.name = name; }
    // ...
}