# Proyecto de Reservas - Backend

Este proyecto es un sistema backend desarrollado con Spring Boot que gestiona las reservas. El proyecto utiliza Java 21 y PostgreSQL como base de datos.

## Requisitos

Antes de comenzar, asegúrate de tener instalados los siguientes requisitos en tu sistema:

1. **Java 21**: Necesitas Java Development Kit (JDK) 21 para compilar y ejecutar la aplicación. Puedes descargarlo desde [aquí](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).

2. **PostgreSQL**: La aplicación requiere PostgreSQL como base de datos. Puedes descargar e instalar PostgreSQL desde [aquí](https://www.postgresql.org/download/).

## Configuración de la Base de Datos

1. **Crear la Base de Datos**:

   - Abre el terminal o consola de comandos de PostgreSQL y ejecuta los siguientes comandos para crear la base de datos y un usuario con privilegios:

     ```sql
     CREATE DATABASE reservation_system;
     CREATE USER linktic WITH PASSWORD 'linktic';
     GRANT ALL PRIVILEGES ON DATABASE reservation_system TO linktic;
     ```

   - Si prefieres utilizar otras credenciales, asegúrate de actualizarlas en el archivo `application.yml` del proyecto.

2. **Modificar Credenciales en `application.yml`**:

   - Si usas credenciales diferentes, abre el archivo `src/main/resources/application.yml` y modifica las secciones correspondientes a la base de datos:

     ```yaml
     spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/reservation_system
         username: linktic
         password: linktic
     ```

## Instrucciones para ejecutar la aplicación

Sigue estos pasos para ejecutar el backend en tu entorno local:

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/JDRoche/reservation-system-api.git
   ```

2. Compilar y construir el proyecto:
   ```bash
   ./gradlew build
   ```
3. Iniciar la aplicación:

   ```bash
   ./gradlew bootRun
   ```

4. Verificar la aplicación:

- Una vez que la aplicación esté corriendo, puedes acceder a la documentación Swagger en:
  http://localhost:8090/swagger-ui/index.html

## Ejecución desde un editor de código

También puedes ejecutar la aplicación utilizando tu editor de código preferido:

1. Abrir el proyecto en el editor:

   - Usa tu editor de código preferido, como IntelliJ IDEA, Eclipse, VS Code, etc., para abrir la carpeta del proyecto.

2. Configurar el entorno:

   - Asegúrate de que el editor esté configurado para usar JDK 21.
   - Si tu editor soporta Spring Boot, deberías ver opciones para ejecutar la aplicación directamente desde la interfaz.

3. Ejecutar la aplicación:

   - Usa la opción "Run" o "Debug" de tu editor para iniciar la aplicación.
   - Alternativamente, puedes ejecutar los comandos de Gradle directamente desde la terminal integrada del editor.

## Creación de Datos Iniciales

- Abre el terminal o consola de comandos de PostgreSQL y ejecuta los siguientes comandos para crear los datos iniciales de la base de datos:

1. Roles:
   ```sql
   INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
   ```
2. Usuarios:
   ```sql
   INSERT INTO users (email, password, role_id) VALUES
   ('link@link.tic', '{bcrypt}password1', 1),
   ('admin@admin.com', '{bcrypt}adminpassword', 2);
   ```
3. Habitaciones:
   ```sql
   INSERT INTO rooms (room_number, type, price, status) VALUES
   ('101', 'SINGLE', 100.00, 'AVAILABLE'),
   ('102', 'SINGLE', 150.00, 'AVAILABLE'),
   ('103', 'SINGLE', 250.00, 'AVAILABLE'),
   ('201', 'DOUBLE', 300.00, 'AVAILABLE'),
   ('202', 'DOUBLE', 350.00, 'AVAILABLE'),
   ('203', 'DOUBLE', 450.00, 'AVAILABLE'),
   ('301', 'SUITE', 500.00, 'AVAILABLE'),
   ('302', 'SUITE', 550.00, 'AVAILABLE'),
   ('303', 'SUITE', 650.00, 'AVAILABLE'),
   ('401', 'DELUXE', 800.00, 'AVAILABLE'),
   ('402', 'DELUXE', 850.00, 'AVAILABLE'),
   ('403', 'DELUXE', 950.00, 'AVAILABLE');
   ```
