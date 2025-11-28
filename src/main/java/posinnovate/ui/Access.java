package posinnovate.ui;

import java.util.Scanner;

import posinnovate.core.SystemPOS;
import posinnovate.user.models.User;
import posinnovate.utils.SystemUtils;

public class Access {

    private static final Scanner scanner = new Scanner(System.in);

    public static void login(SystemPOS system) {
        while (true) {
            SystemUtils.cleanScreen();
            System.out.println("========================================");
            System.out.println("          SISTEMA POSInnovate");
            System.out.println("========================================");

            boolean adminExists = system.users.stream().anyMatch(u -> u.getRol() != null && "admin".equalsIgnoreCase(u.getRol().getName()));

            System.out.println("1. Iniciar sesión");
            if (!adminExists) {
                System.out.println("2. Registrar Admin");
            }
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("\nOpción: ");
            String option = scanner.nextLine().trim();

            if ("1".equals(option)) {
                User user = system.controllerUser.loginUser();
                if (user != null && user.getRol() != null) {
                    Menu.showMenu(system, user, user.getRol());
                }
            } else if ("2".equals(option) && !adminExists) {
                system.controllerUser.registerAdmin();
            } else if ("0".equals(option)) {
                System.out.println("Saliendo del sistema...");
                break;
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }
}
