# 🚀 Proyecto: Imperial Maritime

## 📝 Descripción

**Imperial Maritime** es una aplicación de escritorio diseñada para la gestión integral de una compañía de cruceros. Su objetivo principal es proporcionar una plataforma robusta y fácil de usar para administrar diversos aspectos del negocio, incluyendo la gestión de administradores, cruceros, clientes y reservas. La aplicación permite a los administradores realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre estas entidades, garantizando un control eficiente y centralizado de la información.

El sistema está diseñado para facilitar la operativa diaria de una empresa de cruceros, desde la planificación de rutas y la asignación de recursos hasta la gestión de la clientela y la tramitación de reservas, incluyendo la aplicación de lógicas de precios y descuentos complejas a través de procedimientos almacenados en la base de datos.

---

## 🧰 Tecnologías utilizadas

*   **Java Development Kit (JDK) 21**
*   **Swing** (para la interfaz gráfica de usuario, con el apoyo de **WindowBuilder** en Eclipse)
*   **MySQL 8.0** (como sistema de gestión de bases de datos relacionales)
*   **JDBC** (Java Database Connectivity, para la conexión y manipulación de la base de datos MySQL)
*   **Mockito** (para la implementación de pruebas unitarias, aunque aún no está completamente integrado en la versión actual)

**Herramientas de Desarrollo:**

*   **Eclipse IDE** (entorno de desarrollo principal)
*   **MySQL Workbench** (para la gestión y administración de la base de datos)

---

## 📚 Dependencias

Este proyecto utiliza las siguientes librerías externas, las cuales deben ser añadidas al classpath del proyecto para su correcta compilación y ejecución:

*   **Conector JDBC MySQL**: `mysql-connector-java-8.0.15.jar` (o una versión compatible)
*   **JCalendar**: `jcalendar-1.4.jar` (para la selección de fechas en la interfaz gráfica)
*   **Mockito**: `mockito-core-x.x.x.jar` (para la creación de mocks en pruebas unitarias)

**Asegúrate de añadir los archivos `.jar` al proyecto siguiendo las instrucciones de tu IDE:**

*   **En Eclipse**: Haz clic derecho en el proyecto → `Properties` → `Java Build Path` → `Libraries` → `Add External JARs...`
*   **En IntelliJ IDEA**: `File` → `Project Structure` → `Modules` → `Dependencies` → `+` → `JARs or directories...`

---

## 🗄️ Base de datos

El proyecto incluye el script completo de la base de datos necesario para su funcionamiento: `imperialMaritime.sql`.

### ▶️ Cómo usarlo

Para configurar la base de datos, sigue estos pasos:

1.  Crea una nueva base de datos en tu servidor MySQL (por ejemplo, `imperialmaritime`).
2.  Importa el contenido del fichero `imperialMaritime.sql` en la base de datos recién creada.

**a. Desde la consola (terminal):**

```bash
mysql -u tu_usuario -p tu_base_de_datos < imperialMaritime.sql
```

*(Reemplaza `tu_usuario` con tu nombre de usuario de MySQL y `tu_base_de_datos` con el nombre de la base de datos que creaste.)*

**b. O usando una herramienta gráfica como MySQL Workbench:**

1.  Abre MySQL Workbench y conéctate a tu servidor MySQL.
2.  Selecciona la base de datos `imperialmaritime`.
3.  Ve a `File` → `Open SQL Script...` y selecciona `imperialMaritime.sql`.
4.  Ejecuta el script (icono del rayo).

---

## 📦 Instalación

### 📥 Clonar el repositorio

Para obtener una copia local del proyecto, clona el repositorio de GitHub:

```bash
git clone https://github.com/cj2339/RETO_FINAL.git
```

### 📁 Importar el proyecto en Eclipse

1.  Abre Eclipse IDE.
2.  Ve a `File` → `Import...`.
3.  Selecciona `General` → `Existing Projects into Workspace` y haz clic en `Next`.
4.  Haz clic en `Browse...` junto a `Select root directory:` y navega hasta la carpeta donde clonaste el repositorio (`RETO_FINAL`).
5.  Asegúrate de que el proyecto `RETO_FINAL` esté seleccionado y haz clic en `Finish`.

---

## ▶️ Ejecución

Una vez que el proyecto esté importado en Eclipse y las dependencias JAR estén configuradas, puedes ejecutar la aplicación:

1.  Abre el archivo `Main.java` ubicado en `src/main/Main.java`.
2.  Haz clic derecho en el editor de código y selecciona `Run As` → `Java Application`.

---

## 🧪 Tests

El proyecto está preparado para incluir pruebas unitarias utilizando la librería Mockito. Aunque la implementación de los tests no está completa en la versión actual, la estructura está lista para su adición.

**Para ejecutar tests (una vez implementados):**

*   Desde Eclipse: Haz clic derecho en el archivo de test o en el paquete de tests y selecciona `Run As` → `JUnit Test`.
*   Asegúrate de que los JAR de Mockito están correctamente añadidos al classpath del proyecto.
