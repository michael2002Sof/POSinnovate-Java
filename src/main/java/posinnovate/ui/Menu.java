package posinnovate.ui;

import java.util.*;

import posinnovate.core.SystemPOS;
import posinnovate.user.models.User;
import posinnovate.user.models.Rol;
import posinnovate.utils.SystemUtils;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu(SystemPOS system, User user, Rol role) {
        while (true) {
            SystemUtils.cleanScreen();
            System.out.println("\nBIENVENIDO: " + user.getName() + ". ROL: " + role.getName());
            System.out.println("================================");
            System.out.println("         MENÚ DISPONIBLE        ");
            System.out.println("================================");

            Map<String, List<String>> modules = role.getModules();
            if (modules == null || modules.isEmpty()) {
                System.out.println("Este rol no tiene módulos asignados.");
                return;
            }

            List<String> moduleNames = new ArrayList<>(modules.keySet());
            for (int i = 0; i < moduleNames.size(); i++) {
                System.out.println((i + 1) + ". " + moduleNames.get(i));
            }
            System.out.println("0. Cerrar sesión");

            System.out.print("Seleccione el módulo: ");
            String opt = scanner.nextLine().trim();

            if ("0".equals(opt)) {
                break;
            }

            int moduleIndex;
            try {
                moduleIndex = Integer.parseInt(opt) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida.");
                continue;
            }

            if (moduleIndex < 0 || moduleIndex >= moduleNames.size()) {
                System.out.println("Opción inválida.");
                continue;
            }

            String moduleName = moduleNames.get(moduleIndex);
            List<String> perms = modules.get(moduleName);
            if (perms == null || perms.isEmpty()) {
                System.out.println("No hay permisos definidos para este módulo.");
                continue;
            }

            while (true) {
                SystemUtils.cleanScreen();
                System.out.println("MÓDULO: " + moduleName);
                System.out.println("Permisos disponibles:");
                for (int i = 0; i < perms.size(); i++) {
                    System.out.println((i + 1) + ". " + perms.get(i));
                }
                System.out.println("0. Volver");

                System.out.print("Seleccione una opción: ");
                String permOpt = scanner.nextLine().trim();
                if ("0".equals(permOpt)) break;

                int permIndex;
                try {
                    permIndex = Integer.parseInt(permOpt) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Opción inválida.");
                    continue;
                }

                if (permIndex < 0 || permIndex >= perms.size()) {
                    System.out.println("Opción inválida.");
                    continue;
                }

                String permName = perms.get(permIndex);

                System.out.println("==============================================");
                System.out.println("EJECUTANDO '" + permName + "' EN MÓDULO '" + moduleName + "'");
                System.out.println("==============================================");

                Map<String, Runnable> moduleActions = system.AVAILABLE_MODULES.get(moduleName);
                if (moduleActions != null) {
                    Runnable action = moduleActions.get(permName);
                    if (action != null) {
                        action.run();
                    } else {
                        System.out.println("Permiso no implementado o inexistente.");
                    }
                } else {
                    System.out.println("Módulo no encontrado en el sistema.");
                }

                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }
}
