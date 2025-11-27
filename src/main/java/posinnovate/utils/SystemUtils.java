package posinnovate.utils;

public class SystemUtils {
    public static void cleanScreen() {
        try {
            // Verifica el sistema operativo
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Comando para Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Comando para Unix/Linux/macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Si falla, regresa a hacer saltos de línea (fallback)
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
    }
}