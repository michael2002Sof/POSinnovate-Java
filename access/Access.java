package access;
import utils.SystemUtils;
import menu.Menu;
import system.SystemApp;

import java.util.Scanner;

public class Access {

    public static void login(SystemApp system) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            SystemUtils.cleanScreen();
            System.out.println("========================================");
            System.out.println("          SISTEMA POSSinovate");
            System.out.println("========================================");

            boolean adminExists = system.users.stream()
                .anyMatch(u -> u.getRol().equals("admin"));

            System.out.println("1. Iniciar sesión");
            if (!adminExists) {
                System.out.println("2. Registrar Administrador (Primera vez)");
            }
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            String option = sc.nextLine();

            switch (option) {

                case "1":
                    SystemUtils.cleanScreen();
                    System.out.println("========================================");
                    System.out.println("            INICIAR SESIÓN");
                    System.out.println("========================================");

                    System.out.print("Correo: ");
                    String email = sc.nextLine();

                    System.out.print("Contraseña: ");
                    String password = sc.nextLine();

                    // Buscar usuario
                    var user = system.users.stream()
                            .filter(u -> u.getEmail().equals(email)
                                    && u.getPassword().equals(password))
                            .findFirst()
                            .orElse(null);

                    if (user == null) {
                        System.out.println("\nUsuario no encontrado.");
                        System.out.println("El administrador aún no le ha creado un usuario.\n");
                        return;
                    }

                    // Obtener rol asociado
                    var role = system.roles.stream()
                            .filter(r -> r.getName().equals(user.getRol()))
                            .findFirst()
                            .orElse(null);

                    SystemUtils.cleanScreen();
                    Menu.show(system, user, role);
                    break;

                case "2":
                    if (!adminExists) {
                        SystemUtils.cleanScreen();
                        system.controllerUser.registerAdmin();
                    } else {
                        System.out.println("Opción inválida.\n");
                    }
                    break;

                case "0":
                    System.out.println("Saliendo del sistema...");
                    return;

                default:
                    System.out.println("Opción inválida\n");
                    break;
            }
        }
    }
}
