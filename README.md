# MercaFacil - Sistema de Gestión de Inventarios

**MercaFacil** es un proyecto académico de software desarrollado desde cero en **Java**. Consiste en un sistema de escritorio integral para la gestión de inventarios, almacenes, productos y proveedores, diseñado específicamente para conectarse y administrar una **base de datos MySQL** previamente modelada.

---

## Características Principales

* **Arquitectura Robusta:** Desarrollado bajo el patrón arquitectónico **MVC** (Modelo-Vista-Controlador) para separar la interfaz gráfica de la lógica de negocio.
* **Manejo Eficiente de Datos:** Implementación estricta del patrón **DAO** (Data Access Object) para todas las transacciones SQL.
* **Conexión Segura:** Conexión a la base de datos gestionada a través del patrón **Singleton** en JDBC, garantizando una única instancia de conexión para optimizar el rendimiento del servidor.
* **UI Personalizada:** Diseño de interfaz gráfica único denominado *"Pastel Glass"*, el cual se aleja del diseño tradicional de Java Swing utilizando componentes redondeados, colores de alto contraste y paneles adaptables con *scroll*.

## 📂 Módulos del Sistema

1. **Gestión (CRUD Completo):**
   *  **Almacenes:** Registro, edición y eliminación de almacenes físicos.
   *  **Productos:** Gestión de catálogo con asociación dinámica de categorías mediante llaves foráneas.
   *  **Proveedores:** Control del directorio de proveedores.
2. **Auditoría y Monitoreo (Solo Lectura):**
   *  **Historial de Ventas:** Visualización detallada de transacciones.
   *  **Historial de Clientes:** Registro y estado de los clientes.
   *  **Panel de Auditoría:** Dashboard centralizado con subconsultas gerenciales complejas (estado del inventario general, productos más vendidos y estatus de pedidos).

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java (JDK 8 o superior).
* **Interfaz Gráfica:** Java Swing (AWT/Swing).
* **Base de Datos:** MySQL.
* **Conector:** JDBC MySQL Connector (mysql-connector-j).
* **Entorno de Desarrollo:** Apache NetBeans IDE.

---

## ⚙️ Instalación y Configuración

Sigue estos pasos para ejecutar el proyecto en tu entorno local:

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/MercaFacil.git
```

### 2. Base de Datos
1. Abre tu gestor de base de datos (ej. phpMyAdmin, MySQL Workbench o DBeaver).
2. Crea una base de datos llamada `mercafacil`.
3. Ejecuta el script SQL de creación de tablas, vistas y disparadores (asegúrate de importar tu esquema local).

### 3. Configurar el Entorno (NetBeans)
1. Abre Apache NetBeans y selecciona **File > Open Project...**
2. Busca la carpeta clonada de `MercaFacilCRUD` y ábrela.
3. Asegúrate de tener agregado el driver **MySQL JDBC Driver** en la carpeta de Librerías (`Libraries`) del proyecto.

### 4. Configurar Credenciales de Conexión
1. Ve al paquete `conexion` y abre el archivo `Conexion.java`.
2. Modifica las siguientes variables con las credenciales de tu servidor MySQL local:
```java
private static final String USUARIO  = "root";      // Tu usuario de MySQL
private static final String PASSWORD = "tu_password_aqui"; // Tu contraseña de MySQL
```
*(Nota: Por razones de seguridad, nunca subas tu contraseña real a GitHub).*

### 5. Ejecutar
Localiza la clase principal (o arranca desde `FormMenu.java` si está configurado como Entry Point) y presiona **Run** (F6).

---

## 👥 Equipo de Desarrollo

Proyecto desarrollado con fines académicos por:
* Nicole
* Isabella
* William
