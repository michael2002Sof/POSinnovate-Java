package menu;

import utils.SystemUtils;
import system.SystemApp;
import user.models.User;
import user.models.Rol;

import java.util.*;

public class Menu {

    public static void show(SystemApp system, User user, Rol role) {
        Scanner sc = new Scanner(System.in);

        SystemUtils.cleanScreen();
        System.out.println("\nBIENVENIDO: " + user.getName() + ". ROL: " + role.getName());
        System.out.println("========================================");
        System.out.println("             MENÚ DISPONIBLE");
        System.out.println("========================================");

        while (true) {

            Map<String, List<String>> modules = role.getModules();  
            // modules = { "Usuarios": ["Crear Usuario", "Listar Usuarios"], ... }

            System.out.println("MODULOS DISPONIBLES");
            System.out.println("========================================");

            List<String> moduleNames = new ArrayList<>(modules.keySet());

            for (int i = 0; i < moduleNames.size(); i++) {
                System.out.println((i + 1) + ". " + moduleNames.get(i));
            }

            System.out.println("0. Cerrar sesión");
            System.out.println("========================================");
            System.out.print("Seleccione un módulo: ");

            String choice = sc.nextLine();

            if (choice.equals("0")) {
                break;
            }

            try {
                int index = Integer.parseInt(choice) - 1;
                if (index < 0 || index >= moduleNames.size()) {
                    System.out.println("Opción inválida.");
                    continue;
                }

                String moduleName = moduleNames.get(index);
                List<String> permissions = modules.get(moduleName);

                SystemUtils.cleanScreen();
                System.out.println("========================================");
                System.out.println("  PERMISOS DEL MÓDULO: " + moduleName);
                System.out.println("========================================");

                for (int j = 0; j < permissions.size(); j++) {
                    System.out.println((j + 1) + ". " + permissions.get(j));
                }

                System.out.println("========================================");
                System.out.print("Seleccione acción: ");

                String actionInput = sc.nextLine();
                int actionIndex = Integer.parseInt(actionInput) - 1;

                if (actionIndex < 0 || actionIndex >= permissions.size()) {
                    System.out.println("Opción inválida.");
                    continue;
                }

                String permName = permissions.get(actionIndex);

                SystemUtils.cleanScreen();
                System.out.println("==================================================");
                System.out.println(" EJECUTANDO '" + permName + "' EN MÓDULO '" + moduleName + "'");
                System.out.println("==================================================");

                // Ejecutar acción
                Runnable actionFunction = system.AVAILABLE_MODULES.get(moduleName).get(permName);

                if (actionFunction != null) {
                    System.out.println(); // Espacio visual
                    actionFunction.run();
                } else {
                    System.out.println("Permiso no implementado o inexistente.");
                }

            } catch (Exception e) {
                System.out.println("Opción inválida.");
            }
        }
    }
}
