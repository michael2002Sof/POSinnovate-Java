package posinnovate.user.models;

public class User {

    private String name;
    private String email;
    private String password;
    private Rol rol;
    private String status = "Active";

    public User(String name, String email, String password, Rol rol) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Rol getRol() { return rol; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Nombre: " + name +
                " || Correo: " + email +
                " || Rol: " + (rol != null ? rol.getName() : "Sin rol") +
                " || Estado: " + status;
    }
}
