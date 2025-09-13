# 🌂 COS_ALPHA v2.1

Aplicación de escritorio desarrollada en **JavaFX** para la **gestión
integral de un punto de venta**.\
Incluye módulos de **Ventas, Inventario, Pedidos, Empleados y
Finanzas**, con autenticación de operadores, **temas claro/oscuro** y
una **interfaz moderna** basada en FXML + CSS.

------------------------------------------------------------------------

## 🚀 Características principales

-   🔑 **Login seguro** con roles de operador y sesión activa.
-   🛒 **Ventas** con carrito dinámico y descuentos configurables.
-   📦 **Inventario** con control de stock, precios y estados de
    producto.
-   📋 **Pedidos** y gestión completa del flujo de ventas.
-   👥 **Empleados** con control de usuarios y roles.\
-   💰 **Finanzas** con indicadores y reportes.
-   🎨 **UI moderna**: temas claro/oscuro, animaciones y componentes
    personalizados.
-   🗄️ **Persistencia en MySQL** para datos de negocio.
-   🧩 Librerías utilizadas: `ControlsFX`, `Ikonli`, `TilesFX`,
    `FormsFX`, `ValidatorFX`, `BootstrapFX`.

------------------------------------------------------------------------

## 🛠️ Tecnologías

-   **Java 21** (modular con `module-info.java`)
-   **JavaFX 21** (graphics, controls, fxml, web, swing)
-   **Maven** (`javafx-maven-plugin`)
-   **MySQL 8.x** con conector JDBC
-   **JUnit 5** para pruebas

------------------------------------------------------------------------

## 📋 Requisitos previos

-   ☕ **JDK 21**
-   🔧 **Maven 3.9+**
-   🗄️ **MySQL 8.x** (local o remoto)

------------------------------------------------------------------------

## ⚙️ Configuración rápida

1.  Crear base de datos:

    ``` sql
    CREATE DATABASE cos_db;
    ```

2.  Ajustar credenciales en:\
    `src/main/java/com/l3xxd/cos_alpha/config/DBConnection.java`

3.  Tabla mínima para login:

    ``` sql
    CREATE TABLE operadores (
      id INT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(50) UNIQUE NOT NULL,
      password VARCHAR(255) NOT NULL,
      rol VARCHAR(50) NOT NULL,
      first_name VARCHAR(100),
      last_name VARCHAR(100),
      email VARCHAR(150),
      phone VARCHAR(50)
    );
    ```

4.  Scripts de tablas adicionales:

    ``` sql
    CREATE TABLE IF NOT EXISTS productos (
      id INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      type VARCHAR(50) NOT NULL,
      price_purchase DECIMAL(10,2) NOT NULL,
      price_sale DECIMAL(10,2) NOT NULL,
      stock INT NOT NULL DEFAULT 0,
      status VARCHAR(20) NOT NULL DEFAULT 'Activo',
      url_photo VARCHAR(255)
    );
    ```

    ``` sql
    CREATE TABLE IF NOT EXISTS pedidos (
      id_pedido INT AUTO_INCREMENT PRIMARY KEY,
      operador_id INT NOT NULL,
      fecha_solicitud DATE NOT NULL,
      fecha_llegada DATE,
      estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
      CONSTRAINT fk_pedidos_operador FOREIGN KEY (operador_id) REFERENCES operadores(id)
    );
    ```

    ``` sql
    CREATE TABLE IF NOT EXISTS pedido_detalle (
      id_detalle INT AUTO_INCREMENT PRIMARY KEY,
      pedido_id INT NOT NULL,
      producto_id INT NOT NULL,
      cantidad INT NOT NULL,
      subtotal DECIMAL(10,2),
      CONSTRAINT fk_detalle_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
      CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
    );
    ```

------------------------------------------------------------------------

## ▶️ Ejecutar en desarrollo

Con Maven:

``` bash
mvn clean javafx:run
```

Desde el IDE: ejecutar clase principal\
`com.l3xxd.cos_alpha.Launcher`

------------------------------------------------------------------------

## 📦 Empaquetar

``` bash
mvn clean package
```

> Para distribución standalone usar `jlink` o `jpackage`.

------------------------------------------------------------------------

## 📂 Estructura del proyecto

-   `Launcher.java` → punto de entrada
-   `controllers/` → controladores JavaFX
-   `models/` → modelos de dominio (Inventario, Pedido, Operador, etc.)
-   `dao/` → acceso a datos (MySQL)
-   `views/` → vistas FXML
-   `assets/css/` → estilos CSS (claro/oscuro)
-   `pom.xml` → dependencias y plugins

------------------------------------------------------------------------

## 📑 Módulos principales

-   🔐 **Login** -- autenticación y arranque de sesión
-   🛒 **Ventas** -- tarjetas de productos + carrito
-   📦 **Inventario** -- tabla y formularios de productos
-   📋 **Pedidos** -- gestión de pedidos y detalles
-   👥 **Empleados** -- operadores y roles
-   💰 **Finanzas** -- indicadores y métricas

------------------------------------------------------------------------

## 📸 Capturas de pantalla

### 🔐 Login (Tema Claro / Oscuro)

![Login
Claro](./CAPS/Captura%20de%20pantalla%202025-09-13%20165158.png)\
![Login
Oscuro](./CAPS/Captura%20de%20pantalla%202025-09-13%20165202.png)

### 🛒 Módulo de Ventas

![Ventas](./CAPS/Captura%20de%20pantalla%202025-09-13%20165231.png)

### 📦 Módulo de Inventario

![Inventario](./CAPS/Captura%20de%20pantalla%202025-09-13%20165242.png)\
![Inventario con
Formulario](./CAPS/Captura%20de%20pantalla%202025-09-13%20165257.png)

------------------------------------------------------------------------

## 📌 Estado del proyecto

-   Versión actual: **2.1**
-   Estado: **Alfa** (desarrollo y pruebas internas)

------------------------------------------------------------------------

## 📜 Licencia

Actualmente no especificada.\
Si se planea distribución, incluir una licencia adecuada.
