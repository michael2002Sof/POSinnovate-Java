package posinnovate.user.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.user.models.Rol;

public class RolController {

    private final SystemPOS system;
    private final Scanner scanner = new Scanner(System.in);

    public RolController(SystemPOS system) {
        this.system = system;
    }

    public void createRole() {
        System.out.println("REGISTRO DE ROL:");
        System.out.print("Nombre del rol: ");
        String name = scanner.nextLine().trim();

        List<String> moduleNames = new ArrayList<>(system.AVAILABLE_MODULES.keySet());
        Map<String, List<String>> modulesWithPermissions = new LinkedHashMap<>();

        System.out.println("\nSELECCIÓN DE MÓDULOS PARA EL ROL:");
        for (int i = 0; i < moduleNames.size(); i++) {
            System.out.println((i + 1) + ". " + moduleNames.get(i));
        }
        System.out.println("Ejemplo de selección: 1,3");

        System.out.print("Módulos a asignar: ");
        String selectedModulesRaw = scanner.nextLine();
        String[] selectedModules = selectedModulesRaw.split(",");

        for (String m : selectedModules) {
            m = m.trim();
            if (!m.matches("\d+")) continue;
            int moduleIndex = Integer.parseInt(m) - 1;
            if (moduleIndex < 0 || moduleIndex >= moduleNames.size()) continue;

            String moduleName = moduleNames.get(moduleIndex);
            Map<String, Runnable> actions = system.AVAILABLE_MODULES.get(moduleName);
            if (actions == null || actions.isEmpty()) continue;

            List<String> moduleOptions = new ArrayList<>(actions.keySet());
            System.out.println("\nPermisos para módulo: " + moduleName);
            for (int i = 0; i < moduleOptions.size(); i++) {
                System.out.println((i + 1) + ". " + moduleOptions.get(i));
            }
            System.out.println("Ejemplo de selección: 1,2");

            System.out.print("Permisos a asignar: ");
            String permsRaw = scanner.nextLine();
            String[] selectedPerms = permsRaw.split(",");

            List<String> perms = new ArrayList<>();
            for (String p : selectedPerms) {
                p = p.trim();
                if (!p.matches("\d+")) continue;
                int pIdx = Integer.parseInt(p) - 1;
                if (pIdx < 0 || pIdx >= moduleOptions.size()) continue;
                perms.add(moduleOptions.get(pIdx));
            }

            if (!perms.isEmpty()) {
                modulesWithPermissions.put(moduleName, perms);
            }
        }

        Rol newRole = new Rol(name, modulesWithPermissions);
        system.roles.add(newRole);
        System.out.println("Rol '" + name + "' registrado correctamente.");
    }
}
