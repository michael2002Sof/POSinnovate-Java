package posinnovate.core;

import java.util.*;

import posinnovate.user.controllers.*;
import posinnovate.user.models.*;
import posinnovate.inventory.controllers.*;
import posinnovate.inventory.models.*;
import posinnovate.sale.controllers.*;
import posinnovate.sale.models.*;
import posinnovate.finance.controllers.*;
import posinnovate.finance.models.*;

public class SystemPOS {

    public List<User> users = new ArrayList<>();
    public List<Rol> roles = new ArrayList<>();
    public List<Producto> products = new ArrayList<>();
    public List<Insumo> supplies = new ArrayList<>();
    public List<Solicitud> requisitions = new ArrayList<>();
    public List<Sale> sales = new ArrayList<>();
    public List<FinancialMovement> financialMovements = new ArrayList<>();

    public UserController controllerUser;
    public RolController controllerRol;
    public InsumoController controllerInsumo;
    public ProductoController controllerProducto;
    public SolicitudController controllerSolicitud;
    public SaleController controllerSale;
    public FinanceController controllerFinance;

    public Map<String, Map<String, Runnable>> AVAILABLE_MODULES = new LinkedHashMap<>();

    public SystemPOS() {
        this.controllerUser = new UserController(this);
        this.controllerRol = new RolController(this);

        this.controllerInsumo = new InsumoController(this);
        this.controllerProducto = new ProductoController(this);
        this.controllerSolicitud = new SolicitudController(this);

        this.controllerSale = new SaleController(this);
        this.controllerFinance = new FinanceController(this);

        this.controllerInsumo.cargarInsumosIniciales();
        this.controllerProducto.cargarProductosIniciales();

        configurarModulos();
    }

    private void configurarModulos() {
        Map<String, Runnable> usuarios = new LinkedHashMap<>();
        usuarios.put("Crear Usuario", () -> controllerUser.createUser());
        usuarios.put("Crear Admin", () -> controllerUser.registerAdmin());
        usuarios.put("Listar Usuarios", () -> controllerUser.listUsers());
        usuarios.put("Crear Rol", () -> controllerRol.createRole());
        AVAILABLE_MODULES.put("Usuarios", usuarios);

        Map<String, Runnable> inventario = new LinkedHashMap<>();
        inventario.put("Registrar Insumo", () -> controllerInsumo.registrarInsumos());
        inventario.put("Consultar Insumos", () -> controllerInsumo.consultarInsumos());
        inventario.put("Alertas de Stock", () -> controllerInsumo.mostrarAlertasStock());
        inventario.put("Registrar Producto", () -> controllerProducto.registrarProductos());
        inventario.put("Consultar Insumos (Producción)", () -> controllerProducto.consultarInsumosProduccion());
        inventario.put("Solicitar Insumos", () -> controllerSolicitud.solicitarInsumos());
        inventario.put("Gestionar Solicitudes", () -> controllerSolicitud.gestionarSolicitudesInventario());
        AVAILABLE_MODULES.put("Inventario", inventario);

        Map<String, Runnable> ventas = new LinkedHashMap<>();
        ventas.put("Registrar Venta", () -> controllerSale.registrarVenta());
        ventas.put("Consultar Productos (Ventas)", () -> controllerSale.consultarProductosDisponibles());
        AVAILABLE_MODULES.put("Ventas", ventas);

        Map<String, Runnable> finanzas = new LinkedHashMap<>();
        finanzas.put("Reporte Gastos (Insumos)", () -> controllerFinance.reporteGastosInsumos());
        finanzas.put("Reporte Ingresos (Ventas)", () -> controllerFinance.reporteIngresosVentas());
        finanzas.put("Historial Movimientos", () -> controllerFinance.historialMovimientos());
        AVAILABLE_MODULES.put("Finanzas", finanzas);
    }
}
