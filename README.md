# Appointment System

Sistema de turnos por consola escrito en Java puro (sin frameworks). Permite gestionar clientes, profesionales, servicios ofrecidos y turnos, persistiendo todo en una base de datos MySQL a través de JDBC.

Es un proyecto de práctica orientado a reforzar diseño en capas (UI → Service → Repository), manejo de excepciones de dominio y testing con JUnit 5 + Mockito.

## Funcionalidades

- **Clientes**: alta, baja, modificación, listado y búsqueda por ID.
- **Profesionales**: alta, baja, modificación, listado y búsqueda por ID (incluye especialidad).
- **Servicios ofrecidos**: alta, baja, modificación, listado y búsqueda por ID (nombre y precio).
- **Turnos**: creación con validaciones de negocio, actualización, cancelación, finalización, listado y búsqueda por ID.
  - No permite agendar un turno en el pasado.
  - No permite superponer horarios para el mismo profesional.
  - No permite superponer horarios para el mismo cliente.
  - Estados: `SCHEDULED`, `COMPLETED`, `CANCELLED`.

## Stack técnico

- Java 25
- Maven
- JDBC (`mysql-connector-j`) contra MySQL
- JUnit 5 (Jupiter) + Mockito para tests unitarios

## Estructura del proyecto

```
src/main/java
├── Main.java                 # punto de entrada, arma las dependencias
├── database/                 # conexión JDBC
├── model/                    # entidades de dominio (Client, Professional, OfferedService, Appointment...)
├── exception/                # excepciones de dominio (EntityNotFoundException, ScheduleConflictException...)
├── repository/                # acceso a datos (SQL crudo vía JDBC)
├── queries/                   # SQL de turnos centralizado
├── service/                   # lógica de negocio
└── ui/                        # menú por consola (App, InputUtils)

src/test/java/service          # tests unitarios de los servicios (Mockito)
```

## Requisitos previos

- JDK 25
- Maven
- Un servidor MySQL corriendo localmente (o accesible por red)

## Configuración de la base de datos

La conexión está definida en [`DatabaseConnection.java`](src/main/java/database/DatabaseConnection.java):

- URL: `jdbc:mysql://localhost:3306/appointment_system_db`
- Usuario: `root`
- Contraseña: se lee de la variable de entorno `DB_PASSWORD`

Antes de correr la app, definí la variable de entorno con tu contraseña de MySQL:

```bash
export DB_PASSWORD=tu_password
```

Y creá el esquema (los nombres de tabla/columna deben coincidir exactamente, ya que las queries usan SQL crudo):

```sql
CREATE DATABASE IF NOT EXISTS appointment_system_db;
USE appointment_system_db;

CREATE TABLE clients (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    lastname  VARCHAR(100) NOT NULL,
    email     VARCHAR(150) NOT NULL
);

CREATE TABLE professionals (
    professional_id INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    lastname        VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL,
    speciality      VARCHAR(100) NOT NULL
);

CREATE TABLE offered_services (
    offered_service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name        VARCHAR(100) NOT NULL,
    price                DECIMAL(10,2) NOT NULL
);

CREATE TABLE status (
    status_id         INT PRIMARY KEY,
    appointment_status VARCHAR(20) NOT NULL
);

INSERT INTO status (status_id, appointment_status) VALUES
    (1, 'SCHEDULED'),
    (2, 'COMPLETED'),
    (3, 'CANCELLED');

CREATE TABLE appointments (
    appointment_id      INT AUTO_INCREMENT PRIMARY KEY,
    professional_id     INT NOT NULL,
    client_id           INT NOT NULL,
    offered_service_id  INT NOT NULL,
    datetime             DATETIME NOT NULL,
    status_id            INT NOT NULL DEFAULT 1,
    FOREIGN KEY (professional_id) REFERENCES professionals(professional_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id),
    FOREIGN KEY (offered_service_id) REFERENCES offered_services(offered_service_id),
    FOREIGN KEY (status_id) REFERENCES status(status_id)
);
```

> Nota: el proyecto también incluye la dependencia `sqlite-jdbc` y un archivo `appointment-system.db`, pero la conexión activa (`DatabaseConnection`) apunta a MySQL; el soporte SQLite no está conectado actualmente.

## Cómo correr la aplicación

Este proyecto no tiene configurado un plugin de ejecución en el `pom.xml`, así que la forma más simple de correrlo es desde el IDE:

1. Abrí el proyecto en IntelliJ IDEA.
2. Configurá la variable de entorno `DB_PASSWORD` en la configuración de ejecución.
3. Ejecutá `Main.java`.

La app te va a mostrar un menú interactivo por consola:

```
===== APPOINTMENT SYSTEM =====
1. Clients
2. Professionals
3. Offered Services
4. Appointments
5. Exit
```

Para crear un turno se te pide fecha y hora en formato `dd/MM/yyyy HH:mm`.

## Tests

El proyecto usa JUnit 5 y Mockito para testear la capa de servicios de forma aislada (sin tocar la base de datos real).

```bash
mvn test
```

## Posibles próximos pasos

- Externalizar la configuración de conexión (URL, usuario) a un `application.properties` o variables de entorno.
- Agregar un script de migración (`schema.sql`) versionado en el repo.
- Sumar un plugin `exec-maven-plugin` para poder correr la app con `mvn exec:java`.
