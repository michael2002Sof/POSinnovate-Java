package system;
import access.Access;
// ------------------ USUARIOS ------------------
import user.controllers.UserController;
import user.controllers.RolController;

import user.models.User;
import user.models.Rol;

// ------------------ INVENTARIO ------------------
import inventory.controllers.InsumoController;
import inventory.controllers.ProductController;
import inventory.controllers.SolicitudController;

// ------------------ VENTAS ------------------
import sale.controllers.SaleController;
import sale.models.Sale;

// ------------------ FINANZAS ------------------
import finance.controllers.FinanceController;
import finance.models.FinancialMovement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SystemApp {

    // LISTAS DEL SISTEMA
    public List <User> users = new ArrayList<>();
    public List<Rol> roles = new ArrayList<>();

    // MODELOS
    public Class<User> modelUser = User.class;
    public Class<Rol> modelRol = Rol.class;

    // CONTROLADORES
    public UserController controllerUser;
    public RolController controllerRol;

 

    // MÓDULOS
    public HashMap<String, HashMap<String, Runnable>> AVAILABLE_MODULES;

    public SystemApp() {

        // Inicializar controladores enviando "this" como en Python
        controllerUser = new UserController(this);
        controllerRol = new RolController(this);


        // CARGUE DE INSUMOS Y PRODUCTOS INICIALES
        controllerInsumo.cargarInsumosIniciales();
        controllerProduct.cargarProductosIniciales();

        // MÓDULOS DEL SISTEMA
        AVAILABLE_MODULES = new HashMap<>();

        // ----------------- MÓDULO USUARIOS -----------------
        HashMap<String, Runnable> modUsuarios = new HashMap<>();
        modUsuarios.put("Crear Usuario", () -> controllerUser.createUser());
        modUsuarios.put("Crear Admin", () -> controllerUser.registerAdmin());
        modUsuarios.put("Listar Usuarios", () -> controllerUser.listUsers());
        modUsuarios.put("Crear Rol", () -> controllerRol.createRole());

        // ----------------- MÓDULO INVENTARIO -----------------


        // ----------------- MÓDULO VENTAS -----------------
        HashMap<String, Runnable> modVentas = new HashMap<>();
        modVentas.put("Registrar Venta", () -> controllerSale.registrarVenta());
        modVentas.put("Consultar Productos Disponibles", () -> controllerSale.consultarProductosDisponibles());

        // ----------------- MÓDULO FINANZAS -----------------
        HashMap<String, Runnable> modFinanzas = new HashMap<>();
        modFinanzas.put("Reporte Gastos Insumos", () -> controllerFinance.reporteGastosInsumos());
        modFinanzas.put("Reporte Ingresos Ventas", () -> controllerFinance.reporteIngresosVentas());
        modFinanzas.put("Historial Movimientos", () -> controllerFinance.historialMovimientos());



        // Registrar todos los módulos en el sistema
        AVAILABLE_MODULES.put("Usuarios", modUsuarios);
        AVAILABLE_MODULES.put("Ventas", modVentas);
        AVAILABLE_MODULES.put("Finanzas", modFinanzas);
    }

    public static void main(String[] args) {
        SystemApp system = new SystemApp();
        Access.login(system);
    }
}
