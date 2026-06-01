# plan-servicio

Microservicio de gestión de planes para la plataforma Karübag.

## Descripción
Gestiona los planes de suscripción disponibles para los clientes de Karübag, incluyendo planes básicos y corporativos.

## Tecnologías
- Java 21
- Spring Boot 3.5.14
- Spring Data JPA
- PostgreSQL (Neon)

## Puerto
`8082`

## Base de datos
`karubag_plan`

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /api/planes | Listar todos los planes |
| GET | /api/planes/activos | Listar planes activos |
| GET | /api/planes/{id} | Obtener plan por ID |
| POST | /api/planes | Crear plan |
| PUT | /api/planes/{id} | Actualizar plan |
| DELETE | /api/planes/{id} | Eliminar plan |

## Cómo ejecutar
```bash
./mvnw spring-boot:run
```

## Variables de entorno
```
spring.datasource.url=jdbc:postgresql://<host>/karubag_plan
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
```