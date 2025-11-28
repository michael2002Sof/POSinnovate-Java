package posinnovate.user.controllers;

import java.util.*;
import posinnovate.core.SystemPOS;
import posinnovate.user.models.Rol;
import posinnovate.user.models.User;

public class UserController {

    private final SystemPOS system;
    private final Scanner scanner = new Scanner(System.in);

    public UserController(SystemPOS system) {
        this.system = system;
    }

    public void registerAdmin() {
        System.out.println("\n================================");
        System.out.println("REGISTRO DE USUARIO ADMINISTRADOR");
        System.out.println("================================");

        Rol adminRole = system.roles.stream()
                .filter(r -> "admin".equalsIgnoreCase(r.getName()))
                .findFirst()
                .orElse(null);

        if (adminRole == null) {
            Map<String, List<String>> fullPermissions = new LinkedHashMap<>();
            system.AVAILABLE_MODULES.forEach((module, actions) -> {
                fullPermissions.put(module, new ArrayList<>(actions.keySet()));
            });
            adminRole = new Rol("admin", fullPermissions);
            system.roles.add(adminRole);
            System.out.println("\n!ROL ADMIN FUE CREADO CON TODOS LOS PERMISOS¡\n");
        }

        System.out.print("Nombre del admin: ");
        String name = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String email = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();

        User admin = new User(name, email, password, adminRole);
        system.users.add(admin);
        System.out.println("\nUSUARIO ADMIN REGISTRADO CORRECTAMENTE.\n");
    }

    public void createUser() {
        System.out.println("================================");
        System.out.println("REGISTRO DE USUARIO");
        System.out.println("================================");

        System.out.print("Nombre: ");
        String name = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String email = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();

        if (system.roles.isEmpty()) {
            System.out.println("No hay roles registrados. Cree primero un rol.");
            return;
        }

        System.out.println("\n================================");
        System.out.println("SELECCION DE ROL:");
        System.out.println("--------------------------------");
        for (int i = 0; i < system.roles.size(); i++) {
            System.out.println((i + 1) + ". " + system.roles.get(i).getName());
        }
        System.out.println("================================");
        System.out.print("\nOpción: ");
        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        if (idx < 0 || idx >= system.roles.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Rol rol = system.roles.get(idx);
        User user = new User(name, email, password, rol);
        system.users.add(user);

        System.out.println("\nUSUARIO '" + name + "' REGISTRADO CORRECTAMENTE.\n");
    }

    public void listUsers() {
        if (system.users.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        System.out.println("================================");
        System.out.println("LISTA DE USUARIOS");
        System.out.println("================================");
        for (User u : system.users) {
            System.out.println(u);
        }
    }

    public User loginUser() {
        System.out.println("\n================================");
        System.out.println("INICIO DE SESION");
        System.out.println("================================");
        System.out.print("Correo: ");
        String email = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();

        return system.users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email)
                        && u.getPassword().equals(password))
                .findFirst()
                .map(u -> {
                    System.out.println("Inicio de sesión exitoso. Bienvenido " + u.getName());
                    return u;
                })
                .orElseGet(() -> {
                    System.out.println("Credenciales inválidas.");
                    return null;
                });
    }
}
