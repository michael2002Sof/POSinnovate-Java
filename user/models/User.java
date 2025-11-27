package user.models;

public class User {

    // 1. ATRIBUTOS (FIELDS)
    // Definición de variables con sus tipos de datos y visibilidad (private es lo común)
    private String name;
    private String email;
    private String password;
    private String rol;
    private String status;

    // 2. CONSTRUCTOR (Equivalente a __init__ en Python)
    // Inicializa los atributos cuando se crea un nuevo objeto
    public User(String name, String email, String password, String rol) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
        // Asignación de valor por defecto
        this.status = "Active"; 
    }

    // 3. GETTERS Y SETTERS (Métodos para acceder y modificar atributos privados)
    // Necesarios para obtener los valores de los atributos 'private' desde fuera de la clase
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Puedes añadir el resto de getters/setters aquí (ej. getEmail, setPassword, etc.)

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }


    // 4. MÉTODO toString() (Equivalente a __str__ en Python)
    // Define la representación textual del objeto
    @Override
    public String toString() {
        return "Nombre: " + this.name + 
               " || Correo: " + this.email + 
               " || Contraseña: " + this.password + // Ten cuidado al imprimir contraseñas en entornos reales
               " || rol: " + this.rol + 
               " || estado: " + this.status;
    }
}