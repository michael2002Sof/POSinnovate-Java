package posinnovate.user.models;

import java.util.*;

public class Rol {

    private String name;
    private Map<String, List<String>> modules = new LinkedHashMap<>();
    private String status = "Active";

    public Rol(String name, Map<String, List<String>> modules) {
        this.name = name;
        if (modules != null) {
            this.modules.putAll(modules);
        }
    }

    public String getName() { return name; }

    public Map<String, List<String>> getModules() {
        return modules;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Rol: " + name + "\nMódulos y permisos:\n");
        for (Map.Entry<String, List<String>> entry : modules.entrySet()) {
            sb.append("  - ").append(entry.getKey()).append(": ");
            sb.append(String.join(", ", entry.getValue()));
            sb.append("\n");
        }
        return sb.toString();
    }
}
