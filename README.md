POSinnovate JAVA

    Este proyecto es una aplicación de consola desarrollada en Java, enfocada en el sistema
    POS (Point of Sale) que es la combinación de hardware y software que utilizan los negocios para completar las transacciones de venta con sus clientes, nuestro sistema toma apartir 
    de eso la base y lo impulsa mas haya creando modulos complemetarios, como inventario y 
    gestion de usuario, para mayor presencia en el mundo digital como sistema.


ARQUITECTURA MVC (Model – Controller – View simulada vía CLI)

POSINNOVATE-JAVA/

    ├── main.py             → menú principal interactivo.
    ├── README.md           → explicación completa de la estructura y flujo del programa.

inventory/                  → Módulo de inventarios

    ├── Controllers/        
        ├── insumo.py       
        ├── product.py
        ├── solicitud.py
    └── Models
        └── insumo.py       → Modelo de Insumo
        └── product.py      → Modelo de Producto
        └── solicitud.py    → Modelo de Solicitud de Insumos

sale/                        → Módulo de ventas

    ├── Controllers         
    └── modules/


user/                       → Módulo de usuarios

    ├── Controllers        
        └── rol.py          → Lógica de roles del sistema
        └── user.py         → Lógica de gestión de usuarios
    └── Models
        └── rol.py
        └── user.py

utils/

    └── system_utils.py

